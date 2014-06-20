/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.parser.html;

import de.tynne.htmltosax.parser.dfa.Action;
import de.tynne.htmltosax.parser.dfa.DFA;
import de.tynne.htmltosax.parser.dfa.Transition;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A DFA that accepts HTML code and does SAX callbacks.
 * @author fury
 */
public class HtmlDFA extends DFA {

    private final static Logger LOGGER = LoggerFactory.getLogger(HtmlDFA.class.getName());
	    
	private final static String DEFAULT_URI = "";
	private final static String DEFAULT_LOCAL_NAME = "";
    
    private ContentHandler contentHandler;
    private ErrorHandler errorHandler;
	    
    public HtmlDFA(Reader r) {
        super(r, HtmlState.INIT);
		init(createTransitions().toArray(new Transition[0]));
		try {
			HtmlEntities.getInstance();
		} catch (IOException ex) {
			// throw an error at init, not later
			throw new Error("Internal Error", ex);
		}
    }
    
    public void setContentHandler(org.xml.sax.ContentHandler handler) {
        this.contentHandler = handler;
    }

    public void setErrorHandler(org.xml.sax.ErrorHandler handler) {
        this.errorHandler = handler;
    }
    
    private final static String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String DIGITS = "0123456789";
    private final static String SPACE = " \t\r\n";

    private Locator createLocator() {
        return new Locator() {
            @Override
            public String getPublicId() {
                return null;
            }

            @Override
            public String getSystemId() {
                return null;
            }

            @Override
            public int getLineNumber() {
                return line();
            }

            @Override
            public int getColumnNumber() {
                return column();
            }
        };
    }
	
	/** A marked index in the current string builder. */
	private int lastMarkPosition;
	/** Stored element name we're currently in. */
	private String currentElementName;	
	/** Stored attribute name we're currently in. */
	private String currentAttributeName;
	/** Map of attributes read until now. */
	private Map<String, String> currentAttributes = new LinkedHashMap<String, String>();
	
	/** Emits a generic SAX error. */
	private final static Action<HtmlDFA> EmitErrorAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			int len = in.length();
			final int limit = 16;
			if (len > limit)
				len = limit;
			
			final String excerpt = in.substring(in.length() - len, in.length());
			
			final String msg = "General parsing error at position "+dfa.pos()+", state: "+transition.from()+", context before: "+excerpt;
			if (dfa.errorHandler != null)
				dfa.errorHandler.error(new SAXParseException(msg, dfa.createLocator()));
		}
	};
	/** Stores the element name that is in the buffer.
	 * @see #currentElementName
	 * @see #toElementName(de.tynne.htmltosax.parser.html.HtmlDFA, java.lang.CharSequence) 
	 */
	private final static Action<HtmlDFA> ElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			try {
				dfa.currentElementName = toElementName(dfa, in);
			}
			catch (SAXParseException spe) {
				if (dfa.errorHandler != null)
					dfa.errorHandler.error(spe);
				return;
			}
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Seen element named {}", dfa.currentElementName);
			in.setLength(0);
		}
	};
	/** Stores the attribute name that is in the buffer. The attribute value is first assigned the same String as
	 * the attribute name.
	 * @see #currentAttributeName
	 * @see #toAttributeName(de.tynne.htmltosax.parser.html.HtmlDFA, java.lang.CharSequence) 
	 */
	private final static Action<HtmlDFA> AttributeNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			try {
				dfa.currentAttributeName = toAttributeName(dfa, in);
			}
			catch (SAXParseException spe) {
				if (dfa.errorHandler != null)
					dfa.errorHandler.error(spe);
				return;
			}
			
			// if no value comes, the name is the value
			dfa.currentAttributes.put(dfa.currentAttributeName, dfa.currentAttributeName);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Seen attribute named {}", dfa.currentAttributeName);
			in.setLength(0);
		}
	};
	/** Stores the attribute value that is in the buffer.
	 * @see #toAttributeValue(java.lang.CharSequence) 
	 */
	private final static Action<HtmlDFA> AttributeValueAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			String v = toAttributeValue(in);
			dfa.currentAttributes.put(dfa.currentAttributeName, v);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Seen attribute named {} and valued {}", new Object[]{dfa.currentAttributeName,v});
			in.setLength(0);
		}
	};
	/** Clears the internal state to be prepared for a new element. */
	private final static Action<HtmlDFA> BeforeElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			dfa.currentAttributes.clear();
			dfa.currentAttributeName = null;
			dfa.currentElementName = null;
		}
	};
	/** Emits a start element to the SAX listener.
	 * @see ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes) 
	 */
	private final static Action<HtmlDFA> HandlerStartElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			ContentHandler handler = dfa.contentHandler;
			
			if (dfa.currentElementName == null) {
				throw new IllegalStateException("Element name expected at "+dfa.pos()+", but not given");
			}
			
			if (handler != null) {
				// TODO add the real attrs
				AttributesImpl attrs = new AttributesImpl();
				for (Map.Entry<String,String> entry : dfa.currentAttributes.entrySet()) {
					attrs.addAttribute(DEFAULT_URI, DEFAULT_LOCAL_NAME, entry.getKey(), "", entry.getValue());
				}
				handler.startElement(DEFAULT_URI, DEFAULT_LOCAL_NAME, dfa.currentElementName, attrs);
			}
			in.setLength(0);
			
			if (dfa.currentElementName.equalsIgnoreCase("script")) {
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("Switching to script mode");
				dfa.forceTo(HtmlState.WAIT_SCRIPT_END);
			}
		}
	};
	/** Emits an end element to the SAX listener.
	 * @see ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String) 
	 */
	private final static Action<HtmlDFA> HandlerEndElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			ContentHandler handler = dfa.contentHandler;
			String name = dfa.currentElementName;
			if (name == null) {
				//
				throw new IllegalStateException("element name is empty");
			}
			if (handler != null) {
				handler.endElement(DEFAULT_URI, DEFAULT_LOCAL_NAME, name);
			}
			in.setLength(0);
		}
	};
	/** Emits a SCRIPT end element to the SAX listener.
	 * @see ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String) 
	 */
	private final static Action<HtmlDFA> HandlerEndScriptElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			ContentHandler handler = dfa.contentHandler;
			String name = "script";
			if (handler != null) {
				handler.endElement(DEFAULT_URI, DEFAULT_LOCAL_NAME, name);
			}
			in.setLength(0);
		}
	};
	/** Marks the current position in the buffer for later reference.
	 * @see ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String) 
	 */
	private final static Action<HtmlDFA> MarkPositionAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			dfa.lastMarkPosition = in.length() - 1;
		}
	};	
	@Deprecated
	private final static Action<HtmlDFA> ClearElementNameAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			dfa.currentElementName = null;
			in.setLength(0);
		}
	};
	
	/** Emits a character array (CDATA) node to the SAX listener.
	 * @see ContentHandler#characters(char[], int, int) 
	 */
	private final static Action<HtmlDFA> HandlerCharactersAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			if (in.length() == 0)
				return; // no call if empty String
			String txt = toText(in);
			ContentHandler handler = ((HtmlDFA)dfa).contentHandler;
			// TODO could recycle character arrays here
			if (handler != null) {
				char carr[] = txt.toCharArray();
				handler.characters(carr, 0, carr.length);
			}
			in.setLength(0);
		}
	};
	/** Does nothing to the buffer, mistaken entity reference, for example
	 * <code>'hello=42&abc=12'</code>.
	 */
	private final static Action<HtmlDFA> AmpAndNoEntityReferenceAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Mistaken entity ref, ignoring", in);
		}
	};
	/** Replaces an character entity reference with its character point in the buffer.
	 */
	private final static Action<HtmlDFA> EntityReferenceAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			String all = in.toString();
			String entity = all.substring(dfa.lastMarkPosition);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Entity reference: {}", entity);
			Integer codePoint = HtmlEntities.getInstance().get(entity, true);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("Resolved code point: {}", codePoint);
			if (codePoint != null) {
				// remove entity from string builder
				in.setLength(in.length() - entity.length());
				in.append((char)codePoint.intValue());
			} else {
				LOGGER.warn("Unknown entity reference {}", entity);
				if (dfa.errorHandler != null)
					dfa.errorHandler.warning(new SAXParseException("Unknown entity: "+entity, dfa.createLocator()));
			}
		}
	};
	/** Replaces a numeric character reference with its character point in the buffer.
	 */
	private final static Action<HtmlDFA> NumericReferenceAction = new Action<HtmlDFA>() {
		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			String all = in.toString();
			String numeric = all.substring(dfa.lastMarkPosition);
			LOGGER.debug("Numeric reference: {}", numeric);
			try {
				int intNumeric = Integer.parseInt(numeric.substring(2, numeric.length() - 1));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Parsed number: {}", intNumeric);
				}
				// remove entity from string builder
				in.setLength(in.length() - numeric.length());
				in.append((char) intNumeric);
			} catch (NumberFormatException e) {
				LOGGER.warn("Malformatted numeric character reference {}", numeric);
				if (dfa.errorHandler != null) {
					dfa.errorHandler.warning(new SAXParseException("Malformatted numeric character reference " + numeric, dfa.createLocator()));
				}
			}
		}
	};
	/** Replaces a hexadecimal character reference with its character point in the buffer.
	 */
	private final static Action<HtmlDFA> HexReferenceAction = new Action<HtmlDFA>() {

		@Override
		public void fire(HtmlDFA dfa, Transition transition, StringBuilder in) throws Exception {
			String all = in.toString();
			String numeric = all.substring(dfa.lastMarkPosition);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Hex reference: {}", numeric);
			}
			String hexPart = numeric.substring(3, numeric.length() - 1);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Hex part: {}", hexPart);
			}
			try {
				int intNumeric = Integer.parseInt(hexPart, 16);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Parsed number: {}", intNumeric);
				}
				// remove entity from string builder
				in.setLength(in.length() - numeric.length());
				in.append((char) intNumeric);
			} catch (NumberFormatException e) {
				LOGGER.warn("Malformatted hex character reference {}", numeric);
				if (dfa.errorHandler != null) {
					dfa.errorHandler.warning(new SAXParseException("Malformatted hex character reference " + numeric, dfa.createLocator()));
				}
			}
		}
	};
	
	private static List<Transition<HtmlDFA>> createAmpTransitions(HtmlState ampEntry, HtmlState ampEntity, HtmlState ampNumeric,HtmlState ampHex,HtmlState ampExit) {
		List<Transition<HtmlDFA>> result;
		
		result = Arrays.asList(
			new Transition<HtmlDFA>(ampEntry, CHARS, ampEntity),
			new Transition<HtmlDFA>(ampEntry, "#", ampNumeric),
			new Transition<HtmlDFA>(ampEntry, null, ampExit, EmitErrorAction),		
			
			new Transition<HtmlDFA>(ampEntity, ";", ampExit, EntityReferenceAction),		
			new Transition<HtmlDFA>(ampEntity, CHARS+DIGITS, ampEntity),
			new Transition<HtmlDFA>(ampEntity, null, ampExit, AmpAndNoEntityReferenceAction),
			
			new Transition<HtmlDFA>(ampNumeric, ";", ampExit, NumericReferenceAction),
			new Transition<HtmlDFA>(ampNumeric, "x", ampHex),
			new Transition<HtmlDFA>(ampNumeric, DIGITS, ampNumeric),
			new Transition<HtmlDFA>(ampNumeric, null, ampExit, EmitErrorAction),
			
			new Transition<HtmlDFA>(ampHex, DIGITS+"abcdefABCDEF", HtmlState.AMP_HEX_REF),
			new Transition<HtmlDFA>(ampHex, null, ampExit, EmitErrorAction),
			new Transition<HtmlDFA>(ampHex, ";", ampExit, HexReferenceAction)
		);
		return result;
	}
	
	private static List<Transition<HtmlDFA>> createTransitions() {
		List<Transition<HtmlDFA>> result = new ArrayList<Transition<HtmlDFA>>();
		result.addAll(fixedTransitions);
		// this keeps us from copying & pasting
		result.addAll(createAmpTransitions(HtmlState.AMP, HtmlState.AMP_ENTITY_REF, HtmlState.AMP_NUMERIC_REF, HtmlState.AMP_HEX_REF, HtmlState.INIT));
		// this keeps us from copying & pasting
		result.addAll(createAmpTransitions(HtmlState.SQ_AMP, HtmlState.SQ_AMP_ENTITY_REF, HtmlState.SQ_AMP_NUMERIC_REF, HtmlState.SQ_AMP_HEX_REF, HtmlState.SQ_ATTRIBUTE_VALUE));
		// this keeps us from copying & pasting
		result.addAll(createAmpTransitions(HtmlState.DQ_AMP, HtmlState.DQ_AMP_ENTITY_REF, HtmlState.DQ_AMP_NUMERIC_REF, HtmlState.DQ_AMP_HEX_REF, HtmlState.DQ_ATTRIBUTE_VALUE));
		return result;
	}
	
    private final static List<Transition<HtmlDFA>> fixedTransitions = Arrays.asList(
        new Transition<HtmlDFA>(HtmlState.INIT, "<", HtmlState.LT, Transition.DeleteLastAction, HandlerCharactersAction),
        new Transition<HtmlDFA>(HtmlState.INIT, "&", HtmlState.AMP, MarkPositionAction),
        new Transition<HtmlDFA>(HtmlState.INIT, "", HtmlState.INIT, HandlerCharactersAction),
		        
        new Transition<HtmlDFA>(HtmlState.LT, ">", HtmlState.INIT),
        new Transition<HtmlDFA>(HtmlState.LT, "/", HtmlState.CLOSE_TAG, Transition.ClearAction),
        new Transition<HtmlDFA>(HtmlState.LT, CHARS, HtmlState.ELEMENT_NAME, BeforeElementNameAction),
		// the better way would be handling comments correctly <!-- blah -->
        new Transition<HtmlDFA>(HtmlState.LT, "!", HtmlState.WAIT_GT),
        new Transition<HtmlDFA>(HtmlState.LT, SPACE, HtmlState.INIT, EmitErrorAction),
		
		// in case of an error this will drop everyting until the next ">"
		new Transition<HtmlDFA>(HtmlState.WAIT_GT, null, HtmlState.WAIT_GT, Transition.DeleteLastAction),
		new Transition<HtmlDFA>(HtmlState.WAIT_GT, ">", HtmlState.INIT),

        new Transition<HtmlDFA>(HtmlState.ELEMENT_NAME, "/", HtmlState.AFTER_ELEMENT_NAME_SLASH, Transition.DeleteLastAction, ElementNameAction),
        new Transition<HtmlDFA>(HtmlState.ELEMENT_NAME, ">", HtmlState.INIT, Transition.DeleteLastAction, ElementNameAction, HandlerStartElementNameAction, ClearElementNameAction),
        new Transition<HtmlDFA>(HtmlState.ELEMENT_NAME, SPACE, HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction, ElementNameAction),
        
        new Transition<HtmlDFA>(HtmlState.AFTER_ELEMENT_NAME, ">", HtmlState.INIT, Transition.DeleteLastAction, HandlerStartElementNameAction, ClearElementNameAction), // TODO with attrs
        new Transition<HtmlDFA>(HtmlState.AFTER_ELEMENT_NAME, "/", HtmlState.AFTER_ELEMENT_NAME_SLASH, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.AFTER_ELEMENT_NAME, CHARS, HtmlState.ATTRIBUTE_NAME),
        new Transition<HtmlDFA>(HtmlState.AFTER_ELEMENT_NAME, SPACE, HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.AFTER_ELEMENT_NAME_SLASH, ">", HtmlState.INIT, HandlerStartElementNameAction, HandlerEndElementNameAction, ClearElementNameAction),
        
        new Transition<HtmlDFA>(HtmlState.ATTRIBUTE_NAME, ">", HtmlState.INIT, Transition.DeleteLastAction, AttributeNameAction, HandlerStartElementNameAction, ClearElementNameAction),
        new Transition<HtmlDFA>(HtmlState.ATTRIBUTE_NAME, "=", HtmlState.BEFORE_ATTRIBUTE_VALUE, Transition.DeleteLastAction, AttributeNameAction, Transition.ClearAction),
        new Transition<HtmlDFA>(HtmlState.ATTRIBUTE_NAME, SPACE, HtmlState.AFTER_ATTRIBUTE_NAME, Transition.DeleteLastAction, AttributeNameAction, Transition.ClearAction),
        
        new Transition<HtmlDFA>(HtmlState.AFTER_ATTRIBUTE_NAME, "=", HtmlState.BEFORE_ATTRIBUTE_VALUE, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.AFTER_ATTRIBUTE_NAME, SPACE, HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.AFTER_ATTRIBUTE_NAME, CHARS, HtmlState.ATTRIBUTE_NAME),        
        new Transition<HtmlDFA>(HtmlState.AFTER_ATTRIBUTE_NAME, ">", HtmlState.INIT, Transition.DeleteLastAction, HandlerStartElementNameAction, ClearElementNameAction),
        new Transition<HtmlDFA>(HtmlState.BEFORE_ATTRIBUTE_VALUE, CHARS, HtmlState.ATTRIBUTE_VALUE),        
        new Transition<HtmlDFA>(HtmlState.BEFORE_ATTRIBUTE_VALUE, SPACE, HtmlState.BEFORE_ATTRIBUTE_VALUE, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.BEFORE_ATTRIBUTE_VALUE, "'", HtmlState.SQ_ATTRIBUTE_VALUE, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.BEFORE_ATTRIBUTE_VALUE, "\"", HtmlState.DQ_ATTRIBUTE_VALUE, Transition.DeleteLastAction),
        new Transition<HtmlDFA>(HtmlState.BEFORE_ATTRIBUTE_VALUE, ">", HtmlState.INIT, Transition.DeleteLastAction, HandlerStartElementNameAction, ClearElementNameAction),
		
        new Transition<HtmlDFA>(HtmlState.ATTRIBUTE_VALUE, SPACE, HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction, AttributeValueAction, Transition.ClearAction),
        new Transition<HtmlDFA>(HtmlState.ATTRIBUTE_VALUE, ">", HtmlState.INIT, Transition.DeleteLastAction, AttributeValueAction, HandlerStartElementNameAction, Transition.ClearAction),
        
        new Transition<HtmlDFA>(HtmlState.SQ_ATTRIBUTE_VALUE, "'", HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction, AttributeValueAction, Transition.ClearAction),
        new Transition<HtmlDFA>(HtmlState.SQ_ATTRIBUTE_VALUE, "&", HtmlState.SQ_AMP, MarkPositionAction),
        new Transition<HtmlDFA>(HtmlState.SQ_ATTRIBUTE_VALUE, "\n\r", HtmlState.SQ_ATTRIBUTE_VALUE, EmitErrorAction, Transition.DeleteLastAction),
		
        new Transition<HtmlDFA>(HtmlState.DQ_ATTRIBUTE_VALUE, "\"", HtmlState.AFTER_ELEMENT_NAME, Transition.DeleteLastAction, AttributeValueAction, Transition.ClearAction),
        new Transition<HtmlDFA>(HtmlState.DQ_ATTRIBUTE_VALUE, "&", HtmlState.DQ_AMP, MarkPositionAction),
        new Transition<HtmlDFA>(HtmlState.DQ_ATTRIBUTE_VALUE, "\n\r", HtmlState.DQ_ATTRIBUTE_VALUE, EmitErrorAction, Transition.DeleteLastAction),
                
        new Transition<HtmlDFA>(HtmlState.CLOSE_TAG, ">", HtmlState.INIT, Transition.DeleteLastAction, ElementNameAction, HandlerEndElementNameAction, ClearElementNameAction),
        new Transition<HtmlDFA>(HtmlState.CLOSE_TAG, CHARS+DIGITS, HtmlState.CLOSE_TAG),
        new Transition<HtmlDFA>(HtmlState.CLOSE_TAG, null, HtmlState.CLOSE_TAG, Transition.DeleteLastAction),
		
        new Transition<HtmlDFA>(HtmlState.WAIT_SCRIPT_END, "<", HtmlState.WSE_LT),
        new Transition<HtmlDFA>(HtmlState.WSE_LT, "/", HtmlState.WSE_LTS),		
        new Transition<HtmlDFA>(HtmlState.WSE_LT, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS, "sS", HtmlState.WSE_LTS_S),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_S, "cC", HtmlState.WSE_LTS_SC),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_S, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SC, "rR", HtmlState.WSE_LTS_SCR),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SC, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCR, "iI", HtmlState.WSE_LTS_SCRI),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCR, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRI, "pP", HtmlState.WSE_LTS_SCRIP),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRI, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRIP, "tT", HtmlState.WSE_LTS_SCRIPT),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRIP, null, HtmlState.WAIT_SCRIPT_END),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRIPT, ">", HtmlState.INIT, 
			Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction, Transition.DeleteLastAction,
			HandlerCharactersAction, HandlerEndScriptElementNameAction),
        new Transition<HtmlDFA>(HtmlState.WSE_LTS_SCRIPT, null, HtmlState.WAIT_SCRIPT_END, Transition.DeleteLastAction)
	);
	
	private static String toElementName(HtmlDFA dfa, CharSequence in) throws SAXParseException {
		if (in.length() == 0)
			throw new SAXParseException("Element name is empty", dfa.createLocator());
                String str = in.toString();
		if (str.contains(" "))
			throw new SAXParseException("Element name with space at in \""+in+"\"", dfa.createLocator());
		return str.toLowerCase();
	}
	
	private static String toAttributeName(HtmlDFA dfa, CharSequence in) throws SAXParseException {
		if (in.length() == 0)
			throw new SAXParseException("Attr name is empty", dfa.createLocator());
                
                String str = in.toString();
		if (str.contains(" "))
			throw new SAXParseException("Attr name with space at in \""+in+"\"", dfa.createLocator());
		return str.toLowerCase();
	}
	
	private static String toAttributeValue(CharSequence in) {
		return in.toString();
	}
	
	private static String toText(CharSequence in) {
		return in.toString();
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.dom;

import java.io.IOException;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses a HTML document and tries to build a DOM tree from it.
 * @see Document
 * @author fury
 */
public class DomParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(DomParser.class);
	
    /** Builder for DOM elements. */
	private final DocumentBuilder builder;
    
    /** The document root. */
	private Document document;
    
    /** Whether document writing is finished or not. */
	private boolean closed;
    
    /** The current node being processed. */
	private Node current;
    
    /** The SAX callback handler being used. */
	private final MyDefaultHandler defaultHandler;
    
    /** The stack for storing open HTML elements. */
	private final Stack<Node> stack;
	
	private DomParser() throws ParserConfigurationException {
		LOGGER.debug("c'tor");
		
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		closed = false;
		defaultHandler = new MyDefaultHandler();
		stack = new Stack();				
	}

    /** Parses a HTML document from the given input source.
     * @param is the input source with HTML data.
     * @return a DOM document root element.
     */
	public static Document parse(InputSource is) throws ParserConfigurationException, SAXException, IOException {
		LOGGER.debug("start");
		
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		DomParser domFactory = new DomParser();

		LOGGER.debug("calling parse");
		parser.parse(is, domFactory.defaultHandler);
		LOGGER.debug("called parse");
		
		return domFactory.document;
	}
	
	private void preWrite() {
		if (closed) {
			throw new IllegalStateException("Document already closed");
		}
	}
	
	private void popTo(String uri, String localName, String qName) {
		LOGGER.debug("popping to {} {} {}", new Object[] {uri, localName, qName});
		
		int idx = -1;
		for (int i=stack.size() - 1; i >= 0; i--) {
			Node n = stack.elementAt(i);
			if (n.getNodeName().equals(qName)) {
				LOGGER.debug("found element at idx {}", i);
				idx = i;
				break;
			}
		}
		
		if (idx != -1) {
			for (int i=stack.size() - 1; i >= idx; i--) {
				stack.removeElementAt(i);
			}
			current = stack.peek();
		}
	}
	
	private void setAttrs(Element e, Attributes attrs) {
		for (int i=0; i < attrs.getLength(); i++) {
			String name = attrs.getQName(i);
			String val = attrs.getValue(i);
			
			if (name.contains(":")) {
				LOGGER.warn("Ignoring name containing colon {}", name);
				continue;
			}
			e.setAttribute(name, val);
		}
	}
	
    /** SAX default handler callback for the parser. */
	private class MyDefaultHandler extends DefaultHandler {

		@Override
		public void endDocument() throws SAXException {
			closed = true;
			stack.clear();
			current = null;
		}

		@Override
		public void startDocument() throws SAXException {
			LOGGER.debug("startDocument");
			document = builder.newDocument();
			current = document;
			stack.push(document);
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			LOGGER.debug("characters");
			preWrite();
			
			if (current.getNodeType() == Node.DOCUMENT_NODE) {
				LOGGER.warn("Ignoring top level text {}", new String(ch, start, length));
				return;
			}
			
			Text text = document.createTextNode(new String(ch, start, length));
			current.appendChild(text);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			LOGGER.debug("endElement {}", qName);
			popTo(uri, localName, qName);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			LOGGER.debug("startElement {}", qName);
			preWrite();
			Element e = document.createElement(qName);

			if (attributes != null)
				setAttrs(e, attributes);

			current.appendChild(e);
			
			current = e;
			stack.push(e);
		}
	}
}

/*
Html2Sax - A HTML parser that creates SAX API calls
Copyright (C) 2008  Stephan Fuhrmann

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
package de.sfuhrm.htmltosax;

import de.sfuhrm.htmltosax.parser.html.HtmlDFA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * A HTML parser that passed its parsed stuff to a ContentHandler.
 * @author Stephan Fuhrmann
 */
class HtmlToSaxReader implements XMLReader {

        private final static Logger LOGGER = LoggerFactory.getLogger(HtmlToSaxReader.class);

	/** The content handler to use. */
	private ContentHandler contentHandler;
	/** The error handler to use. */
	private ErrorHandler errorHandler;

	/** Gets the content handler in use. */
	@Override
	public ContentHandler getContentHandler() {
		return contentHandler;
	}

	/** No DTD handler is supported. 
	 * @return returns <code>null</code>.
	 */
	@Override
	public DTDHandler getDTDHandler() {
		return null;
	}

	/** No entity resolver is supported. 
	 * @return returns <code>null</code>.
	 */
	@Override
	public EntityResolver getEntityResolver() {
		return null;
	}

	/** Returns the current error handler.
	 */
	@Override
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/** No features are supported at the moment.
	 * @return returns <code>false</code>.
	 */
	@Override
	public boolean getFeature(String name) throws SAXNotRecognizedException,
	SAXNotSupportedException {
		return false;
	}

	/** No properties are supported at the moment.
	 * @return returns <code>false</code>.
	 */
	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException,
	SAXNotSupportedException {
		// unsupported
		return null;
	}

	/** Runs the parser on the given input source. There's a fallback mechanism
	 * to decode charset encodings correctly.
	 * First the call will try th get the {@link InputSource#getCharacterStream() character stream}.
	 * If this fails it will get the {@link InputSource#getByteStream() byte stream}
	 * and the {@link InputSource#getEncoding() encoding}. If all fails, the
	 * {@link InputSource#getByteStream() byte stream} without encoding is used.
	 * @param input the input source.
	 */
	@Override
	public void parse(InputSource input) throws IOException, SAXException {
		HtmlDFA htmlDfa = null;
		Reader r = input.getCharacterStream();
		if (htmlDfa == null && r != null) {
			// best choice
			LOGGER.debug("Got reader for InputSource");
			BufferedReader br=new BufferedReader(r);
			htmlDfa = new HtmlDFA(br);
		} 
		
		if (htmlDfa == null && input.getEncoding() != null) {
			InputStream is = input.getByteStream();
			
			if (is != null) {
				LOGGER.debug("Got InputStream with encoding {} for InputSource", input.getEncoding());
				InputStreamReader isr = new InputStreamReader(is, input.getEncoding());
				BufferedReader br = new BufferedReader(isr);
				htmlDfa = new HtmlDFA(br);
			}
		}
                
		if (htmlDfa == null) {
			InputStream is = input.getByteStream();
			
			if (is != null) {
				LOGGER.warn("Got InputStream with default encoding ISO-8859-1 for InputSource");
				InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
				BufferedReader br = new BufferedReader(isr);
				htmlDfa = new HtmlDFA(br);
			}
		}
                
		// TODO guess charset? 
		
		if (htmlDfa == null)
			throw new SAXException("Failed to init the HtmlToSax driver");
		
		htmlDfa.setContentHandler(getContentHandler());
		htmlDfa.setErrorHandler(getErrorHandler());
		getContentHandler().startDocument();
		
		htmlDfa.parse();
		
		getContentHandler().endDocument();
		
		
		if (htmlDfa == null)
			throw new SAXException("Could not initialize input");
	}

	/** Parses the given system id. 
	 */
	@Override
	public void parse(String systemId) throws IOException, SAXException {
		// i hate luxory calls
		parse(new InputSource(systemId));
	}

	/** Sets the content handler to use.
	 */
	@Override
	public void setContentHandler(ContentHandler handler) {
		contentHandler = handler;
	}

	/** DTD handlers are unsupported.
	 */
	@Override
	public void setDTDHandler(DTDHandler handler) {
		// no dtd handler allowed
	}

	/** Entity resolvers are unsupported.
	 */
	@Override
	public void setEntityResolver(EntityResolver resolver) {
		// no resolver allowed
	}

	/** Sets the error handler to use.
	 */
	@Override
	public void setErrorHandler(ErrorHandler handler) {
		errorHandler = handler;
	}

	/** Features are unsupported.
	 */
	@Override
	public void setFeature(String name, boolean value)
	throws SAXNotRecognizedException, SAXNotSupportedException {
		// no features supported
	}

	/** Properties are unsupported.
	 */
	@Override
	public void setProperty(String name, Object value)
	throws SAXNotRecognizedException, SAXNotSupportedException {
		// no properties supported
	}

}

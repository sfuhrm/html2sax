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
package de.tynne.htmltosax;

import javax.xml.parsers.SAXParser;

import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * The SAX parser adapter for the JavaCC generated parser.
 * Will usually be instantiated by the {@link SAXParserFactory}.
 * @see HtmlToSaxParserFactory
 * @author Stephan Fuhrmann
 */
public class HtmlToSaxParser extends SAXParser {

	/** The XML reader use.
	 * Is a {@link HtmlToSaxReader}.
	 * */
	private HtmlToSaxReader xmlReader;
	
	/** Creates a new instance. */
	HtmlToSaxParser() {
		xmlReader = new HtmlToSaxReader();
	}
	
	/** The call is deprecated and unimplemented.
	 * @deprecated Parser interface is unsupported.
	 * @throws SAXException all the time.
	 */
	@Override
	public Parser getParser() throws SAXException {
		throw new SAXException("Unimplemented");
	}

	/** Properties are unsupported. 
	 * @return <code>null</code>
	 */
	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException,
			SAXNotSupportedException {
		return null;
	}

	/** Get the XML reader for this parser.
	 */
	@Override
	public XMLReader getXMLReader() throws SAXException {
		return xmlReader;
	}

	/** The parser is not namespace aware. 
	 * @return <code>false</code>	 
	 */
	@Override
	public boolean isNamespaceAware() {
		return false;
	}

	/** The parser is not validating. 
	 * @return <code>false</code>
	 */
	@Override
	public boolean isValidating() {
		return false;
	}

	/** Properties are unsupported. Will do nothing.
	 */
	@Override
	public void setProperty(String name, Object value)
			throws SAXNotRecognizedException, SAXNotSupportedException {
	}

}

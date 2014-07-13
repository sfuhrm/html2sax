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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * The sax parser factory implementation for the html2sax parser.
 * The parser will usually not be initiated directly, but using the
 * {@link SAXParserFactory}.
 * The following example shows the initialization:
 * <br>
 * <pre>
 * {@code 
 * 		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
 *		SAXParser parser = factory.newSAXParser();
 * 		YourCallback s = new YourCallback();
 *		parser.parse(new InputSource(new URL(args[0]).openStream()), s);
 * }</pre>
 * 
 * @author Stephan Fuhrmann
 */
public class HtmlToSaxParserFactory extends SAXParserFactory {

	/**
	 * Html2Sax currently supports <em>no</em> features.
	 */
	@Override
	public boolean getFeature(String name) throws ParserConfigurationException,
			SAXNotRecognizedException, SAXNotSupportedException {
		return false;
	}

	/**
	 * Creates a new {@link HtmlToSaxParser} instance.
	 */
	@Override
	public SAXParser newSAXParser() throws ParserConfigurationException,
			SAXException {
		return new HtmlToSaxParser();
	}

	/**
	 * Html2Sax currently supports <em>no</em> features.
	 */
	@Override
	public void setFeature(String name, boolean value)
			throws ParserConfigurationException, SAXNotRecognizedException,
			SAXNotSupportedException {
	}
}

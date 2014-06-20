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
package de.tynne.htmltosax.dom;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;

/**
 * Test for the {@link DomParser DOM parser}.
 * @author Stephan Fuhrmann
 */
public class DomParserTest {
	@Test
	public void testParseWithEmptyInputSource() throws ParserConfigurationException, SAXException, IOException {
		Document doc = DomParser.parse(new InputSource(new StringReader("")));
		assertNotNull(doc);
		assertNotNull(doc.getChildNodes());
	}
	
	@Test
	public void testParseWithOkDocument() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document doc = DomParser.parse(new InputSource(new StringReader("<html><head><title>hallo</title></head><body>du</body></html>")));
		assertNotNull(doc);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		assertNotNull(xpath.evaluate("/html", doc, XPathConstants.NODE));
		assertEquals(2., xpath.evaluate("count(/html/*)", doc, XPathConstants.NUMBER));
		assertNotNull(xpath.evaluate("/html/head", doc, XPathConstants.NODE));
		assertEquals("hallo", xpath.evaluate("/html/head/title/text()", doc, XPathConstants.STRING));
		assertNotNull(xpath.evaluate("/html/body", doc, XPathConstants.NODE));
		assertNotNull(xpath.evaluate("/html/body/text()", doc, XPathConstants.NODE));
		assertEquals("du", xpath.evaluate("/html/body/text()", doc, XPathConstants.STRING));
	}
	
	@Test
	public void testParseWithMissingCloseElements() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document doc = DomParser.parse(new InputSource(new StringReader("<html><head><title>hallo</head><body>du</body>")));
		assertNotNull(doc);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		assertNotNull(xpath.evaluate("/html", doc, XPathConstants.NODE));
		assertEquals(2., xpath.evaluate("count(/html/*)", doc, XPathConstants.NUMBER));
		assertNotNull(xpath.evaluate("/html/head", doc, XPathConstants.NODE));
		assertEquals("hallo", xpath.evaluate("/html/head/title/text()", doc, XPathConstants.STRING));
		assertNotNull(xpath.evaluate("/html/body", doc, XPathConstants.NODE));
		assertNotNull(xpath.evaluate("/html/body/text()", doc, XPathConstants.NODE));
		assertEquals("du", xpath.evaluate("/html/body/text()", doc, XPathConstants.STRING));
	}
	
	@Test
	public void testParseWithIllegalCloser() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document doc = DomParser.parse(new InputSource(new StringReader("<html><head><title>hallo</hans></head><body>du</body>")));
		assertNotNull(doc);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		assertNotNull(xpath.evaluate("/html", doc, XPathConstants.NODE));
		assertEquals(2., xpath.evaluate("count(/html/*)", doc, XPathConstants.NUMBER));
		assertNotNull(xpath.evaluate("/html/head", doc, XPathConstants.NODE));
		assertEquals("hallo", xpath.evaluate("/html/head/title/text()", doc, XPathConstants.STRING));
		assertNotNull(xpath.evaluate("/html/body", doc, XPathConstants.NODE));
		assertNotNull(xpath.evaluate("/html/body/text()", doc, XPathConstants.NODE));
		assertEquals("du", xpath.evaluate("/html/body/text()", doc, XPathConstants.STRING));
	}
	
	@Test
	public void testParseWithAttrs() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document doc = DomParser.parse(new InputSource(new StringReader("<html><head><title lang=\"de\" version=\"1\">hallo</head><body>du</body>")));
		assertNotNull(doc);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		assertEquals("de", xpath.evaluate("/html/head/title/@lang", doc, XPathConstants.STRING));
		assertEquals("1", xpath.evaluate("/html/head/title/@version", doc, XPathConstants.STRING));
	}
	
	@Test
	public void testParseWithRealWorldDoc() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Document doc = DomParser.parse(new InputSource(new URL("http://www.intpornforum.com/").openStream()));
		assertNotNull(doc);
	}
}

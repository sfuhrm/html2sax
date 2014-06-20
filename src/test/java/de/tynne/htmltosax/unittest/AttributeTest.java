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
package de.tynne.htmltosax.unittest;

import de.tynne.htmltosax.unittest.recorder.PlainTextPart;
import de.tynne.htmltosax.unittest.recorder.Part;
import de.tynne.htmltosax.unittest.recorder.EndDocumentPart;
import de.tynne.htmltosax.unittest.recorder.StartElementPart;
import de.tynne.htmltosax.unittest.recorder.EndElementPart;
import de.tynne.htmltosax.unittest.recorder.ErrorPart;
import de.tynne.htmltosax.unittest.recorder.PartRecorder;
import de.tynne.htmltosax.unittest.recorder.StartDocumentPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import static org.junit.Assert.*;

/**
 * Junit test for HTML attribute related stuff.
 * @author Stephan Fuhrmann
 */
public class AttributeTest {
	/** Test one attribute without value assigned.
	 * */
        @Test
	public void testNoValueAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "hello");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Test two attributes without value assigned.
	 * */
        @Test
	public void testTwoNoValueAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello world>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "hello");
		attrs.addAttribute("", "", "world", "", "world");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Test one single quoted attribute.
	 * */
        @Test
	public void testSQuotedAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello='you'>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Test one single quoted attribute, two spaces before first attr.
	 * */
        @Test
	public void testSQuotedAttr2() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html  hello='you'>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test one single quoted attribute.
	 * */
        @Test
	public void testSQuotedEntityAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello='you&amp;'>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you&");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test one double quoted attribute.
	 * */
        @Test
	public void testDQuotedAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello=\"you\">");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
	/** Test one double quoted attribute with a CRLF within.
	 * */
        @Test
	public void testDQuotedAttrWithCRLF() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello=\"you\r\ndude\">");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "youdude");
		expectedParts.add(new ErrorPart());
		expectedParts.add(new ErrorPart());
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Test one double quoted attribute.
	 * */
        @Test
	public void testDQuotedEntityAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello=\"yo&amp;u\">");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "yo&u");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Test one double quoted attribute.
	 * */
        @Test
	public void testDQuotedAttrStartEnd() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello=\"you\"/>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test two double quoted attributes.
	 * */
        @Test
	public void testTwoDQuotedAttrStartEnd() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html hello=\"you\" world=\"nothing\"/>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "hello", "", "you");
		attrs.addAttribute("", "", "world", "", "nothing");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test two double quoted attributes, changed sequence, some spaces.
	 * */
        @Test
	public void testTwoDQuotedAttrStartSeqSpaces() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html   world=\"nothing\"    hello=\"you\">");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "world", "", "nothing");
		attrs.addAttribute("", "", "hello", "", "you");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test double quoted attribute, with spaces within.
	 * */
        @Test
	public void testTwoDQuotedSpaces() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html   world=\"one space\"   >");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "world", "", "one space");
		expectedParts.add(new StartElementPart("html", attrs));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Test plain attribute with query part that should come in quotes but doesn't.
	 * */
        @Test
	public void testPlainWithQuery() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<a href=test.html?param=value>hehe</a>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "href", "", "test.html?param=value");		
		expectedParts.add(new StartElementPart("a", attrs));
		expectedParts.add(new PlainTextPart("hehe"));
		expectedParts.add(new EndElementPart("a"));		
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
        @Test
	public void testPlainWithAmpError() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<script \n"+
                    "data-galleryui-images-suffix=\"?nvb=20121006055000&nva=20121007153802&hash=0aeb57fef6f58784c3aff\"\n"+
                    "data-galleryui-thumbs-prefix=\"http://abc.com/1271/pics/thm/\"\n"+
                    "data-galleryui-thumbs-suffix=\"?nvb=20121006055000&nva=20121007153802&hash=04b97ae24264dea1db184\"/>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "data-galleryui-images-suffix", "", "?nvb=20121006055000&nva=20121007153802&hash=0aeb57fef6f58784c3aff");		
		attrs.addAttribute("", "", "data-galleryui-thumbs-prefix", "", "http://abc.com/1271/pics/thm/");		
		attrs.addAttribute("", "", "data-galleryui-thumbs-suffix", "", "?nvb=20121006055000&nva=20121007153802&hash=04b97ae24264dea1db184");		
		expectedParts.add(new StartElementPart("script", attrs));
		expectedParts.add(new EndElementPart("script"));		
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}		
		
        //@Test
	public void testPlainWithAmpClosed() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<a \n"+
                    "href=\"http://lala.com/&\"\n"+
                    "/>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "href", "", "http://lala.com/&");		
		expectedParts.add(new StartElementPart("a", attrs));
		expectedParts.add(new EndElementPart("a"));		
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}	
	
}

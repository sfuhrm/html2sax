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
import de.tynne.htmltosax.unittest.recorder.ErrorPart;
import de.tynne.htmltosax.unittest.recorder.PartRecorder;
import de.tynne.htmltosax.unittest.recorder.StartDocumentPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;

import org.xml.sax.SAXException;

import static org.junit.Assert.*;

/** Some tests for plain text and entity resolving.
 * @author Stephan Fuhrmann
 * */
public class PlainTextEntityTest {

	/** Parse empty string, expect only start and end document parts.
	 * */
        @Test
	public void testEmpty() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse plain string, expect only start-, plain and end document parts.
	 * */
        @Test
	public void testPlain() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello world");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello world"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse plain string with HTML entities, 
	 * expect only start-, plain and end document parts.
	 * */
        @Test
	public void testPlainWithEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &amp; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello & <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse plain string with some known and some unknown HTML entities, 
	 * expect only start-, plain and end document parts.
	 * The unknown entities are passed thru.
	 * */
        @Test
	public void testUnknownEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &watunga; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new ErrorPart());
		expectedParts.add(new PlainTextPart("hello &watunga; <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Parse plain string with some decimal HTML entities, 
	 * expect only start-, plain and end document parts.
	 * */
        @Test
	public void testDecEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &#65; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello A <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/**
	 * Same as {@link #testDecEntities()} but with illegal hex.
	 * Expect pass-thru.
	 * */
        @Test
	public void testIllegalDecEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &#41z; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new ErrorPart());
		expectedParts.add(new PlainTextPart("hello &#41z; <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse plain string with some hexadecimal HTML entities, 
	 * expect only start-, plain and end document parts.
	 * */
        @Test
	public void testHexEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &#x41; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello A <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/**
	 * Same as {@link #testHexEntities()} but with illegal hex.
	 * Expect pass-thru.
	 * */
        @Test
	public void testIllegalHexEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &#x41z; &lt;world&gt;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new ErrorPart());
		expectedParts.add(new PlainTextPart("hello &#x41z; <world>"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/**
	 * Test recursive entities. Expect only one resolving (?).
	 * */
        @Test
	public void testRecursiveEntities() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &amp;amp;");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello &amp;"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/**
	 * Test two amp signs. Expect pass thru, but also an error.
	 * */
        @Test
	public void testAmpAmp() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello &&");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new ErrorPart());
		expectedParts.add(new PlainTextPart("hello &&"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/**
	 * Test whether unicode chars get broken (here: Euro sign). Expect pass thru.
	 * */
        @Test
	public void testUnicode() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("hello \u20ac");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new PlainTextPart("hello \u20ac"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
}

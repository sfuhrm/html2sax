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
import de.tynne.htmltosax.unittest.recorder.PartRecorder;
import de.tynne.htmltosax.unittest.recorder.StartDocumentPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.junit.Ignore;
import org.junit.Test;

import org.xml.sax.SAXException;

import static org.junit.Assert.*;

/** Some tests for basic element stuff.
 * @author Stephan Fuhrmann
 * */
public class ElementTest {

	/** Parse one element, expect only start doc, element and end doc parts.
	 * */
        @Test
	public void testStartE() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse one element with additional space in name, expect only start doc, element and end doc parts.
	 * Space is ignored.
	 * */
        @Test
	public void testStartESpace() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html >");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Parse one element with additional space in name, expect only start doc, element and end doc parts.
	 * Space is ignored.
	 * TODO element gets eaten, no output
	 * */
        @Test
	public void testEndESpace() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("</html >");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Parse two elements, expect only start doc, two elements and end doc parts.
	 * */
        @Test
	public void testTwoStartE() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html><head>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new StartElementPart("head"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Parse start and end elements, expect start doc, two elements and end doc parts.
	 * */
        @Test
	public void testStartEnd() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html></html>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Parse combined start/end element, expect start doc, start+end elements and end doc parts.
	 * */
        @Test
	public void testStartEndCombined() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html/>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
	/** Parse combined start/end element with space before slash, expect start doc, start+end elements and end doc parts.
	 * */
        @Test
	public void testStartEndCombinedSpace() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html />");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
	/** Parse combined start/end element with space after slash, expect start doc, start+end elements and end doc parts.
	 * */
        @Test
	public void testStartEndCombinedSpace2() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html/ >");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}

	/** Parse start element, plain text and end elements, expect only 
	 * start doc, start elem, plain text, end elem and end doc parts.
	 * */
        @Test
	public void testStartPlainEnd() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html>hello wurld</html>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new PlainTextPart("hello wurld"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
	/** Parse script passage. 
	 * Expect body to be passed thru without modification.
	 * */
        @Test
	public void testScriptInside() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html><script>i = 2;</script></html>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new StartElementPart("script"));
		expectedParts.add(new PlainTextPart("i = 2;"));
		expectedParts.add(new EndElementPart("script"));
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
		
	/** Parse script passage. 
	 * Expect body to be passed thru without modification.
	 * */
        @Test
	public void testScript() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<script><<&&&amp;>></script>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("script"));
		expectedParts.add(new PlainTextPart("<<&&&amp;>>"));
		expectedParts.add(new EndElementPart("script"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Element name case is converted to lower case.
	 * Case sensitivity is an error source here.
	 * */
        @Test
	public void testCaseStart() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<hTmL>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new StartElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
	
	/** Element name case is converted to lower case.
	 * Case sensitivity is an error source here.
	 * */
        @Test
	public void testCaseEnd() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("</hTmL>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
		expectedParts.add(new StartDocumentPart());
		expectedParts.add(new EndElementPart("html"));
		expectedParts.add(new EndDocumentPart());
		
		assertEquals(expectedParts, seenParts);
	}
}

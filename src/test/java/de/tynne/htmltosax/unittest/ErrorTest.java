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
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import static org.junit.Assert.*;

/** JUnit test for parsing errors.
 * @author Stephan Fuhrmann
 */
public class ErrorTest {
        @Test
	public void testErrorAttr() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html><test hello =world> <h2>hello</h2></html>");
		
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "hello", null, "world");
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
                expectedParts.addAll(Arrays.asList(
                    new StartDocumentPart(),
                    new StartElementPart("html"),
                    new StartElementPart("test", attr),
                    new PlainTextPart(" "),
                    new StartElementPart("h2"),
                    new PlainTextPart("hello"),
                    new EndElementPart("h2"),
                    new EndElementPart("html"),
                    new EndDocumentPart()));

		System.out.println("expect:");
		System.out.println(expectedParts);

		System.out.println("seen:");
		System.out.println(seenParts);
		
		assertEquals(expectedParts, seenParts);
	}
		
        @Test
	public void testSpaceBeforeElementName() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html>< test</html>");
		
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
                expectedParts.addAll(Arrays.asList(
                    new StartDocumentPart(),
                    new StartElementPart("html"),
					new ErrorPart(),
                    new PlainTextPart(" test"),
                    new EndElementPart("html"),
                    new EndDocumentPart()));

		System.out.println("expect:");
		System.out.println(expectedParts);

		System.out.println("seen:");
		System.out.println(seenParts);
		
		assertEquals(expectedParts, seenParts);
	}
		
        @Test
	public void testAmpWithNoEntity() throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse("<html><a href=\"test?blah=1&blubb=55\"> <h2>hello</h2></html>");
		
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "href", null, "test?blah=1&blubb=55");
		List<Part>  seenParts = rec.getParts();
		List<Part> expectedParts = new ArrayList<Part>();
                expectedParts.addAll(Arrays.asList(
                    new StartDocumentPart(),
                    new StartElementPart("html"),
                    new StartElementPart("a", attr),
                    new PlainTextPart(" "),
                    new StartElementPart("h2"),
                    new PlainTextPart("hello"),
                    new EndElementPart("h2"),
                    new EndElementPart("html"),
                    new EndDocumentPart()));

		System.out.println("expect:");
		System.out.println(expectedParts);

		System.out.println("seen:");
		System.out.println(seenParts);
		
		assertEquals(expectedParts, seenParts);
	}
}

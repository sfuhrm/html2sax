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
package de.sfuhrm.htmltosax.unittest;

import de.sfuhrm.htmltosax.unittest.recorder.Part;
import de.sfuhrm.htmltosax.unittest.recorder.PartRecorder;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;

/** Send some trash bytes into the lexer
 * and have fun.
 * @author Stephan Fuhrmann
 * */
public class TrashTest {

	public final static int TRASH_BYTES = 16 * 1024;
	
	/**
	 * Creates a specified amount of random bytes.
	 * @author Stephan Fuhrmann
	 */
	class TrashInputStream extends InputStream {
		
		/** The remaining bytes to send. */
		private int trashCount;
		
		private Random rand;
		
		TrashInputStream(int count) {
			trashCount = count;
			rand = new Random(42); // fixed seed for reproducability
		}

		@Override
		public int read() throws IOException {
			if (trashCount > 0) {
				trashCount--;
				return rand.nextInt() & 0x0ff;
			}
			return -1;
		}
		
		/** Get the number of remaining trash. */
		public int getRest() {
			return trashCount;
		}
	}
	
	/** Parse and don't get an Error or Exception ;-).
	 * */
        //@Ignore("Unclear error handling problem here")
        @Test
	public void testTrash() throws ParserConfigurationException, SAXException, IOException {
		TrashInputStream trash = new TrashInputStream(TRASH_BYTES);
                // PrintHandler.parse(trash);
		PartRecorder rec = PartRecorder.parse(trash);
		List<Part> parts = rec.getParts();
		System.out.println("Size: "+parts.size());
		assertEquals(0, trash.getRest());
	}	
}

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
package de.sfuhrm.htmltosax.parser;

import de.sfuhrm.htmltosax.parser.html.HtmlDFA;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for the {@link HtmlDFA}.
 * @author Stephan Fuhrmann
 */
public class HtmlDFATest {

    @Test(expected=NullPointerException.class)
    public void testInitWithNull() {
        HtmlDFA parser = new HtmlDFA(null);
    }
    
    @Test
    public void testInit() {
        HtmlDFA parser = new HtmlDFA(new StringReader(""));
    }
    
    @Test
    public void testParseEmpty() throws IOException {
        HtmlDFA parser = new HtmlDFA(new StringReader(""));
        parser.parse();
    }
    
    @Test
    public void testParseText() throws IOException {
        HtmlDFA parser = new HtmlDFA(new StringReader("text"));
        parser.parse();
    }
    
    @Test
    public void testParseTextAmp() throws IOException {
        HtmlDFA parser = new HtmlDFA(new StringReader("Black &amp; Decker"));
        parser.parse();
    }
    
    @Test
    public void testParseAnchor() throws IOException {
        HtmlDFA parser = new HtmlDFA(new StringReader("<a href=\"http://test.com/\">"));
        parser.parse();
    }
}

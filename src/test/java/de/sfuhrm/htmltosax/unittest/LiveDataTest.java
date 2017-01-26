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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.junit.Test;
import org.xml.sax.InputSource;

import org.xml.sax.SAXException;

/**
 * Junit test for HTML attribute related stuff.
 * @author Stephan Fuhrmann
 */
public class LiveDataTest {

    public static void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance("de.sfuhrm.htmltosax.HtmlToSaxParserFactory", null);
        SAXParser parser = factory.newSAXParser();
        PrintHandler handler = new PrintHandler();
        parser.parse(new InputSource(in), handler);
    }
    
    public static void parse(URL url) throws ParserConfigurationException, SAXException, IOException {
        URLConnection uc = url.openConnection();
        InputStream is = uc.getInputStream();
        try {
            parse(is);
        }
        finally {
            is.close();
        }
    }

    @Test
    public void testMail() throws ParserConfigurationException, SAXException, IOException {
        parse(new URL("http://www.mail.com/"));
    }
    
    @Test
    public void testHeise() throws ParserConfigurationException, SAXException, IOException {
        parse(new URL("http://heise.de/"));
    }
}

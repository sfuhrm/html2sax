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

import de.sfuhrm.htmltosax.unittest.recorder.PartRecorder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A default handler printing out the SAX events.
 * @author Stephan Fuhrmann
 */
public class PrintHandler extends DefaultHandler {

    private PrintStream out;
    
    public PrintHandler(PrintStream out) {
        this.out = out;
    }
    
    public PrintHandler() {
        out = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        });
    }
    
    @Override
    public void warning(SAXParseException e)
            throws SAXException {
        e.printStackTrace();
    }

    @Override
    public void error(SAXParseException e)
            throws SAXException {
        e.printStackTrace();
    }

    @Override
    public void fatalError(SAXParseException e)
            throws SAXException {
        e.printStackTrace();
    }

    @Override
    public void startDocument()
            throws SAXException {
        out.println("<<start>>");
    }

    @Override
    public void endDocument()
            throws SAXException {
        out.println("<<end>>");
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        out.println(ch);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        out.println("</"+localName+">");
    }

    @Override
    public void startElement(String uri,
            String localName,
            String qName,
            Attributes attributes)
            throws SAXException {
        out.println("<"+localName+">");
    }
    
	/** Parse the content given in the String.
	 * This is a factory method that parses and creates a {@link PartRecorder}
	 * of the result.
	 * @param in a document like string
	 * @return the recorded SAX events
	 * */
	public final static void parse(String in) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance("de.sfuhrm.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PrintHandler handler = new PrintHandler();
		parser.parse(new InputSource(new StringReader(in)), handler);
	}
	
	/** Parse the content given in the InputStream.
	 * This is a factory method that parses and creates a {@link PartRecorder}
	 * of the result.
	 * @param in a stream containing a something of a document
	 * @return the recorded SAX events
	 * */
	public final static void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance("de.sfuhrm.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PrintHandler handler = new PrintHandler();
		parser.parse(new InputSource(in), handler);
	}
}

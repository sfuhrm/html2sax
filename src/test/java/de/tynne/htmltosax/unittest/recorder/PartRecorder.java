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
package de.tynne.htmltosax.unittest.recorder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/** Helper class for unit tests. Will store almost every callback from the
 * SAX parser for later comparing.
 * @author Stephan Fuhrmann
 * */
public class PartRecorder extends DefaultHandler {

	/** The recorded parts.
	 * @see #getParts()
	 * */
	private List<Part> parts;

	public PartRecorder() {
		parts = new ArrayList<Part>();
	}

	/** Get the parts recorded so far in a new list. */
	public List<Part> getParts() {
		return new ArrayList<Part>(parts);
	}


    public void warning (SAXParseException e)
	throws SAXException {
    	parts.add( new ErrorPart(e));
    }

    public void error(SAXParseException e)
	throws SAXException {
    	parts.add( new ErrorPart(e));
    }

    public void fatalError(SAXParseException e)
	throws SAXException {
    	parts.add( new ErrorPart(e));
    }

	public void startDocument ()
	throws SAXException
	{
		parts.add( new StartDocumentPart());
	}

	public void endDocument ()
	throws SAXException
	{
		parts.add( new EndDocumentPart());
	}

    public void characters (char ch[], int start, int length)
	throws SAXException
    {
    	parts.add( new PlainTextPart(new String(ch, start, length)));
    }
 

	public void endElement (String uri, String localName, String qName)
	throws SAXException	{
		parts.add( new EndElementPart(qName));
	}

	public void startElement(String uri,
			String localName,
			String qName,
			Attributes attributes)
	throws SAXException {

		parts.add( new StartElementPart(qName, attributes) );
	}

	/** Parse the content given in the String.
	 * This is a factory method that parses and creates a {@link PartRecorder}
	 * of the result.
	 * @param in a document like string
	 * @return the recorded SAX events
	 * */
	public final static PartRecorder parse(String in) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PartRecorder rec = new PartRecorder();
		parser.parse(new InputSource(new StringReader(in)), rec);
		return rec;
	}
	
	/** Parse the content given in the InputStream.
	 * This is a factory method that parses and creates a {@link PartRecorder}
	 * of the result.
	 * @param in a stream containing a something of a document
	 * @return the recorded SAX events
	 * */
	public final static PartRecorder parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PartRecorder rec = new PartRecorder();
		parser.parse(new InputSource(in), rec);
		return rec;
	}
	
	public static void main_time(String args[]) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PartRecorder s = new PartRecorder();

		int count = 100;

		long start = System.nanoTime();
		for (int i=0; i < count; i++)
			parser.parse(new InputSource(new URL(args[0]).openStream()), s);
		long taken = System.nanoTime() - start;
		System.out.println("H2S nanos: "+taken+" -> "+taken/count);		
	}
}

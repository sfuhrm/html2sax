/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.unittest;

import de.tynne.htmltosax.unittest.recorder.PartRecorder;
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
 *
 * @author fury
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
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
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
		SAXParserFactory factory = SAXParserFactory.newInstance("de.tynne.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		PrintHandler handler = new PrintHandler();
		parser.parse(new InputSource(in), handler);
	}
}

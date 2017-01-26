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
package de.sfuhrm.htmltosax;

import java.io.IOException;
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

/** A demonstration program extracting hyperlinks from a web page.
 * Given a URL to the arguments of the main method, it will download
 * the page and print all links in it.
 * <br>
 * Note that this demonstration is so tiny that it is not located into
 * a different folder. It will get delivered to all clients.
 * 
 * @author Stephan Fuhrmann
 */
public class Sample extends DefaultHandler {
	/** Discovered hypertext references. */
	private List<String> hrefs;

	private final static boolean showErrors = true;

	Sample() {
		hrefs = new ArrayList<String>();
	}

	/** Get the list of extracted hyperlinks.
     * @return the list of seen href attribute values.
	 */
	public List<String> getHRefs() {
		return hrefs;
	}

	private void eventuallyShowError(SAXParseException e) {
		if (showErrors)
			e.printStackTrace();
	}

	public void warning (SAXParseException e)
	throws SAXException {
		eventuallyShowError(e);
	}


	public void error (SAXParseException e)
	throws SAXException
	{
		eventuallyShowError(e);
	}


	public void fatalError (SAXParseException e)
	throws SAXException
	{
		eventuallyShowError(e);
	}

	// default handler override
	@Override
	public void startElement(String uri,
			String localName,
			String qName,
			Attributes attributes)
	throws SAXException {
		int len = attributes.getLength();

		for (int i=0; i < len; i++) {
			String qname = attributes.getQName(i);
			if (qname.equalsIgnoreCase("src") || qname.equalsIgnoreCase("href")) {
				if (true)
					System.out.println(hrefs.size()+":"+attributes.getValue(i));
				hrefs.add(attributes.getValue(i));
			}
		}
	}

	/** 
	 * Extract links from a web page.
	 * @param args a one-element array with an URL
	 */
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {

		if (args.length == 0){
			System.err.println("Please add an URL to your argument list!");
			return;
		}

		SAXParserFactory factory = SAXParserFactory.newInstance("de.sfuhrm.htmltosax.HtmlToSaxParserFactory", null);
		SAXParser parser = factory.newSAXParser();
		Sample s = new Sample();

		parser.parse(new InputSource(new URL(args[0]).openStream()), s);

		System.out.println("Extracted links: ");

		List<String> hrefs = s.getHRefs();
		for (int i=0; i < hrefs.size(); i++) {
			System.out.println((i+1)+". "+hrefs.get(i));
		}
	}

}

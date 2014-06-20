/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.parser;

import de.tynne.htmltosax.parser.html.HtmlDFA;
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

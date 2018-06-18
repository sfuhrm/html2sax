html2sax
===================
![Travis CI](https://travis-ci.org/sfuhrm/html2sax.svg?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f0d36889872d4ecc9a3c5d995996b6ba)](https://www.codacy.com/app/sfuhrm/html2sax?utm_source=github.com&utm_medium=referral&utm_content=sfuhrm/html2sax&utm_campaign=badger)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.sfuhrm/html2sax/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.sfuhrm/html2sax) 

 html2sax is a parser for html documents. It reads HTML-documents and creates 
callback calls using the Java(tm) [SAX API](http://docs.oracle.com/javase/7/docs/api/org/xml/sax/ContentHandler.html).

## Background

There are many partly-malformed HTML documents on the web. Many page authors don't 
care about correctness when their browser manages to display their page. 
Web browsers have specially adjusted parsers that can repair malformed 
HTML pages to a certain degree. Unfortunately there is no standard for 
correcting mistakes. So for wrong pages every browser behaves differently.
To take a tool into the web you need to parse web pages. There is a lot of 
fancy XML technology out there, but HTML is actually no XML. 
XHTML is XML, but not very wide-spread today. 

### Purpose

The intention for the original development was to have a really simple 
HTML parser that just splits up the lexical parts of a 
HTML document (tags, attributes, and text). The parser should handle errors 
gracefully and continue after them. It should not try to repair documents 
because the intention was to extract certain parts using XPath-queries.
There is also a Java version of HTML tidy. It does its best at repairing 
malformed HTML documents. It works quite ok, but in my opinion it's too 
much for some applications. 

## The library

html2sax is designed to be the frontend for a web-spider reading websites. 
It can handle (almost?) all error situations, 
but will not try to correct problematic HTML pages. 
It operates on a very low level and is quite fast. 
Tests showed that it is twice as fast as Html-Tidy.
html2sax works as a SAX parser. 
Usually SAX is just designed to handle real XML. html2sax will
emit malformed XML which may confuse SAX-using code.
Don't expect many SAX-code to work with this 'weak' parser.
The parser was written using a simple DFA. 

### Features

The parser supports the following features:

* Speed.
* Simple.
* Pure Java(tm).
* Using the well-understood and fast SAX API.
* JUnit test cases.
* HTML entity expansion (example: "&amp;amp;" gets "&").
* Handles errors gracefully and will continue close to the error.
* No DTD logic that won't work with many documents anyway.
* No structure-level repairing effors that may fail.
* Full source provided.

### Restrictions

 There are several restrictions for html2sax that you should be aware of:

* HTML is not XML. This means SAX is an API for this kind of callbacks, but some existing tools having a SAX input interface will fail with html2sax input. Reason: The documents are not well-formed.
* No DTD-support. You need to do your HTML-thinking for yourself.
* Won't protect your parser callback from senseless trash if documents are really weird.
* Won't repair corrupt documents.

### Requirements

The only requirements for the parser is a Java(tm) 1.6 JRE.

### Example

Usage is quite simple. The following example runs the parser:

---------------------------------------
````java
    SAXParserFactory factory = 
        SAXParserFactory.newInstance(
        "de.sfuhrm.htmltosax.HtmlToSaxParserFactory",
        null);
    SAXParser parser = factory.newSAXParser();
    YourCallback s = new YourCallback();
    parser.parse(new InputSource(
        new URL(args[0]).openStream()), s);
````
---------------------------------------

A working example is in the file [Sample.java](https://github.com/sfuhrm/html2sax/blob/master/src/main/java/de/sfuhrm/htmltosax/Sample.java) in the source distribution. 

### Download

You can either download the library in the release section of github

https://github.com/sfuhrm/html2sax/releases

or add this dependency to your Maven pom:

---------------------------------------
````xml
    <dependency>
        <groupId>de.sfuhrm</groupId>
        <artifactId>html2sax</artifactId>
        <version>2.1.3</version>
    </dependency>
````
---------------------------------------

## Author & License

### Author

html2sax was written by Stephan Fuhrmann. You can reach my at s (at) sfuhrm.de. 

### License

The library is in the [LPGL 2.1](http://www.gnu.de/documents/lgpl-2.1.en.html) license.

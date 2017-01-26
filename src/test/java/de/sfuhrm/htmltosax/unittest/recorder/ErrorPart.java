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
package de.sfuhrm.htmltosax.unittest.recorder;

import org.xml.sax.SAXParseException;

/** 
** Stores the exception of a parse error.
** @author Stephan Fuhrmann
*/
public class ErrorPart implements Part {

	private SAXParseException exception;

	public ErrorPart() {
	}

	public ErrorPart(SAXParseException inException) {
		this.exception = inException;
	}
	
	SAXParseException getException() {
		return exception;
	}
	
	public boolean equals(Object o) {
		return o.getClass() == getClass();
	}
	
	public String toString() {
		if (exception != null)
			return "<!!error: "+exception.getMessage()+"!!>";
		else
			return "<!!error!!>";
	}
}

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

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A start element.
 * @author Stephan Fuhrmann
 */
public class StartElementPart implements Element {
	/** The tag name. */
	private String name;

	/** The attributes of this tag. */
	private AttributesImpl attrs;
	
	public StartElementPart(CharSequence inName) {
		this.name = inName.toString();
		this.attrs = new AttributesImpl();
	}

	public StartElementPart(CharSequence inName, Attributes inAttrs) {
		this.name = inName.toString();
		this.attrs = new AttributesImpl(inAttrs);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(name);
		
		if (attrs.getLength() > 0) {
			for (int i=0; i < attrs.getLength(); i++) {
				sb.append(" ");				
				sb.append(attrs.getQName(i));				
				sb.append("=");				
				sb.append("\"");				
				sb.append(attrs.getValue(i));				
				sb.append("\"");				
			}
		}
		
		sb.append(">");
		return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		
//		result = prime * result + ((attrs == null) ? 0 : attrs.hashCode());
		result = prime * result + hashCode(attrs);				
		
		return result;
	}
	
	/** Custom hashCode implementation for attributes. */
	private static int hashCode(Attributes atts) {
		final int prime = 31;
		int result = 1;
		for (int i=0; i < atts.getLength(); i++) {
			result = prime * result + atts.getQName(i).hashCode();				
			result = prime * result + atts.getValue(i).hashCode();				
		}
		return result;
	}

	/** Custom element wise equals. */
	private static boolean equals(Attributes atts1, Attributes atts2) {
		if (atts1.getLength() != atts2.getLength())
			return false;
		
		for (int i=0; i < atts1.getLength(); i++) {
			if (! atts1.getQName(i).equals(atts2.getQName(i)))
				return false;
			
			if (! atts1.getValue(i).equals(atts2.getValue(i)))
				return false;
		}		
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StartElementPart other = (StartElementPart) obj;
		if (attrs == null) {
			if (other.attrs != null)
				return false;
		} else if (!equals(attrs, other.attrs))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

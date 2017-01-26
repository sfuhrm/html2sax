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
package de.sfuhrm.htmltosax.parser.html;

import de.sfuhrm.htmltosax.parser.dfa.State;

/**
 * The state of the HTML parser.
 * @author Stephan Fuhrmann
 */
 enum HtmlState implements State {
    INIT,
    LT,
    LTEXCL,
	WAIT_GT,
    ELEMENT_NAME,
    AFTER_ELEMENT_NAME,
    AFTER_ELEMENT_NAME_SLASH,
    ATTRIBUTE_NAME,
    AFTER_ATTRIBUTE_NAME,
	BEFORE_ATTRIBUTE_VALUE,
	ATTRIBUTE_VALUE,
    CLOSE_TAG,
    CLOSE_TAG_NAME,
	
    AMP,
    AMP_ENTITY_REF,
	AMP_NUMERIC_REF,
	AMP_HEX_REF,
	
	SQ_ATTRIBUTE_VALUE,
    SQ_AMP,
    SQ_AMP_ENTITY_REF,
	SQ_AMP_NUMERIC_REF,
	SQ_AMP_HEX_REF,
	
    DQ_ATTRIBUTE_VALUE,
    DQ_AMP,
    DQ_AMP_ENTITY_REF,
	DQ_AMP_NUMERIC_REF,
	DQ_AMP_HEX_REF,
	
	WAIT_SCRIPT_END,
	WSE_LT,
	WSE_LTS,
	WSE_LTS_S,
	WSE_LTS_SC,
	WSE_LTS_SCR,
	WSE_LTS_SCRI,
	WSE_LTS_SCRIP,
	WSE_LTS_SCRIPT,
	
 }

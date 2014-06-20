/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.htmltosax.parser.html;

import de.tynne.htmltosax.parser.dfa.State;

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

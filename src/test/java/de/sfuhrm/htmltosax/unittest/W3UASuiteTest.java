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

import de.sfuhrm.htmltosax.unittest.recorder.Part;
import de.sfuhrm.htmltosax.unittest.recorder.PartRecorder;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import static org.junit.Assert.*;

/** Tests the test suite from http://www.w3.org/WAI/UA/TS/html401/Overview.html.
 * @author Stephan Fuhrmann
 * */
public class W3UASuiteTest {
	
	/** Parse and don't get an Error or Exception ;-).
	 * */
	void doTest(URL url) throws ParserConfigurationException, SAXException, IOException {
		PartRecorder rec = PartRecorder.parse(url.openStream());
		List<Part> parts = rec.getParts();
		assertNotNull(parts);
	}

// the following passage has been created with the shell command


// find w3.org/ -iname *.html | while read l; do NAME=$(echo $l|sed -e"y/-/_/" -ne"s#.*/\([^/]*\).html#\1#p"); echo "@Test";echo "public void test_$NAME() throws Exception {"; echo doTest\(getClass\(\).getResource\(\"/$l\"\)\)\;; echo "}"; done > /tmp/x

	
@Test
public void test_0203_INPUT_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-INPUT-ALT.html"));
}
@Test
public void test_0203_IMG() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-IMG.html"));
}
@Test
public void test_0203_FRAME_LONGDESC_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-FRAME-LONGDESC-TEST.html"));
}
@Test
public void test_0203_ACRONYM() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-ACRONYM.html"));
}
@Test
public void test_0203_NOFRAMES_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-NOFRAMES-SRC.html"));
}
@Test
public void test_0203_FRAME_LONGDESC_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-FRAME-LONGDESC-SRC.html"));
}
@Test
public void test_0203_OBJECT_AUDIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-OBJECT-AUDIO.html"));
}
@Test
public void test_0203_OBJECT_VIDEO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-OBJECT-VIDEO.html"));
}
@Test
public void test_0203_NOSCRIPT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-NOSCRIPT.html"));
}
@Test
public void test_0203_IFRAME() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-IFRAME.html"));
}
@Test
public void test_0203_ABBR() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-ABBR.html"));
}
@Test
public void test_0203_TABLE_HEADERS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-TABLE-HEADERS.html"));
}
@Test
public void test_0203_OBJECT_IMAGES() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-OBJECT-IMAGES.html"));
}
@Test
public void test_0203_NOFRAMES_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-NOFRAMES-TEST.html"));
}
@Test
public void test_0203_FRAME_TITLE_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-FRAME-TITLE-TEST.html"));
}
@Test
public void test_0203_TABLE_SUMMARY() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-TABLE-SUMMARY.html"));
}
@Test
public void test_0203_FRAME_TITLE_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-FRAME-TITLE-SRC.html"));
}
@Test
public void test_0203_AREA_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-AREA-ALT.html"));
}
@Test
public void test_0203_MAP_TITLE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0203/0203-MAP-TITLE.html"));
}
@Test
public void test_0906_EVENT_HANDLERS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0906/0906-EVENT-HANDLERS.html"));
}
@Test
public void test_0303_CSS_BLINK() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0303/0303-CSS-BLINK.html"));
}
@Test
public void test_0302_OBJECT_AUDIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0302/0302-OBJECT-AUDIO.html"));
}
@Test
public void test_0302_EMBED_AUDIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0302/0302-EMBED-AUDIO.html"));
}
@Test
public void test_0302_ANIMATED_IMAGES() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0302/0302-ANIMATED-IMAGES.html"));
}
@Test
public void test_0302_OBJECT_VIDEO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0302/0302-OBJECT-VIDEO.html"));
}
@Test
public void test_0302_EMBED_VIDEO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0302/0302-EMBED-VIDEO.html"));
}
@Test
public void test_0414_IGNORE_AUTHOR_USER_STYLE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0414/0414-IGNORE-AUTHOR-USER-STYLE.html"));
}
@Test
public void test_0414_MULTIPLE_AUTHOR_STYLE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0414/0414-MULTIPLE-AUTHOR-STYLE.html"));
}
@Test
public void test_0414_USER_STYLE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0414/0414-USER-STYLE.html"));
}
@Test
public void test_0503_A_TARGET_MANUAL_VIEWPORT_OPEN() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0503/0503-A-TARGET-MANUAL-VIEWPORT-OPEN.html"));
}
@Test
public void test_0503_SCRIPT_MANUAL_VIEWPORT_OPEN() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0503/0503-SCRIPT-MANUAL-VIEWPORT-OPEN.html"));
}
@Test
public void test_1102_INPUT_CONFIGURATION() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1102/1102-INPUT-CONFIGURATION.html"));
}
@Test
public void test_1102_ACCESSKEY_FORMS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1102/1102-ACCESSKEY-FORMS.html"));
}
@Test
public void test_0304_TOGGLE_SCRIPTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0304/0304-TOGGLE-SCRIPTS.html"));
}
@Test
public void test_0905_ONCHANGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0905/0905-ONCHANGE.html"));
}
@Test
public void test_0905_ONFOCUS_ONBLUR() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0905/0905-ONFOCUS-ONBLUR.html"));
}
@Test
public void test_0905_ONMOUSE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0905/0905-ONMOUSE.html"));
}
@Test
public void test_0401_FONT_SIZE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0401/0401-FONT-SIZE.html"));
}
@Test
public void test_return() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/action/return.html"));
}
@Test
public void test_redirect() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/action/redirect.html"));
}
@Test
public void test_new_window_1() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/action/new-window-1.html"));
}
@Test
public void test_0210_UNSUPPORTED_LANGUAGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0210/0210-UNSUPPORTED-LANGUAGE.html"));
}
@Test
public void test_0402_FONT_FAMILY() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0402/0402-FONT-FAMILY.html"));
}
@Test
public void test_1005_LINK_INFORMATION() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1005/1005-LINK-INFORMATION.html"));
}
@Test
public void test_1105_TEXT_SIZE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1105/1105-TEXT-SIZE.html"));
}
@Test
public void test_1105_ACTIVATE_LINK() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1105/1105-ACTIVATE-LINK.html"));
}
@Test
public void test_1105_ENABLED_ELEMENTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1105/1105-ENABLED-ELEMENTS.html"));
}
@Test
public void test_1105_SEARCH_TEXT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1105/1105-SEARCH-TEXT.html"));
}
@Test
public void test_0403_FONT_COLOR() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0403/0403-FONT-COLOR.html"));
}
@Test
public void test_0907_MOVE_CONTENT_FOCUS_REVERSE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0907/0907-MOVE-CONTENT-FOCUS-REVERSE.html"));
}
@Test
public void test_1004_OUTLINE_HEADINGS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1004/1004-OUTLINE-HEADINGS.html"));
}
@Test
public void test_1004_OUTLINE_CAPTIONS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1004/1004-OUTLINE-CAPTIONS.html"));
}
@Test
public void test_1006_HIGHLIGHT_VIEWPORT_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1006/1006-HIGHLIGHT-VIEWPORT-SRC.html"));
}
@Test
public void test_1006_HIGHLIGHT_VIEWPORT_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1006/1006-HIGHLIGHT-VIEWPORT-TEST.html"));
}
@Test
public void test_0505_CONFIRM_FORM_SUBMIT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0505/0505-CONFIRM-FORM-SUBMIT.html"));
}
@Test
public void test_0505_CONFIRM_FORM_SUBMIT_AUTOMATIC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0505/0505-CONFIRM-FORM-SUBMIT-AUTOMATIC.html"));
}
@Test
public void test_0301_CSS_DIV_BACKGROUND_IMAGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0301/0301-CSS-DIV-BACKGROUND-IMAGE.html"));
}
@Test
public void test_0301_CSS_BACKGROUND_IMAGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0301/0301-CSS-BACKGROUND-IMAGE.html"));
}
@Test
public void test_0301_BODY_BACKGROUND() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0301/0301-BODY-BACKGROUND.html"));
}
@Test
public void test_Overview() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/Overview.html"));
}
@Test
public void test_1104_SINGLE_KEY_ANIMATED_IMAGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-ANIMATED-IMAGE.html"));
}
@Test
public void test_1104_SINGLE_KEY_VIDEO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-VIDEO.html"));
}
@Test
public void test_1104_SINGLE_KEY_TEXT_SIZE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-TEXT-SIZE.html"));
}
@Test
public void test_1104_SINGLE_KEY_LINK() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-LINK.html"));
}
@Test
public void test_1104_SINGLE_KEY_GLOBAL_VOLUME() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-GLOBAL-VOLUME.html"));
}
@Test
public void test_1104_SINGLE_KEY_ENABLED_ELEMENTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-ENABLED-ELEMENTS.html"));
}
@Test
public void test_1104_SINGLE_KEY_AUDIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-AUDIO.html"));
}
@Test
public void test_1104_SINGLE_KEY_SEARCH_TEXT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1104/1104-SINGLE-KEY-SEARCH-TEXT.html"));
}
@Test
public void test_0208_AREA_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0208/0208-AREA-ALT.html"));
}
@Test
public void test_0208_INPUT_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0208/0208-INPUT-ALT.html"));
}
@Test
public void test_0208_IMG_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0208/0208-IMG-ALT.html"));
}
@Test
public void test_0306_IMG() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0306/0306-IMG.html"));
}
@Test
public void test_0306_OBJECT_IMAGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0306/0306-OBJECT-IMAGE.html"));
}
@Test
public void test_0502_CURRENT_FOCUS_TOP() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0502/0502-CURRENT-FOCUS-TOP.html"));
}
@Test
public void test_0101_FRAME_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-FRAME-SRC.html"));
}
@Test
public void test_0101_ACCESSKEY_TEXTAREA() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-TEXTAREA.html"));
}
@Test
public void test_0101_ACCESSKEY_INPUT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-INPUT.html"));
}
@Test
public void test_0101_INPUT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-INPUT.html"));
}
@Test
public void test_0101_TABINDEX() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-TABINDEX.html"));
}
@Test
public void test_0101_RADIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-RADIO.html"));
}
@Test
public void test_0101_ACCESSKEY_MAP() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-MAP.html"));
}
@Test
public void test_0101_ACCESSKEY_LABEL() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-LABEL.html"));
}
@Test
public void test_0101_IFRAME() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-IFRAME.html"));
}
@Test
public void test_0101_TEXTAREA() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-TEXTAREA.html"));
}
@Test
public void test_0101_ACCESSKEY_CHECKBOX() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-CHECKBOX.html"));
}
@Test
public void test_0101_ACCESSKEY_RADIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-RADIO.html"));
}
@Test
public void test_0101_FRAME_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-FRAME-TEST.html"));
}
@Test
public void test_0101_BUTTON() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-BUTTON.html"));
}
@Test
public void test_0101_CHECKBOX() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-CHECKBOX.html"));
}
@Test
public void test_0101_ACCESSKEY_LEGEND() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-LEGEND.html"));
}
@Test
public void test_0101_ACCESSKEY() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY.html"));
}
@Test
public void test_0101_ACCESSKEY_BUTTON() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0101/0101-ACCESSKEY-BUTTON.html"));
}
@Test
public void test_0501_AUTO_FOCUS_CHANGE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0501/0501-AUTO-FOCUS-CHANGE.html"));
}
@Test
public void test_0909_TABLE_HEADER() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-TABLE-HEADER.html"));
}
@Test
public void test_0909_TABLE_CAPTION() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-TABLE-CAPTION.html"));
}
@Test
public void test_0909_LIST_ITEMS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-LIST-ITEMS.html"));
}
@Test
public void test_0909_HEADINGS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-HEADINGS.html"));
}
@Test
public void test_0909_LINKS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-LINKS.html"));
}
@Test
public void test_0909_FORMS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0909/0909-FORMS.html"));
}
@Test
public void test_0908_TEXT_SEARCH() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0908/0908-TEXT-SEARCH.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_MAP_AREA() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-MAP-AREA.html"));
}
@Test
public void test_0102_ONCHANGE_INPUT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCHANGE-INPUT.html"));
}
@Test
public void test_0102_ONCHANGE_TEXTAREA() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCHANGE-TEXTAREA.html"));
}
@Test
public void test_0102_ONCLICK_MULTIPLE_EVENTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCLICK-MULTIPLE-EVENTS.html"));
}
@Test
public void test_0102_ONCLICK() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCLICK.html"));
}
@Test
public void test_0102_ONCHANGE_SELECT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCHANGE-SELECT.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_LABEL() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-LABEL.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_INPUT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-INPUT.html"));
}
@Test
public void test_0102_ONMOUSE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONMOUSE.html"));
}
@Test
public void test_0102_ONMOUSE_MULTIPLE_EVENTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONMOUSE-MULTIPLE-EVENTS.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_LINKS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-LINKS.html"));
}
@Test
public void test_0102_ONCHANGE_SELECT_MULTIPLE_EVENTS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONCHANGE-SELECT-MULTIPLE-EVENTS.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_TEXTAREA() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-TEXTAREA.html"));
}
@Test
public void test_0102_ONFOCUS_ONBLUR_BUTTON() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0102/0102-ONFOCUS-ONBLUR-BUTTON.html"));
}
@Test
public void test_0903_MOVE_CONTENT_FOCUS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0903/0903-MOVE-CONTENT-FOCUS.html"));
}
@Test
public void test_0305_REFRESH() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0305/0305-REFRESH.html"));
}
@Test
public void test_0305_REDIRECT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0305/0305-REDIRECT.html"));
}
@Test
public void test_1002_HIGHLIGHT_STYLES() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1002/1002-HIGHLIGHT-STYLES.html"));
}
@Test
public void test_0207_FRAME_TITLE_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-FRAME-TITLE-SRC.html"));
}
@Test
public void test_0207_OBJECT_IMAGES() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-OBJECT-IMAGES.html"));
}
@Test
public void test_0207_MAP_TITLE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-MAP-TITLE.html"));
}
@Test
public void test_0207_FRAME_TITLE_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-FRAME-TITLE-TEST.html"));
}
@Test
public void test_0207_OBJECT_AUDIO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-OBJECT-AUDIO.html"));
}
@Test
public void test_0207_INPUT_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-INPUT-ALT.html"));
}
@Test
public void test_0207_IFRAME() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-IFRAME.html"));
}
@Test
public void test_0207_NOFRAMES_TEST() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-NOFRAMES-TEST.html"));
}
@Test
public void test_0207_OBJECT_VIDEO() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-OBJECT-VIDEO.html"));
}
@Test
public void test_0207_AREA_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-AREA-ALT.html"));
}
@Test
public void test_0207_NOFRAMES_SRC() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-NOFRAMES-SRC.html"));
}
@Test
public void test_0207_IMG_ALT() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp0207/0207-IMG-ALT.html"));
}
@Test
public void test_frame_target3() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/frames/frame-target3.html"));
}
@Test
public void test_frame_target2() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/frames/frame-target2.html"));
}
@Test
public void test_frame_target1() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/frames/frame-target1.html"));
}
@Test
public void test_1001_AXIS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-AXIS.html"));
}
@Test
public void test_1001_CAPTION() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-CAPTION.html"));
}
@Test
public void test_1001_SCOPE() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-SCOPE.html"));
}
@Test
public void test_1001_SUMMARY() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-SUMMARY.html"));
}
@Test
public void test_1001_THEAD_TBODY_TFOOT_OVERFLOW() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-THEAD-TBODY-TFOOT-OVERFLOW.html"));
}
@Test
public void test_1001_TD_HEADERS() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-TD-HEADERS.html"));
}
@Test
public void test_1001_ABBR() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-ABBR.html"));
}
@Test
public void test_1001_COL_COLGROUP() throws Exception {
doTest(getClass().getResource("/w3.org/WAI/UA/TS/html401/cp1001/1001-COL-COLGROUP.html"));
}

public void allTests() throws Exception {
test_0203_INPUT_ALT();
test_0203_IMG();
test_0203_FRAME_LONGDESC_TEST();
test_0203_ACRONYM();
test_0203_NOFRAMES_SRC();
test_0203_FRAME_LONGDESC_SRC();
test_0203_OBJECT_AUDIO();
test_0203_OBJECT_VIDEO();
test_0203_NOSCRIPT();
test_0203_IFRAME();
test_0203_ABBR();
test_0203_TABLE_HEADERS();
test_0203_OBJECT_IMAGES();
test_0203_NOFRAMES_TEST();
test_0203_FRAME_TITLE_TEST();
test_0203_TABLE_SUMMARY();
test_0203_FRAME_TITLE_SRC();
test_0203_AREA_ALT();
test_0203_MAP_TITLE();
test_0906_EVENT_HANDLERS();
test_0303_CSS_BLINK();
test_0302_OBJECT_AUDIO();
test_0302_EMBED_AUDIO();
test_0302_ANIMATED_IMAGES();
test_0302_OBJECT_VIDEO();
test_0302_EMBED_VIDEO();
test_0414_IGNORE_AUTHOR_USER_STYLE();
test_0414_MULTIPLE_AUTHOR_STYLE();
test_0414_USER_STYLE();
test_0503_A_TARGET_MANUAL_VIEWPORT_OPEN();
test_0503_SCRIPT_MANUAL_VIEWPORT_OPEN();
test_1102_INPUT_CONFIGURATION();
test_1102_ACCESSKEY_FORMS();
test_0304_TOGGLE_SCRIPTS();
test_0905_ONCHANGE();
test_0905_ONFOCUS_ONBLUR();
test_0905_ONMOUSE();
test_0401_FONT_SIZE();
test_return();
test_redirect();
test_new_window_1();
test_0210_UNSUPPORTED_LANGUAGE();
test_0402_FONT_FAMILY();
test_1005_LINK_INFORMATION();
test_1105_TEXT_SIZE();
test_1105_ACTIVATE_LINK();
test_1105_ENABLED_ELEMENTS();
test_1105_SEARCH_TEXT();
test_0403_FONT_COLOR();
test_0907_MOVE_CONTENT_FOCUS_REVERSE();
test_1004_OUTLINE_HEADINGS();
test_1004_OUTLINE_CAPTIONS();
test_1006_HIGHLIGHT_VIEWPORT_SRC();
test_1006_HIGHLIGHT_VIEWPORT_TEST();
test_0505_CONFIRM_FORM_SUBMIT();
test_0505_CONFIRM_FORM_SUBMIT_AUTOMATIC();
test_0301_CSS_DIV_BACKGROUND_IMAGE();
test_0301_CSS_BACKGROUND_IMAGE();
test_0301_BODY_BACKGROUND();
test_Overview();
test_1104_SINGLE_KEY_ANIMATED_IMAGE();
test_1104_SINGLE_KEY_VIDEO();
test_1104_SINGLE_KEY_TEXT_SIZE();
test_1104_SINGLE_KEY_LINK();
test_1104_SINGLE_KEY_GLOBAL_VOLUME();
test_1104_SINGLE_KEY_ENABLED_ELEMENTS();
test_1104_SINGLE_KEY_AUDIO();
test_1104_SINGLE_KEY_SEARCH_TEXT();
test_0208_AREA_ALT();
test_0208_INPUT_ALT();
test_0208_IMG_ALT();
test_0306_IMG();
test_0306_OBJECT_IMAGE();
test_0502_CURRENT_FOCUS_TOP();
test_0101_FRAME_SRC();
test_0101_ACCESSKEY_TEXTAREA();
test_0101_ACCESSKEY_INPUT();
test_0101_INPUT();
test_0101_TABINDEX();
test_0101_RADIO();
test_0101_ACCESSKEY_MAP();
test_0101_ACCESSKEY_LABEL();
test_0101_IFRAME();
test_0101_TEXTAREA();
test_0101_ACCESSKEY_CHECKBOX();
test_0101_ACCESSKEY_RADIO();
test_0101_FRAME_TEST();
test_0101_BUTTON();
test_0101_CHECKBOX();
test_0101_ACCESSKEY_LEGEND();
test_0101_ACCESSKEY();
test_0101_ACCESSKEY_BUTTON();
test_0501_AUTO_FOCUS_CHANGE();
test_0909_TABLE_HEADER();
test_0909_TABLE_CAPTION();
test_0909_LIST_ITEMS();
test_0909_HEADINGS();
test_0909_LINKS();
test_0909_FORMS();
test_0908_TEXT_SEARCH();
test_0102_ONFOCUS_ONBLUR_MAP_AREA();
test_0102_ONCHANGE_INPUT();
test_0102_ONCHANGE_TEXTAREA();
test_0102_ONCLICK_MULTIPLE_EVENTS();
test_0102_ONCLICK();
test_0102_ONCHANGE_SELECT();
test_0102_ONFOCUS_ONBLUR_LABEL();
test_0102_ONFOCUS_ONBLUR_INPUT();
test_0102_ONMOUSE();
test_0102_ONMOUSE_MULTIPLE_EVENTS();
test_0102_ONFOCUS_ONBLUR_LINKS();
test_0102_ONCHANGE_SELECT_MULTIPLE_EVENTS();
test_0102_ONFOCUS_ONBLUR_TEXTAREA();
test_0102_ONFOCUS_ONBLUR_BUTTON();
test_0903_MOVE_CONTENT_FOCUS();
test_0305_REFRESH();
test_0305_REDIRECT();
test_1002_HIGHLIGHT_STYLES();
test_0207_FRAME_TITLE_SRC();
test_0207_OBJECT_IMAGES();
test_0207_MAP_TITLE();
test_0207_FRAME_TITLE_TEST();
test_0207_OBJECT_AUDIO();
test_0207_INPUT_ALT();
test_0207_IFRAME();
test_0207_NOFRAMES_TEST();
test_0207_OBJECT_VIDEO();
test_0207_AREA_ALT();
test_0207_NOFRAMES_SRC();
test_0207_IMG_ALT();
test_frame_target3();
test_frame_target2();
test_frame_target1();
test_1001_AXIS();
test_1001_CAPTION();
test_1001_SCOPE();
test_1001_SUMMARY();
test_1001_THEAD_TBODY_TFOOT_OVERFLOW();
test_1001_TD_HEADERS();
test_1001_ABBR();
test_1001_COL_COLGROUP();	
}

	public static void main(String args[]) throws Exception {
		W3UASuiteTest instance = new W3UASuiteTest();
		instance.allTests();
	}
}

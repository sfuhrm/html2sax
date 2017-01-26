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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Html entities for quick reference.
 * @author Stephan Fuhrmann
 */
class HtmlEntities {
    private final static Logger LOGGER = LoggerFactory.getLogger(HtmlEntities.class);

    /** The resource name to read the entities from. 
     * @see #readFromClassPath() 
     */
	private final static String PROPERTIES_NAME = "/entities/htmlentities.properties";
	
	/** Map from HTML entities like <code>&amp;amp;</code> to
	 ** their Unicode character code number.
	 */
	private final Map<String, Integer> map;

	/** Private constructor. 
	 * @see #getInstance() 
	 */
	private HtmlEntities() throws IOException {
		map = readFromClassPath();
	}

	private static Map<String,Integer> readFromClassPath() throws IOException {
		Map<String,Integer> result = new HashMap<String, Integer>();
		InputStream is = null;
		try {
			LOGGER.debug("Getting properties URL");
			URL url = HtmlEntities.class.getResource(PROPERTIES_NAME);
			LOGGER.debug("Opening properties URL {}", url.toExternalForm());
			is = url.openStream();
			LOGGER.debug("Opened properties file");
			final Properties props = new Properties();
			LOGGER.debug("Loading properties file");
			props.load(is);
			LOGGER.debug("Loaded properties file");
			
			for (Map.Entry<Object,Object> entry : props.entrySet()) {
				String name = (String)entry.getKey();
				if (! name.matches("&.*;")) {
					throw new IllegalArgumentException("Found illegal entry of the form '"+name+"', expecting regex '&.*;'");
				}
				String value = (String)entry.getValue();
				Integer integerValue = Integer.parseInt(value);
				
				result.put(name, integerValue);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Put {} = {}", new Object[]{name, integerValue});
				}
			}
			
			LOGGER.debug("Read "+result.size()+" entries");
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return result;
	}
	/** The singleton instance. */
	private static HtmlEntities INSTANCE;

	/** Gets the singleton instance of html entities. */
	public static synchronized HtmlEntities getInstance() throws IOException {
		if (INSTANCE == null) {
			INSTANCE = new HtmlEntities();
		}
		return INSTANCE;
	}

	/** Gets the named entity reference code point.
	 * @param entityReference an exact HTML entity reference like <code>&amp;amp;</code>.
	 * @return the Unicode character code the entity matches or <code>null</code> if not found.
	 * @see #get(java.lang.String, boolean) 
	 */
	public Integer get(String entityReference) {
		return get(entityReference, false);
	}
	
	/** Gets the named entity reference code point.
	 * @param entityReference a HTML entity reference like <code>&amp;amp;</code>.
	 * @param fuzzy whether or not to do fuzzy searching if not found in an exact way. Fuzzy searching
	 * means at the moment looking for a lower case version.
	 * @return the Unicode character code the entity matches or <code>null</code> if not found.
	 */
	public Integer get(String entityReference, boolean fuzzy) {
		Integer result = map.get(entityReference);
		if (fuzzy && result == null) {
			result = map.get(entityReference.toLowerCase());
		}
		return result;
	}
}

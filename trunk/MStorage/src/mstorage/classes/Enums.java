/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: mstorage.project@gmail.com
 * @date: 2016
 */

package mstorage.classes;

import java.util.*;

/**
 * This is container for enums
 */
public class Enums {
	protected static TreeMap<String, TreeMap<String, String>> Icons = null;

	/**
	 * Icons for appearance styles
	 * 
	 * @return 
	 */
	public static TreeMap<String, TreeMap<String, String>> getIcons() {
		if (null != Enums.Icons) {
			return Enums.Icons;
		}
		
		Enums.Icons = new TreeMap<>();
		
		// Delta style
		TreeMap<String, String> Delta = new TreeMap<>();
		Delta.put("tree_leaf", "page_white.16x16.png");
		Delta.put("tree_closed", "folder.16x16.png");
		Delta.put("tree_open", "folder_go.16x16.png");
		Enums.Icons.put("Delta", Delta);
		
		// Delta style
		TreeMap<String, String> Vega = new TreeMap<>();
		Vega.put("tree_leaf", "page.16x16.png");
		Vega.put("tree_closed", "bullet_toggle_plus.16x16.png");
		Vega.put("tree_open", "bullet_toggle_minus.16x16.png");
		Enums.Icons.put("Vega", Vega);
		
		return Icons;
	}
	
}

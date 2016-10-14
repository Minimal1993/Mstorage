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

package mstorage.components;

import java.nio.file.Path;
import mstorage.storagecollection.File;
import org.apache.commons.lang3.StringUtils;

/**
 * Component to serve all task with crypt of files
 * Work with GUI objects
 */
public class CryptComp {

	
	
	/**
	 * Check whether file is crypted
	 * 
	 * @param file
	 * @return 
	 */
	public static boolean isCryptedFile(Path path) {
		boolean ret = false;

		String extension = "";
		String filename = path.getFileName().toString();

		// Get extention of file
		int i = filename.lastIndexOf('.');
		if (i > 0) {
			extension = StringUtils.lowerCase(filename.substring(i + 1));
		}
		
		if (!extension.isEmpty() && extension.equals("crypt")) {
			ret = true;
		}
		
		return ret;
	}
}

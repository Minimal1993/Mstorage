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

package mstorage.findreplace.types;

import java.nio.file.Path;

/*
* Input data fro FindReplace
*/
public class FindInput {
	protected Path FileName;
	protected String Find;
	protected String Replace = null;
	protected boolean AccordingToCase = false;
	
	/**
	 * 
	 * @param fn
	 * @param f 
	 */
	public FindInput(Path fn, String f){
		this.FileName = fn;
		this.Find = f;
	}
	
	public Path getFileName() {
		return FileName;
	}

	public void setFileName(Path FileName) {
		this.FileName = FileName;
	}

	public String getFind() {
		return Find;
	}

	public void setFind(String Find) {
		this.Find = Find;
	}

	public String getReplace() {
		return Replace;
	}

	public void setReplace(String Replace) {
		if (Replace.equals("")) Replace = null;
		this.Replace = Replace;
	}

	public boolean getAccordingToCase() {
		return AccordingToCase;
	}

	public void setAccordingToCase(boolean AccordingToCase) {
		this.AccordingToCase = AccordingToCase;
	}
}

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

package FindReplace.Types;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Result object witch return by FindReplae class
 */
public class FindResult {
	protected Path FileName;
	protected ArrayList<FindResultItem> Collection;
	
	public FindResult(Path fn, ArrayList<FindResultItem> items){
		this.FileName = fn;
		this.Collection = items;
	}

	public Path getFileName() {
		return FileName;
	}

	public ArrayList<FindResultItem> getCollection() {
		return Collection;
	}
	
	
}

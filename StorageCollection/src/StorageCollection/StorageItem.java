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
package StorageCollection;

//import java.io.*; 
import Utils.FileUtils;

import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import java.io.IOException;

/**
 *
 */
public abstract class StorageItem implements Cloneable {

	protected String FileName;
	protected Path path;
	protected String type;
	protected StorageItem Father;
	protected long lastModified;
	
	public StorageItem getFather() {
		return Father;
	}

	public void setFather(StorageItem Father) {
		this.Father = Father;
	}

	public StorageItem() {
		String classname = this.getClass().getSimpleName();
		if (classname.equals("Folder")) {
			type = "folder";
		} else if (classname.equals("Image")) {
			type = "image";
		} else {
			type = "file";
		}
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String FileName) {
		this.FileName = FileName;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return FileName;
	}

	public void rename(String newname) throws Exception  {
		java.io.File oldfile = new java.io.File(this.path.toAbsolutePath().toString());
		java.io.File newfile = new java.io.File(oldfile.getParent() + "/" + newname);
		oldfile.renameTo(newfile);
		
		this.setFileName(newname);
		this.setPath( Paths.get(newfile.toURI()) );
		
	}

	public void remove() {
		java.io.File rootf = new java.io.File(this.path.toAbsolutePath().toString());
		FileUtils.deleteRecursive(rootf);
	}

	public void move(Folder father) throws IOException {
		Path moveto = Paths.get( father.getPath().toAbsolutePath().toString() + "/" + this.getFileName() );
		
		// Check whether exists file the same name in destination directory
		java.io.File iofile = new java.io.File(moveto.toAbsolutePath().toString());
		if (iofile.exists()) {
			throw new IOException(this.getType() + " '" + this.getFileName() + "' already exists");
		}
		
		Files.move(this.getPath(), moveto, REPLACE_EXISTING);
		
		this.setPath(moveto);
	}
	
	/**
	 * 
	 * @param path 
	 *		  destination file to copy to.
	 * @throws IOException 
	 */
	public void copy(Path path) throws IOException {
		java.io.File iodir = new java.io.File(path.getParent().toAbsolutePath().toString());
		if (!iodir.isDirectory()) {
			throw new IOException(path.toAbsolutePath().toString() + " must be a directory");
		}
		
		java.io.File iofile = new java.io.File(path.toAbsolutePath().toString());
		if (iofile.exists()) {
			throw new IOException(this.getType() + " '" + iofile.getAbsolutePath().toString() + "' already exists");
		}
		
		Files.copy(this.getPath(), iofile.toPath(), REPLACE_EXISTING);
	}
	
	@Override
    protected StorageItem clone() throws CloneNotSupportedException {
        return (StorageItem)super.clone();
    }

	/**
	 * Get new name for new file.
	 * Scan all files in parent and calculating a new unique name
	 * @param father
	 * @return 
	 */
	protected static String _getNewName(Folder father, String name){
		String newName = name;
		int coincidence = 0;
				
		while(!checkNewName(father, newName)) {
			newName = name + " (" + ++coincidence + ")";
		}

		return newName;
	}
	
	/**
	 * Check whether this filename exists in father directory.
	 * For using in getNewName
	 * 
	 * @param Father father
	 * @param String name
	 * @return 
	 */
	private static boolean checkNewName(Folder father, String name){
		if (father.Folders.containsKey(name)) return false;
		if (father.Files.containsKey(name)) return false;
		if (father.Images.containsKey(name)) return false;
		
		return true;
	}
	
}

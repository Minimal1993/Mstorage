/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: naveter@gmail.com
 * @date: 2016
 */
package StorageCollection;

import Utils.FileUtils;

import java.util.TreeMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Folder extends StorageItem {

	public TreeMap<String, File> Files = new TreeMap<>();
	public TreeMap<String, Folder> Folders = new TreeMap<>();
	public TreeMap<String, Image> Images = new TreeMap<>();
	
	protected static String defaultNewName = "newFolder";

	public void addFolder(Folder folder) {
		this.Folders.put(folder.getFileName(), folder);
	}

	public void removeFolder(Folder folder) {
		this.Folders.remove(folder.getFileName());
	}

	public void addFile(File file) {
		this.Files.put(file.getFileName(), file);
	}

	public void removeFile(File file) {
		this.Files.remove(file.getFileName());
	}

	public void addImage(Image image) {
		this.Images.put(image.getFileName(), image);
	}

	public void removeImage(Image image) {
		this.Images.remove(image.getFileName());
	}

	public static Folder create(Folder parent, String name) throws IOException {
		if (name.isEmpty()) {
			throw new IOException("Cant create a folder with empty name");
		}

		Object dir = new java.io.File(parent.path.toAbsolutePath().toString() + "/" + name).mkdir();

		Folder folder = new Folder();
		folder.setFileName(name);
		folder.setPath(Paths.get(parent.path.toAbsolutePath().toString() + "/" + name));
		folder.setType("folder");
		folder.setFather(parent);

		parent.addFolder(folder);

		return folder;
	}

	@Override
	public void remove() {
		super.remove();

		// Delete hisself from father directory
		Folder father = (Folder) this.Father;
		father.removeFolder(this);
		this.Father = null; // break down a link
	}

	@Override
	public void rename(String newname) throws Exception {
		String oldname = this.getFileName();
		
		if (newname.equals(oldname)){
			return;
		}
		
		java.io.File iofile = new java.io.File(this.getPath().getParent().toAbsolutePath().toString() + "/" + newname);
		if (iofile.exists()) {
			throw new Exception("Folder '" + this.getFileName() + "' already exists");
		}
		
		super.rename(newname);

		// Rescan this Folder
		StorageCollection sc = new StorageCollection(this.getPath().toAbsolutePath().toString());
		Folder newfolder = sc.scan();
		
		this.Folders = newfolder.Folders;
		this.Files = newfolder.Files;
		this.Images = newfolder.Images;
		
		// Change info into Father
		Folder curfather = (Folder) this.Father;
		curfather.Folders.remove(oldname);
		curfather.Folders.put(newname, this);
	}
	
	@Override
	public void move(Folder father) throws IOException {
		Folder curfather = (Folder) this.Father;
		
		super.move(father);
		
		this.setFather(father);
		
		// Rescan this Folder
		StorageCollection sc = new StorageCollection(this.getPath().toAbsolutePath().toString());
		Folder newfolder = sc.scan();
		
		this.Folders = newfolder.Folders;
		this.Files = newfolder.Files;
		this.Images = newfolder.Images;
		
		// Change new father
		Folder newfather = (Folder) this.Father;
		newfather.Folders.put(this.getFileName(), this);
		
		// Change info into old Father
		curfather.Folders.remove(this.getFileName());
	}
	
	/**
	 * Get a name for new folder
	 * 
	 * @param father
	 * @return 
	 */
	public static String getNewName(Folder father){
		return StorageItem._getNewName(father, defaultNewName);
	}
	
	/**
	 * Looking for File pointed by Path
	 * 
	 * @param Path path
	 * @return File
	 */
	public File findFile(Path path){
		return this._findFile(this, path);
	}
	
	private File _findFile(Folder fn, Path path){
		// Check current Files
		if (this.Files.size() > 0) {
			TreeMap<String, File> files = (TreeMap<String, File>) fn.Files;
			Set<Map.Entry<String, File>> fileSet = files.entrySet();
			for (Map.Entry<String, File> f : fileSet) {
				if (f.getValue().getPath().toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
					return f.getValue();
				}
			}
		}
		
		// Check current Folders
		if (this.Folders.size() > 0) {
			TreeMap<String, Folder> folders = (TreeMap<String, Folder>) fn.Folders;
			Set<Map.Entry<String, Folder>> folderSet = folders.entrySet();
			for (Map.Entry<String, Folder> fld : folderSet) {
				File founded = this._findFile(fld.getValue(), path);
				if (null != founded) {
					return founded;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Find Folder in this folder
	 * 
	 * @param Path path
	 * @return Folder
	 */
	public Folder findFolder(Path path){
		// If we lokking for current folder
		if (this.getPath().toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
			return this;
		}
		
		return this._findFolder(this, path);
	}
	
	private Folder _findFolder(Folder fn, Path path){
		if (this.Folders.size() > 0) {
			TreeMap<String, Folder> folders = (TreeMap<String, Folder>) fn.Folders;
			Set<Map.Entry<String, Folder>> folderSet = folders.entrySet();
			for (Map.Entry<String, Folder> fld : folderSet) {
				if (fld.getValue().getPath().toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
					return fld.getValue();
				}
				
				Folder founded = this._findFolder(fld.getValue(), path);
				if (null != founded) {
					return founded;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void copy(Path path) throws IOException {
		throw new IOException(this.getClass().getEnclosingMethod().toString() + " is not avalibal for Folder");
	}
	
	/**
	 * Find out root folder of whole storage tree
	 * 
	 * @return  Folder - root folder
	 */
	public Folder getRootFolder(){
		Folder rootFolder = this;
		
		while(null != rootFolder.getFather()) {
			rootFolder = (Folder)rootFolder.getFather();
		}
		
		return rootFolder;
	}


}

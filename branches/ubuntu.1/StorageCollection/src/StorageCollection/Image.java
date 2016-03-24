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

import java.util.regex.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 *
 */
public class Image extends StorageItem {

	/**
	 * Separator for ParentFileName and OrigName
	 */
	public static String Separator = "_";

	/**
	 * Name picture without parent name
	 */
	protected String OrigName = null;

	/**
	 * Name of parent File
	 */
	protected String ParentFileName = null;
	
	public String getOrigName() {
		return this.OrigName;
	}

	public String getParentFileName() {
		return this.ParentFileName;
	}
	
	public void changeParentFileName(String name) throws Exception  {
		this.ParentFileName = name;
		this.rename(this.OrigName);
	}

	/**
	 * When Image set the FileName, he calculate OrigName and ParentFileName if
	 * can. This action use when scanning
	 *
	 * @param FileName
	 */
	@Override
	public void setFileName(String FileName) {
		super.setFileName(FileName);

		if (this.FileName.isEmpty()) {
			return;
		}

		Pattern pat = Pattern.compile("^([^" + Image.Separator + "]+)_(.+)$");
		Matcher mat = pat.matcher(this.FileName);

		if (mat.matches()) {
			this.ParentFileName = mat.group(1);
			this.OrigName = mat.group(2);
		}

	}

	@Override
	public void remove() {
		java.io.File f = new java.io.File(this.getPath().toAbsolutePath().toString());
		FileUtils.deleteRecursive(f);
		
		// Delete data from File and Folder about hisself
		File file = (File)this.getFather();
		file.removeImage(this);
	}

	@Override
	public void rename(String newname) throws Exception {
		String oldname = this.getFileName();
		String filename = this.getParentFileName() + Image.Separator + newname;
		
		super.rename(filename);
		
		this.OrigName = newname;
		
		// father's file and directory
		File father_file = (File) this.getFather();
		father_file.Images.remove(oldname);
		father_file.addImage(this);
		
		Folder father_folder = (Folder) father_file.getFather();
		father_folder.Images.remove(oldname);
		father_folder.addImage(this);

	}

	@Override
	public void move(Folder father) throws IOException {
		File curfile = (File) this.Father;
		Folder curfolder = (Folder) curfile.getFather();
		
		super.move(father);
		
		curfolder.Images.remove(this.getFileName());
		father.addImage(this);
	}

	@Override
	public void copy(Path path) throws IOException {
		String newname = path.getFileName() + "_" + this.getOrigName();
		
		super.copy(Paths.get(path.getParent().toAbsolutePath().toString() + "/" + newname));
	}
	
	/**
	 * Create a new image in the storage tree
	 * 
	 * @param parent
	 *		  File father
	 * @param file
	 *        java.io.File, picture to copy from
	 * @return the new File object placed in storage tree
	 */
	public static Image create(File parent, java.io.File file) throws IOException{
		String newname = parent.getFileName() + "_" + file.getName();
		
		// Check if old name satisfy demand for new file name
		Pattern pat = Pattern.compile("^([^" + Image.Separator + "]+)_(.+)$");
		Matcher mat = pat.matcher(file.getName());
		if (mat.matches() && mat.group(1).equals(parent.getFileName())) {
			newname = file.getName();
		}
		
		java.io.File newImage = new java.io.File(parent.getFather().path.toAbsolutePath().toString() + "/" + newname);
		if (newImage.exists()) {
			throw new IOException("Image '" + newImage.getAbsolutePath().toString() + "' already exists");
		}
		
		Files.copy(file.toPath(), newImage.toPath(), REPLACE_EXISTING);
		
		Image img = new Image();
		img.setFileName(newname);
		img.setPath(newImage.toPath());
		img.setType("image");
		parent.addImage(img);
		img.setFather(parent);
		
		return img;
	}
	
}

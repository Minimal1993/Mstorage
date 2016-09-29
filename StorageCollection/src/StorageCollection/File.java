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

import Utils.FileUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TreeMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.Map;
import java.util.Set;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class File extends StorageItem {

	public TreeMap<String, Image> Images = new TreeMap<>();
	
	protected static String defaultNewName = "newFile";

	public static File create(Folder parent, String name) throws IOException {
		if (name.isEmpty()) {
			throw new IOException("Cant create a file with empty name");
		}

//		java.io.File newFile = new java.io.File(parent.path.toAbsolutePath().toString() + "/" + name);
//		newFile.createNewFile();

		Path newFile = Paths.get(parent.path.toAbsolutePath().toString() + "/" + name);
		ArrayList<String> lines =  new ArrayList<>();
		Files.write(newFile, lines, StandardCharsets.UTF_8);

		File file = new File();
		file.setFileName(name);
		file.setPath(Paths.get(parent.path.toAbsolutePath().toString() + "/" + name));
		file.setType("file");
		file.setFather(parent);

		parent.addFile(file);

		return file;
	}

	public String getContent() throws IOException {
		return new String(Files.readAllBytes(this.getPath()));
	}

	public void save(String text) throws IOException {
		java.io.File iofile = new java.io.File(this.getPath().toAbsolutePath().toString());
		FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.close();
	}

	public void addImage(Image image) throws IOException {
		Image exist_img = this.Images.get(image.getFileName());
		if (this.Images.containsKey(image.getFileName())) {
			throw new IOException("Picture '" + image.getFileName() + "' already exists");
		}

		this.Images.put(image.getFileName(), image);
		
		Folder folder = (Folder) this.Father;
		folder.addImage(image);
	}

	public void removeImage(Image image) {
		this.Images.remove(image.getFileName());
		
		Folder folder = (Folder) this.Father;
		folder.removeImage(image);
	}

	@Override
	public void remove() {
		// Firstly delete images
		if (this.Images.size() > 0) {
			// Clone is need because this.Images will change as Father in Image object
			TreeMap<String, Image> images = (TreeMap<String, Image>) this.Images.clone();
			Set<Map.Entry<String, Image>> imgSet = images.entrySet();
			for (Map.Entry<String, Image> image : imgSet) {
				image.getValue().remove();
			}
		}

		java.io.File f = new java.io.File(this.getPath().toAbsolutePath().toString());
		FileUtils.deleteRecursive(f);
		
		// Rescan parent Folder
		Folder father = (Folder) this.Father;
		StorageCollection sc = new StorageCollection(father.getPath().toAbsolutePath().toString());
		Folder newfolder = sc.scan();
		
		father.Folders = newfolder.Folders;
		father.Files = newfolder.Files;
		father.Images = newfolder.Images;
		
	}

	@Override
	public void rename(String newname) throws Exception  {
		if (newname.equals(this.getFileName())){
			return;
		}
		
		java.io.File iofile = new java.io.File(this.getPath().getParent().toAbsolutePath().toString() + "/" + newname);
		if (iofile.exists() && !StringUtils.lowerCase(newname).equals(StringUtils.lowerCase(this.getFileName()))) {
			throw new Exception("File '" + this.getFileName() + "' already exists");
		}
		
		super.rename(newname);

		// Rename all images is owned by File 
		if (this.Images.size() > 0) {
			// Clone is need because this.Images will change as Father in Image object
			TreeMap<String, Image> images = (TreeMap<String, Image>) this.Images.clone();
			Set<Map.Entry<String, Image>> imgSet = images.entrySet();
			for (Map.Entry<String, Image> image : imgSet) {
				image.getValue().changeParentFileName(newname);
			}
		}
		
		// Rescan this Folder
		StorageCollection sc = new StorageCollection(this.getPath().getParent().toString());
		Folder newfolder = sc.scan();
		
		// Change father's folder
		Folder curfather = (Folder) this.Father;
		curfather.Files = newfolder.Files;
		curfather.Images = newfolder.Images;
		
		this.Images = newfolder.Files.get(newname).Images;
		
	}

	@Override
	public void move(Folder father) throws IOException {
		Folder curfather = (Folder) this.Father;
		
		super.move(father);
		
		this.setFather(father);
		
		if (this.Images.size() > 0) {
			// Clone is need because this.Images will change as Father in Image object
			TreeMap<String, Image> images = (TreeMap<String, Image>) this.Images.clone();
			Set<Map.Entry<String, Image>> imgSet = images.entrySet();
			for (Map.Entry<String, Image> image : imgSet) {
				image.getValue().move(father);
			}
		}
		
		// Rescan this Folder
		StorageCollection sc = new StorageCollection(father.getPath().toAbsolutePath().toString());
		Folder newfolder = sc.scan();
		
		father.Folders = newfolder.Folders;
		father.Files = newfolder.Files;
		father.Images = newfolder.Images;
		
		// Change new father
		this.Father = father;
		this.Images = father.Files.get(this.getFileName()).Images;
		
		// Change info into old Father
		curfather.Files.remove(this.getFileName());
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
	 * Copy File either storage tree path or path placed outside.
	 * 
	 * @param Path 
	 *        destination file to copy to
	 * @throws IOException 
	 */
	@Override
	public void copy(Path path) throws IOException {
		java.io.File iodir = new java.io.File(path.getParent().toAbsolutePath().toString());
		if (!iodir.isDirectory()) {
			throw new IOException(path.getParent().toAbsolutePath().toString() + " must be a directory");
		}
		java.io.File iofile = new java.io.File(path.toAbsolutePath().toString());
		if (iofile.exists()) {
			throw new IOException(this.getType() + " '" + iofile.getAbsolutePath().toString() + "' already exists");
		}
		
		Folder rootFolder = ((Folder)this.getFather()).getRootFolder();
				
		// Whether parent folder exists in storage tree
		Folder folder2copy = rootFolder.findFolder(path.getParent());
		if (null != folder2copy) {
			File newfile = this.create(folder2copy, path.getFileName().toString());
			newfile.save(this.getContent());
			
			if (this.Images.size() > 0) {
				TreeMap<String, Image> images = (TreeMap<String, Image>) this.Images;
				Set<Map.Entry<String, Image>> imgSet = images.entrySet();
				for (Map.Entry<String, Image> image : imgSet) {
					iofile = new java.io.File(image.getValue().getPath().toAbsolutePath().toString());
					Image newimage = Image.create(newfile, iofile);
				}
			}
		}
		else {
			// Physical copy without link to storage tree
			super.copy(path);

			if (this.Images.size() > 0) {
				TreeMap<String, Image> images = (TreeMap<String, Image>) this.Images;
				Set<Map.Entry<String, Image>> imgSet = images.entrySet();
				for (Map.Entry<String, Image> image : imgSet) {
					image.getValue().copy(path);
				}
			}
		}
		
		

	}

}

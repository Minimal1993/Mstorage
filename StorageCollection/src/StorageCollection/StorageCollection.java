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

import java.util.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.lang.Object.*;

public final class StorageCollection {

	Path Dirname; // root directory
	public Folder FStorage; // main storage as Folder object

	public StorageCollection(String dirname) {
		try {
			Dirname = Paths.get(dirname);

			// TODO: Make sure directory is exists
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		FStorage = new Folder();
		FStorage.setFileName("/");
		FStorage.setPath(Dirname);
		FStorage.setType("folder");

	}

	public Folder scan() {

		FStorage = getFileNames(FStorage, Dirname);

		return FStorage;
	}

	/**
	 * For recursive implement to find out all files and directories
	 *
	 * @param fn
	 * @param dir
	 * @return Folder
	 */
	public Folder getFileNames(Folder fn, Path dir) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {
				if (path.toFile().isDirectory()) {
					// TODO: There must use correct method Folder.create()
					Folder folder = new Folder();
					folder.setFileName(path.getFileName().toString());
					folder.setPath(path);
					folder.setType("folder");
					folder.setFather(fn);
					fn.Folders.put(folder.getFileName(), getFileNames(folder, path));
				} else {
					String extension = "";
					String filename = path.getFileName().toString();

					// Get extention of file
					int i = filename.lastIndexOf('.');
					if (i > 0) {
						extension = filename.substring(i + 1);
					}

					// If picture
					if (extension.equals("jpg")
							|| extension.equals("jpeg")
							|| extension.equals("gif")
							|| extension.equals("png")) {

						// TODO: There must use correct method Image.create()
						Image file = new Image();
						file.setFileName(path.getFileName().toString());
						file.setPath(path);
						file.setType("image");
						fn.Images.put(file.getFileName(), file);

					} else {
						// TODO: There must use correct method File.create()
						File file = new File();
						file.setFileName(path.getFileName().toString());
						file.setPath(path);
						file.setType("file");
						file.setFather(fn);
						fn.Files.put(file.getFileName(), file);
					}
				}

			} // END for

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set images for files into folder
		setImagesToFiles(fn);

		return fn;
	}

	/**
	 * Set images for files into folder
	 *
	 * @param fn
	 */
	public static void setImagesToFiles(Folder fn) {
		if (0 == fn.Images.size()) {
			return;
		}

		Set<Map.Entry<String, Image>> ImageSet = fn.Images.entrySet();
		for (Map.Entry<String, Image> img : ImageSet) {

			Set<Map.Entry<String, File>> fileSet = fn.Files.entrySet();
			for (Map.Entry<String, File> file : fileSet) {
				if (file.getValue().getFileName().equals(img.getValue().getParentFileName())) {
					file.getValue().Images.put(img.getValue().getFileName(), img.getValue());

					img.getValue().setFather(file.getValue());
				}
			}
		}
	}

	/**
	 * Move item to another item
	 *
	 * @param item
	 * @param toitem
	 */
	public void move(StorageItem item, StorageItem toitem) {

	}
	


}

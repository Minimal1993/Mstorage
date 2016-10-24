/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstorage.storagecollection;

import mstorage.utils.TestUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilya.gulevskiy
 */
public class FileTest {

	protected Folder RootFolder;

	@Before
	public void setUp() {
		this.RootFolder = TestUtils.beforeMethod();
	}

	@Test
	public void testCreate() throws IOException {
		File file = File.create(this.RootFolder, "created");

		java.io.File f = new java.io.File(this.RootFolder.path.toAbsolutePath().toString() + "/created");

		assertTrue(f.exists());
		assertEquals(this.RootFolder.path, file.getFather().path);
	}

	@Test
	public void testGetContent() throws IOException {
		String content = "super content";
		File file = this.RootFolder.Files.get("firstfile");

		// Write test content
		java.io.File iofile = new java.io.File(file.getPath().toAbsolutePath().toString());
		FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		String fromfile = file.getContent();

		assertEquals(content, fromfile);

	}

	@Test
	public void testSave() throws IOException {
		String content = "super content for save info test file";
		File file = this.RootFolder.Files.get("firstfile");
		file.save(content);

		String fromfile = new String(Files.readAllBytes(file.getPath()));

		assertEquals(content, fromfile);
	}

	@Test
	public void testRemove() throws IOException, Exception {
		File firstfile = this.RootFolder.Folders.get("first").Files.get("firstfile");

		// Create copy of Images collection
		TreeMap<String, Image> images = new TreeMap<String, Image>();
		Set<Map.Entry<String, Image>> imgSet1 = firstfile.Images.entrySet();
		for (Map.Entry<String, Image> image : imgSet1) {
			Image img = (Image) image.getValue().clone();
			images.put(img.getFileName(), img);
		}

		firstfile.remove();

		// Check file is exists
		java.io.File f = new java.io.File(firstfile.path.toAbsolutePath().toString());

		assertFalse(f.exists());
		assertFalse(this.RootFolder.Folders.get("first").Files.containsKey("firstfile"));

		// Check whether image is exists
		Set<Map.Entry<String, Image>> imgSet = images.entrySet();
		for (Map.Entry<String, Image> image : imgSet) {
			f = new java.io.File(image.getValue().path.toAbsolutePath().toString());

			assertFalse(f.exists());
			assertFalse(this.RootFolder.Folders.get("first").Images.containsKey(image.getValue().getFileName()));
		}

	}

	@Test
	public void testRename() throws Exception {
		String oldname = "firstfile";
		String newname = "newfirstfile";
		File firstfile = this.RootFolder.Folders.get("first").Files.get(oldname);

		// Create copy of Images collection
		TreeMap<String, Image> images = new TreeMap<String, Image>();
		Set<Map.Entry<String, Image>> imgSet1 = firstfile.Images.entrySet();
		for (Map.Entry<String, Image> image : imgSet1) {
			Image img = (Image) image.getValue().clone();
			images.put(img.getFileName(), img);
		}

		firstfile.rename(newname);

		// Check parent
		assertFalse(this.RootFolder.Folders.get("first").Files.containsKey(oldname));
		assertTrue(this.RootFolder.Folders.get("first").Files.containsKey(newname));
		assertEquals(newname, this.RootFolder.Folders.get("first").Files.get(newname).getFileName());

		// Check file itself
		java.io.File f = new java.io.File(firstfile.path.getParent().toString() + "/" + newname);
		assertTrue(f.exists());

		// Check old Imagesfiles dont exist anymore
		Set<Map.Entry<String, Image>> imgSet2 = images.entrySet();
		for (Map.Entry<String, Image> image : imgSet2) {
			f = new java.io.File(image.getValue().path.toAbsolutePath().toString());
			assertFalse(f.exists());
		}

	}

	@Test
	public void testMove() throws IOException, Exception {
		File file_orig = this.RootFolder.Folders.get("first").Files.get("firstfile");
		java.io.File f = new java.io.File(file_orig.getPath().toAbsolutePath().toString());
		java.io.File img = new java.io.File(
			file_orig.Images.get("firstfile_secondimage.jpg").getPath().toAbsolutePath().toString()
		);
		Folder folder_to = this.RootFolder.Folders.get("second");

		file_orig.move(folder_to);

		assertEquals(file_orig.getPath(), folder_to.Files.get("firstfile").getPath());
		assertEquals(
			file_orig.Images.get("firstfile_firstimage.jpg").getPath(), 
			folder_to.Files.get("firstfile").Images.get("firstfile_firstimage.jpg").getPath()
		);
		assertEquals(file_orig.Images.size(), folder_to.Files.get("firstfile").Images.size());
		assertTrue(folder_to.Images.containsKey("firstfile_firstimage.jpg"));

		assertNull( this.RootFolder.Folders.get("first").Files.get("firstfile") );
		
		assertFalse(f.exists());
		assertFalse(img.exists());
	}
	
	@Test
	public void testGetNewName() {
		String name = "newFile";
		Folder father = this.RootFolder.Folders.get("first");

		assertEquals(name, File.getNewName(father));

		try {

			// Create new file 
			File f1 = File.create(father, name);
			assertEquals(name + " (1)", File.getNewName(father));

			// Create new file 
			File f2 = File.create(father, name + " (1)");
			assertEquals(name + " (2)", File.getNewName(father));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Copy to external file system, not to storage tree
	 * 
	 * @throws IOException
	 * @throws Exception 
	 */
	@Test
	public void testCopyExternal() throws IOException, Exception {
		File file_orig = this.RootFolder.Folders.get("first").Files.get("firstfile");
		java.io.File f = new java.io.File(file_orig.getPath().toAbsolutePath().toString());
		java.io.File img = new java.io.File(
			file_orig.Images.get("firstfile_secondimage.jpg").getPath().toAbsolutePath().toString()
		);
		java.io.File folder_to = new java.io.File(TestUtils.OutsideRoot);

		String newfileURL = folder_to.toPath().toAbsolutePath().toString() + "/newfile";
		file_orig.copy(Paths.get(newfileURL));

		java.io.File f2 = new java.io.File(newfileURL);
		java.io.File img2 = new java.io.File(
			folder_to.toPath().toAbsolutePath().toString() + "/newfile_secondimage.jpg"
		);
		java.io.File img3 = new java.io.File(
			folder_to.toPath().toAbsolutePath().toString() + "/newfile_firstimage.jpg"
		);
		
		// Check file was copied phisically
		assertTrue(f2.exists());
		assertTrue(img2.exists());
		assertTrue(img3.exists());
		assertTrue(f.exists());
		assertTrue(img.exists());
		
	}
	
	/**
	 * Copy to folder of storage tree
	 * 
	 * @throws IOException
	 * @throws Exception 
	 */
	@Test
	public void testCopyInternal() throws IOException, Exception {
		File file_orig = this.RootFolder.Folders.get("first").Files.get("firstfile");
		java.io.File f = new java.io.File(file_orig.getPath().toAbsolutePath().toString());
		java.io.File img = new java.io.File(
			file_orig.Images.get("firstfile_secondimage.jpg").getPath().toAbsolutePath().toString()
		);
		Folder folder_to = this.RootFolder.Folders.get("second");

		String newfileURL = folder_to.getPath().toAbsolutePath().toString() + "/newfile";
		file_orig.copy(Paths.get(newfileURL));

		// filename will not changed
		java.io.File f2 = new java.io.File(folder_to.getPath().toAbsolutePath().toString() + "/newfile");
		java.io.File img2 = new java.io.File(
			folder_to.getPath().toAbsolutePath().toString() + "/newfile_firstfile_secondimage.jpg"
		);
		
		// Check file was copied phisically
		assertTrue(f2.exists());
		assertTrue(img2.exists());
		assertTrue(f.exists());
		assertTrue(img.exists());
		
		// Check data is saved into storage tree
		assertTrue(folder_to.Files.containsKey("newfile"));
		assertEquals(2, folder_to.Files.get("newfile").Images.size());
		assertEquals(
			"firstfile_secondimage.jpg", 
			folder_to.Files.get("newfile").Images.get("newfile_firstfile_secondimage.jpg").getOrigName()
		);
		assertTrue(folder_to.Images.containsKey("newfile_firstfile_firstimage.jpg"));
		
	}

}

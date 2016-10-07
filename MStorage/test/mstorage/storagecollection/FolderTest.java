/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstorage.storagecollection;

import mstorage.utils.TestUtils;
import static mstorage.utils.TestUtils.Root;

import java.util.*;
import java.io.IOException;
import java.nio.file.*;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilya.gulevskiy
 */
public class FolderTest {

	protected Folder RootFolder;

	public FolderTest() {
	}

	@Before
	public void setUp() {
		this.RootFolder = TestUtils.beforeMethod();
	}

	@Test
	public void testCreate() throws IOException {
		Folder folder = Folder.create(this.RootFolder, "created");

		java.io.File f = new java.io.File(this.RootFolder.path.toAbsolutePath().toString() + "/created");

		assertTrue(f.exists());
		assertEquals(this.RootFolder.path, folder.getFather().path);
	}

	@Test
	public void testRemove() {
		Folder folder = this.RootFolder.Folders.get("first");
		folder.remove();

		java.io.File f = new java.io.File(folder.path.toAbsolutePath().toString());

		assertFalse(f.exists());
		assertNull(this.RootFolder.Folders.get("first"));
	}

	@Test
	public void testRename() throws Exception {
		// Try to rename first
		Folder folder = this.RootFolder.Folders.get("first");
		folder.rename("newfirst");

		java.io.File newfile = new java.io.File(
				this.RootFolder.Folders.get("newfirst").getPath().getParent().toString() + "/newfirst"
		);
		assertTrue(newfile.exists());
		assertEquals("newfirst", folder.getFileName());

		// Chek child must has a right absolute path
		Path path = this.RootFolder.Folders.get("newfirst").Files.get("firstfile").getPath().getParent();
		assertEquals(path, folder.getPath());

	}

	@Test
	public void testMove() throws IOException {
		Folder folder_orig = this.RootFolder.Folders.get("first");
		Folder folder_to = this.RootFolder.Folders.get("second");

		folder_orig.move(folder_to);

		assertEquals(folder_orig.getPath(), folder_to.Folders.get("first").getPath());
		assertEquals(folder_orig.Files.get("firstfile"), folder_to.Folders.get("first").Files.get("firstfile"));
		assertEquals(folder_orig.Files.size(), folder_to.Folders.get("first").Files.size());
		assertEquals(folder_orig.Folders.size(), folder_to.Folders.get("first").Folders.size());

		assertNull(this.RootFolder.Folders.get("first"));

	}

	@Test
	public void testGetNewName() {
		String name = "newFolder";
		Folder father = this.RootFolder.Folders.get("first");

		assertEquals(name, Folder.getNewName(father));

		try {

			// Create new folder
			Folder f1 = Folder.create(father, name);
			assertEquals(name + " (1)", Folder.getNewName(father));

			// Create new folder
			Folder f2 = Folder.create(father, name + " (1)");
			assertEquals(name + " (2)", Folder.getNewName(father));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testFindFile(){
		Path FirstPath = this.RootFolder.Folders.get("second").Files.get("firstfile2").getPath();
		File FirstFile = this.RootFolder.Folders.get("second").Files.get("firstfile2");
		File FirstFind = this.RootFolder.findFile(FirstPath);
		
		assertEquals(FirstFile, FirstFind);
		
		Path SecondPath = this.RootFolder.Files.get("firstfile").getPath();
		File SecondFile = this.RootFolder.Files.get("firstfile");
		File SecondFind = this.RootFolder.findFile(SecondPath);
		
		assertEquals(SecondFile, SecondFind);
	}
	
	@Test
	public void testFindFolder(){
		// Not root folder
		Path FirstPath = this.RootFolder.Folders.get("first").Folders.get("first").getPath();
		Folder FirstFolder = this.RootFolder.Folders.get("first").Folders.get("first");
		
		assertEquals(FirstFolder, this.RootFolder.findFolder(FirstPath));
		
		Path SecondPath = this.RootFolder.Folders.get("second").getPath();
		Folder SecondFolder = this.RootFolder.Folders.get("second");
		
		assertEquals(SecondFolder, this.RootFolder.findFolder(SecondPath));
		
		
		// Rood folder as destination path
		Folder rootFolder = FirstFolder.getRootFolder();
		Path ThirdPath = Paths.get(rootFolder.getPath().toAbsolutePath().toString() + "/somefile");
		Folder folder2copy = rootFolder.findFolder(ThirdPath.getParent());
		
		assertEquals(rootFolder, folder2copy);
	}
	
	@Test
	public void testGetRootFolder(){
		// Test not root folder as instance
		Folder folder = this.RootFolder.Folders.get("first").Folders.get("first");
		assertEquals(folder.getRootFolder(), this.RootFolder);
		
		// Root folder as instance
		Folder folder2 = this.RootFolder;
		assertEquals(folder2.getRootFolder(), this.RootFolder);
	}

}

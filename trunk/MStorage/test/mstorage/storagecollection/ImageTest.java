/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstorage.storagecollection;

import mstorage.utils.TestUtils;

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
public class ImageTest {
	
	protected Folder RootFolder;
	
	public ImageTest() {
	}
	
	@Before
	public void setUp() {
		this.RootFolder = TestUtils.beforeMethod();
	}

	@Test
	public void testSetFileName() {
		Image img = new Image();
		img.setFileName("Троцкий Лейба_очхоркартинка.jpg");
		assertEquals("Троцкий Лейба", img.getParentFileName());
		assertEquals("очхоркартинка.jpg", img.getOrigName());
	}

	@Test
	public void testRemove() {
		Image img = this.RootFolder.Folders.get("first").Files.get("firstfile").Images.get("firstfile_secondimage.jpg");
		
		img.remove();
		
		java.io.File f = new java.io.File(img.path.toAbsolutePath().toString());
		assertFalse(f.exists());
		
		// Image dont contain in File and Folder
		assertFalse(this.RootFolder.Folders.get("first").Files.get("firstfile").Images.containsKey("firstfile_secondimage.jpg"));
		assertFalse(this.RootFolder.Folders.get("first").Images.containsKey("firstfile_secondimage.jpg"));
	}

	@Test
	public void testRename() throws Exception {
		Image img = this.RootFolder.Folders.get("first").Files.get("firstfile").Images.get("firstfile_firstimage.jpg");
		
		img.rename("newfirstimage.jpg");
		
		// Picture is exists
		java.io.File f = new java.io.File(img.path.toAbsolutePath().toString());
		assertTrue(f.exists());
		
		// Check exists in File and Folder
		assertTrue(this.RootFolder.Folders.get("first").Files.get("firstfile").Images.containsKey(img.getFileName()));
		assertTrue(this.RootFolder.Folders.get("first").Images.containsKey(img.getFileName()));
		
	}

	@Test
	public void testMove() throws Exception {
		Image img = this.RootFolder.Folders.get("first").Files.get("firstfile").Images.get("firstfile_firstimage.jpg");
		String abs_path = img.getPath().toAbsolutePath().toString();
		Folder folder_to = this.RootFolder.Folders.get("second");
		
		img.move(folder_to);
		
		assertTrue(folder_to.Images.containsKey(img.getFileName()));
		
		java.io.File f = new java.io.File(abs_path);
		assertFalse(f.exists());
	}
	
	@Test
	public void testCreate(){
		Image img = this.RootFolder.Folders.get("first").Files.get("firstfile").Images.get("firstfile_firstimage.jpg");
		java.io.File from = new java.io.File(img.getPath().toAbsolutePath().toString());
		
		Image newimg = null;
		try {
			newimg = Image.create(this.RootFolder.Files.get("firstfile"), from);
		}
		catch(IOException e) {
			System.out.println("Cant crate a file");
			return;
		}
		
		assertEquals(img.getOrigName(), newimg.getOrigName());
		assertEquals(1, ((File)newimg.getFather()).Images.size());
	}
	
}

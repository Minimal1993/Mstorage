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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import StorageCollection.File;
import StorageCollection.Image;
import StorageCollection.Folder;
import StorageCollection.StorageCollection;
import Utils.FileUtils;
import Utils.TestUtils;
import static Utils.TestUtils.Root;

import java.nio.file.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author ilya.gulevskiy
 */
public class StorageCollectionTest {

	protected Folder RootFolder;

	@Before
	public void setUp() {
		this.RootFolder = TestUtils.beforeMethod();
	}
	
	/**
	 * Test of initialization of class StorageCollection.
	 */
	@Test
	public void testStorageCollection() {
		StorageCollection sc = new StorageCollection(this.RootFolder.getPath().toAbsolutePath().toString());
		sc.scan();

	}

	/**
	 * Test of scan method, of class StorageCollection.
	 */
	@Test
	public void testScan() {

	}

	/**
	 * Check whether correct separates pictures with files
	 */
	@Test
	public void testSetImagesToFiles() {
		Folder folder = new Folder();

		File file = new File();
		file.setFileName("Троцкий Лейба");
		Image img = new Image();
		img.setFileName("Троцкий Лейба_очхоркартинка.jpg");
		Image img2 = new Image();
		img2.setFileName("Троцкий Лейба_просто классная жвачка.gif");

		folder.Files.put(file.getFileName(), file);
		folder.Images.put(img.getFileName(), img);
		folder.Images.put(img2.getFileName(), img2);

		file = new File();
		file.setFileName("Моисей Урицкий");
		img = new Image();
		img.setFileName("Моисей Урицкий_ещё один враг.jpg");

		folder.Files.put(file.getFileName(), file);
		folder.Images.put(img.getFileName(), img);

		file = new File();
		file.setFileName("Иешуа Соломон Свердлов");
		img = new Image();
		img.setFileName("Иешуа Соломон_файл никуда не подходит.jpg");

		folder.Files.put(file.getFileName(), file);
		folder.Images.put(img.getFileName(), img);

		// In fact 3 files and 4 pictures
		StorageCollection.setImagesToFiles(folder);

		File f1 = folder.Files.get("Троцкий Лейба");
		assertEquals(2, f1.Images.size());
		assertEquals("очхоркартинка.jpg", f1.Images.get("Троцкий Лейба_очхоркартинка.jpg").getOrigName());
		assertEquals("просто классная жвачка.gif", f1.Images.get("Троцкий Лейба_просто классная жвачка.gif").getOrigName());

		File f2 = folder.Files.get("Моисей Урицкий");
		assertEquals(1, f2.Images.size());
		assertEquals("ещё один враг.jpg", f2.Images.get("Моисей Урицкий_ещё один враг.jpg").getOrigName());

	}

	@Test
	public void testCreate() {
		TestUtils.createTestStorage();

	}
	
}

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
package mstorage;

import mstorage.storagecollection.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilya.gulevskiy
 */
public class MStorageTest {
	
	public MStorageTest() {
	}

	@Test
	public void testMain() {
	}
	
	@Test
	public void testStorageCollection() {
		System.out.println("Hello from test");
		File file = new File();
		file.setFileName("superfilename");
	}
	
}

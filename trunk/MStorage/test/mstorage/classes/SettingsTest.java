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
package mstorage.classes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.TreeMap;

/**
 *
 * @author ilya.gulevskiy
 */
public class SettingsTest {
	
	Settings Settings = null;
	
	public SettingsTest() {
		this.Settings = mstorage.classes.Settings.getInstance();
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		this.Settings.setProperty("first", "1");
		this.Settings.setProperty("second", "2");
		this.Settings.setProperty("third", "3");
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * It must be confidence that file is not rewriting every time when it is initialized
	 */
	@Test
	public void testSettings() {
		this.Settings.setProperty("first", "11"); // write to file
		
		// The same user test must has the same settings file
		Settings newSettings = mstorage.classes.Settings.getInstance(); // read from file
		assertEquals("11", newSettings.getProperty("first"));
		
		// Origin object mustn't know about that ghange
		newSettings.setProperty("first", "111"); // write to file
		assertNotEquals("11", this.Settings.getProperty("first")); // will never read the file
	}

	@Test
	public void testGetProperty() {
		assertEquals("1", this.Settings.getProperty("first"));
		assertEquals("2", this.Settings.getProperty("second"));
	}

	@Test
	public void testSetProperty() {
		this.Settings.setProperty("third", "33");
		assertEquals("33", this.Settings.getProperty("third"));
	}

	@Test
	public void testSetProperties() {
		TreeMap<String,String> ss = new TreeMap<>();
		ss.put("third", "333");
		ss.put("fourth", "4");
		
		this.Settings.setProperties(ss);
		
		assertEquals("333", this.Settings.getProperty("third"));
		assertEquals("4", this.Settings.getProperty("fourth"));
	}
	
	@Test
	public void testCheckStorageExists(){
		this.Settings.setProperty("third", "34");
		
		java.io.File f = new java.io.File(this.Settings.getStorageFile().toAbsolutePath().toString());
		assertTrue(f.exists());
	}
	
}

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
package mstorage.classes;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.TreeMap;
import mstorage.utils.FileUtils;

import hirondelle.date4j.DateTime;

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
	
	/**
	 * Is General settings work correct
	 */
	@Test
	public void testGetGeneralSettings() {
		assertEquals("MStorage", this.Settings.getProperty("AppName"));
		
		// another value mustnt be set
		this.Settings.setProperty("AppName", "Batman forever");
		assertEquals("MStorage", this.Settings.getProperty("AppName"));
	}
	
	/**
	 * Test that serialized\deserialized object is the same as original
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testFontSerealization() throws IOException, ClassNotFoundException {
		Font font = new java.awt.Font("Monospaced", 0, 12);		
		String fontString = FileUtils.serializeObject(font);	
		Settings.getInstance().setProperty("EditorsFont", fontString);
		Font font2 = (Font)FileUtils.deserializeObject(Settings.getInstance().getProperty("EditorsFont"));
		
		assertEquals(font2, font);
		
		Font font3 = new java.awt.Font("Serif", 0, 12);		
		String fontString2 = FileUtils.serializeObject(font3);
		Settings.getInstance().setProperty("EditorsFont", fontString2);
		Font font4 = (Font)FileUtils.deserializeObject(Settings.getInstance().getProperty("EditorsFont"));
		
		assertEquals(font3, font4);
        
	}
	
	@Test
	public void tmpTest(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("date: " + dateFormat.format( new Date() ) );
		
		String datePast = "2016-10-15";
		
		DateTime dateAndTime = new DateTime(datePast);
		DateTime plus7 = dateAndTime.plusDays(7);
		System.out.println("datePast + 7 days:: " + plus7.format("YYYY-MM-DD") );
		
		if (dateAndTime.lt(plus7))  {
			System.out.println(plus7.format("YYYY-MM-DD") + " is greater than " +  dateAndTime.format("YYYY-MM-DD"));
		}
		
		
		
	}
	
}

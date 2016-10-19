/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstorage.findreplace;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilya.gulevskiy
 */
public class FindReplaceTest {
	protected static Path Root;
	
	public FindReplaceTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		TestUtils.beforeMethod();
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Find in single file
	 */
	@Test
	public void testFind1() {
		FindInput input1 = new FindInput(Paths.get(TestUtils.Root + "firstfile"), "Франция");
		FindReplace find1 = new FindReplace(input1);
		ArrayList<FindResult> result1 = find1.find();
		assertEquals(3, result1.get(0).getCollection().size());
		assertEquals(1, result1.get(0).getCollection().get(0).getLineNumber());
		assertEquals(1, result1.get(0).getCollection().get(0).getCharNumber());
		assertEquals("Франция", result1.get(0).getCollection().get(0).getResult());
		assertEquals(4, result1.get(0).getCollection().get(1).getLineNumber());
		assertEquals(43, result1.get(0).getCollection().get(1).getCharNumber());
		assertEquals("франция", result1.get(0).getCollection().get(2).getResult());
	}
	
	/**
	 * Find in single file, try RegExp
	 */
	@Test
	public void testFind2() {
		FindInput input1 = new FindInput(Paths.get(TestUtils.Root + "firstfile"), "^Франция");
		FindReplace find1 = new FindReplace(input1);
		ArrayList<FindResult> result1 = find1.find();
		assertEquals(1, result1.get(0).getCollection().size());
		assertEquals(1, result1.get(0).getCollection().get(0).getLineNumber());
		assertEquals(1, result1.get(0).getCollection().get(0).getCharNumber());
		assertEquals("Франция", result1.get(0).getCollection().get(0).getResult());
	}
	
	/**
	 * Find in single file, try RegExp
	 */
	@Test
	public void testFind3() {
		FindInput input1 = new FindInput(Paths.get(TestUtils.Root + "firstfile"), "государ[а-я]+");
		FindReplace find1 = new FindReplace(input1);
		ArrayList<FindResult> result1 = find1.find();
		assertEquals(2, result1.get(0).getCollection().size());
		assertEquals("государственная", result1.get(0).getCollection().get(0).getResult());
		assertEquals("государством", result1.get(0).getCollection().get(1).getResult());
	}
	
	/**
	 * Find in directory according to case
	 */
	@Test
	public void testFind4() {
		FindInput input1 = new FindInput(Paths.get(TestUtils.Root), "бУржуа");
		input1.setAccordingToCase(true);
		FindReplace find1 = new FindReplace(input1);
		
		ArrayList<FindResult> result1 = find1.find();
		assertEquals(5, result1.size());
		assertEquals(1, result1.get(0).getCollection().size());
		assertEquals(2, result1.get(0).getCollection().get(0).getLineNumber());
		assertEquals(9, result1.get(0).getCollection().get(0).getCharNumber());
		assertEquals("бУржуа", result1.get(0).getCollection().get(0).getResult());
	}

	@Test
	public void testReplace() {
		assertTrue(true);
	}
	
	@Test
	public void testTest(){
		String line = "отмены подавляющего Франция большинства Франция денежных налогов и перехода к натуральным отношениям";
		int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
		Pattern patt = Pattern.compile("^отмен", flags);
		Matcher m = patt.matcher(line);  

		if(m.find()) {
			System.out.println((m.start() + 1) + " " + m.group());
		}

		System.out.println("Hello World");
		assertTrue(true);
	}
	
}

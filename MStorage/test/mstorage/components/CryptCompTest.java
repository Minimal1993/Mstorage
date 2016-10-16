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
package mstorage.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import mstorage.classes.AESEncrypter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bass
 */
public class CryptCompTest {
    
    public static String Text = "Как известно, в своем развитии SQL устремился в разные стороны.";
    public static String Password = "Password";
    public static String FileName = "filename";
    
    public CryptCompTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isCryptedFile method, of class CryptComp.
     */
    @Test
    public void testIsCryptedFile() {
        assertTrue(true);
    }
    
    @Test
    public void testCryptWrong() {
        try {
            AESEncrypter encrypter = new AESEncrypter(CryptCompTest.Password);
            String content = encrypter.encrypt(CryptCompTest.Text);
            String content2 = encrypter.decrypt(content);

            assertTrue( content2.equals(CryptCompTest.Text) );
            
            // write to file
            java.io.File iofile = new java.io.File(CryptCompTest.FileName);
            FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            
            // Read from file
            String contentFF = new String(Files.readAllBytes(Paths.get(CryptCompTest.FileName)));
            
            assertTrue( content.equals(contentFF) );
            
            AESEncrypter encrypter2 = new AESEncrypter(CryptCompTest.Password);
            String contentFFDec = encrypter2.decrypt( contentFF );
            
            assertTrue( !contentFFDec.equals(CryptCompTest.Text) );
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
    }
    
    @Test
    public void testCryptSuffix() {
        try {
            AESEncrypter encrypter = new AESEncrypter(CryptCompTest.Password);
            String content = encrypter.encrypt(CryptCompTest.Text);
            String content2 = encrypter.decrypt(content);

            assertTrue( content2.equals(CryptCompTest.Text) );
            
            AESEncrypter encrypter3 = new AESEncrypter(CryptCompTest.Password);
            String content3 = encrypter3.decrypt(content);
            
            assertTrue( content3.equals(CryptCompTest.Text) );
            
            // write to file
            String contentToWrite = "=" + content + "=";
            java.io.File iofile = new java.io.File(CryptCompTest.FileName);
            FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contentToWrite);
            bw.close();
            
            // Read from file
            String contentFF = new String(Files.readAllBytes(Paths.get(CryptCompTest.FileName)));
            contentFF = contentFF.substring(1, contentFF.length() - 1);
            assertTrue( content.equals(contentFF) );
            
            AESEncrypter encrypter2 = new AESEncrypter(CryptCompTest.Password);
            String contentFFDec = encrypter2.decrypt( contentFF );
            
            assertTrue( contentFFDec.equals(CryptCompTest.Text) );
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
    }
    
}

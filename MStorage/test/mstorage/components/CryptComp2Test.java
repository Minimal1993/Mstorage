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
import mstorage.classes.AESEncrypter2;
//import mstorage.classes.AdvancedEncryptionStandard;
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
public class CryptComp2Test {
    
    public static String Text = "Наличие механизма распространения ошибки: если при передаче произойдёт изменение";
    public static String Password = "111";
    public static String FileName = "filename2";
    
    public CryptComp2Test() {
    }
    
    @Test
    public void testCrypt1() {
        try {
            AESEncrypter2 encrypter = new AESEncrypter2(CryptComp2Test.Password);
            String content = encrypter.encrypt(CryptComp2Test.Text);
            String content2 = encrypter.decrypt(content);

            assertTrue( content2.equals(CryptComp2Test.Text) );
            
            // write to file
            java.io.File iofile = new java.io.File(CryptComp2Test.FileName);
            FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            
            // Read from file
            String contentFF = new String(Files.readAllBytes(Paths.get(CryptComp2Test.FileName)));
            
            assertTrue( content.equals(contentFF) );
            
            AESEncrypter2 encrypter2 = new AESEncrypter2(CryptComp2Test.Password);
            String contentFFDec = encrypter2.decrypt( contentFF );
            
            assertTrue( contentFFDec.equals(CryptComp2Test.Text) );
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
      
    }
    
    @Test
    public void testCrypt2() {
        try {
            // Read from file
            String contentFF = new String(Files.readAllBytes(Paths.get(CryptComp2Test.FileName)));
                        
            AESEncrypter2 encrypter2 = new AESEncrypter2(CryptComp2Test.Password);
            String contentFFDec = encrypter2.decrypt( contentFF );
            
            assertTrue( contentFFDec.equals(CryptComp2Test.Text) );
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
      
    }
    
    @Test
    public void testCryptSuffix() {
        try {
            AESEncrypter2 encrypter = new AESEncrypter2(CryptComp2Test.Password);
            String content = encrypter.encrypt(CryptComp2Test.Text);
            String content2 = encrypter.decrypt(content);

            assertTrue( content2.equals(CryptComp2Test.Text) );
            
            AESEncrypter2 encrypter3 = new AESEncrypter2(CryptComp2Test.Password);
            String content3 = encrypter3.decrypt(content);
            
            assertTrue( content3.equals(CryptComp2Test.Text) );
            
            // write to file
            String contentToWrite = "=" + content + "=";
            java.io.File iofile = new java.io.File(CryptComp2Test.FileName);
            FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contentToWrite);
            bw.close();
            
            // Read from file
            String contentFF = new String(Files.readAllBytes(Paths.get(CryptComp2Test.FileName)));
            contentFF = contentFF.substring(1, contentFF.length() - 1);
            assertTrue( content.equals(contentFF) );
            
            AESEncrypter2 encrypter2 = new AESEncrypter2(CryptComp2Test.Password);
            String contentFFDec = encrypter2.decrypt( contentFF );
            
            assertTrue( contentFFDec.equals(CryptComp2Test.Text) );
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
      
    }
    

    
}

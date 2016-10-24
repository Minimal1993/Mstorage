/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mstorage.utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import mstorage.storagecollection.*;

/**
 *
 */
public class TestUtils {
	public static String Root = "./storage/";
	public static String OutsideRoot = "./tmp/";
	public static String Content = "\nCromwell was one of the strongest and most powerful advocates of the English Reformation. \n"
			+ "He helped to engineer an annulment of the king's marriage to Queen Catherine of Aragon to allow Henry to marry his mistress Anne Boleyn.\n"
			+ "After failing in 1534 to obtain the Pope's approval of the request for annulment, \n"
			+ "Parliament endorsed the King's claim to be head of the breakaway Church of England, \n"
			+ "thus giving Henry the authority to annul his own marriage. Cromwell subsequently plotted an evangelical, \n"
			+ "reformist course for the embryonic Church of England from the unique posts of vicegerent in spirituals and vicar-general.";

	/**
	 * Create test storage tree in directory of this progect
	 */
	public static void createTestStorage() {
		java.io.File rootf = new java.io.File(Root);
		FileUtils.deleteRecursive(rootf);
		
		java.io.File rootout = new java.io.File(OutsideRoot);
		FileUtils.deleteRecursive(rootout);

		// Create directories
		new java.io.File(Root + "first").mkdirs();
		new java.io.File(Root + "second").mkdir();
		new java.io.File(Root + "third").mkdir();
		new java.io.File(Root + "first/first").mkdir();
		new java.io.File(OutsideRoot).mkdir();

		// Create files
		java.io.File newFile1 = new java.io.File(Root + "firstfile");
		java.io.File newFile2 = new java.io.File(Root + "first/firstfile");
		java.io.File newFile3 = new java.io.File(Root + "first/secondfile");
		java.io.File newFile4 = new java.io.File(Root + "second/firstfile2");
		java.io.File newFile5 = new java.io.File(Root + "first/firstfile_firstimage.jpg");
		java.io.File newFile6 = new java.io.File(Root + "first/firstfile_secondimage.jpg");
		java.io.File newFile7 = new java.io.File(Root + "second/firstfile2_firstimage.jpg");
		java.io.File newFile8 = new java.io.File(Root + "first/first/firstfile");
		java.io.File newFile9 = new java.io.File(Root + "third/firstfile");

		try {
			newFile1.createNewFile();
			newFile2.createNewFile();
			newFile3.createNewFile();
			newFile4.createNewFile();
			newFile5.createNewFile();
			newFile6.createNewFile();
			newFile7.createNewFile();
			newFile8.createNewFile();
			newFile9.createNewFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Write content
		try {
			// To file1
			FileWriter fw = new FileWriter(newFile1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(newFile1.getAbsolutePath() + Content);
			bw.close();

			// To newFile2
			fw = new FileWriter(newFile2.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(newFile2.getAbsolutePath() + Content);
			bw.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Create storage tree and scan it to Folder collection
	 * @return 
	 */
	public static Folder beforeMethod() {
		TestUtils.createTestStorage();
		StorageCollection sc = new StorageCollection(Root);
		sc.scan();
		
		return sc.FStorage;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mstorage.findreplace;

import mstorage.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class TestUtils {
	public static String Root = "tmp/";
	public static String Content = "Франция в XVIII веке была абсолютной монархией, опиравшейся на бюрократическую централизацию и регулярную армию. Существовавший в стране социально-экономический и политический режим сложился в результате сложных компромиссов, выработанных в ходе длительного политического противостояния и гражданских войн XIV—XVI вв.\n" +
"Один из бУржуа таких компромиссов существовал между королевской властью и привилегированными сословиями — за отказ от политических прав государственная власть всеми бывшими в её распоряжении средствами охраняла социальные привилегии этих двух сословий.\n" +
"Другой компромисс существовал по отношению к крестьянству — в течение длительной серии крестьянских войн XIV—XVI вв. крестьяне добились отмены подавляющего большинства денежных налогов и перехода к натуральным отношениям в сельском хозяйстве.\n" +
"Третий компромисс существовал в отношении Франция (которая в то время являлась средним классом, в интересах которой правительство тоже делало немало, сохраняя ряд привилегий буржуазии по отношению к основной массе населения (крестьянству) и поддерживая существование десятков тысяч мелких предприятий, владельцы которых и составляли слой французских буржуа).\n" +
"Однако сложившийся в результате этих сложных компромиссов режим не обеспечивал нормального развития Франции, которая в XVIII в. начала отставать от своих соседей, прежде всего от франция. Кроме того, чрезмерная эксплуатация все больше вооружала против монархии народные массы, жизненные интересы которых совершенно игнорировались государством.\n";

	/**
	 * Create test tree with files
	 */
	public static void createTestStorage() {
		java.io.File rootf = new java.io.File(Root);
		FileUtils.deleteRecursive(rootf);

		// Create directories
		new java.io.File(Root + "first").mkdirs();
		new java.io.File(Root + "second").mkdir();
		new java.io.File(Root + "first/first").mkdir();
		
		// Create files
		java.io.File newFile1 = new java.io.File(Root + "firstfile");
		java.io.File newFile2 = new java.io.File(Root + "first/firstfile");
		java.io.File newFile3 = new java.io.File(Root + "first/secondfile");
		java.io.File newFile4 = new java.io.File(Root + "second/firstfile2");
		java.io.File newFile8 = new java.io.File(Root + "first/first/firstfile");

		try {
			newFile1.createNewFile();
			newFile2.createNewFile();
			newFile3.createNewFile();
			newFile4.createNewFile();
			newFile8.createNewFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Write content
		try {
			// To file1
			FileWriter fw = new FileWriter(newFile1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Content);
			bw.close();

			// To newFile2
			fw = new FileWriter(newFile2.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(Content);
			bw.close();
			
			// To newFile3
			fw = new FileWriter(newFile3.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(Content);
			bw.close();
			
			// To newFile4
			fw = new FileWriter(newFile4.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(Content);
			bw.close();
			
			// To newFile8
			fw = new FileWriter(newFile8.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(Content);
			bw.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Create storage tree and scan it to Folder collection
	 * @return 
	 */
	public static Path beforeMethod() {
		TestUtils.createTestStorage();
		
		return Paths.get(Root);
	}
}

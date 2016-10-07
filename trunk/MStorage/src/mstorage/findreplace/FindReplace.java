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

package mstorage.findreplace;

import mstorage.findreplace.types.FindInput;
import mstorage.findreplace.types.FindResult;
import mstorage.findreplace.types.FindResultItem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.util.regex.PatternSyntaxException;

/**
 * Main class looking for pattern and replace if it necessary
 */
public class FindReplace {
	/**
	 * Input data for searching
	 */
	protected FindInput FindInput;
	
	/**
	 * Precompile RegExp
	 */
	protected Pattern Pattern;
	
	public FindReplace(FindInput input) {
		this.FindInput = input;
		this.FindInput.setFind(this.FindInput.getFind().trim());
		
		if (this.FindInput.getFind().isEmpty()){
			throw new RuntimeException("Cant find empty pattern");
		}
		
		// Check whether file exists or not
		java.io.File iofile = new java.io.File(input.getFileName().toAbsolutePath().toString());
		if (!iofile.exists()) {
			throw new RuntimeException(input.getFileName().toAbsolutePath().toString() + " does not exists");
		}
		
		// Check regExp will compile
		try {
			// If case insensitive
			int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
			if (this.FindInput.getAccordingToCase()) flags = 0;
			
			this.Pattern = Pattern.compile(this.FindInput.getFind(), flags);
		}
		catch (PatternSyntaxException e){
			throw new RuntimeException("Cant compile RegExp string: " + this.FindInput.getFind());
		}
	}
	
	/**
	 * Perform search
	 * @return 
	 */
	public ArrayList<FindResult> find() {
		ArrayList<FindResult> result = new ArrayList<FindResult>();
		java.io.File file = new java.io.File(this.FindInput.getFileName().toAbsolutePath().toString());
		
		if (file.isDirectory()){
			this.findInDir(file, result);
		}
		else {
			FindResult fr = this.findInFile(file);
			if (null != fr) result.add(fr);
		}
		
		return result;
	}
	
	/**
	 * Scan directory or looking in file
	 * @param file
	 * @param result
	 * @return 
	 */
	protected ArrayList<FindResult> findInDir(java.io.File file, ArrayList<FindResult> result){
		if (!file.isDirectory()) throw new RuntimeException("File has to be a directory");
		
		java.io.File[] files = file.listFiles();

		if (files == null) return result;
		
		for (java.io.File currentFile : files) {
			if (currentFile.isDirectory()) {
				this.findInDir(currentFile, result);
				continue;
			}
			
			// Find in current file
			FindResult fr = this.findInFile(currentFile);
			if (null != fr) result.add(fr);
		}
		
		return result;
	}
	
	/**
	 * Search in file
	 * @param file
	 * @return 
	 */
	protected FindResult findInFile(java.io.File file){
		int linecount = 0;
        String line;
		FindResult findResult = new FindResult(file.toPath(), new ArrayList<FindResultItem>());

		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));

            while (( line = bf.readLine()) != null) {
				linecount++;
				Matcher m = this.Pattern.matcher(line);  
				if(!m.find()) continue; 
				
				FindResultItem fri = new FindResultItem(
					findResult.getFileName(), 
					linecount, 
					m.start() + 1, 
					m.group(), 
					this.FindInput.getReplace());
		
				findResult.getCollection().add(fri);
            }

			bf.close();

		}
		catch(FileNotFoundException e) {
			return null;
		}
		catch(IOException e) {
			return null;
		}
		
		if (0 == findResult.getCollection().size()) return null;
		return findResult;
	}
	
	/**
	 * Try to replace string in file.
	 * Find out all coincidence and replace it.
	 * Now this method can replace many times but still return 1
	 * 
	 * @param item
	 * @return 
	 */
	public int replace(FindResultItem item) {
		java.io.File iofile = new java.io.File(item.getFileName().toAbsolutePath().toString());
		if (!iofile.exists()){
			return 0;
		}
		
		try {
			String contentOrig = new String(Files.readAllBytes(item.getFileName()));
			String contentCh = contentOrig.replaceAll(item.getResult(), item.getReplace());

			if (contentOrig.equals(contentCh)) return 0;
			
			FileWriter fw = new FileWriter(iofile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contentCh);
			bw.close();
			
			return 1;
		}
		catch(IOException e){
			return 0;
		}
	}
	
	
}

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

import java.nio.file.Path;

/**
 * Part of FindResult class
 */
public class FindResultItem {
	protected Path FileName;
	protected int LineNumber;
	protected int CharNumber;
	protected String Result;
	protected String Replace;
    protected int GlobalCharNumber;
	
	public FindResultItem(
            Path FileName, 
            int LineNumber, 
            int CharNumber, 
            String Result, 
            String Replace, 
            int GlobalCharNumber
        ){
        
		this.FileName = FileName;
		this.LineNumber = LineNumber;
		this.CharNumber = CharNumber;
		this.Result = Result;
		this.Replace = Replace;
        this.GlobalCharNumber = GlobalCharNumber;
        
	}

    public int getGlobalCharNumber() {
        return GlobalCharNumber;
    }

	public int getLineNumber(){
		return this.LineNumber;
	}
	
	public Path getFileName() {
		return FileName;
	}

	public int getCharNumber() {
		return CharNumber;
	}

	public String getResult() {
		return Result;
	}

	public String getReplace() {
		return Replace;
	}
	
	public String toString(){
        return "line" + Integer.toString(this.LineNumber) + "<b>" + this.Result + "</b>";
    }
}

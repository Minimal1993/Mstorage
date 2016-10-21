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
	protected String PreText;
	protected String PostText;
	
	public int MaxLengthItemText = 70;
	
	public FindResultItem(
            Path FileName, 
            int LineNumber, 
            int CharNumber, 
            String Result, 
            String Replace, 
            int GlobalCharNumber,
			String PreText,
			String PostText
        ){
        
		this.FileName = FileName;
		this.LineNumber = LineNumber;
		this.CharNumber = CharNumber;
		this.Result = Result;
		this.Replace = Replace;
        this.GlobalCharNumber = GlobalCharNumber;
		this.PreText = PreText;
		this.PostText = PostText;
        
	}
	
	public String getPreText() {
		return PreText;
	}

	public String getPostText() {
		return PostText;
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
		String result = this.getResult();
		if ( this.getPreText().length() + result.length() + this.getPostText().length() > this.MaxLengthItemText) {
			int rest = this.MaxLengthItemText - (this.getPreText().length() + this.getPostText().length());
			result = result.substring(0, rest) + "...";
		}

		String text = "<html><span style='color:#acacac'>" + Integer.toString(this.getLineNumber()) + ":</span>"
			+ " ..." + this.getPreText() 
			+ "<b>" + result + "</b> "
			+ this.getPostText() + "..." 
			+ "<span style='color:#acacac'>[col:" + Integer.toString(this.getCharNumber()) + "]</span>"
			+ "</html>";

		return text;
    }
}

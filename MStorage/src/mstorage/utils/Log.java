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

package mstorage.utils;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import mstorage.MainForm;
import mstorage.classes.Settings;

/**
 * Simple wrapper for Logger class. Write log.
 */
public class Log {
	protected static Logger instance = null;
	
	protected void Log(){
		
	}
	
	public static Logger getInstance(){
		if (null == Log.instance) {
			Log.instance = Logger.getLogger(MainForm.class.getName());
			try {
				FileHandler fileHandler = new FileHandler(Settings.getInstance().getProperty("LogFileName"), true);        
				Log.instance.addHandler(fileHandler);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	
		return Log.instance;
	}
	
	public static void info(String message){
		Log.getInstance().info(message);
	}
	
}

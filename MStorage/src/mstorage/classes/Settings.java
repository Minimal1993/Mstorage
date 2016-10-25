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
import java.io.*;
import java.util.*;
import java.nio.file.*;
import mstorage.MainForm;
import mstorage.utils.FileUtils;

/**
 * This class is needed for saving settings of application. All settings will
 * save in file. This is wrapper for Properties
 */
public class Settings {

	/**
	 * Singleton
	 */
	protected static Settings Instance = null;

	/**
	 * Collection of properties
	 */
	protected Properties Properties = new Properties();
	
	/**
	 * General settings, not save and not extract
	 */
	protected HashMap<String, String> GeneralSettings = new HashMap();

	/**
	 * Url to properties storage
	 */
	protected Path StorageFile = null;
	
	protected static String SettingsFileName = "settings";

	public static void setSettingsFileName(String SettingsFileName) {
		Settings.SettingsFileName = SettingsFileName;
	}

	public Path getStorageFile() {
		return StorageFile;
	}

	/**
	 * Default value for any property
	 */
	protected static String DefaultValue = "NULL";

	public static String getDefaultValue() {
		return DefaultValue;
	}

	/**
	 * General and default settings of application. If will need to add a new major
	 * property, it have to do there.
	 */
	protected void initGeneral() {
		// General settings
		this.GeneralSettings.put("Version", "1.1.2");
		this.GeneralSettings.put("Author", "Gulevskiy Ilya");
		this.GeneralSettings.put("Year", "2016");
		this.GeneralSettings.put("Email", "mstorage.project@gmail.com");
		this.GeneralSettings.put("AppName", "MStorage");
		this.GeneralSettings.put("License", "GNU General Public License");
		
		// Default
		this.Properties.setProperty("ViewMenuToolbar", "true");
		this.Properties.setProperty("ViewStorageTreePanel", "true");
		this.Properties.setProperty("OpenedFiles", ""); // ; as delimiter
        this.Properties.setProperty("MainWindowLocation", "0,0"); 
		
		// Selected files in last opened files
		this.Properties.setProperty("OpenedFilesSelected", ""); 
		
		// Opeted files with flag read-only
		this.Properties.setProperty("OpenedFilesReadOnly", ""); 
		
		// Width and height main window
		this.Properties.setProperty("MainWindowWidth", "700"); 
		this.Properties.setProperty("MainWindowHeight", "600"); 
        
        // Adjust settings
        this.Properties.setProperty("ExcludeExtension", "pdf,fb2,exe");
		this.Properties.setProperty("Icons", "Delta");
		this.Properties.setProperty("StorageDirectory", Settings.getDefaultValue());
        this.Properties.setProperty("Command2ViewExplorer", "explorer %s");
		this.Properties.setProperty("EditorsFont_name", "Monospaced");
		this.Properties.setProperty("EditorsFont_style", "0");
		this.Properties.setProperty("EditorsFont_size", "12");
		
	}

	protected Settings() {
		this.initGeneral();

		SecurityManager security = System.getSecurityManager();

		try {
			if (null != security) {
				security.checkPropertyAccess("user.home");
			}
		} catch (java.lang.SecurityException e) {
			MainForm.showError("Can't read system user properties 'user.home'");
			return;
		}

		String url = System.getProperty("user.home") + File.separator + "."
				+ this.getProperty("AppName") 
				+ File.separator + "." + Settings.SettingsFileName;

		this.StorageFile = Paths.get(url);

		this.load();
	}

	public static Settings getInstance() {
		if (null == Instance) {
			Instance = new Settings();
		}

		return Instance;
	}

	public String getProperty(String key) {
		// Check is GeneralSettings
		if (this.GeneralSettings.containsKey(key)) {
			return this.GeneralSettings.get(key);
		}
		
		return this.Properties.getProperty(key, Settings.DefaultValue);
	}

	public void setProperty(String key, String val) {
		// Check whether the same value or it is GeneralSettings
		if (this.Properties.getProperty(key) == val || this.GeneralSettings.containsKey(key)) {
			return;
		}

		this.Properties.setProperty(key, val);
		this.store();
	}

	/**
	 * Set many properties to save
	 *
	 * @param properties
	 */
	public void setProperties(TreeMap<String, String> properties) {
		if (0 == properties.size()) {
			return;
		}
		
		boolean wasChanged = false;

		Set<Map.Entry<String, String>> propSet = properties.entrySet();
		for (Map.Entry<String, String> p : propSet) {
			// The same value, do nothing
			if (this.getProperty(p.getKey()) == p.getValue()) {
				continue;
			}

			this.setProperty(p.getKey(), p.getValue());
			wasChanged = true;
		}

		if (wasChanged) {
			this.store();
		}
	}

	/**
	 * Read data from file
	 */
	protected void load() {
		// If file exists load content to memory
		try {
			FileInputStream in = new FileInputStream(this.StorageFile.toAbsolutePath().toString());
			this.Properties.load(in);

			// If not, create it
		} catch (FileNotFoundException e) {
			java.io.File folder = new java.io.File(this.StorageFile.getParent().toAbsolutePath().toString());
			if(!folder.exists()){
				folder.mkdir();
			}
			java.io.File file = new java.io.File(this.StorageFile.toAbsolutePath().toString());
			try {
				file.createNewFile();
			} catch (IOException ee) {
				MainForm.showError(ee.getMessage());
			}

		} catch (IOException e) {
			MainForm.showError(e.getMessage());
		}
	}

	/**
	 * Save data into file
	 */
	protected void store() {
		try {
			FileOutputStream in = new FileOutputStream(this.StorageFile.toAbsolutePath().toString());
			this.Properties.store(in, "settings");
			in.close();
			// If not
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			MainForm.showError(e.getMessage());
		}
	}
	
	/**
	 * Serialize Object to String
	 * 
	 * @param obj
	 * @return
	 * @throws IOException 
	 */
	public static String serializeObject(Object obj) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baos);
		oout.writeObject(obj);
		oout.close();
		
		return baos.toString();
	}
	
	/**
	 * Deserialize String to Object
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static Object deserializeObject(String str) throws IOException, ClassNotFoundException{
		byte[] buf = str.getBytes();
		if (null == buf ) return null;
		
		ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
		return objectIn.readObject();
	}

}

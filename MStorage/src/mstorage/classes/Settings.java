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

import hirondelle.date4j.DateTime;
import java.awt.Font;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mstorage.MainForm;
import mstorage.storagecollection.Image;
import mstorage.utils.FileUtils;
import mstorage.utils.Log;

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
	protected static String LogFileName = "settings.log";

	public static void setSettingsFileName(String SettingsFileName) {
		Settings.SettingsFileName = SettingsFileName;
		Settings.LogFileName = SettingsFileName + ".log";
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
		this.GeneralSettings.put("Version", "1.1.3");
		this.GeneralSettings.put("Author", "Gulevskiy Ilya");
		this.GeneralSettings.put("Year", "2017");
		this.GeneralSettings.put("Email", "mstorage.project@gmail.com");
		this.GeneralSettings.put("AppName", "MStorage");
		this.GeneralSettings.put("License", "GNU General Public License");
		
		String log = System.getProperty("user.home") + File.separator + "."
				+ this.getProperty("AppName") 
				+ File.separator + "." + Settings.LogFileName;
		this.GeneralSettings.put("LogFileName", log); 
		
		// Default
		this.Properties.setProperty("ViewMenuToolbar", "true");
		this.Properties.setProperty("ViewStorageTreePanel", "true");
		this.Properties.setProperty("OpenedFiles", ""); // ; as delimiter
        this.Properties.setProperty("MainWindowLocation", "0,0"); 
		this.Properties.setProperty("CheckUpdatesURL", "https://sourceforge.net/projects/mstorage/files/"); 
		this.Properties.setProperty("CheckUpdatesURLPattern", 
			"https://sourceforge.net/projects/mstorage/files/MStorage\\.(.+)\\.zip/download"
		);
		this.Properties.setProperty("ProgectURL", "http://mstorage.sourceforge.net"); 
		
		// In first launch set last update as now
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.Properties.setProperty("LastCheckUpdate", dateFormat.format( new Date() )); 
		
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
		this.Properties.setProperty("HowOftenCheckUpdates", "7"); 
		
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
            
            // For compatibility with old storages
            // Settings exists, not first launch of app
            if(this.getProperty("Encoding").equals(Settings.DefaultValue)) {
                this.setProperty("Encoding", "System"); 
            }

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
        
        // Set default encoding for first launch of app
        if(this.getProperty("Encoding").equals(Settings.DefaultValue)) {
            this.setProperty("Encoding", "UTF-8"); 
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
			Log.info(e.getMessage());
		} catch (IOException e) {
			MainForm.showError(e.getMessage());
		}
	}
	
	/**
	 * Serialize Object to String. Work not correct for GUI
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
    
    /**
     * Choose from list newest version of app
     * 
     * @param list
     * @return 
     */
    public static ArrayList<String> compareNewVersion(HashMap<String, String> list ){
        if (list.isEmpty()) return null;
        
        // Add current version for compare
        list.put("current", Settings.getInstance().getProperty("Version"));
        
        Pattern pattern = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)(\\..+)?$", 0);
        String newestStr = "";
        int newestInt = 0;
        Set<Map.Entry<String, String>> listSet = list.entrySet();
        for(Map.Entry<String, String> lst : listSet) {
            Matcher m = pattern.matcher(lst.getValue());
            if (!m.find() ) continue;
            
            try {
                String g1 = m.group(1);
                String g2 = String.format("%03d", Integer.decode(m.group(2)));
                String g3 = String.format("%03d", Integer.decode(m.group(3)));
                String g4 = m.group(4);
                
                if (null != g4) continue;
                
                String newStr = g1 + g2 + g3;
                int newInt = Integer.decode(newStr);
                
                if (newestStr.isEmpty() || newestInt < newInt){
                    newestStr = lst.getKey();
                    newestInt = newInt;
                }
            }
            catch (Exception e) {
                Log.info(e.getMessage());
            }            
        }
        
        // Check if current version is older than newest
        if (newestStr.isEmpty() || newestStr.equals("current")) return null;
        
        ArrayList<String> al = new ArrayList<>();
        al.add(newestStr);
        al.add(list.get(newestStr));
        
        return al;
    }

}

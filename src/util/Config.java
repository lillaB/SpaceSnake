package util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Properties;


/**
 * Class that after it is initiated can be called with a static method to get properties.
 * @author Gustav
 * @version 2016-03-05
 */

public class Config {
	
	private static Properties configFile;
	private static boolean loaded;
	
	
	/**
	 * Constructor that takes a filename and loads its content into a static variable.
	 * @param filename
	 */
	public Config (String filename) {
		configFile = new Properties();
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			configFile.load(in);
			loaded = true;
		} catch (Exception e) {
			loaded = false;
		}
	}
	
	
	/**
	 * Check if the config file was properly loaded
	 * @return true if the file was loaded properly
	 */
	public boolean fileLoaded() {
		return loaded;
	}
	
	
	/**
	 * Gets a certain property from the config file (after it is loaded).
	 * @param key	The key in the config file (no spaces)
	 * @return		The value associated with the key. 
	 */
	public static String get(String key) {
		if (!loaded) return "";
		String value = (String) configFile.get(key);
		if (value == null) return "";
		return value;
	}
	
}

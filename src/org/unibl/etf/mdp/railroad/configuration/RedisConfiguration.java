package org.unibl.etf.mdp.railroad.configuration; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisConfiguration {
public static String REDIS_CONFIG_PATH = System.getProperty("user.home") + File.separator + "Railroad" + File.separator + "config" + File.separator + "redis_config.properties";
	
	public static void writeConfiguration() throws IOException {
		File folder = new File("config");
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File file = new File(REDIS_CONFIG_PATH);
		file.createNewFile();
		
		Properties properties = new Properties();
		properties.setProperty("HOST", "localhost");
		
		FileOutputStream out = new FileOutputStream(file);
		properties.store(out, null);
		
		out.close();
	}
	
	public static Properties readParameters() throws IOException {	
		Properties properties = new Properties();
		FileInputStream in = new FileInputStream(new File(REDIS_CONFIG_PATH));
		properties.load(in);
		in.close();		
		
		return properties;
	}
	
	public static boolean checkIfFileExists() {
		return new File(REDIS_CONFIG_PATH).exists();
	}
	
}

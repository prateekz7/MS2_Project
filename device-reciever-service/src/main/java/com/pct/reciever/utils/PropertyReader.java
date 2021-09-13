package com.pct.reciever.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private static PropertyReader instance = null;
	static Properties property = null;
	static FileReader reader = null;

	// private constructor so no other class can instantiate this
	private PropertyReader() {

	}

	public static Properties populateProperties() {

		try {
			if (property != null) {
				return property;
			}
			File directory = new File("./");
			reader = new FileReader("resources/application.properties");
			property = new Properties();
			property.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Property file not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in loading property file values");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in getting values from property file");
		}
		return property;
	}

}

package com.javacowboy.ideal.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileService extends Service {
	
	public void save(String dir, String filename, String content) {
		save(new File(dir, filename), content);
	}
	
	public void save(String filename, String content) {
		save(new File(filename), content);
	}
	
	/**
	 * For saving/replacing content to a file on disk
	 * @param file
	 * @param content
	 */
	public void save(File file, String content) {
		logger.info("Saving file: " + file.getAbsolutePath());
		try {
			file.getParentFile().mkdirs();
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void serialize(String dir, String filename, Object object) {
		serialize(new File(dir, filename), object);
	}
	
	public void serialize(String filename, Object object) {
		serialize(new File(filename), object);
	}
	
	/**
	 * For serializing an object to disk
	 * @param file
	 * @param object
	 */
	public void serialize(File file, Object object) {
		logger.info("Serializing file: " + file.getAbsolutePath());
		try {
			file.getParentFile().mkdirs();
			// Write to disk with FileOutputStream
			FileOutputStream fOut = new FileOutputStream(file);
			// Write object with ObjectOutputStream
			ObjectOutputStream oOut = new ObjectOutputStream (fOut);
			// Write object out to disk
			oOut.writeObject(object);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object deserialize(String dir, String filename) {
		return deserialize(new File(dir, filename));
	}
	
	public Object deserialize(String filename) {
		return deserialize(new File(filename));
	}
	
	/**
	 * For deserializing an object that was saved to disk
	 * @param file
	 * @return
	 */
	public Object deserialize(File file) {
		logger.info("Deserializing file: " + file.getAbsolutePath());
		try {
			// Read from disk using FileInputStream
			FileInputStream fIn = new FileInputStream(file);
			// Read object using ObjectInputStream
			ObjectInputStream oIn = new ObjectInputStream (fIn);
			// Read an object
			return oIn.readObject();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

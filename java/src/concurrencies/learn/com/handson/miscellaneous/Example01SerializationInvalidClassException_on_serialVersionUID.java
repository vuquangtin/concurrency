/**
 * 
 */
package com.handson.miscellaneous;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author sveera
 *
 */
public class Example01SerializationInvalidClassException_on_serialVersionUID {

	/*
	 * Example on serialization specific to serialVersionUID. If serialVersionUID
	 * version's are changed between serialization and de-serialization , We get
	 * java.io.InvalidClassException:with serialVersionUID change exception. In this
	 * example ,first run this program with option 1 and change the
	 * "serialVersionUID" version in SerializableClass and re-execute the program
	 * with option 2 to see the exception.
	 * 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Path targetDirectoryPath = Paths.get("target" + File.separator + "object.file");
		File file = new File(targetDirectoryPath.toAbsolutePath().toString());
		System.out.println("Type 1 to serialize the object or 2 to deserialize ");
		try (Scanner scanner = new Scanner(System.in);) {
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				serializeObject(file);
				break;
			case 2:
				deserializeObject(file);
				break;
			default:
				System.err.println("Invalid option please choose eaither 1 or 2 ");
				break;
			}
		}
	}

	private static void deserializeObject(File file) throws IOException, ClassNotFoundException, FileNotFoundException {
		try (FileInputStream fileOutputStream = new FileInputStream(file);
				ObjectInputStream objectOutputStream = new ObjectInputStream(fileOutputStream)) {
			SerializableClass serializableClassReadFromFile = (SerializableClass) objectOutputStream.readObject();
			System.out.println("Completed Object De-serialization of object " + serializableClassReadFromFile);
		} finally {
			file.delete();
		}

	}

	private static void serializeObject(File file) throws IOException, FileNotFoundException {
		NonSerializableClass nonSerializableClass = new NonSerializableClass(10);
		SerializableClass serializableClass = new SerializableClass("Hello , Greetings from Veera !!",
				nonSerializableClass);

		file.createNewFile();
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			objectOutputStream.writeObject(serializableClass);
			System.out.println("Completed Object Serialization.");
		}
	}

	private static class SerializableClass implements Serializable {

		private static final long serialVersionUID = 1L;
//		private static final long serialVersionUID = 2L;
		private transient int counter;
		private final String message;
		private transient final NonSerializableClass nonSerializableClass;

		{
			counter++;
		}

		public SerializableClass(String message, NonSerializableClass nonSerializableClass) {
			super();
			this.message = message;
			this.nonSerializableClass = nonSerializableClass;
		}

		public int getCounter() {
			return counter;
		}

		public String getMessage() {
			return message;
		}

		public NonSerializableClass getNonSerializableClass() {
			return nonSerializableClass;
		}

		@Override
		public String toString() {
			return "SerializableClass [counter=" + getCounter() + ", message=" + getMessage()
					+ ", nonSerializableClass=" + getNonSerializableClass() + "]";
		}

	}

	private static class NonSerializableClass {
		private final int id;

		public NonSerializableClass(int id) {
			super();
			this.id = id;
		}

		public int getId() {
			return id;
		}

		@Override
		public String toString() {
			return "NonSerializableClass [id=" + getId() + "]";
		}

	}

}

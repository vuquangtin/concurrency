/**
 * 
 */
package com.handson.miscellaneous;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author sveera
 *
 */
public class Example01Serialization {

	/*
	 * Example on serialization.In order to make an object to participate in
	 * Serialization ,object's class has to implement Serializable interface.This
	 * includes class types of instance variable of the class.If an instance
	 * variable type doesn't implement Serializable interface when try to Serialize
	 * that object , we get java.io.NotSerializableException (Remove transient
	 * keywork from nonSerializableClass instance variable in SerializableClass to
	 * see this exception in below example). In order to skip a variable from
	 * Serialization process tar the variable with transient keyword.
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		NonSerializableClass nonSerializableClass = new NonSerializableClass(10);
		SerializableClass serializableClass = new SerializableClass("Hello , Greetings from Veera !!",
				nonSerializableClass);
		SerializableClass serializableClass2 = new SerializableClass("Hi , Greetings from Veera !!",
				nonSerializableClass);
		Path targetDirectoryPath = Paths.get("target" + File.separator + "object.file");
		File file = new File(targetDirectoryPath.toAbsolutePath().toString());
		file.createNewFile();
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			objectOutputStream.writeObject(serializableClass);
			objectOutputStream.writeObject(serializableClass2);
			System.out.println("Completed Object Serialization.");
		}

		try (FileInputStream fileOutputStream = new FileInputStream(file);
				ObjectInputStream objectOutputStream = new ObjectInputStream(fileOutputStream)) {
			SerializableClass serializableClassReadFromFile = (SerializableClass) objectOutputStream.readObject();
			SerializableClass serializableClassReadFromFile2 = (SerializableClass) objectOutputStream.readObject();
			System.out.println("Completed Object De-serialization of object 1 " + serializableClassReadFromFile);
			System.out.println("Completed Object De-serialization of object 2 " + serializableClassReadFromFile2);
		}
		file.delete();
	}

	private static class SerializableClass implements Serializable {

		private static final long serialVersionUID = 1L;
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

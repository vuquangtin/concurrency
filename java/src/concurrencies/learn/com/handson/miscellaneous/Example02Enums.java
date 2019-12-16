/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example02Enums {

	/*
	 * Example on enums.Enums are special class in Java.Where you can define fixed
	 * constants/instances of enum inside it self. All enums are by default extended
	 * from Enum.class.
	 */
	public static void main(String[] args) {
		Group ece = Group.ECE;
		// Invoking Instance method on enum.
		out.println("ECE Enum group id is " + ece.getGroupId());
		// Invoking static method on enum.
		out.println("Get enum for id 1 -> " + Group.getGroupById(1));
		out.println("Invoking valueOf method of parent class Enum.class -> " + Group.valueOf("EEE"));
		out.println();
		out.println("All enums defined in Group enum using Enum.values() parent method");
		for (Group group : Group.values())
			out.println("Group with name " + group);

	}

	private static enum Group {
		// All enum constants are defined inside enum it self by invoking own
		// constructor.
		ECE(0), CSE(1), EEE(2);

		// Constructor
		Group(int groupId) {
			this.groupId = groupId;
		}

		// Instance Variable.
		private final int groupId;

		// Instance method.
		public int getGroupId() {
			return groupId;
		}

		// Static method.
		public static Group getGroupById(int id) {
			return id == ECE.groupId ? ECE : id == CSE.groupId ? CSE : EEE;
		}

	}

}

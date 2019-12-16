/**
 * 
 */
package com.handson.functional.streams;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author veera
 *
 */
public class StreamsExample08 {

	/*
	 * Stream.map with custom value Example
	 */
	public static void main(String[] args) {
		List<Employee> names = new ArrayList<>();
		names.add(new Employee(1, "Veerabhadrudu", "NA"));
		names.add(new Employee(2, "Venkatesh", "NA"));
		names.add(new Employee(3, "Gopal", "NA"));
		names.add(new Employee(4, "Veerakumar", "NA"));
		names.add(new Employee(5, "Veerashekar", "NA"));
		Stream<Employee> employees = names.stream();
		Stream<Employee> filteredEmployees = employees.filter(employee -> employee.getId() < 5);
		Map<Integer, String> employeeMap = filteredEmployees
				.collect(Collectors.toMap(Employee::getId, Employee::getName));
		employeeMap.forEach((empID, employeeName) -> out
				.println(String.format("Employee with ID %s has name %s ", empID, employeeName)));
	}

	static class Employee {
		private final int id;
		private final String name;
		private final String address;

		public Employee(int id, String name, String address) {
			super();
			this.id = id;
			this.name = name;
			this.address = address;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getAddress() {
			return address;
		}

		@Override
		public String toString() {
			return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
		}

	}

}
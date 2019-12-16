/**
 * 
 */
package com.handson.functional.streams;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author veera
 *
 */
public class StreamsExample07 {

	/*
	 * Example on Stream terminal operation to reduce to map(Using collectors.toMap)
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
		Map<Integer, Employee> employeeMap = filteredEmployees
				.collect(Collectors.toMap((Employee emp1) -> emp1.getId(), Function.identity()));
		employeeMap.forEach((empID, employee) -> out
				.println(String.format("Employee with ID %s has details %s ", empID, employee)));
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
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
public class StreamsExample10 {

	/*
	 * Stream.map groupBy with custom value example
	 */
	public static void main(String[] args) {
		List<Employee> names = new ArrayList<>();
		names.add(new Employee(1, "Veerabhadrudu", "Bangalore"));
		names.add(new Employee(2, "Venkatesh", "Hydrabad"));
		names.add(new Employee(3, "Gopal", "Bangalore"));
		names.add(new Employee(4, "Veerakumar", "Mumbai"));
		names.add(new Employee(5, "Veerashekar", "Hydrabad"));
		Stream<Employee> employees = names.stream();
		Stream<Employee> filteredEmployees = employees.filter(employee -> employee.getId() < 5);
		Map<String, List<String>> employeeGroupByLocation = filteredEmployees.collect(Collectors
				.groupingBy(Employee::getAddress, Collectors.mapping(Employee::getName, Collectors.toList())));
		employeeGroupByLocation.forEach((location, employeeList) -> {
			out.println(String.format("Employees in location %s are ", location));
			employeeList.forEach(employeeName -> out.println(String.format("Employee Name - %s ", employeeName)));
			out.println();
		});
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

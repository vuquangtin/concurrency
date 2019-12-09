package basic.beginner;

import java.util.Iterator;
import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
@ThreadSafe
public class EmployeeManager2 {
	/*
	 * Mutating operations are encapsulated inside the class. State (employees)
	 * is not shared outside class directly.
	 */
	private List<Employee> employees;

	public void addEmployee(Employee e) {
		// do some validations or preprocessing
		synchronized (this) {
			// only this code blocks
			this.employees.add(e);
		}
		// do somehting else
	}

	/*
	 * This is the right way to get data from collections. Finding by index is
	 * not safe as index may change over time.
	 */
	public Employee getEmployee(String name) throws CloneNotSupportedException {
		// get the employee from the list
		Employee employee = this.findEmployeeByName(name);
		if (employee != null) {
			return (Employee) (employee.clone());
		} else {
			return null;
		}
	}

	public void deleteEmployee(String name) {
		// get the employee from the list
		synchronized (this) {
			for (Iterator<Employee> it = employees.iterator(); it.hasNext();) {
				Employee employee = it.next();
				if (employee.getName().equals(name)) {
					it.remove();// delet the employee
					break;
				}
			}
		}
	}

	private Employee findEmployeeByName(String name) {
		for (Iterator<Employee> it = employees.iterator(); it.hasNext();) {
			Employee employee = it.next();
			if (employee.getName().equals(name)) {
				return employee;
			}
		}
		return null;
	}
}
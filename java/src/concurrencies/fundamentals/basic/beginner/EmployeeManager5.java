package basic.beginner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class EmployeeManager5 {

	// private final Map<Integer, Employee> employees =
	// Collections.synchronizedMap(new HashMap<Integer, Employee>());

	private Map<Integer, Employee> employees = Collections
			.synchronizedMap(new HashMap<Integer, Employee>());

	public void addNewEmployee(Employee employee) {
		synchronized (employees) {
			this.employees.put(employee.getEmployeeId(), employee);
		}
	}

	public boolean updateEmployee(Employee employee) {
		synchronized (employees) {
			if (this.employees.containsKey(employee.getEmployeeId())) {
				this.employees.put(employee.getEmployeeId(), employee);
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean deleteEmployee(Employee employee) {
		synchronized (employees) {
			if (this.employees.containsKey(employee.getEmployeeId())) {
				this.employees.remove(employee.getEmployeeId());
				return true;
			} else {
				return false;
			}
		}
	}

	public Employee getEmployee(int employeeId) {
		synchronized (employees) {
			if (this.employees.containsKey(employeeId)) {
				return this.employees.get(employeeId);
			} else {
				return null;
			}
		}
	}
}
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
public class EmployeeManager4 {
	 
    private Map<Integer, Employee> employees =
            Collections.synchronizedMap(new HashMap<Integer, Employee>());
 
    public synchronized void addNewEmployee(Employee employee) {
        this.employees.put(employee.getEmployeeId(), employee);
    }
 
    public synchronized boolean updateEmployee(Employee employee) {
        if (this.employees.containsKey(employee.getEmployeeId())) {
            this.employees.put(employee.getEmployeeId(), employee);
            return true;
        } else {
            return false;
        }
    }
 
    public synchronized boolean deleteEmployee(Employee employee) {
        if (this.employees.containsKey(employee.getEmployeeId())) {
            this.employees.remove(employee.getEmployeeId());
            return true;
        } else {
            return false;
        }
    }
 
    public synchronized Employee getEmployee(int employeeId) {
        if (this.employees.containsKey(employeeId)) {
            return this.employees.get(employeeId);
        } else {
            return null;
        }
    }
}
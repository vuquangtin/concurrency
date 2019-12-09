package basic.beginner;

import java.util.ArrayList;
import java.util.Collections;
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
public class EmployeeManager6 {
    private List<Employee> emplyees;
    private final Object lockMonitor = new Object();
 
    public EmployeeManager6(){
        this.emplyees = new ArrayList<Employee>();
    }
 
    public Employee getEmployee(int index)
                throws CloneNotSupportedException {
        synchronized(this.lockMonitor){
            return (Employee)(this.emplyees.get(index)).clone();
        }
    }
 
    public void addEmployee(Employee employee) {
        synchronized(this.lockMonitor){
            this.emplyees.add(employee);
        }
    }
 
    public void deleteEmployee(int employeeId) {
        synchronized(this.lockMonitor){
            for (Iterator<Employee> it = emplyees.iterator(); it.hasNext();) {
                Employee emp = it.next();
                if(employeeId == emp.getEmployeeId()){
                    it.remove();
                    break;
                }
            }
        }
    }
 
    public void deleteEmployee(Employee employee) {
        synchronized(this.lockMonitor){
            this.emplyees.remove(employee);
        }
    }
 
    /*
     * returns an unmodifiable list.
     * Can also return a copy of the original list.
     *
     */
    public List<Employee> getAllEmployees(){
        return Collections.unmodifiableList(this.emplyees);
    }
}

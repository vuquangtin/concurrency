package basic.beginner;


/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class EmployeeManager6Client {
    private EmployeeManager6 employeeManager6;
 
    public EmployeeManager6Client(){
        this.employeeManager6 = new EmployeeManager6();
    }
 
    public void deleteEmployeeRecord(int employeeId){
        try {
            Employee employee = this.employeeManager6.getEmployee(employeeId);
            this.employeeManager6.deleteEmployee(employee);
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
}
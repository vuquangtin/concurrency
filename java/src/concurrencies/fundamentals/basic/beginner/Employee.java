package basic.beginner;

public class Employee {
	private int id;
	private String name;

	Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public Integer getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
     * This is the right way to get data from collections.
     * Finding by index is not safe as index may change over time.
     */
    public Employee getEmployee(String name)
                    throws CloneNotSupportedException{
        //get the employee from the list
        Employee employee = this.findEmployeeByName(name);
        if(employee != null){
            return (Employee)(employee.clone());
        }else{
            return null;
        }
    }

	private Employee findEmployeeByName(String name) {

		return null;
	}
}

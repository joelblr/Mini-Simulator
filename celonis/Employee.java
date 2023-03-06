package celonis;


public class Employee {
    private int id;
    private String ename;
    private double salary;
   
    
    Employee(int id,String name,double salary){
    	this.id=id;
    	this.ename=name;
    	this.salary=salary;
    }
    public int getId() {
    	return id;
    }
    public double getSalary() {
    	return salary;
    }
    public String getName() {
    	return ename;
    }
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return ename+" "+id+" "+salary;
    }
}

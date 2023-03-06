package celonis;

import java.util.*;

import design.DLabel;
import design.Design;
public class EmpMain {

	
	public static void run() {
		EmpMain.run();
	}


   public static void main(String args[]) {
	   List<Employee>a=new ArrayList<Employee>();
	   Scanner sc=new Scanner(System.in);
	   Scanner s=new Scanner(System.in);
	   int ch;
	   boolean found=false;
	   do {
//		   System.out.println("===============================");
//		   System.out.println("1.Insert");
//		   System.out.println("2.Display");
//		   System.out.println("3.Search");
//		   System.out.println("4.Delete");
//		   System.out.println("5.Update");
//		   System.out.println("===============================");
//		   System.out.print("Enter your choice:  ");
////		   ch=sc.nextInt();
//		   System.out.println("===============================");
		   List<String> stdin = 
					Design.printBox(0,
						new DLabel("", "<top>", ""),
						new DLabel("Celonis Simulator", "<tag>", "Y"),
						new DLabel("", "<joint>", ""),
						new DLabel(" *1*  Insert", "", "C"),
						new DLabel(" *2*  Display", "", "C"),
						new DLabel(" *3*  Search", "", "C"),
						new DLabel(" *4*  Delete", "", "C"),
						new DLabel(" *5*  Update", "", "C"),
						new DLabel(" *0*  Close Celonis Simulator", "", "C"),
						new DLabel("", "<joint>", ""),
						new DLabel("Enter Choice:  ", "<input>", "P"),
						new DLabel("", "<base>", "")
					);
		   ch = Integer.parseInt(stdin.get(0).trim());


		   
		   switch(ch) {
		   case 1: System.out.print("Enter the employee id: ");
		           int id=sc.nextInt();
		           System.out.print("Enter the name: ");
		           String name=s.nextLine();
		           System.out.print("Enter the salary:  ");
		           double salary=sc.nextDouble();
		           a.add(new Employee(id,name,salary));
		           break;
		            
		   case 2: //System.out.println("===============================");
		           Iterator<Employee> it=a.iterator();
		           while(it.hasNext()) {
		        	   
		        	    Employee e=it.next();
		        	    
		        	   System.out.println(e);
		           }
		          // System.out.println("===============================");
		           break;
		           
		    case 3: 
				   System.out.print("Enter the id to search:  ");
				   int idno=sc.nextInt();
				  // System.out.println("===============================");
		           it=a.iterator();
		           while(it.hasNext()) {
		        	    Employee e=it.next();
		        	    if(e.getId()==idno) {
		        	   System.out.println(e);
		        	   found=true;
		        	    }
		           }
		           //System.out.println("===============================");
		           if(!found) {
		        	   System.out.println("Record not found");
		           }
		           break;
		           
		   case 4: 
		           System.out.print("Enter the id to search:  ");
		            idno=sc.nextInt();
			         //System.out.println("===============================");
					   it=a.iterator();
					   while(it.hasNext()) {
						    Employee e=it.next();
						    if(e.getId()==idno) {
						   it.remove();
						   found=true;
						    }
					   }
					  // System.out.println("===============================");
					   if(!found) {
						   System.out.println("Record not found");
					   }
					   else {
						   System.out.println("Deleted sucessfully");
					   }
					   break;
					   
		   case 5: 
		           System.out.print("Enter the id to search:  ");
		            idno=sc.nextInt();
			         //System.out.println("===============================");
					   ListIterator<Employee>li=a.listIterator();
					   while(li.hasNext()) {
						    Employee e=li.next();
						    if(e.getId()==idno) {
						   System.out.print("Enter new name");
						   String nname=s.nextLine();
						   
						   System.out.println("Enter the new salary :  ");
						   double salarynew=sc.nextDouble();
						   li.set(new Employee (idno,nname,salarynew));
						   found=true;
						    }
					   }
					  // System.out.println("===============================");
					   if(!found) {
						   System.out.println("Record not found");
					   }
					   else {
						   System.out.println("Updated sucessfully");
					   }
					   break;
		}
		   
	   }while(ch!=0);
   }
}
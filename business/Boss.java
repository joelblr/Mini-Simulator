package business;

import design.*;
import errors.*;
import java.util.*;


public class Boss {

	/** @info Boss Fields */
	private String name, password, company, phno;
	HashMap<String, Employee> team;


	/** @info Constructor */
	public Boss(List<String> stdin) {
		this.updateName(stdin.get(0).trim());
		this.updateCompany(stdin.get(1).trim());
		this.updatePhoneNumber(stdin.get(2).trim());
		this.updatePassword(stdin.get(3).trim(), stdin.get(4).trim());
		team = new HashMap<String, Employee>();
	}


	/** @info Basic Set/Get Methods */
	public void updateName(String name) {
		if (name.matches("^[a-z A-Z]+$") == false)
			throw new BadAccountException("<BAD NAME!>\nAlphabets only.");
		this.name = name;

	}public void updateCompany(String company) {
		if (name.matches("^[a-z A-Z]+$") == false)
			throw new BadAccountException("<BAD COMPANY-NAME!>\nAlphabets only.");
		this.company = company;

	}public void updatePhoneNumber(String phno) {
		if (phno.matches("^[0-9]{10}$") == false)
			throw new BadAccountException("<BAD PHONE NUMBER!>\nValid 10 Digit Number Plz.");
		this.phno = phno;

	}public void updatePassword(String password1, String password2) {
		if (password1.length() < 8)
			throw new BadAccountException("<BAD PASSWORD!>\nLength >= 8");
		if (password1.equals(password2) == false)
			throw new BadAccountException("<BAD PASSWORD!>\nPasswords don't Match.");
		this.password = password1;


	}public String getName() {
		return this.name;

	}public String getCompany() {
		return this.company;

	}public String getPhoneNumber() {
		return this.phno;

	}public String getPassword() {
		return this.password;

	}
//-----------------------------------------------------------------------------------------

	/** @info Class Methods */

	/**
	 * @info This method needs to create a new account for the user;
	 * So, accept all the Axis-Account Credentials like name, phno....
	 and these credentials differ from a bank to another.
	
	** Ur given a Linked-List of axis-bank-accounts, <it may be empty> ,
	 just  get the credentials and create a new instance of AxisBank*/
	public static void createAccount(HashMap<String, Boss> bossAccounts) {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Boss : Create Account", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Company:  ", "<input>", "C"),
				new DLabel("Enter Phone No.:  ", "<input>", "C"),
				new DLabel("Enter Password:  ", "<hide>", "C"),
				new DLabel("Confirm Password:  ", "<hide>", "C"),
				new DLabel("", "<base>", "")
			);
		Boss acc = new Boss(stdin);
		bossAccounts.put(acc.getName(), acc);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Created Boss Account", "", "C"),
			new DLabel("", "<base>", "")
		);

	}


	/**
	 * @info This method helps to Sign-In an Account */
	public static Boss signInAccount(HashMap<String, Boss> accList) {

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Boss: Sign In", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Password:  ", "<hide>", "C"),
				new DLabel("", "<base>", "")
			);
		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(String.format("<INVALID ACCESS!>\nAccount: %s NOT Found.", name));

		Boss acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadAccountException(String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Signed In", "", "C"),
			new DLabel("", "<base>", "")
		);

		return acc;
	}


	/**
	 * @info This method needs to delete an existing account;
	 * So, accept Credentials like name and phno <to verify>, then
	 check if that name exists, then verify if phno is correct: finally remove that instance<ie, account>
	 else "throw IllegalAccessException"

	** Ur given a Linked-List of axis-bank-accounts, <it may be empty> ,
	 just  get the credentials and remove that existing instance of AxisBank*/
	public Boss deleteAccount(HashMap<String, Boss> accList) {
		List<String> stdin = 
				Design.printBox(40,
					new DLabel("", "<top>", ""),
					new DLabel("Axis Bank: Delete Account", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Name:  ", "<input>", "C"),
					new DLabel("Enter Password:  ", "<hide>", "C"),
					new DLabel("", "<base>", "")
				);

		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nAccount: %s NOT Found.", name));

		Boss acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadAccountException(
				String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		accList.remove(name);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Deleted Boss Account", "", "C"),
			new DLabel("", "<base>", "")
		);

		return null;

	}


	/**
	 * @info This method helps to Sign-Out an Account */
	public Boss signOutAccount() {
		return null;
	}



//------------------------------------------------------------------------------------
	/** @info Boss Operations */

	private String generateID() {
		String randID;
		do {
		randID = ((Math.random()+0.11111) * 100000 + "").substring(0, 4);
		}while (this.team.containsKey(randID));
		return randID;
	}

	/** @info Hire Employees */
	public void hireEmployee() throws BadAccountException {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel(this.getCompany()+" : Hire Employee", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Phone No.:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		String name = stdin.get(0).trim();
		String phno = stdin.get(1).trim();
		//salary TODO XXX
		Employee e = new Employee(name, phno, this.getCompany());
		e.updateID(generateID());

		this.team.put(e.getID(), e);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Hired Employee", "", "C"),
			new DLabel("", "<base>", "")
		);
	}


	/** @info Fire Employees */
	public void fireEmployee() {

		if (this.team.size() == 0)
			throw new BadAccountException("<NO EMPLOYEES!>\nPlz Hire Employees to Fire");

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel(this.getCompany()+" : Fire Employee", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter ID:  ", "<input>", "C"),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Phone No.:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);

		String id = stdin.get(0).trim();
		String name = stdin.get(1).trim();
		String phno = stdin.get(2).trim();

		if (!team.containsKey(id))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nEmployee ID: %s NOT Found.", id));

		Employee acc = team.get(id);
		if (!acc.getName().equals(name))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nEmployee : %s NOT Found.", name));

		if (!acc.getPhoneNumber().equals(phno))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nPhone No.: %s NOT Found.", phno));


		team.remove(id);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Fired Employee", "", "C"),
			new DLabel("", "<base>", "")
		);
	}


	/** @info Investigate an/all Employee(s) */
	public void exploreEmployees(int flag) {

		if (this.team.size() == 0)
			throw new BadAccountException("<NO EMPLOYEES!>\nPlz Hire Employees to Explore");

		/** @info All Employees*/
		if (flag == 1)
			for (String eId : this.team.keySet())
				this.team.get(eId).showDetails();

		/** @info An Employee */
		else {
			String id = this.exploreEmployee();
			this.team.get(id).showDetails();
		}
	}

	/** @info Investigate an Employee */
	public String exploreEmployee() {

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel(this.getCompany()+" : Explore Employee", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter ID:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);

		String id = stdin.get(0).trim();

		if (!team.containsKey(id))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nEmployee ID: %s NOT Found.", id));

		return id;
	}


	/** @info Update Employee Details
	public void updateEmployee() {
		
	}	@deprecated	*/


	/** @info Company's Salary-Slip */
	public void salarySheet() {

		if (this.team.size() == 0)
			throw new BadAccountException("<NO EMPLOYEES!>\nPlz Hire Employees to Display Bill");

		Employee.fields(1);

		for (String id : this.team.keySet())
			team.get(id).toString();

		Employee.fields(0);
		Design.printBox(141, new DLabel("", "<base>", ""));

	}

}




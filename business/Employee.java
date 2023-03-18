package business;

import design.*;
import errors.*;
import java.util.*;


public class Employee {

	/** @info Class Fields */
	public static String top, mid, base, field;
	public static String m, I, lR, lT, lL, rR, rT, rL, M, P, W;

	static {
		m = Design.m; I = Design.I;
		lR = Design.lR; M = Design.M; rR = Design.rR;
		lT = Design.lT; P = Design.P; rT = Design.rT;
		lL = Design.lL; W = Design.W; rL = Design.rL;

		field   = (I +" ID"+"   "+I) + (" EMPLOYEE NAME"+" ".repeat(7+1)+I);
		field += (" Basic   "+I) + (" DA      "+I) + (" HRA     "+I) + (" Medical "+I) + (" Bonus   "+I) + (" Gross T "+I);
		field += (" Contri  "+I) + (" Inc Tax "+I) + (" Pro Tax "+I) + (" Deduc T "+I) + (" Net Pay "+I);

		top 	= (lR + m.repeat(1+4+1)+M) + (m.repeat(1+20+1)+M);
		mid 	= (lT + m.repeat(1+4+1)+P) + (m.repeat(1+20+1)+P);
		base 	= (lL + m.repeat(1+4+1)+W) + (m.repeat(1+20+1)+W);

		for (int i = 0; i < 10; i++) {
			top		+= (m.repeat(1+7+1)+M);
			mid		+= (m.repeat(1+7+1)+P);
			base	+= (m.repeat(1+7+1)+W);
		}

		top		+= (m.repeat(1+7+1) + rR);
		mid		+= (m.repeat(1+7+1) + rT);
		base	+= (m.repeat(1+7+1) + rL);
	}

	/** @info Employee Fields */
	private String id, name, phno, company;
	private String salary[];
	private static LinkedList<String> salKeys;

	static {
		salKeys = new LinkedList<String>();
		salKeys.add("Basic");salKeys.add("DA");salKeys.add("HRA");
		salKeys.add("Medical");salKeys.add("Bonus");salKeys.add("Gross Total");
		salKeys.add("Contribution");salKeys.add("Income Tax");salKeys.add("Prof Tax");
		salKeys.add("Deduction Total");salKeys.add("Net Payment");
	}

	/** @info Employee Constructor */
	public Employee(String name, String phno, String com) {
		updateName(name);
		updatePhoneNumber(phno);
		updateCompany(com);
		updateSalary();

	}

	private void resizeArray(int idx, int k) {
		int len = salary[idx].length();
		salary[idx] = salary[idx].substring(0, len-3) + "," + salary[idx].substring(len-3, len);
		salary[idx] = " ".repeat(k-len-1) + salary[idx];
	}


	/** @info Basic Update/Get Methods */
	public void updateID(String id) {
		this.id = id;

	}public void updateName(String name) {
		if (name.matches("[a-z A-Z]+") == false)
			throw new BadAccountException("<BAD NAME!>\nAlphabets only.");
		this.name = name;

	}public void updatePhoneNumber(String phno) {
		if (phno.matches("^[0-9]{10}$") == false)
			throw new BadAccountException("<BAD PHONE NUMBER!>\nValid 10 Digit Number Plz.");
		this.phno = phno;

	}public void updateCompany(String company) {
		if (name.matches("[a-z A-Z]+") == false)
			throw new BadAccountException("<BAD COMPANY-NAME!>\nAlphabets only.");
		this.company = company;

	}public void updateSalary() {
		this.salary = new String[11];

		Random rm = new Random();
		long basic = rm.nextInt(100000 - 10000) + 10000 ;	/** @info Basic */
		salary[0] = basic + "";		resizeArray(0, 7);

		long gross = basic;
		for (int i = 1; i < 5; i++) {
			long amt = (long)(((0.50 - 0.20)*rm.nextDouble() + 0.20) * basic);
			gross += amt;
			salary[i] = amt + "";
			resizeArray(i, 7);

		}salary[5] = gross + "";				/** @info Total Gross */
		resizeArray(5, 7);

		long deduc = 0;
		for (int i = 6; i < 9; i++) {
			long amt = (long)(((0.15 - 0.10)*rm.nextDouble() + 0.10) * gross);
			deduc += amt;
			salary[i] = amt + "";
			resizeArray(i, 7);
		}

		salary[9] = deduc + "";					/** @info Total Deductions */
		salary[10] = (gross - deduc) + "";		/** @info Grand Total */

		resizeArray(9, 7);
		resizeArray(10, 7);


	}public String getID() {
		return this.id;

	}public String getName() {
		return this.name;

	}public String getPhoneNumber() {
		return this.phno;

	}public String getCompany() {
		return this.company;

	}public String[] getSalary() {
		return this.salary;

	}


	public void showDetails() {

		/** @info Extracting Details */
		String eId = this.getID();
		String eName = this.getName();
		String ePhno = this.getPhoneNumber();
		String eCom = this.getCompany();

		Iterator<String> itr = salKeys.iterator();
		String basic1 = "", gross, basic2 = "", deduc, net;	int i;

		/** @info Processing Details */
		for (i = 0; i < 5; i++)
			basic1 += itr.next()+": " + salary[i] + "; ";
		gross = itr.next()+": Rs. " + salary[i];
		for (i = 6; i < 9; i++)
			basic2 += itr.next()+": " + salary[i] + "; ";
		deduc = itr.next()+": Rs. " + salary[i++];
		net = itr.next()+": Rs. " + salary[i];

		/** @info Displaying Details */
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Employee Details", "<tag>", "Y"),
			new DLabel("\tPersonal Details", "<joint>", "P"),
			new DLabel("", "<blank>", ""),
			new DLabel("  ID: "+eId, "", "C C: G"),
			new DLabel("  Name: "+eName, "", "C C: G"),
			new DLabel("  Phno: "+ePhno, "", "C C: G"),
			new DLabel("  Company: "+eCom, "", "C C: G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\tSalary Details", "<joint>", "P"),
			new DLabel("\t\t\tGross Salary", "<joint>", "Y"),
			new DLabel("  "+basic1, "", "C C: G W, G R;"),
			new DLabel("  "+gross, "", "C C: G W, G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\t\t\tDeductions Salary", "<joint>", "Y"),
			new DLabel("  "+basic2, "", "C C: G W, G R;"),
			new DLabel("  "+deduc, "", "C C: G W, G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\t\t\tNet Salary", "<joint>", "Y"),
			new DLabel("  "+net, "", "C C: G W, G"),
			new DLabel("", "<base>", "")
		);
	}


	public static void fields(int flag) {

		if (flag == 1)
		Design.printBox(141,
			new DLabel("", "<top>", ""),
			new DLabel("Bill", "<tag>", "Y"),
			new DLabel(top, "", "G"),
			new DLabel(field, "", "G"+I + " P")
		);

		else	Design.printBox(141, new DLabel(base, "", "G"));
	}


	@Override
	public String toString() {

		String emp = I+ (" "+this.id+" "+I) + (" ".repeat(21-this.getName().length()) + this.getName() + " "
		+ I+" "+String.join(" "+I+" ", getSalary())+" "+I) ;

		Design.printBox(141, 
			new DLabel(mid, "", "G"+I + " P"),
			new DLabel(emp, "", "G"+I + " P")
		);

		return emp;
	}


}



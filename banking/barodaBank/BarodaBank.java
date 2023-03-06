package banking.barodaBank;

import banking.*;
import errors.*;
import design.*;
import java.util.*;


public class BarodaBank implements Bank {

	static double rate = 7.5;
	/*
	 Credential1, Credential2, Credential3,...
	 u can add ur own, but make sure to differ it from one bank to another 
	 */
	String name, phno, password;
	double amount;

	BarodaBank() {
		amount = 0.0;
	}

	public void amtUpdate(double amt) {
		this.amount += amt;
	}

	public String getName() {
		return this.name;

	}public String getPhNo() {
		return this.phno;

	}public String getPassword() {
		return this.password;

	}public double getBalance() {
		return this.amount;
	}

	public void setName(String name) {
		this.name = name;

	}public void setPhNo(String phno) {
		try {
			Integer.parseInt(phno);
		}catch (NumberFormatException e) {
			throw new BadPhoneNumberException("<BAD PHONE NUMBER!>\nValid 10 Digit Number Plz.");
		}
		if (phno.length() != 10)
			throw new BadPhoneNumberException("<BAD PHONE NUMBER!>\n10 Digit Number Plz.");
		this.phno = phno;

	}public void setPassword(String password) {
		if (password.length() < 8)
			throw new BadPasswordException("<BAD PASSWORD!>\nLength >= 8");
		this.password = password;
	}

	/** @info This method needs to create a new account for the user;
	 * So, accept all the Axis-Account Credentials like name, phno....
	 and these credentials differ from a bank to another.
	
	** Ur given a Linked-List of axis-bank-accounts, <it may be empty> ,
	 just  get the credentials and create a new instance of BarodaBank*/
	public static void createAccount(HashMap<String, BarodaBank> accList) {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank: Create Account", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter your name:  ", "<input>", "C"),
				new DLabel("Enter your phone no.:  ", "<input>", "C"),
				new DLabel("Enter your password:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		BarodaBank acc = new BarodaBank();
		acc.setName(stdin.get(0).trim());
		acc.setPhNo(stdin.get(1).trim());
		acc.setPassword(stdin.get(2).trim());

		accList.put(acc.getName(), acc);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Created BarodaBank Account", "", "C"),
			new DLabel("", "<base>", "")
		);

	}

	/** @info This method needs to delete an existing account;
	 * So, accept Credentials like name and phno <to verify>, then
	 check if that name exists, then verify if phno is correct: finally remove that instance<ie, account>
	 else "throw IllegalAccessException"

	** Ur given a Linked-List of axis-bank-accounts, <it may be empty> ,
	 just  get the credentials and remove that existing instance of BarodaBank*/
	public void deleteAccount(HashMap<String, BarodaBank> accList) {
		List<String> stdin = 
				Design.printBox(40,
					new DLabel("", "<top>", ""),
					new DLabel("Axis Bank: Delete Account", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter your name:  ", "<input>", "C"),
					new DLabel("Enter your password:  ", "<input>", "C"),
					new DLabel("", "<base>", "")
				);

		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(String.format("<INVALID ACCESS!>\nAccount: %s NOT Found.", name));

		BarodaBank acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadPasswordException(String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		accList.remove(name);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Deleted BarodaBank Account", "", "C"),
			new DLabel("", "<base>", "")
		);
	}
	public static BarodaBank signInAccount(HashMap<String, BarodaBank> accList) throws IllegalAccessException {

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank: Sign In", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter your name:  ", "<input>", "C"),
				new DLabel("Enter your password:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(String.format("<INVALID ACCESS!>Account: %s NOT Found.", name));

		BarodaBank acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadPasswordException(String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Signed In BarodaBank Account", "", "C"),
			new DLabel("", "<base>", "")
		);

		return acc;
	}

	/** @info This method needs to switch an existing account with another;
	 * So, accept Credentials like name and phno <to verify>, then
	 check if that name exists, then verify if phno is correct:
	 		finally set "currentAcc" with the instance<ie, account> that user wants
	 else "throw IllegalAccessException"

	** Ur given a Linked-List of axis-bank-accounts, <it may be empty> ,
	 just  get the credentials and remove that existing instance of BarodaBank
	 Finally: return the switchedAcc*/
	public void signOutAccount() {
		return;
	}


	/** @info This Method displays the Simple/Compound Interest (ur choice)
	based on the principal, rate of I, and time period
	
	Simple */
	public void loanInterest() {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank: Loan Calci", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Principal Amount: Rs  ", "<input>", "C"),
				new DLabel("Enter Time Period(in yrs):  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		double prin = Double.parseDouble(stdin.get(0).trim());
		double time = Double.parseDouble(stdin.get(1).trim());

		if (prin <= 0 || time <= 0)
			throw new BadTransactionException("<BAD NUMBERS!>\nPrincipal and Time > 0");

		double interest = (prin * rate * time)/100.0;
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Axis Bank: Simple Interest", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(String.format("Interest Amount: Rs  %.2f", interest) , "", "C"),
			new DLabel(String.format("Maturity Amount: Rs  %.2f", interest+prin), "", "G"),
			new DLabel("", "<base>", "")
		);
	}

	/** @info This Method displays Current Balance of the account. */
	public void amountBalance() {
		Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank: Balance", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(String.format("Balance Amount: Rs  %.2f", this.getBalance()) , "", "C"),
				new DLabel("", "<base>", "")
			);
	}

	/** @info This Method accepts the amount the user wants to deposit in account acc
	update the amount
	 throw "BadTransactionException" if amt is not valid, ie, negative. */
	public void amountDeposit() throws BadTransactionException {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank: Transaction", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Deposit Amount: Rs  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		double DAmt = Double.parseDouble(stdin.get(0).trim());
		if (DAmt > 1e6 || DAmt <= 0)
			throw new BadTransactionException(String.format("<TRANSACTION OVERFLOW!>\nCan't Deposit Rs %.2f.", DAmt));

		this.amtUpdate(DAmt);

		Design.printBox(40,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(String.format("Rs %.2f Deposited.", DAmt), "", "C"),
			new DLabel(String.format("Balance Amount: Rs  %.2f", this.getBalance()) , "", "C"),
			new DLabel("", "<base>", "")
		);

	}

	/** @info This Method accepts the amount the user wants to withdraw in account acc
	update the amount
	 throw "BadTransactionException" if amt is not valid, ie, negative or if overflow cases. */
	public void amountWithdraw() throws BadTransactionException {
		List<String> stdin = 
				Design.printBox(40,
					new DLabel("", "<top>", ""),
					new DLabel("Axis Bank: Transaction", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Withdraw Amount: Rs  ", "<input>", "C"),
					new DLabel("", "<base>", "")
				);
			double WAmt = Double.parseDouble(stdin.get(0).trim());
			if (WAmt > 1e6 || WAmt <= 0 || WAmt > this.getBalance())
				throw new BadTransactionException(String.format("<TRANSACTION OVERFLOW!>\nCan't Withdraw Rs %.2f.", WAmt));

			this.amtUpdate(-WAmt);

			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Successful", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(String.format("Rs %.2f Withdrawn.", WAmt), "", "C"),
				new DLabel(String.format("Balance Amount: Rs  %.2f", this.getBalance()) , "", "C"),
				new DLabel("", "<base>", "")
			);

	}


}

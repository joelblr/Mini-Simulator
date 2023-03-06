package banking;

import java.util.*;


public interface Bank {

	Scanner sc = new Scanner(System.in);

	public static void createAccount(HashMap<String, Bank> accList) {
	}
	public static Bank signInAccount(HashMap<String, Bank> accList) {
		return accList.get("bank");//just a return statement, dont worry much, it'll be overridden.
	}

	default public void deleteAccount() {}

	public void signOutAccount();

	public void loanInterest();

	public void amountBalance();
	
	public void amountDeposit();

	public void amountWithdraw();

}
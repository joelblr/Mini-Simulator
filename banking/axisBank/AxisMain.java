package banking.axisBank;

import errors.*;
import design.*;
import banking.*;


import java.util.*;
public class AxisMain extends BankMain {

	static AxisBank currentAxisAcc;
	static HashMap<String, AxisBank> axisAccounts;

	static {
		currentAxisAcc = null;
		axisAccounts = new HashMap<String, AxisBank>();
	}

	private static void showLogin() {
		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Axis Bank", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Create Account", "", "C"),
					new DLabel(" *2*  Sign In", "", "C"),
					new DLabel(" *0*  Quit Login", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}

	public static void showMenu() {

		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Axis Bank", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Delete Account", "", "C"),
					new DLabel(" *2*  Sign out", "", "C"),
					new DLabel(" *3*  Find Loan-Interest", "", "R"),
					new DLabel(" *4*  Find Balance Amount", "", "C"),
					new DLabel(" *5*  Deposit Amount", "", "C"),
					new DLabel(" *6*  Withdraw Amount", "", "R"),
					new DLabel(" *0*  Quit Axis-Bank", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}


	public static void run() {
		AxisRun();
	}


	public static void AxisRun() {

		currentAxisAcc = null;
		while (true) {


			if (currentAxisAcc == null) {

				try {

					Design.clearScreen();
					if (Design.errorFlag)
						Design.showErrorMessage(Design.errorMsg);
	
					showLogin();
					Design.loadingProcess(1500);
	
					switch (Design.IntegerChoice) {
	
						case 1 :
							AxisBank.createAccount(axisAccounts);
							Design.continueProcess();
							break;
	
						case 2 :
							currentAxisAcc = AxisBank.signInAccount(axisAccounts);
							break;

						case 0 :
							return;
	
						default :
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2");
	
					}
	
				}catch ( BadPhoneNumberException | BadPasswordException |
						BadAccountException | BadTransactionException e) {
					Design.errorMsg = e.getMessage();
					Design.errorFlag = true;
	
				}catch (NumberFormatException e) {
					if (!e.getMessage().startsWith("<"))
						Design.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2";
					else
						Design.errorMsg = e.getMessage();
					Design.errorFlag = true;
	
				}catch(Exception e) {
					Design.errorMsg = "<HEY!>\nOops.";	Design.errorFlag = true;
					e.printStackTrace();
					Design.scan.nextLine();
	
				}
			}
			else {

				try {
	
					Design.clearScreen();
					if (Design.errorFlag)
						Design.showErrorMessage(Design.errorMsg);
	
					showMenu();
					Design.loadingProcess(1500);
	
					switch (Design.IntegerChoice) {
	
						case 1 :
							currentAxisAcc.deleteAccount(axisAccounts);
							currentAxisAcc = null;
							break;
	
						case 2 :
							currentAxisAcc.signOutAccount();
							currentAxisAcc = null;
							break;
	
						case 3 :
							currentAxisAcc.loanInterest();
							break;
	
						case 4 :
							currentAxisAcc.amountBalance();
							break;
		
						case 5 :
							currentAxisAcc.amountDeposit();
							break;
	
						case 6 :
							currentAxisAcc.amountWithdraw();
							break;
	
						case 0 :
							return;
	
						default :
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6");
	
					}Design.continueProcess();
	
				}catch ( BadPhoneNumberException | BadPasswordException |
						BadAccountException | BadTransactionException e) {
					Design.errorMsg = e.getMessage();
					Design.errorFlag = true;
	
				}catch (NumberFormatException e) {
					if (!e.getMessage().startsWith("<"))
						Design.errorMsg = "<INVALID INPUT!>\nPlz give Proper Inputs.";
					else
						Design.errorMsg = e.getMessage();
					Design.errorFlag = true;
	
				}catch(Exception e) {
					Design.errorMsg = "<HEY!>\nOops.";	Design.errorFlag = true;
					e.printStackTrace();
					Design.scan.nextLine();
	
				}

			}
		}
	}
}



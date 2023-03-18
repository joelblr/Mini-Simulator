package banking.axisBank;

import errors.*;
import design.*;
import java.util.*;


public class AxisMain {

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
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void showMenu() {

		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Axis Bank", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Delete Account", "", "C"),
				new DLabel(" *2*  Sign out", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *3*  Find Loan-Interest", "", "C"),
				new DLabel(" *4*  Find Balance Amount", "", "C"),
				new DLabel(" *5*  Deposit Amount", "", "C"),
				new DLabel(" *6*  Withdraw Amount", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *0*  Quit Axis-Bank", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		AxisRun();
	}


	public static void AxisRun() {

		/** @AutoSignOut
		currentAxisAcc = null; */

		while (true) {

			if (currentAxisAcc == null) {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();
				showLogin();
				Cmd.loadingProcess(1500);

				try {

					switch (Cache.StringChoice) {

						case "1" :
							AxisBank.createAccount(axisAccounts);
							Cmd.continueProcess();
							break;

						case "2" :
							currentAxisAcc = AxisBank.signInAccount(axisAccounts);
							break;

						case "0" :
							return;

						default :
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2");
					}

				}catch ( BadAccountException | NumberFormatException e) {
					Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

				}catch(Exception e) {
					if (e.getMessage() == null)
						Cache.errorMsg = "<HEY!>\nAxis Login, [B0$$!";
					else
						Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

					e.printStackTrace();
					Cache.scan.nextLine();
				}

			}else {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();

				showMenu();
				Cmd.loadingProcess(1500);

				try {

					switch (Cache.StringChoice) {

						case "1" :
							currentAxisAcc.deleteAccount(axisAccounts);
							currentAxisAcc = null;
							Cmd.continueProcess();
							break;

						case "2" :
							currentAxisAcc.signOutAccount();
							currentAxisAcc = null;
							break;

						case "3" :
							currentAxisAcc.loanInterest();
							Cmd.continueProcess();
							break;

						case "4" :
							currentAxisAcc.amountBalance();
							Cmd.continueProcess();
							break;

						case "5" :
							currentAxisAcc.amountDeposit();
							Cmd.continueProcess();
							break;

						case "6" :
							currentAxisAcc.amountWithdraw();
							Cmd.continueProcess();
							break;

						case "0" :
							return;

						default :
							Cmd.continueProcess();
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6");

					}

				}catch ( BadAccountException | BadTransactionException | NumberFormatException e) {
					Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

				}catch(Exception e) {
					if (e.getMessage() == null)
						Cache.errorMsg = "<HEY!>\nAxisBank, [B0$$!";
					else
						Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

					e.printStackTrace();
					Cache.scan.nextLine();

				}
			}
		}
	}
}



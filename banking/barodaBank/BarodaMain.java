package banking.barodaBank;

import errors.*;
import design.*;
import java.util.*;


public class BarodaMain {

	static BarodaBank currentBarodaAcc;
	static HashMap<String, BarodaBank> barodaAccounts;
	static {
		currentBarodaAcc = null;
		barodaAccounts = new HashMap<String, BarodaBank>();
	}

	private static void showLogin() {
		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Baroda Bank", "<tag>", "Y"),
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
				new DLabel("Baroda Bank", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Delete Account", "", "C"),
				new DLabel(" *2*  Sign out", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *3*  Find Loan-Interest", "", "C"),
				new DLabel(" *4*  Find Balance Amount", "", "C"),
				new DLabel(" *5*  Deposit Amount", "", "C"),
				new DLabel(" *6*  Withdraw Amount", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *0*  Quit Baroda-Bank", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		BarodaRun();
	}


	public static void BarodaRun() {

		currentBarodaAcc = null;
		while (true) {

			if (currentBarodaAcc == null) {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();
				showLogin();
				Cmd.loadingProcess(1500);

				try {

					switch (Cache.StringChoice) {

						case "1" :
							BarodaBank.createAccount(barodaAccounts);
							Cmd.continueProcess();
							break;

						case "2" :
							currentBarodaAcc = BarodaBank.signInAccount(barodaAccounts);
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
						Cache.errorMsg = "<HEY!>\nBaroda Login, [B0$$!";
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
							currentBarodaAcc.deleteAccount(barodaAccounts);
							currentBarodaAcc = null;
							break;

						case "2" :
							currentBarodaAcc.signOutAccount();
							currentBarodaAcc = null;
							break;

						case "3" :
							currentBarodaAcc.loanInterest();
							break;

						case "4" :
							currentBarodaAcc.amountBalance();
							break;

						case "5" :
							currentBarodaAcc.amountDeposit();
							break;

						case "6" :
							currentBarodaAcc.amountWithdraw();
							break;

						case "0" :
							return;

						default :
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6");

					}Cmd.continueProcess();

				}catch ( BadAccountException | BadTransactionException | NumberFormatException e) {
					Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

				}catch(Exception e) {
					if (e.getMessage() == null)
						Cache.errorMsg = "<HEY!>\nBarodaBank, [B0$$!";
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



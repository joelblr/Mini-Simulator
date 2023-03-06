//package banking.barodaBank;
//
//import banking.*;
//import design.Design;
//
//import java.util.*;
//public class BarodaMain extends BankMain {
//
//	static BarodaBank currentBarodaAcc;
//	static HashMap<String, BarodaBank> barodaAccounts;
//	
//	static {
//		currentBarodaAcc = null;
//		barodaAccounts = new HashMap<String, BarodaBank>();
//	}
//
//
//	public static void showMenu() {
//
//		List<String> stdin = 
//				Design.printBox(
//					"Baroda Bank", "",
//					" *1*  Create an Account",
//					" *2*  Delete an Account",
//					" *3*  Switch an Account", "",
//					" *4*  Find Loan-Interest",
//					" *5*  Find Amount-Balance",
//					" *6*  Find Amount-Deposit",
//					" *7*  Find Amount-Withdraw", "",
//					" *0*  Quit Baroda-Bank", "",
//					"Enter Choice: $"
//				);
//
//		getChoice = Integer.parseInt(stdin.get(0).trim());
//	}
//
//
//	public static void run() {
//		BarodaRun();
//	}
//
//
//	public static void BarodaRun() {
//
//		currentBarodaAcc = null;
//		while (true) {
//
//			try {
//
//				Design.clearScreen();
//				if (errorFlag) {
//					Design.showErrorMessage(errorMsg);
//					errorFlag = false;
//				}
//				showMenu();
//				Design.loadingProcess(1500);
//
//				switch (getChoice) {
//
//					case 1 :
//						BarodaBank.createAccount(barodaAccounts);
//						break;
//	
//					case 2 :
//						BarodaBank.deleteAccount(barodaAccounts);
//						break;
//
//					case 3 :
//						BarodaBank.switchAccount(barodaAccounts);
//						break;
//
//					case 4 :
//						BarodaBank.loanInterest(currentBarodaAcc);
//						break;
//
//					case 5 :
//						BarodaBank.amountBalance(currentBarodaAcc);
//						break;
//	
//					case 6 :
//						BarodaBank.amountDeposit(currentBarodaAcc);
//						break;
//
//					case 7 :
//						BarodaBank.amountWithdraw(currentBarodaAcc);
//						break;
//
//					case 0 :
//						return;
//
//					default :
//						errorMsg = "<INVALID CHOICE!>";  errorFlag = true;
//						throw new NumberFormatException();
//
//				}Design.continueProcess();
//
//			}catch (NumberFormatException e) {
//				Design.loadingProcess(600);
//				errorMsg = "<INVALID INPUT!>";	errorFlag = true;
//
//			}catch (IllegalAccessException e) {
//				errorMsg = "<Account Expired!>";	errorFlag = true;
//
//			}catch (UnsupportedOperationException e) {
//				errorMsg = "<Not Enough Money!>";	errorFlag = true;
//
//			}catch(Exception e) {
//				errorMsg = "<HEY!>";	errorFlag = true;
//				e.printStackTrace();
//				Design.printBox("Pls Report this ErrorType to the Admin.");
//				scan.nextLine();
//
//			}
//
//		}
//	}
//}
//
//



package banking.barodaBank;

import errors.*;
import design.*;
import banking.*;


import java.util.*;
public class BarodaMain extends BankMain {

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

			Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}

	public static void showMenu() {

		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Baroda Bank", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Delete Account", "", "C"),
					new DLabel(" *2*  Sign out", "", "C"),
					new DLabel(" *3*  Find Loan-Interest", "", "R"),
					new DLabel(" *4*  Find Balance Amount", "", "C"),
					new DLabel(" *5*  Deposit Amount", "", "C"),
					new DLabel(" *6*  Withdraw Amount", "", "R"),
					new DLabel(" *0*  Quit Baroda-Bank", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}


	public static void run() {
		BarodaRun();
	}


	public static void BarodaRun() {

		currentBarodaAcc = null;
		while (true) {


			if (currentBarodaAcc == null) {

				try {

					Design.clearScreen();
					if (Design.errorFlag)
						Design.showErrorMessage(Design.errorMsg);
	
					showLogin();
					Design.loadingProcess(1500);
	
					switch (Design.IntegerChoice) {
	
						case 1 :
							BarodaBank.createAccount(barodaAccounts);
							break;
	
						case 2 :
							currentBarodaAcc = BarodaBank.signInAccount(barodaAccounts);
							break;

						case 0 :
							break;
	
						default :
							throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2");
	
					}Design.continueProcess();
	
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
							currentBarodaAcc.deleteAccount(barodaAccounts);
							currentBarodaAcc = null;
							break;
	
						case 2 :
							currentBarodaAcc.signOutAccount();
							currentBarodaAcc = null;
							break;
	
						case 3 :
							currentBarodaAcc.loanInterest();
							break;
	
						case 4 :
							currentBarodaAcc.amountBalance();
							break;
		
						case 5 :
							currentBarodaAcc.amountDeposit();
							break;
	
						case 6 :
							currentBarodaAcc.amountWithdraw();
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


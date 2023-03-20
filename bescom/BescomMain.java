package bescom;

import design.*;
import errors.*;
import java.util.*;


public class BescomMain {

	static Bescom currentBescomAcc;
	static HashMap<String, Bescom> bescomAccounts;
	static {
		currentBescomAcc = null;
		bescomAccounts = new HashMap<String, Bescom>();
	}


	private static void showLogin() {
		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom", "<tag>", "Y"),
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
				new DLabel("Bescom", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Enter Monthly Unit-Details", "", "C"),
				new DLabel(" *2*  Update Unit-Details", "", "C"),
				new DLabel(" *3*  Print a Bill", "", "C"),
				new DLabel(" *4*  Print All Bills", "", "C"),
				new DLabel(" *5*  Print Bill Table", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *6*  Delete Account", "", "C"),
				new DLabel(" *7*  Sign out", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *0*  Quit Bescom", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		BescomRun();
	}


	public static void BescomRun() {

		/** @AutoSignOut
		currentAxisAcc = null; */

		while (true) {

			if (currentBescomAcc == null) {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();
				showLogin();
				Cmd.loadingProcess(1500);

				try {

					switch (Cache.StringChoice) {

					case "1" :
						Bescom.createAccount(bescomAccounts);
						Cmd.continueProcess();
						break;

					case "2" :
						currentBescomAcc = Bescom.signInAccount(bescomAccounts);
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
						Cache.errorMsg = "<HEY!>\nBescom Login, [B0$$!";
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
						currentBescomAcc.inputUnitDetails("Input Data");
						Cmd.continueProcess();
						break;

					case "2" :
						currentBescomAcc.inputUnitDetails("Update Data");
						Cmd.continueProcess();
						break;

					case "3" :
						currentBescomAcc.printMonthBill();
						Cmd.continueProcess();
						break;

					case "4" :
						currentBescomAcc.printAllBill();
						Cmd.continueProcess();
						break;

					case "5" :
						currentBescomAcc.printBillTable();
						Cmd.continueProcess();
						break;

					case "6" :
						currentBescomAcc = currentBescomAcc.deleteAccount(bescomAccounts);
						break;

					case "7" :
						currentBescomAcc = currentBescomAcc.signOutAccount();
						break;

					case "0" :
						return;

					default :
						throw new NumberFormatException(
							"<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6/7");

					}

				}catch ( BadAccountException | BadTransactionException | NumberFormatException e) {
					Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

				}catch(Exception e) {
					if (e.getMessage() == null)
						Cache.errorMsg = "<HEY!>\nBescom, [B0$$!";
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



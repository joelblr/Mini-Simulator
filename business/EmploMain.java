package business;


import errors.*;
import design.*;
import java.util.*;


public class EmploMain {

	static Boss currentBossAcc;
	static HashMap<String, Boss> bossAccounts;
	static {
//		Boss b = new Boss(Arrays.asList("admin", "Amazon", "1231231233", "asdasdasd", "asdasdasd"));
//		currentBossAcc = b;
		currentBossAcc = null;
		bossAccounts = new HashMap<String, Boss>();
//		bossAccounts.put("asd", b);
//
//		Employee e = new Employee("John Fredy Peter", "1234567890", "Id");
//		e.updateID("1234");
//		b.team.put("1234", e);
//
//		e = new Employee("James Andrew Matthew", "1234567890", "Id");
//		e.updateID("1334");
//		b.team.put("1334", e);
	}


	private static void showLogin() {
		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Boss", "<tag>", "Y"),
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
		String cmp = currentBossAcc.getCompany();

		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel(cmp, "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Hire Employee", "", "C"),
				new DLabel(" *2*  Fire Employee", "", "C"),
				new DLabel(" *3*  Salary Sheet", "", "C"),
				new DLabel(" *4*  Explore Employee", "", "C"),
				new DLabel(" *5*  Explore all Employees", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *6*  Delete Account", "", "C"),
				new DLabel(" *7*  Sign out", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *0*  Quit " + cmp, "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		EmploRun();
	}


	public static void EmploRun() {

		/** @AutoSignOut
		currentBossAcc = null; */

		while (true) {

			if (currentBossAcc == null) {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();
				showLogin();
				Cmd.loadingProcess(1500);

				try {

					switch (Cache.StringChoice) {

						case "1" :
							Boss.createAccount(bossAccounts);
							Cmd.continueProcess();
							break;

						case "2" :
							currentBossAcc = Boss.signInAccount(bossAccounts);
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
						Cache.errorMsg = "<HEY!>\nBoss Login, [B0$$!";
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
							currentBossAcc.hireEmployee();
							Cmd.continueProcess();
							break;

						case "2" :
							currentBossAcc.fireEmployee();
							Cmd.continueProcess();
							break;

						case "3" :
							currentBossAcc.salarySheet();
							Cmd.continueProcess();
							break;

						case "4" :
							currentBossAcc.exploreEmployees(0);
							Cmd.continueProcess();
							break;

						case "5" :
							currentBossAcc.exploreEmployees(1);
							Cmd.continueProcess();
							break;

						case "6" :
							currentBossAcc = currentBossAcc.deleteAccount(bossAccounts);
							break;

						case "7" :
							currentBossAcc = currentBossAcc.signOutAccount();
							break;

						case "0" :
							return;

						default :
							throw new NumberFormatException(
								"<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6/7");

					}

				}catch ( BadAccountException | NumberFormatException e) {
					Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

				}catch(Exception e) {
					if (e.getMessage() == null)
						Cache.errorMsg = "<HEY!>\nEmploMain, [B0$$!";
					else
						Cache.errorMsg = e.getMessage();
					Cache.errorFlag = true;

					e.printStackTrace();
					Cache.scan.nextLine();

				}
			}
		}
	}


	/** @TestRun */
	public static void main(String[] args) {
		EmploMain.run();
	}


}






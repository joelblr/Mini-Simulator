package bescom;

import design.*;
import java.util.List;


public class BescomMain {


	public static Bescom EB = new Bescom();


	public static void showMenu() {

		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Bescom", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Enter Account Credentials", "", "C"),
					new DLabel(" *2*  Enter Monthly-Unit Details", "", "C"),
					new DLabel(" *3*  Update Unit Details", "", "C"),
//					new DLabel(" *4*  Print a Bill", "", "C"),
					new DLabel(" *4*  Print All Bills", "", "C"),
					new DLabel(" *0*  Close Bescom", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			Cache.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}

	public static void run() {
		BescomRun();
	}


	public static void BescomRun() {

		while (true) {

			try {

				Cmd.clearScreen();
				if (Cache.errorFlag)
					Cmd.showErrorMessage();

				showMenu();
				Cmd.loadingProcess(1500);

				switch (Cache.IntegerChoice) {

					case 1 :
						EB.getCredentials();
						break;
	
					case 2 :
						EB.inputMonthBill();
						break;

					case 3 :
						EB.updateUnits();
						break;

//					case 4 :
//						EB.printMonthBill();
//						break;

					case 4 :
						EB.printAllBills();
						break;

					case 0 :
						return;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5");

				}Cmd.continueProcess();

			}catch (NumberFormatException e) {
				Cmd.loadingProcess(600);
				if (e.getMessage() == null)
					Cache.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2/3/4/5";
				else
					Cache.errorMsg = e.getMessage();

			}catch(Exception e) {
				Cache.errorMsg = "<HEY!>\nOops.";	Cache.errorFlag = true;
				e.printStackTrace();
				Cache.scan.nextLine();

			}

		}
	}

	/**
	 * @Info Test Runs */
	public static void main(String[] args) {
		BescomMain.run();
	}
}





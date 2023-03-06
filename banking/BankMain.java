package banking;

import java.util.List;
import banking.axisBank.*;
import banking.barodaBank.*;
import design.*;


public class BankMain {

	/* HashMap accounts = {
				 "axis" : bankAccount1 -> bankAccount2 -> bankAccount3, -> ...
				 "baroda" : bankAccount1 -> bankAccount2 -> bankAccount3, -> ...
				}
	 */


	public static void showMenu() {

		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Banks of India", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Axis Bank", "", "C"),
					new DLabel(" *2*  Baroda Bank", "", "C"),
					new DLabel(" *0*  Close Banking", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}


	public static void run() {
		BankRun();
	}


	public static void BankRun() {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						AxisMain.run();
						break;
	
					case 2 :
						BarodaMain.run();
						break;

					case 0 :
						return;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2");

				}//continueProcess();

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
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
	}
	public static void main(String[] args) {
		BankMain.run();
	}
}


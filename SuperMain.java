
import design.*;
import calci.CalciMain;
import java.util.*;

public class SuperMain {


	public static void showMenu() {

		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Program Simulator", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Casio fx-707", "", "C"),
					new DLabel(" *2*  Celonis", "", "R"),
					new DLabel(" *3*  BESCOM", "", "R"),
					new DLabel(" *4*  Cash Ware", "", "R"),
					new DLabel(" *0*  Close Program Simulator", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}


	public static void run() {
		return;
	}


	public final static void main(String[] args) {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						CalciMain.run();
						break;

//					case 2 :
//						EmployeeMain.run();
//						break;
//
//					case 3 :
//						BescomMain.run();
//						break;
//
//					case 4 :
//						BankMain.run();
//						break;


					case 0 :
						Design.scan.close();
						Design.clearScreen();
						System.exit(0);

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4");
//						Design.errorMsg = "<INVALID CHOICE!>";	Design.errorFlag = true;
				}

			}catch (NumberFormatException e) {
//				System.out.println();
				Design.loadingProcess(600);
//				e.printStackTrace();
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2/3/4";
				else
					Design.errorMsg = e.getMessage();
				//Design.errorMsg = "<INVALID INPUT!>";
				Design.errorFlag = true;

			}catch (Exception e) {
				e.printStackTrace();
				Design.errorMsg = "<HEY!>\nBOSS!";
				Design.errorFlag = true;
			}
		}

	}
}


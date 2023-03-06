package calci.mode_5;

import design.*;
import errors.*;
import java.util.*;
import calci.Setup;



/**
 * @info Mode-5 : Equations
 * */
public class EqnMain {


	public static void showMenu() {
		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Equation Mode", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Solve Linear Equations", "", "C"),
				new DLabel(" *2*  Solve Quadratic Equation", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *SM*  Setup Mode", "", "C"),
				new DLabel(" *AC*  Switch Mode", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);
		Design.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		Mode5Run();
	}


	public static void Mode5Run() {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.StringChoice) {

					case "ac" :
						return;
	
					case "sm" :
						Setup.showSetup();
						break;

					case "1" :
						Equations.LinearRun();
						Design.continueProcess();
						break;
	
					case "2" :
						Equations.QuadraticRun();
						Design.continueProcess();
						break;

					default :
//						Design.errorMsg = "<INVALID CHOICE!>";  Design.errorFlag = true;
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: ac/sm/1/2");

				}//Design.continueProcess();

			/* TODO: Condense All Common Errors' Functionality */
			}catch (LimitExceededException e) {
				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (InputsFreqException e) {
				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (ArithmeticException e) {
				Design.loadingProcess(600);
				e.printStackTrace();
				Design.errorMsg = "<DIVISION BY ZERO!>\nZero uh?";	Design.errorFlag = true;

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<INVALID INPUT!>\nChoices must be ac/sm/1/2";	Design.errorFlag = true;

			}catch (QuadraticException e) {
				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch(Exception e) {
				Design.errorMsg = "<HEY!>\nLol!";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}


	public static void main(String[] args) {
		EqnMain.run();
	}
}
















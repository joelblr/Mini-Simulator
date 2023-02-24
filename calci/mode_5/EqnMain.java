package calci.mode_5;

import java.util.*;
import design.*;


/**
 * @info Mode-5 : Equations
 * */
public class EqnMain {


	public static void showMenu() {
		List<String> stdin = 
				Design.printBox(
					"Equation Mode T", "",
					" *1*  Solve Linear Equations M",
					" *2*  Solve Quadratic Equation M",
					" *0*  Switch Mode M", "",
					"Enter Choice:  $"
				);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}


	public static void run() {
		Mode5Run();
	}


	public static void Mode5Run() {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag) {
					Design.showErrorMessage(Design.errorMsg);
					Design.errorFlag = false;
				}

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						Equations.LinearRun();
						break;
	
					case 2 :
						Equations.QuadraticRun();
						break;

					case 0 :
						return;

					default :
//						Design.errorMsg = "<INVALID CHOICE!>";  Design.errorFlag = true;
						throw new NumberFormatException("<INVALID CHOICE!>");

				}Design.continueProcess();

			//To catch Errors: Implement Verify.java inside Convertor.java
			}catch (ArithmeticException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<DIVISION BY ZERO!>";	Design.errorFlag = true;

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<INVALID INPUT!>";	Design.errorFlag = true;

			}catch (InputMismatchException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<NOT A QUADRATIC!>";	Design.errorFlag = true;

			}catch(Exception e) {
				Design.errorMsg = "<HEY!>";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}
}
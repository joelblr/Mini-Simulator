package calci;

import design.*;
import errors.*;
import java.util.*;

import calci.mode_1.*;
import calci.mode_2.*;
//import calci.mode_3.*;
import calci.mode_4.*;
import calci.mode_5.*;
import calci.mode_6.*;


public class CalciMain {


	public static void showMenu() {

		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Casio fx-991+ Modes", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Scientific", "", "C"),
				new DLabel(" *2*  Complex Mode", "", "C"),
//				new DLabel(" *3*  Statistics", "", "R"),
//				new DLabel(" *4*  Base-N Mode", "", "C"),
				new DLabel(" *5*  Equation Mode", "", "C"),
				new DLabel(" *6*  Programming", "", "R"),
//				new DLabel(" *7*  Table Mode", "", "R"),
//				new DLabel(" *8*  Vector Mode", "", "R"),
				new DLabel(" *9*  Setup Mode", "", "C"),
				new DLabel(" *0*  Close Casio fx-707", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}

	public static String showDisplay(String tag) {
		List<String> stdin = 
			Design.printBox(50,
				new DLabel("", "<top>", ""),
				new DLabel(tag, "<tag>", "Y"),
				new DLabel("", "<input>", "C"),
				new DLabel("", "<base>", "")
		);
		return stdin.get(0);
	}

	public static void run() {
		CalciRun();
	}


	public static void CalciRun() {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						CompMain.run();
						break;
	
					case 2 :
						KomplxMain.run();
						break;

//					case 3 ://TODO XXX
////						StatMain.run();
//						break;
//
//					case 4 ://TODO XXX
//						NBaseMain.run();
//						break;

					case 5 :
						EqnMain.run();
						break;

					case 6 :
						BoolMain.run();
						break;

					case 9 :
						Setup.showSetup();
						break;

					case 0 :
						return;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6/7/8/9");

				}//Design.continueProcess();

			//To catch Errors: Implement Verify.java inside Convertor.java
			}catch (ArithmeticException e) {
				Design.loadingProcess(600);
//				Design.errorMsg = "<DIVISION BY ZERO!>\nSorry cant handle ∞ Infinity";
				Design.errorFlag = true;

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2/3/4/5/6/7/8/9";
				else
					Design.errorMsg = e.getMessage();

				Design.errorFlag = true;

			}catch (InputsFreqException e) {
				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (QuadraticException e) {
				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch(ArrayIndexOutOfBoundsException e) {
				Design.errorMsg = "<NOT ENOUGH INPUTS!>\nPls give Valid Inputs";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}catch(Exception e) {
				Design.errorMsg = "<HEY!>\nI'll CalciMain the Boss!";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}
}




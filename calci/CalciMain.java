package calci;

import design.*;
import errors.*;

import java.util.*;

import calci.mode_1.*;
import calci.mode_2.*;
//import calci.mode_4.*;
//import calci.mode_5.*;



public class CalciMain {

	private static String angle;

	static {
		angle = "rad";	// deg or rad
	}

	public static void setAngleMode(String mode) {
		angle = mode;

	}public static String getAngleMode() {
		return angle;
	}

//	private static String showDisplay(String tag) {
//		List<String> stdin = 
//			Design.printBox(50,
//				new DLabel("", "<top>", ""),
//				new DLabel(tag, "<tag>", "Y"),
//				new DLabel("", "<input>", "C"),
//				new DLabel("", "<base>", "")
//		);
//		return stdin.get(0);
//	}

	public static void showSetup() {

		while (true) {
			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);
	
			List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Casio fx-991+ Setup", "<tag>", "Y"),
					new DLabel("", "<blank>", ""),
					new DLabel(" *1*  Degree", "", "C"),
					new DLabel(" *2*  Radian", "", "C"),
					new DLabel(" *0*  Close Setup", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			try {
	
				Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
				Design.loadingProcess(1500);
	
				switch (Design.IntegerChoice) {
	
					case 1 :
						setAngleMode("deg");
						return;
	
					case 2 :
						setAngleMode("rad");
						return;
	
					case 0 :
						return;
	
					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2");
				}
	
			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2";
				else
					Design.errorMsg = e.getMessage();
	
				Design.errorFlag = true;
	
			}
		}
	}

	public static void showMenu() {

		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Casio fx-991+ Modes", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(" *1*  Scientific", "", "C"),
				new DLabel(" *2*  Programming", "", "C"),
				new DLabel(" *3*  Complex Mode", "", "R"),
				new DLabel(" *4*  Base-N Mode", "", "C"),
				new DLabel(" *5*  Equation Mode", "", "C"),
				new DLabel(" *6*  Matrix Mode", "", "R"),
				new DLabel(" *7*  Table Mode", "", "R"),
				new DLabel(" *8*  Vector Mode", "", "R"),
				new DLabel(" *9*  Setup Mode", "", "C"),
				new DLabel(" *0*  Close Casio fx-707", "", "C"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Choice:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}


	public static void run() {
		CalciRun();
	}


	public static void CalciRun() {

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
						CompMain.run();
						break;
	
					case 2 :
						BoolMain.run();
						break;

					case 3 :
//						Expressions.booleanRun();
						break;

					case 4 :
//						NBaseMain.run();
						break;

//					case 5 :
//						EqnMain.run();
//						break;

					case 9 :
						showSetup();
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

			}catch (ManyInputsException e) {
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




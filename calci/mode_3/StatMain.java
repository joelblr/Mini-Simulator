package calci.mode_3;


import design.*;
import errors.*;
import java.util.*;


public class StatMain {


	public static void showMenu() {

//		List<String> stdin = 
//			Design.printBox(0,
//				new DLabel("", "<top>", ""),
//				new DLabel("Statistics Mode", "<tag>", "Y"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *1*  New Model", "", "C"),
//				new DLabel(" *2*  Update Model", "", "C"),
//
//				new DLabel(" avg prod sum max min var sd", "", "R"),
//				new DLabel(" func_name ( valid exp )", "", "C"),
//				new DLabel(" sum(A+B*C)", "", "C"),
//
//				new DLabel(" *SM*  Setup Mode", "", "C"),
//				new DLabel(" *AC*  Switch Mode", "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel("Enter Choice:  ", "<input>", "P"),
//				new DLabel("", "<base>", "")
//			);
//
//		Cache.IntegerChoice = Integer.parseInt(stdin.get(0).trim());

	}

//	public static String showDisplay(String tag) {
//		List<String> stdin = 
//			Design.printBox(50,
//				new DLabel("", "<top>", ""),
//				new DLabel(tag, "<tag>", "Y"),
//				new DLabel("", "<input>", "C"),
//				new DLabel("", "<base>", "")
//		);
//		return stdin.get(0);
//	}

	public static void run() {
//		CalciRun();
	}


//	public static void CalciRun() {
//
//		while (true) {
//
//			Cmd.clearScreen();
//			if (Cache.errorFlag)
//				Cmd.showErrorMessage();
//
//			try {
//
//				showMenu();
//				Cmd.loadingProcess(1500);
//
//				switch (Cache.IntegerChoice) {
//
//					case 1 :
//						CompMain.run();
//						break;
//	
//					case 2 :
//						KomplxMain.run();
//						break;
//
//					case 3 ://TODO XXX
//						StatMain.run();
//						break;
//
//					case 4 ://TODO XXX
//						NBaseMain.run();
//						break;
//
//					case 5 :
//						EqnMain.run();
//						break;
//
//					case 6 :
//						BoolMain.run();
//						break;
//
//					case 9 :
//						Setup.showSetup();
//						break;
//
//					case 0 :
//						return;
//
//					default :
//						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/1/2/3/4/5/6/7/8/9");
//
//				}
//			}
//
//			catch (InputsFreqException | ArithmeticException | BadNumberException e) {
//				Cache.errorMsg = e.getMessage();
//				Cache.errorFlag = true;
//
//			}
//
//			catch (NumberFormatException e) {
//				Cmd.loadingProcess(600);
//				if (!e.getMessage().startsWith("<"))
//					Cache.errorMsg = "<INVALID INPUT!>\nChoice must be 0/1/2/3/4/5/6/7/8/9";
//				else
//					Cache.errorMsg = e.getMessage();
//
//				Cache.errorFlag = true;
//
//			}
//			catch(ArrayIndexOutOfBoundsException e) {
//				Cache.errorMsg = "<NOT ENOUGH INPUTS!>\nPls give Valid Inputs";
//				Cache.errorFlag = true;
//				e.printStackTrace();
//
//			}catch(Exception e) {
//				Cache.errorMsg = "<HEY!>\nCalci [B0$$!";
//				Cache.errorFlag = true;
//				e.printStackTrace();
//			}
//
//		}
//
//	}
}

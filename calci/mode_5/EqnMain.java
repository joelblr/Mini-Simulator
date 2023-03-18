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
		Cache.StringChoice = stdin.get(0).trim();
	}


	public static void run() {
		Mode5Run();
	}


	public static void Mode5Run() {

		while (true) {

			Cmd.clearScreen();
			if (Cache.errorFlag)
				Cmd.showErrorMessage();

			showMenu();
			Cmd.loadingProcess(1500);

			try {

				switch (Cache.StringChoice) {

					case "ac" :
						return;

					case "sm" :
						Setup.showSetup();
						break;

					case "1" :
						Equations.LinearRun();
						Cmd.continueProcess();
						break;

					case "2" :
						Equations.QuadraticRun();
						Cmd.continueProcess();
						break;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: ac/sm/1/2");

				}

			}catch (InputsFreqException | LimitExceededException |
					BadExpressionException | BadNumberException e) {

				Cmd.loadingProcess(600);
				Cache.errorMsg = e.getMessage();
				Cache.errorFlag = true;

			}

			/**XXX @Warning ???? */
//			catch (NumberFormatException e) {
//				Design.loadingProcess(600);
//				Design.errorMsg = "<INVALID INPUT!>\nChoices must be ac/sm/1/2";
//				Design.errorFlag = true;
//			}

			catch(Exception e) {
				Cache.errorMsg = "<HEY!>\n[B0$$, Mode5!";
				Cache.errorFlag = true;

				e.printStackTrace();
				Cache.scan.nextLine();

			}

		}

	}


	public static void main(String[] args) {
		EqnMain.run();
	}
}
















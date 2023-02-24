package calci.mode_2;

import calci.*;
import errors.*;
import design.*;


/**
 * @info  <p>{@code Mode-2 Boolean Mode}
 * */
public class BoolMain {


	public static void showMenu() {

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Boolean Mode", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(" & | ~ ^ >> << ", "", "C"),
			new DLabel("", "<joint>", ""),
			new DLabel(" *ac*  Switch Mode", "", "P"),
			new DLabel("", "<base>", "")
		);
	}


	public static void run() {
		Mode2Run();
	}


	public static void Mode2Run() {

		while (true) {

			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);

			showMenu();
			String stdin = CalciMain.showDisplay("Enter Boolean Expression")
					.trim().toLowerCase();
			Design.loadingProcess(750);

			try {

				switch (stdin) {

					case "ac" :
						return;

					default:
						Expressions.printTruthTable(stdin);
				}

			}catch (BadNumberException e) {
				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (InvalidFunctionException e) {
				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (ArithmeticException e) {
				Design.loadingProcess(600);
				e.printStackTrace();
//				Design.errorMsg = "<DIVISION BY ZERO!>\nSorry cant handle ∞ Infinity";
				Design.errorFlag = true;

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				e.printStackTrace();
				Design.errorMsg = "<INVALID INPUT!>\nChoice: AC/S or some Valid Exp!";
				Design.errorFlag = true;

			}catch(Exception e) {
				Design.errorMsg = "<HEY!>";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}
}

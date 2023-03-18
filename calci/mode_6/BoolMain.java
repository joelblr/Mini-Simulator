package calci.mode_6;

import calci.*;
//import errors.*;
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

			Cmd.clearScreen();
			if (Cache.errorFlag)
				Cmd.showErrorMessage();

			showMenu();
			String stdin = CalciMain.showDisplay("Enter Boolean Expression").trim();//.toLowerCase();
			Cmd.loadingProcess(750);

			try {

				switch (stdin) {

					case "ac" :		return;
					default:	BoolExpression.printTruthTable(stdin);

				}Cmd.continueProcess();

			}

			catch (NumberFormatException e) {
				Cmd.loadingProcess(600);
				e.printStackTrace();
				Cache.scan.nextLine();
				Cache.errorMsg = "<INVALID INPUT!>\nChoice: AC or S or Some Valid Exp!";
				Cache.errorFlag = true;


			}catch(Exception e) {
				Cache.errorMsg = "<HEY!>\n[B0$$, Mode6!";
				Cache.errorFlag = true;
				e.printStackTrace();
				Cache.scan.nextLine();

			}

		}

	}


	public static void main(String[] args) {
		BoolMain.run();
	}
}




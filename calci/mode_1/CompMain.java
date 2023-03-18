package calci.mode_1;

import calci.*;
import errors.*;
import design.*;
import calci.transformo.*;


/**
 * @info  Mode-1 : {@code Scientific Mode}<p>
 * */
public class CompMain {

	static String result = "0";

	public static void showMenu() {
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Scientific Mode", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(" ceil floor abs pow sqrt cbrt", "", "C"),
			new DLabel(" exp log ln fact ncr npr", "", "C"),
			new DLabel(" sin cos tan csc sec cot", "", "C"),
			new DLabel(" asin acos atan acsc asec acot", "", "C"),
			new DLabel(" sinh cosh tanh csch sech coth", "", "C"),
			new DLabel("", "<joint>", ""),
			new DLabel(" *SM*  Setup Mode", "", "P"),
			new DLabel(" *AC*  Switch Mode", "", "P"),
			new DLabel("", "<base>", "")
		);
	}


	private static void showResult() {
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Result", "<tag>", "Y"),
			new DLabel(result, "", "G"),
			new DLabel("", "<base>", "")
		);
	}


	public static void run() {
		Mode1Run();
	}


	public static void Mode1Run() {

		while (true) {

			Cmd.clearScreen();
			if (Cache.errorFlag)
				Cmd.showErrorMessage();

			showMenu();
			Cmd.cursorGoto(0, 5, 0, 0, 0);
			showResult();
			Cmd.cursorGoto(9, 0, 0, 0, 0);
			String stdin = CalciMain.showDisplay("Enter Arithmetic Expression").trim().toLowerCase();

			try {

				switch (stdin) {

					case "ac" :
						return;

					case "sm" :
						Setup.showSetup();
						break;

					default :
						double ans = Computo.solveScientific(stdin);
						result = (Setup.getNumberType()) ? Fractions.toFractions(ans) :
								String.format("%."+ Setup.getPrecision() +"f", ans);
				}

			}catch (BadNumberException | BadExpressionException | InputsFreqException e) {
				Cache.errorMsg = e.getMessage();
				Cache.errorFlag = true;

			}catch (NumberFormatException e) { 
				e.printStackTrace();
				Cache.errorMsg = "<INVALID EXP!>\nPlz, Give a Valid Expression.";
				Cache.errorFlag = true;

			}catch(Exception e) {
				Cache.errorMsg = "<HEY!>\n[Bo$$, Mode1!";
				Cache.errorFlag = true;

				e.printStackTrace();
				Cache.scan.nextLine();
			}
		}
	}


	public static void main(String[] args) {
		CompMain.run();
//		System.out.println(new Fractions(1, 2).toString());
//		System.out.println( Fractions.toFractions(1.33));
//		System.out.println( Fractions.toFractions(1.67));
//		System.out.println( Fractions.toFractions(0.167));
	}
}





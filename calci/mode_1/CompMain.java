package calci.mode_1;

import calci.*;
import errors.*;
import design.*;
import java.util.*;
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

			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);

			showMenu();
			Design.cursorGoto(0, 5, 0, 0, 0);
			showResult();
			Design.cursorGoto(9, 0, 0, 0, 0);
			String stdin = CalciMain.showDisplay("Enter Boolean Expression").trim().toLowerCase();

			try {

				switch (stdin) {

					case "ac" :
						return;

					case "sm" :
						Setup.showSetup();
						break;

					default:
						double ans = Computo.solveScientific(stdin);

						result = (Setup.getNumberType()) ? Fractions.toFractions(ans) :
								String.format("%."+ Setup.getPrecision() +"f", ans);

						showResult();
				}

			}catch (NullPointerException e) {
				e.printStackTrace();
				Design.errorMsg = "<!>\nOops!";
//				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (EmptyStackException e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (BadNumberException e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (BadFunctionException e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorFlag = true;

			}catch (ArithmeticException e) {
//				Design.loadingProcess(600);
				e.printStackTrace();
//				Design.errorMsg = "<DIVISION BY ZERO!>\nSorry cant handle ∞ Infinity";
				Design.errorFlag = true;

			}catch (NumberFormatException e) {
//				Design.loadingProcess(600);
				e.printStackTrace();
				Design.errorMsg = "<INVALID INPUT!>\nChoice: AC/S or some Valid Exp!";
				Design.errorFlag = true;

			}catch(Exception e) {
				e.printStackTrace();
				Design.errorMsg = "<HEY!>\nI'll tell the Boss!";
				Design.errorFlag = true;
//				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}

	public static void main(String[] args) {
		CompMain.run();
		System.out.println(new Fractions(1, 2).toString());
		System.out.println( Fractions.toFractions(1.33));
		System.out.println( Fractions.toFractions(1.67));
		System.out.println( Fractions.toFractions(0.167));
	}
}
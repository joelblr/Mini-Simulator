package calci.mode_2;

import calci.*;
import errors.*;
import design.*;
//import java.util.*;
import calci.transformo.*;
/**
 * @info  <p>{@code Mode-3 Complex Numbers Mode}
 * */
public class KomplxMain {

	static String result = "0 + 0i";

	public static void showMenu() {
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Complex Mode", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(" pol rec ", "", "C"),
			new DLabel(" arg conj mod ", "", "C"),
			new DLabel("", "<joint>", ""),
			new DLabel(" *SM*  Setup Mode", "", "P"),
			new DLabel(" *AC*  Switch Mode", "", "P"),
			new DLabel("", "<base>", "")
		);

	}

//	private static void resultUpdate() {//TODO
//		if (result.startsWith("∠")) {
//
//			double θ = 0.0;
////			if (result.contains("⅃")) {
////				if (result.contains("°"))
////					θ = Fractions.toDecimal(result.substring(2, result.length()-1));
////				else
////					θ = Fractions.toDecimal(result.substring(2, result.length()));
////			}
//
//			if (Setup.getAngleType().equals("deg")) {
//
//				try {
//					try {θ = Math.toDegrees(
//							Double.parseDouble(result.substring(2, result.length()))
//							);}
//					catch(NumberFormatException e)
//					{θ = Double.parseDouble(result.substring(2, result.length()-1));}
//				}
//				catch(Exception e) {
//					if (result.contains("⅃")) {
//						if (result.contains("°"))
//							θ = Fractions.toDecimal(result.substring(2, result.length()-1));
//						else
//							θ = Math.toDegrees(
//							Fractions.toDecimal(result.substring(2, result.length())));
//					}
//				}
//
//			}else {
//				try {
//					try {θ = Double.parseDouble(result.substring(2, result.length()));}
//					catch(NumberFormatException e)
//					{θ = Math.toRadians(
//						Double.parseDouble(result.substring(2, result.length()-1))
//						);}
//				}
//				catch (Exception e) {
//					if (result.contains("⅃")) {
//						if (result.contains("°"))
//							θ = Math.toRadians(
//							Fractions.toDecimal(result.substring(2, result.length()-1)));
//						else
//							θ = Fractions.toDecimal(result.substring(2, result.length()));
//					}
//				}
//			}
//
//			int p = Setup.getPrecision();
//			if (Setup.getNumberType()) {
//				if (Setup.getAngleType() == "deg")
//					result = String.format("∠ %s°", Fractions.toFractions(θ));
//				else
//					result = String.format("∠ %s", Fractions.toFractions(θ));
//
//			}else {
//				if (Setup.getAngleType() == "deg")
//					result = String.format("∠ %."+p+"f°", θ);
//				else
//					result = String.format("∠ %."+p+"f", θ);
//			}
//		}
//		else if (!result.contains("∠")) {
//			double z[] = new Komplx(result).toArray();
//			int p = Setup.getPrecision();
//			char flag = (z[1] >= 0.0) ? '+' : '-';
//
//			if (Setup.getNumberType())
//				result = Fractions.toFractions(z[0]) + 
//				" "+flag+" " + Fractions.toFractions(Math.abs(z[1])) +"i";
//			else {
//				result = String.format("%."+p+"f", z[0]) + 
//						" "+flag+" " + String.format("%."+p+"f", Math.abs(z[1]))+"i";
//				;
//			}
//
//		}
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
		Mode2Run();
	}


	public static void Mode2Run() {

		while (true) {

			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);

			showMenu();
			Design.cursorGoto(0, 5, 0, 0, 0);
//			resultUpdate();
			showResult();
			Design.cursorGoto(9, 0, 0, 0, 0);
			String stdin = CalciMain.showDisplay("Enter Complex Expression").trim().toLowerCase();

			try {

				switch (stdin) {

					case "ac" :
						return;

					case "sm" :
						Setup.showSetup();
						break;

					default:
						result = KplxComputo.solveKplx(stdin);
//						showResult();
				}

			}catch (BadNumberException e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (BadFunctionException e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (BadExpression e) {
				e.printStackTrace();
//				Design.loadingProcess(600);
				Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (ArithmeticException e) {
//				Design.loadingProcess(600);
				e.printStackTrace();
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<DIVISION BY ZERO!>\nSorry cant handle ∞ Infinity";
				else
					Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch (NumberFormatException e) {
//				Design.loadingProcess(600);
				e.printStackTrace();
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>\nChoice: AC/S or some Valid Exp!";
				else
					Design.errorMsg = e.getMessage();
				Design.errorFlag = true;

			}catch(Exception e) {
				e.printStackTrace();
				Design.errorMsg = "<HEY!>\nI'll tell the Boss!";
				Design.errorFlag = true;
				Design.scan.nextLine();

			}

		}

	}

	public static void main(String[] args) {
		KomplxMain.run();
		System.out.println(new Fractions(1, 2).toString());
		System.out.println( Fractions.toFractions(1.33));
		System.out.println( Fractions.toFractions(1.67));
		System.out.println( Fractions.toFractions(0.167));
	}
}
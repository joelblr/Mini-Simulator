package calci;

import design.*;
import java.util.*;


public class Setup {

	private static int precision = 0;
	private static String angle = "deg";
	private static boolean isFraction = false;
	private static int nbase = 10;


	public static void setPrecision(Object precision) {
		try {
			Setup.precision = Integer.parseInt((String) precision);
			if (Setup.precision > 9 || Setup.precision < 0)
				throw new NumberFormatException();
		}catch (Exception e) {
			throw new NumberFormatException("<PRECISION ERROR!>\n0<= Precision <= 9");
		}

	}public static void setAngleType(String angle) {
		Setup.angle = angle;

	}public static void setNumberType(boolean isFraction) {
		Setup.isFraction = isFraction;

	}public static void setNBase(int nbase) {
		Setup.nbase = nbase;

	}

	public static int getPrecision() {
		return precision;

	}public static String getAngleType() {
		return angle;

	}public static boolean getNumberType() {
		return isFraction;

	}public static int getNBase() {
		return nbase;

	}

	public static void showSetup() {

		while (true) {
			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);
	
			List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Casio fx-991+ Setup", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel(" *1*  Degree", "", "C"),
					new DLabel(" *2*  Radian", "", "C"),
					new DLabel(" *3*  Fraction Format", "", "C"),
					new DLabel(" *4*  Decimal Format", "", "C"),
					new DLabel(" *0*  Close Setup", "", "C"),
					new DLabel(" ENTER F(0 ~ 9) TO SET PRECISION", "", "W PF C P0 C P9 C) W"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			try {
	
				Design.StringChoice = stdin.get(0).trim();
				Design.loadingProcess(1500);

				if (Design.StringChoice.startsWith("F")) {
					Setup.setPrecision(Design.StringChoice.substring(1).trim());
					return;
				}

				switch (Design.StringChoice) {
					case "1" :
						Setup.setAngleType("deg");
						return;
	
					case "2" :
						Setup.setAngleType("rad");
						return;

					case "3" :
						Setup.setNumberType(true);
						return;
	
					case "4" :
						Setup.setNumberType(false);
						return;

					case "0" :
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


	public static void showNBaseSetup() {

		while (true) {
			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);
	
			List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("N-Base Setup", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("  *2*  Binary", "", "C"),
					new DLabel("  *8*  Octal", "", "C"),
					new DLabel(" *10*  Decimal", "", "C"),
					new DLabel(" *16*  Hexadecimal", "", "C"),
					new DLabel("  *0*  Close Setup", "", "C"),
					new DLabel("", "<joint>", ""),
					new DLabel("Enter Choice:  ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);

			try {
	
				Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 2 :	case 8 :	case 10 :	case 16 :
						Setup.setNBase(Design.IntegerChoice);
						return;

					case 0 :
						return;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>\nChoices are: 0/2/8/10/16");
				}
	
			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>\nChoice must be 0/2/8/10/16";
				else
					Design.errorMsg = e.getMessage();
	
				Design.errorFlag = true;
	
			}
		}
	}
}

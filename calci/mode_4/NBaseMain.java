package calci.mode_4;

import calci.*;
import errors.*;
import design.*;
import java.util.*;
import calci.transformo.*;

/**
 * @info  <p>{@code Mode-4 Base-N}
 * */
public class NBaseMain {


	private static String currAns, prevAns;
	private static int curRadix, prevRadix;
	private static Map<String, Integer> modes;

	static {
		currAns = prevAns = "0";	curRadix = prevRadix = 10;
		modes = new HashMap<String, Integer>();
		modes = Map.of("Binary", 2, "Octal", 8, "Decimal", 10, "Hexadecimal", 16);
	}


	private static String convertToNewBase(String num, int srcBase, int desBase) {
		return Integer.toString(Integer.parseInt(num, srcBase) , desBase);
	}


	private static void showResult(String tag) {
		String nResult = convertToNewBase(currAns, curRadix, modes.get(tag));
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel(tag+ "\t" + modes.get(tag), "<tag>", "Y"),
			new DLabel(nResult, "", "G"),
			new DLabel("", "<base>", "")
		);
	}


	public static void showMenu() {

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("N-Base Mode", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel(" * + - / ", "", "C"),
			new DLabel(" ! ~ & | ^ >> << ", "", "C"),
			new DLabel("", "<joint>", ""),
			new DLabel(" *SM*  Setup Mode", "", "P"),
			new DLabel(" *AC*  Switch Mode", "", "P"),
			new DLabel("", "<base>", "")
		);
	}


	public static void run() {
		NBaseRun();
	}


	public static void NBaseRun() {

		while (true) {

			Cmd.clearScreen();
			if (Cache.errorFlag)
				Cmd.showErrorMessage();

			String tag = String.format("Enter an Expression\t\t(Base: %s)", curRadix);
			try {

				showMenu();
				Cmd.cursorGoto(0, 5, 0, 0, 0);

				showResult("Binary");
				showResult("Octal");
				showResult("Decimal");
				showResult("Hexadecimal");

				Cmd.cursorGoto(21, 0, 0, 0, 0);
				String stdin = CalciMain.showDisplay(tag).trim();

				switch (stdin) {

					case "ac" :
						return;
	
					case "sm" :
						prevRadix = curRadix ;
						Setup.showNBaseSetup();
						curRadix = Setup.getNBase();
						currAns = Integer.toString(Integer.parseInt(currAns, prevRadix) , curRadix);
						break;
	
					default :
						/** @info Case when Boolean+Arithmetic Expn */
						stdin = stdin.replace("$", "@").replace("^", "$").replace("!", "~");
						Converto.findPostfix(stdin, 'A');
						String pf = Converto.getPostfix().toString();/** @info [Comma, Separated] */
						pf = pf.replace("$", "^").replace("@", "$").substring(1, pf.length()-1);
						LinkedList<String> newPf = new LinkedList<String>();
						for (String s : pf.split(", "))
							newPf.add(s);

						Converto.setPostfix(newPf);
						prevAns = currAns;
						currAns = BaseNComputo.evalPostfix(curRadix);
				}
			}

			catch (NumberFormatException e) {
				e.printStackTrace();
				Cache.scan.nextLine();
				if (!e.getMessage().startsWith("<"))
					Cache.errorMsg = String.format(
						"<OPERAND OVERFLOW!>\n%s undefined for Radix %s", currAns, curRadix);
				else
					Cache.errorMsg = e.getMessage();

				currAns = prevAns;
				Cache.errorFlag = true;

			}catch (BadNumberException | BadExpressionException | InputsFreqException e) {
				Cache.errorMsg = e.getMessage();
				Cache.errorFlag = true;

			}catch(Exception e) {
				Cache.errorMsg = "<HEY!>\n[B0$$, Mode4";
				Cache.errorFlag = true;

				e.printStackTrace();
				Cache.scan.nextLine();
			}

		}

	}


	/** @testRuns */
	public static void main(String[] args) {
		NBaseMain.run();

	}


}






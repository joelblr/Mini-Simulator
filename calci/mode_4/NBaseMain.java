//package calci.mode_4;
//
//import calci.Convertor;
//import design.Design;
//import java.util.*;
//
//
//public class NBaseMain {
//
////	static HashMap<Integer, String[]> baseXp;
//
//	static String curMode;
//	// TODO prevRadix not used !
//	static int result10, prevRadix, curRadix;
//	static Map<Integer, String> modes = new HashMap<Integer, String>();
//
//	static {
//		curRadix = 10;	prevRadix = 10;
//		curMode = "Decimal";
//		modes = Map.of(2, "Binary", 8, "Octal", 10, "Decimal", 16, "Hexadecimal");
//	}
//
//	/// TODO
//	private static ArrayList<String> convertXp(ArrayList<String> pf) {
//		return pf;
//	}
//
//
//	private static void checkValid(String infix)  throws Exception {
//
//		if (curRadix > 9)
//			return;
//
//		for (char x : infix.toCharArray()) {
//			if (Character.isAlphabetic(x) && Character.toUpperCase(x) - '7' >= curRadix )
//				throw new Exception();
//			else if (Character.isDigit(x) && Integer.valueOf(x - '0') >= curRadix)
//				throw new NumberFormatException("<INVALID INPUT!>");
//
//		}
//
//	}
//
//
//	private static void evalAllBases() {
//		for (int radix : modes.keySet())
//				Design.printBox(
//						modes.get(radix) + " T", "",
//						Integer.toString(result10, radix) + " C"
//					);
//
//	}
//
//	private static String convert10(String infix) {
//		String new_infix = "";
//		String num = "";
//		for (int i = 0; i < infix.length(); i++) {
//
//			if (Character.isLetterOrDigit(infix.charAt(i))) {
//				num += infix.charAt(i);
//
//			}else if (i > 0 && Character.isLetterOrDigit(infix.charAt(i-1))) {
//				new_infix += Integer.parseInt(num, curRadix) + "" + infix.charAt(i);
//				num = "";
//
//			}else
//				new_infix += infix.charAt(i);
//		}
//
//		return new_infix;
//	}
//
//
//	private static void evaluate() throws Exception {
//
//		Design.printBox(
//				"Current Mode: " + curMode + " T",
//				"Usage T", "",
//				" '^' : XOR M",
//				" '+', '|' : OR M",
//				" '&', '.' : AND M",
//				" '!' : NOT M",
//				" '~' : NEGATION M"
//			);
//
//		List<String> stdin =
//				Design.printBox(
//						"ENTER EXPRESSION:  $",
//						" ".repeat(50),
//						Integer.toString(result10, curRadix) + " C"
//					);
//
//		
//		String infix = "(" + stdin.get(0) + ")";
//		checkValid(infix);
//		infix = convert10(infix);
//
//
//		Convertor.findPostfix(infix, 'A');
//		Convertor.setPostfix(convertXp(Convertor.getPostfix()));
//
//		result10 = (int)Convertor.evalPostfix(null);
//
//		evalAllBases();
//	}
//
//	private static void changeBase() {
//
//		List<String> stdin = 
//				Design.printBox(
//					"Base-N T", "",
//					"  *2*  Binary M",
//					"  *8*  Octal M",
//					" *10*  Decimal M",
//					" *16*  Hexadecimal M", "",
//					"Enter Base:  $"
//				);
//
//		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
//		prevRadix = curRadix;
//		curRadix = Design.IntegerChoice;
//		curMode = modes.get(curRadix);
//	}
//
//
//	public static void showMenu() {
//
//		List<String> stdin =
//				Design.printBox(
//						"Current Mode: " + curMode + " T", "",
//						" *1*  Change Mode M",
//						" *2*  Continue M",
//						" *0*  Close Base-N M", "",
//						"Enter Choice:  $"
//					);
//
//		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
//	}
//
//
//	public static void run() {
//		NBaseRun();
//	}
//
//
//	public static void NBaseRun() {
//
//		while (true) {
//
//			try {
//
//				Design.clearScreen();
//				if (Design.errorFlag) {
//					Design.showErrorMessage(Design.errorMsg);
//					Design.errorFlag = false;
//				}
//
//				showMenu();
//				Design.loadingProcess(1500);
//
//				switch (Design.IntegerChoice) {
//
//					case 1 :
//						changeBase();
//						Design.loadingProcess(1500);
//
//					case 2 :
//						Design.clearScreen();
//						evaluate();
//						break;
//
//					case 0 :
//						return;
//
//					default :
////						Design.errorMsg = "<INVALID CHOICE!>";  Design.errorFlag = true;
//						throw new NumberFormatException("<INVALID CHOICE!>");
//
//				}Design.continueProcess();
//
//			//Yet to catch all Errors
//			}catch(NumberFormatException e) {
//				Design.errorMsg = e.getMessage();	Design.errorFlag = true;
//				if (!Design.errorMsg.startsWith("<"))
//					Design.errorMsg = "<INVALID INPUT!>";
////				e.printStackTrace();
////				Design.scan.nextLine();
//
//			}catch(Exception e) {
//				Design.errorMsg = "<HEY!>";	Design.errorFlag = true;
//				e.printStackTrace();
//				Design.scan.nextLine();
//
//			}
//
//		}
//
//	}
//}
//
//
//

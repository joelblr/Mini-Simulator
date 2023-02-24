package calci.mode_2;

import calci.*;
import design.*;


public class Expressions {

	//Boolean Expression
	private static int N;
	private static int BoolInput[];


//	private static void getExpression(String type) {
//
//		List<String> stdin = 
//				Design.printBox(
//						"ENTER " + type + " EXPRESSION: $",
//						" ".repeat(55)
//					);
//
//		String infix = "(" + stdin.get(0) + ")";
//		
//		if (type == "ARITHMETIC") {
//			Convertor.findPostfix(infix, 'A');
//
//		}else {
//			Convertor.findPostfix(infix, 'B');
//			N = Convertor.getUnique().size();
//			BoolInput = new int[N];
//		}
//	}



	public static void printTruthTable(String infix) {
		truthTable("(" + infix + ")");

	}


	private static void nextInput() {
		for (int idx = N-1; idx > -1; idx--) {
			BoolInput[idx] ^= 1;
			if (BoolInput[idx] == 1)
				return;
		}

	}private static void truthTable(String infix) {

		Converto.findPostfix(infix, 'B');
		N = Converto.getUnique().size();
		BoolInput = new int[N];

		String row = "";

		Design.printTop(4*N+3);

		for (String key : Converto.getUnique()) {
			String newKey = Colorify.bold + Colorify.fg_red + key + Colorify.reset;
			row += " "+newKey+" │";

		}row += Colorify.bold + Colorify.fg_green + " f " + Colorify.reset;

		Design.printFrame(0, new DLabel(row, "", "C|W"));
		row = "───┼".repeat(N) + "───";
		Design.printFrame(0, new DLabel(row, "", "W"));

		String newKey;
		for (int k = 0; k < 1<<N; k++) {
			row = "";
			for (int val : BoolInput) {
				newKey = Colorify.bold + Colorify.fg_yellow + val + Colorify.reset;
				row += " "+newKey+" │";

			}
			newKey = Colorify.bold + Colorify.fg_green + 
					(int)Computo.evalPostfix(BoolInput) + Colorify.reset;
			row += " " + newKey + " ";

//			Design.printJoinRow("", row);
			Design.printFrame(0, new DLabel(row, "", "G|W"));
			nextInput();

		}Design.printBase(4*N + 3);

		return;
	}
}

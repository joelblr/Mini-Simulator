package calci.mode_6;


import design.*;
import java.util.*;
import calci.transformo.*;


public class BoolExpression {


	private static int N;
	private static HashMap<String, Integer> BoolInput;
	private static ArrayList<HashMap<String, Integer>> BoolMap;

	static {
		BoolInput = new HashMap<String, Integer>();
		BoolMap = new ArrayList<HashMap<String, Integer>>();
	}


	public static void printTruthTable(String infix) {
		truthTable(infix);

	}


	private static void nextInput() {

		for (int idx = 0; idx < N; idx++) {

			HashMap<String, Integer> hmap = BoolMap.get(idx);
			String key = "";	Integer val = 0;
			for (String k : hmap.keySet()) {
				key = k;
				val = hmap.get(k);
			}

			BoolMap.get(idx).put(key, val ^ 1);
			BoolInput.put(key, val ^ 1);

			if (BoolInput.get(key) == 1)
				return;
		}

	}private static void truthTable(String infix) {

		Converto.findPostfix(infix, 'B');
		Object[] variables = Converto.getUnique().toArray();
		N = variables.length;

		/** @info Initialising BoolMap & BoolInput */
		for (Object s : variables) {
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			hm.put(s.toString(), 0);
			BoolMap.add(0, hm);
			BoolInput.put(s.toString(), 0);
		}

		String row = "", newKey;

		Design.printBox(4*N+3, new DLabel("", "<top>", ""));
		for (Object key : variables)
			row += " " + key + " │";

		row += " f ";
		Design.printBox(4*N+3, new DLabel(row, "", "P W│ ".repeat(N) + "G" ));

		row = "───┼".repeat(N) + "───";
		Design.printBox(4*N+3, new DLabel(row, "", "W"));

		for (int k = 0; k < 1<<N; k++) {
			row = "";

			for (int itr = N-1; itr > -1; itr--) {

				HashMap<String, Integer> hmap = BoolMap.get(itr);
				Integer val = null;
				for (String key : hmap.keySet())
					val = hmap.get(key);
				row += " " + val + " │";

			}
			newKey = (int)Computo.evalPostfix(BoolInput) + "";
			row += " " + newKey + " ";
			Design.printBox(4*N+3, new DLabel(row, "", "P W│ ".repeat(N) + "G"));

			nextInput();

		}Design.printBox(4*N+3, new DLabel("", "<base>", ""));

		return;
	}


	public static void main(String[] args) {

		printTruthTable("~(!A*B + A.!B)");

	}

}



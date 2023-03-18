package design;

import java.util.*;

/**
 * @Author Joel A
 * */
public final class Design extends Colorify {

	final static int factor;
	final static String prefix;

	final static String leftR, rightR, mid;
	final static String leftI, rightI;
	final static String leftT, rightT;
	final static String leftL, rightL;
	final static String midP, midW, midM;

	public final static String lR, rR, m;
	public final static String I;
	public final static String lT, rT;
	public final static String lL, rL;
	public final static String P, W, M;

	static {
		prefix = " ".repeat(8);
		factor = 3 + 0 + 0 + 3;
		String fg_box = fg_blue;

		mid = bold + fg_box + "─" + reset;/** @Version1 "╭─╮","╰─╯", @Version2 "┌─┐","└─┘"  */
		leftR = prefix + bold + fg_box + "┌" + reset;	rightR = bold + fg_box + "┐" + reset;
		leftT = prefix + bold + fg_box + "├" + reset;	rightT = bold + fg_box + "┤" + reset;
		leftI = prefix + bold + fg_box + "│   "  + reset;	rightI = bold + fg_box + "   │" + reset;
		leftL = prefix + bold + fg_box + "└" + reset;	rightL = bold + fg_box + "┘" + reset;

		midP = bold + fg_box + "┼" + reset;	midW = bold + fg_box + "┴" + reset;
		midM = bold + fg_box + "┬" + reset;

		m = "─";
		I  = "│";
		lR = "┌"; M = "┬"; rR = "┐";
		lT = "├"; P = "┼"; rT = "┤";
		lL = "└"; W = "┴"; rL = "┘";


	}


	/**
	 * @info Returns a {@code Joined String} with delimiter
	 * @param   delimiter  :  Char/String to be added with each Element.
	 * @param	elements   :  String varargs to be attached with Delimiter.
	 * @usage 	Design.joinWith(String delimiter, String ... elements); <p>
	 * 			delimiter: a {@code String} <p>
	 * 			elements : a {@code String varargs} "elem 1", "elem 2", "elem 3"... , "elem N"
	 * @example	Design.joinWith(" + ", "a", "b", "c"); <p>
	 * @output  a + b + c
	*/
	public static String joinWith(String delimiter, String ... elements) {
		return String.join(delimiter, elements);
	}


	/**
	 * @Info Prints the Input Message in a Box Format
	 * @param   msg   ->  Messages
	 * @usage 	DLabel( msg, tag, color format ); <p>
	 * 			msg: a {@code String} <p>
	 * 			code: a {@code String} "top", "base", "joint", "blank", "tag", ""(nothing)
	 * 				and enclosed in {@code Aungular brackets}
	 * @example
	 * 				Design.printBox(0,
			<br>		new DLabel("", "(top)", ""),
			<br>		new DLabel("Casio fx-991+ Setup", "(tag)", "Y"),
			<br>		new DLabel("", "(joint)", ""),
			<br>		new DLabel(" *1*  Degree", "", "C"),
			<br>		new DLabel(" *2*  Radian", "", "C"),
			<br>		new DLabel(" *3*  Fraction Format", "", "C"),
			<br>		new DLabel(" *4*  Decimal Format", "", "C"),
			<br>		new DLabel(" *0*  Close Setup", "", "C"),
			<br>		new DLabel(" ENTER F(0 ~ 9) TO SET PRECISION", "", "W PF C P0 C P9 C) W"),
			<br>		new DLabel("", "(joint)", ""),
			<br>		new DLabel("Enter Choice:  ", "(input)", "P"),
			<br>		new DLabel("", "(base)", "")
	 */
	public static List<String> printBox(int width, DLabel ... labels) {

		List<Integer[]> cursorEof = new LinkedList<Integer[]>();

		if (width == 0)		width = findMaxWidth(labels);

		int N = labels.length, lines = 1;
		for (int i = 0; i < N; i++) {

			lines++;

			switch (labels[i].getCode()) {

			case "<top>" :
				printTop(width);	continue;

			case "<tag>" :
				if (i == 0)		System.out.println();
				printTag(width, labels[i]);
				continue;

			case "<blank>" :
				printBlank(width);	continue;

			case "<joint>" :
				printJoint(width, labels[i]);	continue;

			case "<base>" :
				printBase(width);	continue;

			case "<input>" : case "<hide>" : case "<read>" :
				int len = prefix.length() + factor/2+1 + labels[i].getTxt().length();
				int hide = (labels[i].getCode() == "<hide>") ? 1 : 0;
				cursorEof.add(new Integer[] {i, len, hide});

			default :
				printFrame(width, labels[i]);
			}

		}//lines++;

		List<String> stdin = new LinkedList<String>();
		if (! cursorEof.isEmpty())
			stdin = getStdin(cursorEof, lines);

		return stdin;
	}


	/**
	 * @Info Input Processor */
	private static List<String> getStdin(List<Integer[]> inputs, int lines) {

		int i = lines;
		List<String> stdin = new LinkedList<String>();
		System.out.print("\033["+(i-inputs.get(0)[0])+"A");

		i = inputs.get(0)[0] -1;
		for (Integer[] arr : inputs) {

			if ((arr[0]-i) != 0) {
				System.out.print("\033["+(arr[0]-i)+"B");
			}
			System.out.print("\033["+arr[1]+"C");
			System.out.print(fg_green + bold);

			/** @ScannerVersion 
			stdin.add(Cache.scan.nextLine());
			System.out.print(reset);
				@deprecated */

			/** @ConsoleVersion */
			if (arr[2] == 1)
				stdin.add(	String.valueOf(	Cache.cnsl.readPassword()));
			else
				stdin.add(Cache.cnsl.readLine());
			System.out.print(reset);

			i = arr[0]+1;
		}

		System.out.print("\n".repeat(lines - i -1));

		return stdin;
	}


	/**
	 * @info Finds the Appropriate Width of the Box
	 * @param  labels
	*/
	private static int findMaxWidth(DLabel ... labels) {
		int max_size = 0;
		for (DLabel dl : labels) {

			int len = dl.getTxt().length();

			if (dl.getTxt().indexOf("\t") != -1) {
				len += 8 * ((dl.getTxt().split("\t")).length - 1);
			}

			if (dl.getCode().equals("<input>") || dl.getCode().equals("<read>"))
				len += 4;
			max_size = Math.max(len, max_size);

		}return max_size;
	}


	/**
	 * @info ╭────────────────────────────────────╮ <p>
	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	private static void printTop(int width) {
		System.out.println(leftR + mid.repeat(width + factor) + rightR);
	}


	/**
	 * @info ╭─ This is a Tag Msg ─ Tag2 ───────╮ <p>
	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	private static void printTag(int width, DLabel ... tagLabel) {

		if (width == 0)
			width = findMaxWidth(tagLabel);

		/**@deprecated
		 * @version Old, Single Tag in One-Line
		for (DLabel tag : tagLabel) {
			int spaceLen = tag.getTxt().length();
			String cTag = colourMsg(tag);
			System.out.println(leftR+mid+ " "+cTag+" " +mid.repeat(width+factor-spaceLen-3)+rightR);
		} @deprecated*/

		/**@version New, Multiple Tags in One-Line */
		for (DLabel tag : tagLabel) {
			int spaceLen = 0;
			String cTag = mid+"";//"";
			String txt = tag.getTxt();
			String clr = tag.getColor();
			for (String t : txt.split("\t")) {
				if (t.length() == 0) {
					spaceLen += 2;
					cTag += mid.repeat(2);
				}
				else {
					spaceLen += t.length() + 4;
					cTag += mid+ " "+colourMsg(new DLabel(t, "", clr)) + " ";
				}
			}
			//cTag = cTag.substring(15);
			//System.out.println(leftR+mid+ " "+cTag + mid.repeat(width+factor-spaceLen)+rightR);
			System.out.println(leftR+cTag + mid.repeat(width+factor-spaceLen)+rightR);
		}

	}


	/**
	 * @info │....................................│ <p>
	 * Prints a Blank Line in the Message Box
	 * @param   width  -> width of the Message Box
	 */
	private static void printBlank(int width) {
		System.out.println(leftI + " ".repeat(width) + rightI);

	}


	/**
	 * @info │       Some Message to display      │ <p>
	 * Prints the Message-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 * @param   message  -> message of the Message Box
	 */
	private static void printFrame(int width, DLabel label) {

		if (width == 0)
			width = findMaxWidth(label);

		int spaceLen = width - label.getTxt().length();
		if (label.getTxt().indexOf("\t") != -1) {
			spaceLen -= 8 * ((label.getTxt().split("\t")).length - 1);
		}


		String cTxt = colourMsg(label);
		System.out.println(leftI + cTxt + " ".repeat(spaceLen) + rightI);
	}


	/**
	 * @info ├────────────────────────────────────┤ <p>
	 * Prints the Joint-Part b/w 2 segments of the Message Box
	 * @param   width    -> width of the Message Box
	 * @idea System.out.println(leftT+mid.repeat(width+factor)+rightT);
	 */
	private static void printJoint(int width, DLabel ... tagLabel) {

		if (width == 0)
			width = findMaxWidth(tagLabel);

		/**@deprecated
		 * @version Old, Single Tag in One-Line
		for (DLabel tag : tagLabel) {
			int spaceLen = tag.getTxt().length();
			String cTag = colourMsg(tag);
			System.out.println(leftR+mid+ " "+cTag+" " +mid.repeat(width+factor-spaceLen-3)+rightR);
		} @deprecated*/

		/**@version New, Multiple Joints in One-Line */
		for (DLabel tag : tagLabel) {

			String txt = tag.getTxt();
			if (txt.length() == 0) {
				System.out.println(leftT+mid.repeat(width+factor)+rightT);
				continue;
			}

			String clr = tag.getColor();

			int spaceLen = 0;
			String cTag = mid+"";
			for (String t : txt.split("\t")) {
				if (t.length() == 0) {
					spaceLen += 2;
					cTag += mid.repeat(2);
				}
				else {
					spaceLen += t.length() + 4;
					cTag += " " + colourMsg(new DLabel(t, "", clr)) + " " + mid;
				}
			}
			//cTag = cTag.substring(15);
//			System.out.println(leftT+mid+ " "+cTag + mid.repeat(width+factor-spaceLen)+rightT);
			System.out.println(leftT+cTag + mid.repeat(width+factor-spaceLen)+rightT);
		}
	}


	/**
	 * @info ╰────────────────────────────────────╯ <p>
	 * Prints the Bottom-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 */
	private static void printBase(int width) {
		System.out.println(leftL+mid.repeat(width+factor)+rightL);
	}




	/**
	 * @Info Test Runs */
	public static void main(String[] args) {

		String eId = "123";
		String eName = "123";
		String ePhno = "123";
		String eCom = "123";

		LinkedList<String> salKeys = new LinkedList<String>();
		salKeys.add("Basic");salKeys.add("DA");salKeys.add("HRA");
		salKeys.add("Medical");salKeys.add("Bonus");salKeys.add("Gross Total");
		salKeys.add("Contribution");salKeys.add("Income Tax");salKeys.add("Prof Tax");
		salKeys.add("Deduction Total");salKeys.add("Net Payment");
		Iterator<String> itr = salKeys.iterator();

		String basic1 = "", gross, basic2 = "", deduc, net;
		int i;

		for (i = 0; i < 5; i++)
			basic1 += itr.next()+": " + "1,000; ";

		gross = itr.next()+": Rs. " + "10,000";

		for (i = 6; i < 9; i++)
			basic2 += itr.next()+": " + "1,000; ";

		deduc = itr.next()+": Rs. " + "10,000";

		net = itr.next()+": Rs. " + "100,000";

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Employee Details", "<tag>", "Y"),
			new DLabel("\tPersonal Details", "<joint>", "P"),
			new DLabel("", "<blank>", ""),
			new DLabel("  ID: "+eId, "", "C C: G"),
			new DLabel("  Name: "+eName, "", "C C: G"),
			new DLabel("  Phno: "+ePhno, "", "C C: G"),
			new DLabel("  Company: "+eCom, "", "C C: G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\tSalary Details", "<joint>", "P"),
			new DLabel("\t\t\tGross Salary", "<joint>", "Y"),
			new DLabel("  "+basic1, "", "C C: G W, G R;"),
			new DLabel("  "+gross, "", "C C: G W, G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\t\t\tDeductions Salary", "<joint>", "Y"),
			new DLabel("  "+basic2, "", "C C: G W, G R;"),
			new DLabel("  "+deduc, "", "C C: G W, G"),
			new DLabel("", "<blank>", ""),
			new DLabel("\t\t\tNet Salary", "<joint>", "Y"),
			new DLabel("  "+net, "", "C C: G W, G"),
			new DLabel("", "<blank>", ""),
			new DLabel("", "<base>", "")
		);

		//		Cache.errorMsg = "<HEY!>\nOops.";
//		Cache.errorFlag = true;
//
//		Cmd.showErrorMessage();
//
//		List<String> s = 
//		Design.printBox(0,
//				new DLabel("", "<top>", ""),
//				new DLabel("Banks of India", "<tag>", "Y"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *1*  Axis Bank", "", "C"),
//				new DLabel(" *2*  Baroda Bank", "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *0*  Close Banking", "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel("Enter Choice:  ", "<input>", "P"),
//				new DLabel("Enter password:  ", "<hide>", "P"),
//				new DLabel("", "<base>", "")
//			);
//
//		Cmd.loadingProcess(2000);
//		Cmd.continueProcess();
//
//		System.out.print(s.get(0) + " ");
//		System.out.println(s.get(1));

//		String cmp = "ID";
//
//		List<String> stdin = 
//			Design.printBox(0,
//				new DLabel("", "<top>", ""),
//				new DLabel(cmp, "<tag>", "Y"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *1*  Delete Account", "", "C"),
//				new DLabel(" *2*  Sign out", "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *3*  Hire Employee", "", "C"),
//				new DLabel(" *4*  Fire Employee", "", "C"),
//				new DLabel(" *5*  Display Employees", "", "C"),
//				new DLabel(" *6*  Search Employee", "", "C"),
////				new DLabel(" *7*  Update Employee Details", "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel(" *0*  Quit " + cmp, "", "C"),
//				new DLabel("", "<joint>", ""),
//				new DLabel("Enter Choice:  ", "<input>", "P"),
//				new DLabel("", "<base>", "")
//			);

		Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Deg\tCasio fx-991+ Setup\t\tDDD", "<tag>", "R"),
				new DLabel("", "<joint>", ""),
				new DLabel("hey\tcool", "<joint>", "G"),
				new DLabel(" *1*  Degreeaasd", "", "C"),
				new DLabel(" *2*  Radian\t\t\tads", "", "C"),
				new DLabel(" *3*  Fraction Format", "", "C"),
				new DLabel(" *4*  Decimal Format", "", "C"),
				new DLabel(" *0*  Close Setup", "", "C"),
				new DLabel(" ENTER F(0 ~ 9)\tqwe", "", "G PF C P0 C P9 C) W"),
				new DLabel("", "<joint>", "Y"),
//				new DLabel("Enter Choice  TO SET PRECISION: ", "<input>", "P"),
				new DLabel("", "<base>", "")
			);

//		Design.printBox(
//				"╭─╮","╰─╯",
//				"│J│",
//				"│E│",
//				"│S│",
//				"│U│",
//				"│S│",
//				"╰─╯",
//				"┌─┐","└─┘"
//				"│J│",
//				"│E│",
//				"├S┤",
//				"│U│",
//				"├S┤",
//				"└─┘"
//			);
	}


}




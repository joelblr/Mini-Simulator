package design;

import java.util.*;

/**
 * @author Joel A
 * */
public class Design extends ConsoleCmd {


	public final static int factor;
	public final static String prefix;
	final static String leftR, rightR, mid;
	final static String leftI, rightI;
	final static String leftT, rightT;
	final static String leftL, rightL;



	static {
		prefix = " ".repeat(8);
		factor = 3 + 0 + 0 + 3;
		String fg_box = fg_blue;

		mid = bold + fg_box + "─" + reset;
		leftR = prefix + bold + fg_box + "┌" + reset;	rightR = bold + fg_box + "┐" + reset;
		leftT = prefix + bold + fg_box + "├" + reset;	rightT = bold + fg_box + "┤" + reset;
		leftI = prefix + bold + fg_box + "│   "  + reset;	rightI = bold + fg_box + "   │" + reset;
		leftL = prefix + bold + fg_box + "└" + reset;	rightL = bold + fg_box + "┘" + reset;
//		mid = "─";
//		leftR = prefix + "┌";	rightR = "┐";
//		leftT = prefix + "├";	rightT = "┤";
//		leftI = prefix + "│   ";	rightI = "   │";
//		leftL = prefix + "└";	rightL = "┘";
	}



	private static int findMaxWidth(DLabel ... labels) {
		int max_size = 0;
		for (DLabel dl : labels) {
			int len = dl.getTxt().length();
			if (dl.getCode() == "<input>")
				len++;
			max_size = Math.max(len, max_size);

		}return max_size;
	}


	/**│									│
	 * Prints a blank line in the Message Box
	 * @param   width  -> width of the Message Box
	 */
	public static void printBlank(int width) {
		System.out.println(leftI + " ".repeat(width) + rightI);

	}/**╭─ This is a Tag Msg ─────────────────╮

	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	public static void printTag(int width, DLabel ... tagLabel) {

		if (width == 0)
			width = findMaxWidth(tagLabel);

		for (DLabel tag : tagLabel) {
			int spaceLen = tag.getTxt().length();
			String cTag = colourMsg(tag);
			System.out.println(leftR+mid+ " "+cTag+" " +mid.repeat(width+factor-spaceLen-3)+rightR);
			
		}

	}/**╭──────────────────────────────────╮
	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	public static void printTop(int width) {
		System.out.println(leftR + mid.repeat(width + factor) + rightR);

	}/**│  Some Message to display  │
	 * Prints the Message-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 * @param   message  -> message of the Message Box
	 */
	public static void printFrame(int width, DLabel label) {

		if (width == 0)
			width = findMaxWidth(label);

		int spaceLen = width - label.getTxt().length();
		String cTxt = colourMsg(label);
		System.out.println(leftI + cTxt + " ".repeat(spaceLen) + rightI);

	}/**╰───────────────────────────────────╯
	 * Prints the Bottom-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 */
	public static void printBase(int width) {
		System.out.println(leftL+mid.repeat(width+factor)+rightL);

	}/**├───────────────────────────────────┤
	 * Prints the Joint-Part b/w 2 segments of the Message Box
	 * @param   width    -> width of the Message Box
	 */
	public static void printJoint(int width) {
		System.out.println(leftT+mid.repeat(width+factor)+rightT);

	}


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
			stdin.add(scan.nextLine());
			System.out.print(reset);

			i = arr[0]+1;
		}

		System.out.print("\n".repeat(lines - i -1));

		return stdin;
	}
	/**
	 * @Info Prints the Input Message in a Box Format
	 * @param   msg   ->  Messages
	 * @usage 	give a set of Strings as parameters.<p>
	 * 			* If a line-break is needed after a line then
	 * give an empty String "" as the next String.<p>
	 * 			* If some input has to be taken at some line
	 * then at the end of that line, add " $" (space dollar).
	 * @example Design.printBox(<p>
						"Line1", "",<p>
						"Line2",<p>
						"Line3", "",<p>
						"Line4 $",<p>
						"Line5 $",<p>
						"Line6", "",<p>
						"Enter Choice: $"<p>
					);
		@todo : <p>
			use "msg T" : for title coloring<p>
			use "msg E" : for error coloring<p>
			use "msg $" : for input coloring<p>
			use "msg M" : for info coloring.....
		
	 */
	public static List<String> printBox(int width, DLabel ... label) {

		List<Integer[]> cursorEof = new LinkedList<Integer[]>();
		int i;
		if (width == 0)
			width = findMaxWidth(label);

//		printTop(width);
		int lines = 0;
		for (i = 0; i < label.length; i++) {

			lines++;
			if (label[i].getCode() == "<top>") {
				printTop(width);	continue;

			}else if (label[i].getCode() == "<blank>") {
				printBlank(width);	continue;

			}else if (label[i].getCode() == "<joint>") {
				printJoint(width);	continue;

			}else if (label[i].getCode() == "<base>") {
				printBase(width);	continue;

			}else if (label[i].getCode() == "<tag>") {

				if (i == 0)
					System.out.println();
				printTag(width, label[i]);

			}else if (label[i].getCode() == "<input>") {

//				int len1 = codes[0].stripTrailing().length()-2;
//				codes[0] = codes[0].substring(0, len1);
////				String field = msg[i].substring(0, msg[i].length()-1);

				printFrame(width, label[i]);

				int len = prefix.length() + factor/2+1 + label[i].getTxt().length();
				cursorEof.add(new Integer[] {i, len});

			}else {
				printFrame(width, label[i]);

			}

		}lines++;//+2

		List<String> stdin = new LinkedList<String>();
		if (! cursorEof.isEmpty())
			stdin = getStdin(cursorEof, lines);

		return stdin;
	}


	public static void main(String[] args) {
		//TODO  "msg >> B : G, T"  => B: colour of Key, G: colour of Value, T: Tag (M: msg, ...)
		/**
		 * "Jesus Loves You >> B = G, T"
		 * */

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Program > Simulator", "<tag>", "G >G C"),
			new DLabel("", "<blank>", ""),
			new DLabel(" <1>  Casio fx-707", "", "G >P C"),
			new DLabel(" Answer: 7", "", "P :G Y"),
			new DLabel(" *3*  BESCOM", "", "R"),
			new DLabel(" *4*  Cash Ware", "", "R"),
			new DLabel(" *0*  Close Program Simulator", "", "C"),
			new DLabel("", "<joint>", ""),
			new DLabel("Enter Choice:  ", "<input>", "P"),
			new DLabel("", "<base>", "")
		);
//		Design.printBox(0,
//			new DLabel("", "<top>", ""),
//			new DLabel("Program Simulator", "<tag>", "Y"),
//			new DLabel("", "<blank>", ""),
//			new DLabel(" *1*  Casio fx-707", "", "C"),
//			new DLabel(" *2*  Celonis", "", "R"),
//			new DLabel(" *3*  BESCOM", "", "R"),
//			new DLabel(" *4*  Cash Ware", "", "R"),
//			new DLabel(" *0*  Close Program Simulator", "", "C"),
//			new DLabel("", "<joint>", ""),
//			new DLabel("Enter Choice:  ", "<input>", "P"),
//			new DLabel("", "<base>", "")
//		);

		//		Design.printBox(
//				"Line1 Y T", "",
//				"Line2 R",
//				"Line3 C", "",
//				"Line4 B",
//				"Line5 G",
//				"Line6 P", "",
//				"Line5 b",
//				"Line6 W", "",
//				"Enter Choice: $"
//			);
//		Design.printBox(
//				"╭─╮",
//				"│J│",
//				"│E│",
//				"│S│",
//				"│U│",
//				"│S│",
//				"╰─╯",
//				"┌─┐",
//				"│J│",
//				"│E│",
//				"├S┤",
//				"│U│",
//				"├S┤",
//				"└─┘"
//			);
	}

}



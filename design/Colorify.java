package design;

import java.util.ArrayList;
import java.util.HashSet;

/**
	 * 90 - 97,		30 - 37
	 * 100 - 107,	40 - 47
	 * (4, 7, 9, 21)
*/
public class Colorify extends Cache {

	public static final String fg_black =	"\u001B[30m";
	public static final String fg_red =		"\u001B[31m";
	public static final String fg_green =	"\u001B[32m";
	public static final String fg_yellow =	"\u001B[33m";
	public static final String fg_blue =	"\u001B[34m";
	public static final String fg_purple =	"\u001B[35m";
	public static final String fg_cyan =	"\u001B[36m";
	public static final String fg_white =	"\u001B[37m";

//	public static final String bg_black =	"\u001B[40m";
//	public static final String bg_red =		"\u001B[41m";
//	public static final String bg_green =	"\u001B[42m";
//	public static final String bg_yellow =	"\u001B[43m";
//	public static final String bg_blue =	"\u001B[44m";
//	public static final String bg_purple =	"\u001B[45m";
//	public static final String bg_cyan =	"\u001B[46m";
//	public static final String bg_white =	"\u001B[47m";

	public static final String bold =	"\u001B[1m";
	public static final String dim =	"\u001B[2m";
	public static final String normal =	"\u001B[22m";
	public static final String reset =	"\u001B[0m";


	protected static String msgColour(String colour, String msg) {

		String colored = bold + msg + reset;

		switch (colour) {

			case "b" :
				colored = fg_black + colored ;
				return colored;
			case "W" :
				colored = fg_white + colored ;
				return colored;
	
			case "R" :
				colored = fg_red + colored ;
				return colored;
			case "C" :
				colored = fg_cyan + colored ;
				return colored;
			case "B" :
				colored = fg_blue + colored ;
				return colored;
			case "G" :
				colored = fg_green + colored ;
				return colored;
			case "Y" :
				colored = fg_yellow + colored ;
				return colored;
			case "P" :
				colored = fg_purple + colored ;
				return colored;

			default :
				return colored;
		}
	}

	protected static String colourMsg(DLabel label) {

		ArrayList<String> keys = new ArrayList<String>();	//Keys and Values
		HashSet<String> seps = new HashSet<String>();		//Separators

		int k = 1;

		//Decoding Colour Format
		char[] format = label.getColor().toCharArray();
		for (int i = 0; i < format.length; i++) {

			if (format[i] == ' ') {
				continue;

			}else if (k == 1) {
				keys.add(format[i]+"");

			}else if (k == 0) {
				seps.add(format[i]+"");
				keys.add(format[i+1]+"");
				i++;

			}k ^= 1;
		}

		if (seps.isEmpty())
			return msgColour(keys.get(0), label.getTxt());

		int idx = 0;
		String newTxt = "";
		for (char ch: label.getTxt().toCharArray()) {

			if (seps.contains(ch+"")) {
				idx = (idx+1) % keys.size();//Sep color
				newTxt += msgColour(keys.get(idx), ch+"");
				idx = (idx+1) % keys.size();//Next keys' color

			}else {
				newTxt += msgColour(keys.get(idx), ch+"");
			}
		}

		return newTxt;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 108; i++) {
			System.out.println( "\u001B["+i+"m" + "Jesus Coming Soon!" + reset + " = "+i);
		}
	}


}

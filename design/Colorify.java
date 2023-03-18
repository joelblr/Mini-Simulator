package design;

import java.util.ArrayList;
import java.util.HashSet;

/**
	 * 90 - 97,		30 - 37
	 * 100 - 107,	40 - 47
	 * (4, 5, 7, 9, 21)
*/
public class Colorify {

	public static final String fg_black =	"\u001B[30m";
	public static final String fg_red =		"\u001B[31m";
	public static final String fg_green =	"\u001B[32m";
	public static final String fg_yellow =	"\u001B[33m";
	public static final String fg_blue =	"\u001B[34m";
	public static final String fg_purple =	"\u001B[35m";
	public static final String fg_cyan =	"\u001B[36m";
	public static final String fg_white =	"\u001B[37m";
	public static final String blink =		"\u001B[5m";

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

			case "w" :
				colored = blink + fg_white + colored ;		break;
			case "r" :
				colored = blink + fg_red + colored ;		break;
			case "c" :
				colored = blink + fg_cyan + colored ;		break;
			case "b" :
				colored = blink + fg_blue + colored ;		break;
			case "g" :
				colored = blink + fg_green + colored ;		break;
			case "y" :
				colored = blink + fg_yellow + colored ;		break;
			case "p" :
				colored = blink + fg_purple + colored ;		break;

			case "W" :
				colored = fg_white + colored ;		break;
			case "R" :
				colored = fg_red + colored ;		break;
			case "C" :
				colored = fg_cyan + colored ;		break;
			case "B" :
				colored = fg_blue + colored ;		break;
			case "G" :
				colored = fg_green + colored ;		break;
			case "Y" :
				colored = fg_yellow + colored ;		break;
			case "P" :
				colored = fg_purple + colored ;		break;

		}return colored;
	}

	protected static String colourMsg(DLabel label) {

		ArrayList<String> keys = new ArrayList<String>();	//Keys and Values
		HashSet<String> seps = new HashSet<String>();		//Separators

		char ch1, ch2;
		//Decoding Colour Format
		String[] format = label.getColor().split(" ");
		for (int i = 0; i < format.length; i++) {

			ch1 = format[i].charAt(0);
			ch2 = (format[i].length() > 1) ? format[i].charAt(1) : ' ';

			keys.add(ch1+"");
			seps.add((ch2+"").trim());

		}

		if (seps.isEmpty())
			return msgColour(keys.get(0), label.getTxt());

		int idx = 0;
		String newTxt = ""; boolean flag = false;
		for (char ch: label.getTxt().toCharArray()) {

			if (seps.contains(ch+"")) {
				if (flag)
					idx = (idx+1) % keys.size();//Sep color
				newTxt += msgColour(keys.get(idx), ch+"");
				idx = (idx+1) % keys.size();//Next keys' color
				flag = false;

			}else {
				flag = true;
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




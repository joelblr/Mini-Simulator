package design;

import java.util.Scanner;
import java.io.Console;

public class Cache {

	/*Scanner Object*/
	public final static Scanner scan;
	public final static Console cnsl;

	/*Choice Holders*/
	public static int IntegerChoice;
	public static double DoubleChoice;
	public static String StringChoice;
	public static char CharacterChoice;

	/*Error Holders*/
	public static String errorMsg = "<!>\nHMM...";
	public static boolean errorFlag = false;

	static {
		errorFlag = false;
		scan = new Scanner(System.in);
		cnsl = System.console();
	}

}

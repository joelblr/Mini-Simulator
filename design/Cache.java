package design;

import java.util.Scanner;


public class Cache {

	/*Scanner Object*/
	public final static Scanner scan;

	/*Choice Holders*/
	public static int IntegerChoice;
	public static double DoubleChoice;
	public static String StringChoice;
	public static char CharacterChoice;

	/*Error Holders*/
	public static String errorMsg;
	public static boolean errorFlag;

	static {
		errorFlag = false;
		scan = new Scanner(System.in);
	}

}

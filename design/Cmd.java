package design;

import java.io.IOException;


public class Cmd extends Colorify {

	private static int k;


	/**
	 * @Info Helps to make Cursor Movements in the Console/Terminal
	 * @param Names are {@code Self Explanatory}
	 */
	public final static void cursorGoto(int up, int down, int fwd, int bwd, int ers) {
		System.out.print("\033["+up+"A"+"\033["+down+"B");
		System.out.print("\033["+fwd+"C"+"\033["+bwd+"D");
		if (ers == 1)
			System.out.print("\033[K");
	}


	/**
	 * @Info Clears the Screen of the Console/Terminal
	 */
	public final static void clearScreen() {

		try {/** @Info Windows */
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

		}catch(IOException | InterruptedException e) {/** @Info Linux/Mac Os */
			System.out.print("\033[H\033[2J");
		}
	}


	/**
	 * @Info Displays the Static-Error Message
	 * */
	public final static void showErrorMessage() {

		try {
			String eTag = Cache.errorMsg.split("\n")[0];
			String eMsg = Cache.errorMsg.split("\n")[1];
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel(eTag, "<tag>", "r"),
				new DLabel(eMsg, "", "G"),
				new DLabel("", "<base>", "")
			);

		}catch (Exception e) {
			e.printStackTrace();

		}Cache.errorMsg = null;	Cache.errorFlag = false;

	}

	/**
	 * @Info Displays the Passed-Error Message
	 * @param msg : Appropriate Error message to print
	 * */
	public final static void showErrorMessage(String msg) {

		try {
			String eTag = msg.split("\n")[0];
			String eMsg = msg.split("\n")[1];
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel(eTag, "<tag>", "r"),
				new DLabel(eMsg, "", "G"),
				new DLabel("", "<base>", "")
			);

		}catch (Exception e) {
			e.printStackTrace();

		}Cache.errorFlag = false;

	}


	/**
	 * @Info Simulates a Waiting Process
	 * */
	public final static void continueProcess() {
		String press = "PRESS <ENTER> KEY TO CONTINUE...  ";
		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel(press, "<input>", "C R< p R> C R. R. R."),
			new DLabel("", "<base>", "")
		);
	}


	/**
	 * @Info Simulates the waiting Process
	 * @param time  number of milli seconds
	 * */
	public final static void loadingProcess(int time) {

		String[] colors =	{fg_black, fg_blue, fg_cyan, fg_green,
							fg_purple, fg_red, fg_white, fg_yellow};

		String a = "|/-\\";
		long start = System.currentTimeMillis();
		try {
			int i = 0;
			String load = "Loading your Choice     ";
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel(load, "", "W"),
				new DLabel("", "<base>", "")
			);
			System.out.print("\033[2A\033["+(8+4+22)+"C");
			while (true) {
				
				System.out.print( colors[k++%8] + bold + a.charAt(i) + reset  + " ");
				Thread.sleep(150);
				System.out.print("\b\b");
				long now = System.currentTimeMillis();
				if (now - start >= time) break;
				i = (i+1) % 4;
			}

		}catch(Exception e) {
			//e.printStackTrace();
		}
		finally {
			System.out.print("\033[1B");
			for (int i = 0; i < 3; i++) {
				System.out.print("\033[2K");
				System.out.print("\033[1A");
			}
		}
		System.out.println();
	}


}

package calci;

import errors.*;
import java.util.*;


public class Computo extends Converto {


	private static double evaluate(double x, String op, double y) {

		double ans = 0.0;

		/* Math Operations */
		if (op.equals("ceil") || op.equals("floor") || op.equals("abs"))
			ans = (op.equals("ceil")) ? Math.ceil(x) : (op.equals("floor")) ? Math.floor(x) : Math.abs(x);
		else if (op.equals("pow") || op.equals("sqrt") || op.equals("cbrt"))
			ans = (op.equals("pow")) ? Math.pow(x, y) : (op.equals("sqrt")) ? Math.sqrt(x) : Math.cbrt(x);
		else if (op.equals("exp") || op.equals("log") || op.equals("ln"))
			ans = (op.equals("exp")) ? Math.exp(x) : (op.equals("log")) ? Math.log10(x) : Math.log(x);
		else if (op.equals("ncr") || op.equals("npr") || op.equals("fact"))
			ans = (op.equals("ncr")) ? Func.nCr(x, y) : (op.equals("npr")) ? Func.nPr(x, y) : Func.fact(x);


		if (CalciMain.getAngleMode() != "rad")
			x = Math.toRadians(x);

		/* Trigonometry Functions: Basic, Inverse, Hyperbolic */
		if (op.equals("sin") || op.equals("cos") || op.equals("tan"))
			ans = op.equals("sin") ? Math.sin(x) : op.equals("cos") ? Math.cos(x) : Math.tan(x);
		else if (op.equals("asin") || op.equals("acos") || op.equals("atan"))
			ans = op.equals("asin") ? Math.asin(x) : op.equals("acos") ? Math.acos(x) : Math.atan(x);
		else if (op.equals("sinh") || op.equals("cosh") || op.equals("tanh"))
			ans = op.equals("sinh") ? Math.sinh(x) : op.equals("cosh") ? Math.cosh(x) : Math.tanh(x);

		else if (op.equals("csc") || op.equals("sec") || op.equals("cot"))
			ans = op.equals("csc") ? 1/Math.sin(x) : op.equals("sec") ? 1/Math.cos(x) : 1/Math.tan(x);
		else if (op.equals("acsc") || op.equals("asec") || op.equals("acot"))
			ans = op.equals("acsc") ? Math.asin(1/x) : op.equals("asec") ? Math.acos(1/x) : Math.atan(1/x);
		else if (op.equals("csch") || op.equals("sech") || op.equals("coth"))
			ans = op.equals("csch") ? 1/Math.sinh(x) : op.equals("sech") ? 1/Math.cosh(x) : 1/Math.tanh(x);

		/* Basic Arithmetic Operations */
		if (op.equals("$") || op.equals("*") || op.equals("+") || op.equals("-")) {
			ans = op.equals("$") ? Math.pow(x, y) : op.equals("*") ? x*y : op.equals("+") ? x+y : x-y ;

		}else if (op.equals("/") || op.equals("%")) {
			if (y == 0)
				throw new BadNumberException("<Division by Zero!>\nSorry cant handle ∞ Infinity");
			ans = op.equals("/") ? x/y : x%y ;
		}

//		else {
		/* Bitwise Operations */
		
//		if ( Integer.parseInt( (x +"").split(".")[0]) != 0 || 
//				Integer.parseInt( (y +"").split(".")[0]) != 0)
//			throw new BadNumberException("<Bitwise Operands!>\nIntegers, Not Decimals");

		int a = (int)x, b = (int)y;
		if (op.equals(">>") || op.equals("<<")) {
			if (y < 0)
				throw new BadNumberException("<Negative Shift Count!>\nShift >= 0");
			ans = op.equals(">>") ? a>>b : a<<b;

		}else if (op.equals("&") || op.equals("|") || op.equals("^") || op.equals("neg")) {
			ans = op.equals("&") ? a&b : op.equals("|") ? a|b : op.equals("^") ? a^b : ~a;

		}else if (op.equals("~")) {
			ans = a^1;
		}


		/* NaN, ± Infinity Errors */
		if (Double.isInfinite(ans) || Double.isNaN(ans))
			throw new BadNumberException("<BAD NUMBERS!>\nNaN or Infinity Detected");

		return ans;
	}

	public static double solve(String infix) {
		findPostfix("(" + infix + ")", 'A');
		return evalPostfix();
	}

	public static double evalPostfix() {

		Stack <Double> stac = new Stack <>();

		for (String ch : postfix) {

			/* For Unary Operators */
			if (uniOperators.contains(ch)) {
				double y = stac.pop();
				stac.push( evaluate(y, ch, 0));

			/* For Binary Operators */
			}else if (biOperators.contains(ch)) {
				double y = stac.pop();
				double x = stac.pop();
				stac.push( evaluate(x, ch, y));

			/* For Operands */
			}else
				stac.push( Double.valueOf(ch));

		}
		return stac.pop();

	}


	public static double evalPostfix(int[] BoolInput) {

		Stack <Double> stac = new Stack <>();

		String[] itr = postfix.toArray(new String[0]);
		for (int i = 0; i < postfix.size(); i++) {
			String ch = itr[i];
		
			/* For Unary Operators */
			if (uniOperators.contains(ch)) {
				double y = stac.pop();
				stac.push( evaluate(y, ch, 0));

			/* For Binary Operators */
			}else if (biOperators.contains(ch)) {
				double y = stac.pop();
				double x = stac.pop();
				stac.push( evaluate(x, ch, y));

			/* For Boolean Operands */
			}else if ( Character.isLetter(ch.charAt(0)) ) {
				char ch1 = ch.charAt(0);

				if ( Character.isLowerCase(ch1) ) {
					ch1 = Character.toUpperCase(ch1);
					stac.push( (double)(BoolInput[i] ^ 1) );

				}else
					stac.push( (double)BoolInput[i]);

			/* For Constants */
			}else
				stac.push( Double.valueOf(ch));

		}

		return stac.pop();

	}

//	public static void main(String[] args) {
//		if (true)
//			System.out.println(2 >= 6);
//		solve("2*3+1/7+1");
//
//	}
}











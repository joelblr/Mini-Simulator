package calci.transformo;

import errors.*;
import java.util.*;

import calci.Setup;


public class Computo extends Converto {


	public static double solveScientific(String infix) {
		findPostfix(infix, 'A');
		return evalPostfix(null);

	}



	private static double evaluate(String op, Stack<Double>arg) {

		double ans = 0.0;


		if (polyOperators.contains(op)) {

			if (op.equals("sum") || op.equals("avg") || op.equals("mean")) {
				ans = (op.equals("sum")) ? MathFunc.Sum(arg) : MathFunc.Mean(arg);

			}else if (op.equals("min") || op.equals("max") || op.equals("round")) {
				ans =	(op.equals("min")) ? MathFunc.Min(arg) :
						(op.equals("max")) ? MathFunc.Max(arg) : MathFunc.Round(arg);
			}
			else {
				double x = arg.pop();	double y = arg.pop();
				if (op.equals("ncr") || op.equals("npr") || op.equals("pow")) {
					ans = (op.equals("ncr")) ? MathFunc.nCr(x, y) : (op.equals("npr")) ? MathFunc.nPr(x, y) : Math.pow(x, y);
				}
			}

		}else if (biOperators.contains(op)) {
			double x = arg.pop();
			double y = arg.pop();

			/** @info Basic Arithmetic Operations */
			if (op.equals("$") || op.equals("*") || op.equals("+") || op.equals("-")) {
				ans = op.equals("$") ? Math.pow(x, y) : op.equals("*") ? x*y : op.equals("+") ? x+y : x-y ;

			}else if (op.equals("/") || op.equals("%")) {
				if (y == 0.0)
					throw new BadNumberException("<Division by Zero!>\nSorry cant handle ∞ Infinity");
				ans = op.equals("/") ? x/y : x%y ;

			}/** @info Bitwise Operations */
			else {

//				if ( x%1 != 0 || y%1 != 0)
//					throw new BadNumberException("<Bitwise Operands!>\nIntegers, Not Decimals");
	
				int a = (int)x, b = (int)y;
				if (op.equals(">>") || op.equals("<<")) {

					if (y < 0)
						throw new BadNumberException("<Negative Shift Count!>\nShift >= 0");
					ans = op.equals(">>") ? a>>b : a<<b;

				}else if (op.equals("&") || op.equals("|") || op.equals("^")) {
					ans = op.equals("&") ? a&b : op.equals("|") ? a|b : a^b ;
	
				}

			}

		}/** @info Math Operations */
		else if (uniOperators.contains(op)) {
			double x = arg.pop();

			if (op.equals("u+") || op.equals("u-")) {
				ans = (op.equals("u+") ? 1 : -1) * x;
			}
			else if (op.equals("~") || op.equals("neg")) {
				if ( x%1 != 0)
					throw new BadNumberException("<Bitwise Operands!>\nIntegers, Not Decimals");
				ans = (op.equals("~")) ? (int)x^1 : -((int)x+1);

			}else if (op.equals("ceil") || op.equals("floor") || op.equals("abs")) {
				ans = (op.equals("ceil")) ? Math.ceil(x) : (op.equals("floor")) ? Math.floor(x) : Math.abs(x);

			}else if (op.equals("sqrt") || op.equals("cbrt") || op.equals("fact") || op.equals("!")) {
				ans = (op.equals("sqrt")) ? Math.sqrt(x) : (op.equals("cbrt")) ? Math.cbrt(x) : MathFunc.fact(x);

			}else if (op.equals("exp") || op.equals("log") || op.equals("ln")) {
				ans = (op.equals("exp")) ? Math.exp(x) : (op.equals("log")) ? Math.log10(x) : Math.log(x);

			}else {

				/** @info Trigonometry Functions: Inverse */
				if (op.startsWith("a")) {
					if (op.equals("asin") || op.equals("acos") || op.equals("atan"))
						ans = op.equals("asin") ? Math.asin(x) : op.equals("acos") ? Math.acos(x) : Math.atan(x);
					else if (op.equals("acsc") || op.equals("asec") || op.equals("acot"))
						ans = op.equals("acsc") ? Math.asin(1/x) : op.equals("asec") ? Math.acos(1/x) : Math.atan(1/x);

					if (Setup.getAngleType() == "deg")
						ans = Math.toDegrees(ans);

				}else {
					if (Setup.getAngleType() == "deg")
						x = Math.toRadians(x);
		
					/** @info Trigonometry Functions: Basic, Hyperbolic*/
					if (op.equals("sin") || op.equals("cos") || op.equals("tan"))
						ans = op.equals("sin") ? Math.sin(x) : op.equals("cos") ? Math.cos(x) : Math.tan(x);
					else if (op.equals("csc") || op.equals("sec") || op.equals("cot"))
						ans = op.equals("csc") ? 1/Math.sin(x) : op.equals("sec") ? 1/Math.cos(x) : 1/Math.tan(x);
	
					else if (op.equals("sinh") || op.equals("cosh") || op.equals("tanh"))
						ans = op.equals("sinh") ? Math.sinh(x) : op.equals("cosh") ? Math.cosh(x) : Math.tanh(x);
					else if (op.equals("csch") || op.equals("sech") || op.equals("coth"))
						ans = op.equals("csch") ? 1/Math.sinh(x) : op.equals("sech") ? 1/Math.cosh(x) : 1/Math.tanh(x);
				}

			}
		}


		/** @info NaN, ± Infinity Errors */
		if (Double.isInfinite(ans) || Double.isNaN(ans))
			throw new BadNumberException("<BAD NUMBERS!>\nNaN or Infinity Detected");

		return ans;
	}


	public static double evalPostfix(HashMap<String, Integer> BoolInput) {

		Stack <String> stac = new Stack <String>();
//		System.out.println(postfix);
		for (String ch : postfix) {

			Stack <Double> arg = new Stack <Double>();

			if (polyOperators.contains(ch)) {
				try {
					do {
						arg.push(Double.parseDouble(stac.peek()));
						stac.pop();
					}while(!stac.isEmpty() && stac.peek().equals(",") && stac.pop().equals(","));

				}catch(NumberFormatException e) {
					if (BoolInput == null)
						throw new InputsFreqException("<INVALID OPERANDS!>Plz give Valid Operands");
					else
						arg.push((double)BoolInput.get(ch));
				}

			}else if (uniOperators.contains(ch)) {
				arg.push( Double.parseDouble(stac.pop()));

			}else if(biOperators.contains(ch)) {
					arg.push( Double.parseDouble(stac.pop()));
					arg.push( Double.parseDouble(stac.pop()));
			}

			/** @info Action */
			if (polyOperators.contains(ch) || biOperators.contains(ch) || uniOperators.contains(ch)) {
				double soln = evaluate(ch, arg);
//				System.out.println(soln);
				stac.push( soln + "");

			}/** @info For Operands and Commas */
			else {
				if(BoolInput == null) {//Arithmetic Operands or commas
					stac.push(ch);

				}else {
					try {	/** @info Boolean Constants */
						Double.parseDouble(ch);
						stac.push(ch);

					}catch (NumberFormatException e) {//Boolean Variables
						stac.push(BoolInput.get(ch) + "");
					}
				}
			}

		}if (BoolInput == null && stac.size() != 1)
			throw new BadExpressionException("<INVALID EXPRESSION!>\nMisuse of Operators/Operands");
		return Double.parseDouble(stac.peek());

	}



	public static void main(String[] args) {
		double ans = solveScientific(" 2*3");
		System.out.println( String.format("%.3f", ans) );

	}
}




//public static int evalPostfix(int[] BoolInput) {
//
//	Stack <Double> stac = new Stack <>();
//
//	String[] itr = postfix.toArray(new String[0]);
//	for (int i = 0; i < postfix.size(); i++) {
//		String ch = itr[i];
//	
//		/* For Unary Operators */
//		if (uniOperators.contains(ch)) {
//			double y = stac.pop();
//			stac.push( evaluate(y, ch, 0));
//
//		/* For Binary Operators */
//		}else if (biOperators.contains(ch)) {
//			double y = stac.pop();
//			double x = stac.pop();
//			stac.push( evaluate(x, ch, y));
//
//		/* For Boolean Operands */
//		}else if ( Character.isLetter(ch.charAt(0)) ) {
//			char ch1 = ch.charAt(0);
//
//			if ( Character.isLowerCase(ch1) ) {
//				ch1 = Character.toUpperCase(ch1);
//				stac.push( (double)(BoolInput[i] ^ 1) );
//
//			}else
//				stac.push( (double)BoolInput[i]);
//
//		/* For Constants */
//		}else
//			stac.push( Double.valueOf(ch));
//
//	}
//
//	return stac.pop();
//
//}



/** @catch BadNumberException | BadExpressionException | InputsFreqException */






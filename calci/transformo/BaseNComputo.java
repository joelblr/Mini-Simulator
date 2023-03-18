package calci.transformo;

import errors.*;
import java.util.*;
import calci.Setup;


public class BaseNComputo extends Converto {


	public static String solveNBase(String infix, int radix) {
		findPostfix(infix, 'A');
		return evalPostfix(radix);

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


	public static String evalPostfix(int radix) {

		Stack <String> stac = new Stack <String>();
		System.out.println(postfix);
		for (String ch : postfix) {

			Stack <Double> arg = new Stack <Double>();

			/** @info Poly-Operators */
			if (polyOperators.contains(ch)) {
				try {
					do {
						arg.push( (double)Integer.parseInt(stac.pop(), radix));
					}while(!stac.isEmpty() && stac.peek().equals(",") && stac.pop().equals(","));

				}catch(NumberFormatException e) {
					throw new InputsFreqException("<INVALID OPERANDS!>Plz give Valid Operands.");
				}

			}/** @info Unary-Operators */
			else if (uniOperators.contains(ch)) {
				arg.push( (double)Integer.parseInt(stac.pop(), radix));

			}/** @info Binary-Operators */
			else if(biOperators.contains(ch)) {
					arg.push( (double)Integer.parseInt(stac.pop(), radix));
					arg.push( (double)Integer.parseInt(stac.pop(), radix));
			}

			/** @info Action */
			if (polyOperators.contains(ch) || biOperators.contains(ch) || uniOperators.contains(ch)) {
				int soln = (int)evaluate(ch, arg);
				System.out.println(soln);
				stac.push( Integer.toString(soln, radix));

			}/** @info For Operands and Commas */
			else {
				try {stac.push(Integer.parseInt(ch, radix) + "");}
				catch(Exception e) {
					if (ch.equals(","))	  stac.push(ch);
					else  throw new BadNumberException(
						  String.format("<BAD OPERAND!>\n%s Undefined", ch));
				}
			}

		}
		if (stac.size() != 1)
			throw new BadExpressionException("<INVALID EXPRESSION!>\nMisuse of Operators/Operands");

		int result = (int)Double.parseDouble(stac.peek()); 
		return Integer.toString(result, radix);

	}


	/** @testRuns */
	public static void main(String[] args) {
		String ans = solveNBase("B*A%C", 16);
		System.out.println( String.format("%s ***", ans) );

	}
}

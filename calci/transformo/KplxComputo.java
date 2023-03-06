package calci.transformo;


import errors.*;
import java.util.*;

import calci.mode_2.Komplx;


public class KplxComputo extends Converto {

	static {
		operators.addAll(Arrays.asList("arg", "conj", "mod", "pol", "rec"));
		polyOperators.addAll(Arrays.asList("rec"));
		uniOperators.addAll(Arrays.asList("arg", "conj", "mod", "pol"));
	}

	public static String solveKplx(String infix) {
		Converto.findPostfix("(" + infix + ")", 'A');
		return evalPostfix();
	}


	private static String evaluate(String op, Stack<Komplx>arg) {

		String ans = "";
	
		/** @info Polar to Rectangular */
		if (polyOperators.contains(op)) {
	
			if (op.equals("rec")) {
				Komplx r = arg.pop(), θ = arg.pop();
				if (r.getImag() != 0.0 || θ.getImag() != 0.0)
					throw new BadNumberException("<BAD NUMBERS!>\nNums for rec(,) must be Real.");
				ans = Komplx.toRectangular(r.getReal(), θ.getReal()).toString();

			}else
				throw new BadFunctionException(
						String.format("<INVALID FUNCTION!>\nUndefined %s for Complex Nums", op));

		}/** @info Basic Arithmetic Operations */
		else if (biOperators.contains(op)) {
			Komplx x = arg.pop();
			Komplx y = arg.pop();
			if (op.equals("/") || op.equals("*") || op.equals("+") || op.equals("-"))
				ans = op.equals("/") ?
						Komplx.divKplx(x, y).toString() : op.equals("*") ?
						Komplx.mulKplx(x, y).toString() : op.equals("+") ?
						Komplx.addKplx(x, y).toString() : Komplx.subKplx(x, y).toString() ;
	
			else
				throw new BadFunctionException(
				String.format("<INVALID OPERATOR!>\nUndefined %s for Complex Nums", op));

		}/** @info Math Operations */
		else if (uniOperators.contains(op)) {

			Komplx x = arg.pop();
			if (op.equals("u-")) {
				x.setReal(-1*x.getReal());	x.setImag(-1*x.getImag());
				ans = x.toString();
			}
			else if (op.equals("conj") || op.contains("pol"))
				ans = op.contains("conj") ? Komplx.findConjugate(x).toString() : Komplx.toPolar(x);
			else if (op.equals("mod") || op.contains("arg"))
				ans = (op.equals("mod")) ? Komplx.findModulus(x)+"" : Komplx.findArgumnet(x);

			else
				throw new BadFunctionException(
				String.format("<INVALID OPERATOR!>\nUndefined %s for Complex Nums", op));
		}

		return ans;
	}


	public static String evalPostfix() {
	
		Stack <String> stac = new Stack <String>();
	
		for (String ch : postfix) {

			Stack <Komplx> arg = new Stack <Komplx>();
			if (polyOperators.contains(ch) ) {/** @info Poly-Operators */

				try {
					do {
						arg.push(new Komplx(stac.peek()));	stac.pop();
					}while(!stac.isEmpty() && stac.peek().equals(",") && stac.pop().equals(","));
	
				}catch(NumberFormatException e) {
					throw new InputsFreqException("<INVALID OPERANDS!>Plz give Valid Operands");
				}

			}/** @info Unary-Operators */
			else if (uniOperators.contains(ch)) {
				arg.push( new Komplx(stac.pop()));

			}/** @info Binary-Operators */
			else if(biOperators.contains(ch)) {
//				try {
					arg.push( new Komplx(stac.pop()));
					arg.push( new Komplx(stac.pop()));

//				}/** @info Unary Operations */
//				catch (EmptyStackException e) {
//					if (ch.equals("+") || ch.equals("-"))
//						arg.push( new Komplx("0"));
//					else
//					throw new BadExpression(
//					String.format("<BAD UNARY OPERATORS!>\nUndefined %s Unary Operator", ch));
//				}
			
			}
			/** @info Action */
			if (polyOperators.contains(ch) || biOperators.contains(ch) || uniOperators.contains(ch)) {
				String soln = evaluate(ch, arg);
				stac.push( soln);

			}

			else {	/** @info Operands */
				stac.push(ch);
			}
	
		}
		if (stac.size() != 1)
			throw new BadExpression("<INVALID EXPRESSION!>\nMis-using Operators/Operands");

		return stac.pop();
	
	}

	/** @testRuns */
	public static void main(String[] args) {
		String ans = solveKplx("-5-i");
		System.out.println( ans );

	}

}



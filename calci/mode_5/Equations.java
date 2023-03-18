package calci.mode_5;


import calci.*;
import errors.*;
import design.*;
import java.util.*;
import calci.transformo.*;


public class Equations {


	protected static void LinearRun() {
		LinearEqn();

	}private static void LinearEqn() {
		Matrix m = new Matrix();
		m.solveSLE();
	}


	protected static void QuadraticRun() {
		QuaticEqn();

	}private static void QuaticEqn() {

		double a, b, c, d, r1, r2;

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Ax² + Bx + C = 0", "<tag>", "Y"),
				new DLabel("ENTER VALUES OF A, B, C", "", "C P,"),
				new DLabel("", "<joint>", ""),
				new DLabel("", "<input>", "C"),
				new DLabel("", "<base>", "")
			);

		String[] coeff = stdin.get(0).split(",");

		if (coeff.length != 3)
			throw new InputsFreqException("<INVALID INPUTS!>\nPls give 3 Valid Inputs");

		/** @info Coeff A */
		try {a = Double.parseDouble(coeff[0]);}
		catch(NumberFormatException e) {a = Computo.solveScientific(coeff[0]);}
		if (a == 0.0)
			throw new BadNumberException("<NOT A QUADRATIC!>\nLeading Coeff ≠ 0");

		/** @info Coeff B */
		try {b = Double.parseDouble(coeff[1]);}
		catch(NumberFormatException e) {b = Computo.solveScientific(coeff[1]);}

		/** @info Coeff C */
		try {c = Double.parseDouble(coeff[2]);}
		catch(NumberFormatException e) {c = Computo.solveScientific(coeff[2]);}

		d = b*b - 4*a*c;

		String[] ans = new String[3];

		if (d >= 0) {

			if (d == 0)
				ans[0] = "REAL AND EQUAL ROOTS.";
			else
				ans[0] = "REAL AND DISTINCT ROOTS.";

			r1 = (-b + Math.sqrt(d))/(2*a);
			r2 = (-b - Math.sqrt(d))/(2*a);

			if (Setup.getNumberType()) {
				ans[1] = String.format("ROOT1: %s", Fractions.toFractions(r1));
				ans[2] = String.format("ROOT2: %s", Fractions.toFractions(r2));

			}else {
				int p = Setup.getPrecision();
				ans[1] = String.format("ROOT1: %."+p+"f", r1);
				ans[2] = String.format("ROOT2: %."+p+"f", r2);
			}

		}else { 
			ans[0] = "IMAGINARY ROOTS.";

			r1 = -b/(2*a);
			r2 = Math.sqrt(-d)/(2*a);

			if (Setup.getNumberType()) {
				String s1 = Fractions.toFractions(r1), s2 = Fractions.toFractions(r2);
				ans[1] = String.format("ROOT1: (%s) + (%s)i", s1, s2);
				ans[2] = String.format("ROOT2: (%s) - (%s)i", s1, s2);

			}else {
				int p = Setup.getPrecision();
				ans[1] = String.format ("ROOT1: %."+p+"f + %."+p+"fi", r1, r2);
				ans[2] = String.format ("ROOT2: %."+p+"f - %."+p+"fi", r1, r2);
			}
		}

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel(ans[0], "<tag>", "Y"),
			new DLabel(ans[1], "", "C W: G"),
			new DLabel(ans[2], "", "C W: G"),
			new DLabel("", "<base>", "")
		);
	}


}



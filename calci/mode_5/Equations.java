package calci.mode_5;

import design.*;
import errors.*;

import java.util.*;


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
				Design.printBox(
						"CONSIDER THE EQUATION : Ax² + Bx + C = 0 T", "",
						"ENTER VALUES OF A, B, C : $"
					);

		String[] coeff = stdin.get(0).split(" ");

		if (coeff.length > 3)
			throw new ManyInputsException("<MANY INPUTS!>\nPls give Valid Inputs");

		a = Double.parseDouble(coeff[0]);

		if (a == 0.0)
			throw new QuadraticException("<NOT A QUADRATIC!>\nLeading Coeff ≠ 0");

		b = Double.parseDouble(coeff[1]);
		c = Double.parseDouble(coeff[2]);

		d = b*b - 4*a*c;

		String[] ans = new String[4];

		ans[1] = "";
		if (d >= 0) {

			if (d == 0)
				ans[0] = "REAL AND EQUAL ROOTS M";
			else
				ans[0] = "REAL AND DISTINCT ROOTS M";

			r1 = (-b + Math.sqrt(d))/(2*a);
			r2 = (-b - Math.sqrt(d))/(2*a);

			ans[2] = String.format("ROOT1: %.4f T", r1);
			ans[3] = String.format("ROOT2: %.4f T", r2);

		}else { 
			ans[0] = "IMAGINARY ROOTS! M";

			r1 = -b/(2*a);
			r2 = Math.sqrt(-d)/(2*a);

			ans[2] = String.format ("ROOT1: %.4f + %.4fi T", r1, r2);
			ans[3] = String.format ("ROOT2: %.4f - %.4fi T", r1, r2);

		}
		Design.printBox(ans);
	}


}



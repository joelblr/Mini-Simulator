package calci.mode_2;

import calci.*;
import errors.*;
import calci.transformo.*;


public class Komplx {

	private double real, imag;

	public Komplx(String z) {
		double nums[] = findValues(z);
		this.real = nums[0];
		this.imag = nums[1];

	}public Komplx(Object real, Object imag) {
		this.real = Double.parseDouble(real.toString());
		this.imag = Double.parseDouble(imag.toString());

	}public void setReal(double real) {
		this.real = real;

	}public void setImag(double imag) {
		this.imag = imag;

	}public double getReal() {
		return this.real;

	}public double getImag() {
		return this.imag;

	}public double[] toArray() {
		return new double[] {this.getReal(), this.getImag()};

	}/** @Override @info String Representation of a Complex Number <p> {@code Z = a + bi} */
	public String toString() {
		int p = Setup.getPrecision();
		double z[] = this.toArray();
		String result = "";
		char flag = (z[1] >= 0.0) ? '+' : '-';

		if (Setup.getNumberType())
			result = Fractions.toFractions(z[0]) + 
			" "+flag+" " + Fractions.toFractions(Math.abs(z[1])) +"i";
		else {
			result = String.format("%."+p+"f", z[0]) + 
					" "+flag+" " + String.format("%."+p+"f", Math.abs(z[1]))+"i";
			;
		}
		return result;
//		if (this.getImag() < 0.0)
//			return this.getReal() + " - " + Math.abs(this.getImag()) + "i";
//		return this.getReal() + " + " + this.getImag() + "i";

	}


	private static double[] findValues(String z) {
		Komplx c = new Komplx(0, 0);
		int flag = 1;
		for (String s : z.split(" ")) {
			s = s.trim();

			try {	c.setReal(flag * Double.parseDouble(s));	}
			catch (NumberFormatException e) {
				if (s.equals("+") || s.equals("-")) {
					flag = (s.equals("+")) ? 1 : -1;
				}else if (s.endsWith("i")) {
					if (s.equals("i") || s.equals("-i"))
						s = s.equals("i") ? "1i" : "-1i";
					c.setImag(flag*Double.parseDouble(s.substring(0, s.length()-1)));
				}else {
					throw new BadNumberException("<BAD NUMBER!>%s is NOT an Operand.");
				}
			}
		}

		return c.toArray();
	}
	private static int findQuadrant(double c[]) {
		if (c[0] >= 0.0 && c[1] >= 0.0)
			return 1;
		if (c[0] < 0.0 && c[1] >= 0.0)
			return 2;
		if (c[0] < 0.0 && c[1] < 0.0)
			return 3;
		if (c[0] >= 0.0 && c[1] < 0.0)
			return 4;
		return -1;
	}


	/** @info Addition of 2 Complex Numbers:.. {@code Z1 + Z2}  */
	public static Komplx addKplx(Komplx z1, Komplx z2) {
		double r = z1.getReal() + z2.getReal();
		double i = z1.getImag() + z2.getImag();
		return new Komplx(r, i);

	}/** @info Subtraction of 2 Complex Numbers:.. {@code Z1 - Z2}  */
	public static Komplx subKplx(Komplx z1, Komplx z2) {
		double r = z1.getReal() - z2.getReal();
		double i = z1.getImag() - z2.getImag();
		return new Komplx(r, i);

	}/** @info Multiplication of 2 Complex Numbers:.. {@code Z1 * Z2}  */
	public static Komplx mulKplx(Komplx z1, Komplx z2) {
		double[] c1 = z1.toArray(), c2 = z2.toArray();
		double r = c1[0]*c2[0] - c1[1]*c2[1];
		double i = c1[1]*c2[0] + c1[0]*c2[1];
		return new Komplx(r, i);

	}/** @info Division of 2 Complex Numbers <p> {@code Z1 / Z2}  */
	public static Komplx divKplx(Komplx z1, Komplx z2) {

		double[] c1 = z1.toArray(), c2 = z2.toArray();
		if (c2[0] == 0.0 && c2[1] == 0.0)
			throw new BadNumberException("<DIVISION ERROR!>Can't divide with Zero Complex Number.");

		double denom = c2[0]*c2[0] + c2[1]*c2[1];
		double r = (c1[0]*c2[0] + c1[1]*c2[1])/denom;
		double i = (c1[1]*c2[0] - c1[0]*c2[1])/denom;
		return new Komplx(r, i);

	}/** @info Conjugate of Complex Number {@code Z} */
	public static Komplx findConjugate(Komplx z) {
		return new Komplx(z.getReal(), -z.getImag());

	}/** @info Argument of Complex Number {@code Z} */
	public static String findArgumnet(Komplx z) {
		double c[] = z.toArray(), θ;//angle

		if (c[0] == 0.0) {
			if (c[1] == 0.0)
				throw new BadNumberException("<ARGUMENT ERROR!>\n∅ Argument of 0 + 0i");
			if (Setup.getAngleType() == "deg")
				θ = (c[1] > 0) ? 90 : -90;
			else 
				θ = (c[1] > 0) ? Math.PI/2 : -Math.PI/2;
		}else {
			θ = Computo.solveScientific(String.format("atan(%f/%f)", Math.abs(c[1]), Math.abs(c[0])));
		}

		double factor = (Setup.getAngleType() == "deg") ? 90 : Math.PI/2;
		switch (findQuadrant(c)) {
			case 1 :	break;
			case 2 :	θ = θ+factor;	break;
			case 3 :	θ = -(θ+factor);break;
			case 4 :	θ = -θ;			break;
		}

		int p = Setup.getPrecision();
		if (Setup.getNumberType()) {
			if (Setup.getAngleType() == "deg")
				return String.format("∠ %s°", Fractions.toFractions(θ));
			else
				return String.format("∠ %s", Fractions.toFractions(θ));

		}else {
			if (Setup.getAngleType() == "deg")
				return String.format("∠ %."+p+"f°", θ);
			else
				return String.format("∠ %."+p+"f", θ);
		}

	}/** @info Modulus of Complex Number {@code Z} */
	public static double findModulus(Komplx z) {
		double val = z.getReal() * z.getReal() + z.getImag() * z.getImag();
		return Computo.solveScientific(String.format("sqrt(%f)", val));

	}/** @info Rectangular form of a Polar Complex Number:.. {@code R, θ} */
	public static Komplx toRectangular(double r, double θ) {
		double	a = Computo.solveScientific(String.format("%f*cos(%f)", r, θ)),
				b = Computo.solveScientific(String.format("%f*sin(%f)", r, θ));
		return new Komplx(a, b);

	}/** @info Polar form of a Rectangular Complex Number:.. {@code a + bi} */
	public static String toPolar(double a, double b) {
		return toPolar(new Komplx(a, b));

	}/** @info Polar form of a Rectangular Complex Number:.. {@code a + bi} */
	public static String toPolar(Komplx z) {

		if (z.getReal() == 0.0 && z.getImag() == 0.0)
			throw new BadNumberException("<ARGUMENT ERROR!>\n∅ Argument of 0 + 0i");

		double r = findModulus(z); double θ;// = findArgumnet(z);//
		String a = findArgumnet(z);
		if (Setup.getAngleType() == "deg") {
			try {θ = Double.parseDouble(a.substring(2, a.length()));}
			catch(NumberFormatException e)
			{θ = Double.parseDouble(a.substring(2, a.length()-1));}
		}else {
			try {θ = Double.parseDouble(a.substring(2, a.length()));}
			catch(NumberFormatException e)
			{θ = Double.parseDouble(a.substring(2, a.length()-1));}
		}

		int p = Setup.getPrecision();

		if (Setup.getNumberType()) {
			if (Setup.getAngleType() == "deg")
				return String.format("%s ∠ %s°", Fractions.toFractions(r), Fractions.toFractions(θ));
			else
				return String.format("%s ∠ %s", Fractions.toFractions(r), Fractions.toFractions(θ));

		}else {
			if (Setup.getAngleType() == "deg")
				return String.format("%."+p+"f ∠ %."+p+"f°", r, θ);
			else
				return String.format("%."+p+"f ∠ %."+p+"f", r, θ);
		}
	}


	public static void main(String[] args) {
		Komplx c1 = new Komplx(-1, 2);
		Komplx c2 = new Komplx(-1, 1);
		String s = c1+"";
		System.out.println(s);
		System.out.println(c2);
		System.out.println(toPolar(c2));
		System.out.println(toPolar(c1));
	}
}




package calci;

import calci.transformo.Computo;
import errors.*;

public class Fractions {

	/* Data Fields */
	private long numer;
	private long denom;

	/* Constructors */
	public Fractions(long a, long b) {
		if (b == 0)
			throw new BadNumberException("<DIVISION BY ZERO!>Sorry, can't handle Infinity.");
		this.numer = a;
		this.denom = b;

	}public Fractions(double a) {

		String f[] = toFractions(a).split(" ⅃ ");
		this.numer = Long.parseLong(f[0]);
		this.denom = Long.parseLong(f[1]);
	}

	/** @info Static Methods */

	/** @info 5 ⅃ 10 >> 0.5 */
	public static double toDecimal(String Frac) {
//		if (!Frac.contains(" ⅃ "))
//			throw new Exception(String.format("<HEY!>\nInvalid Input %s", Frac));
		Frac.replace(" ⅃ ", "/");
		return Computo.solveScientific(Frac);

	}/** @info 0.5 >> 5 ⅃ 10 */
	public static String toFractions(long numer, long denom) {
		return numer + " ⅃ " + denom;


	}public static String toFractions(double num) {

		double err = 0.000_000_1;
		long n = Math.round(Math.floor(num)) ;
		num -= n;

		if (num < err) {
			return n + " ⅃ " + 1;

		}else if(1 - err < num) {
			return (n+1) + " ⅃ " + 1;

		}


		double upper_numer = 1, upper_denom = 1;
		double midle_numer = 1, midle_denom = 1;
		double lower_numer = 0, lower_denom = 1;

		while (true) {
			midle_numer = upper_numer + lower_numer;
			midle_denom = upper_denom + lower_denom;

			if (midle_denom * (num + err) < midle_numer) {
				upper_numer = midle_numer;	upper_denom = midle_denom;

			}else if (midle_denom * (num - err) > midle_numer) {
				lower_numer = midle_numer;	lower_denom = midle_denom;

			}else {
				return (long)(n * midle_denom + midle_numer) + " ⅃ " + (long)midle_denom;
			}
		}
	}


	/* toString Methods */
	public String toString(double num) {
		return numer + " ⅃ " + denom;

	}public String toString(long numer, long denom) {
		return numer + " ⅃ " + denom;

	}@Override
	public String toString() {
		return this.numer + " ⅃ " + this.denom;

	}


	/* Data-Retrieving Methods */
	public long getNumerator() {
		return this.numer;

	}public long getDenominator() {
		return this.denom;
	}


	/* Main Method for Test-Runs */
	public static void main(String[] args) {
		System.out.println(new Fractions(1, 2).toString());
		System.out.println( Fractions.toFractions(1.33));
		System.out.println( Fractions.toFractions(1.67));
		System.out.println( Fractions.toFractions(0.167));
		System.out.println( Fractions.toFractions(2.1875));
		System.out.println( Fractions.toFractions(calci.transformo.Computo.solveScientific("114/354")));
		System.out.println( Fractions.toFractions(calci.transformo.Computo.solveScientific("5/3")));
		System.out.println(5.0/3);
	}

}

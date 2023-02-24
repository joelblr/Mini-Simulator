package calci;

import errors.*;
import java.util.*;


final class Func {

	private static long gcd(long a, long b) {
		for (long i = (a < b) ? a : b;	i > 1; i--)
			if (a % i == 0 && b % i == 0)
				return i;
		/* when one of them is zero */
		return Math.max(a, b);
	}

	protected static double nCr(double n, double r) {

		if ( Integer.parseInt( (n +"").split(".")[1]) != 0 || 
				Integer.parseInt( (r +"").split(".")[1]) != 0)
			throw new BadNumberException("<nCr!>\nIntegers, Not Decimals!");

		if (n < r)
			throw new BadNumberException("<nCr!>N >= R !");
		if (r < 0)
			throw new BadNumberException("<nCr!>N, R >= 0 !");


		long N = (long) n, R = (long) r;
		long c = 1, k = 1;

		R = Math.min(R, N-R);

		if (R != 0) {
			while (R > 0) {

				c *= N;  k *= R;

				long m = gcd(c, k);

				c /= m;  k /= m;
				N--;	 R--;

			}
		}

		return c;
	}

	protected static double nPr(double n, double r) {

		if ( Integer.parseInt( (n +"").split(".")[1]) != 0 || 
				Integer.parseInt( (r +"").split(".")[1]) != 0)
			throw new BadNumberException("<nPr!>\nIntegers, Not Decimals!");

		if (n < r)
			throw new BadNumberException("<nPr!>\nN >= R !");
		if (r < 0)
			throw new BadNumberException("<nPr!>N, R >= 0 !");


		long N = (long) n, R = (long) r;
		long p = 1;

		for (long i = N; i > N-R; i--)
			p *= i;

		return p;
	}

	protected static double fact(double x) {
		return nPr(x, x);
	}

}



public class Converto {

	protected static LinkedList <String> postfix;
	protected static LinkedHashSet<String> unique;
	protected static HashSet<String> uniOperators;
	protected static HashSet<String> biOperators;

	static {
		postfix = new LinkedList<String>();
		unique = new LinkedHashSet<String>();
		uniOperators = new HashSet<String>();
		biOperators = new HashSet<String>();

		uniOperators.addAll(Arrays.asList("~", "neg", "ceil", "floor", "abs",
				"pow", "sqrt", "cbrt", "exp", "log", "ln", "fact", "ncr", "npr",
				"sin", "cos", "tan", "csc", "sec", "cot",
				"asin", "acos", "atan", "acsc", "asec", "acot",
				"sinh", "cosh", "tanh", "csch", "sech", "coth"
				)); //round, min, max, avg

		biOperators.addAll(Arrays.asList(
				"$", "*", "/", "%", "+", "-",
				"<<", ">>", "&", "^", "|"
				));

	}


	public static LinkedHashSet<String> getUnique() {
		return unique;
	}

	public static void setPostfix(LinkedList <String> pf) {
		postfix = pf;
	}


	/**TODO use factorial as {@code !}*/
	private static int priority(String ch) {

		if (ch.equals("(") || ch.equals("|") || ch.equals("^") || ch.equals("&") || ch.equals(">>") || ch.equals("<<"))
			return ch.equals("(") ? 0 : ch.equals("|") ? 1 : ch.equals("^") ? 2 : ch.equals("&") ? 3 : 4 ;
		else if (ch.equals("+") || ch.equals("-"))
			return 5;
		else if (ch.equals("$") || ch.equals("~") || ch.equals("*") || ch.equals("/") || ch.equals("%"))
			return ch.equals("$") ? 7 : ch.equals("~") ? 9 : 6;

		return 9;
	}


	public static void findPostfix(String infix, char type) {

		postfix.clear();
		/* Boolean Exp */
		if (type == 'B')
			unique.clear();

		Stack <String> stac = new Stack <String>();
		char[] itr = infix.toCharArray();

		for (int i = 0; i < infix.length(); i++) {

			char ch1 = itr[i];
			char ch2 = itr[(i+1)%infix.length()];

			if (Character.isWhitespace(ch1)) {
				continue;

			/* extracting a num (123) or a func name (sin). */
			}else if (Character.isLetterOrDigit(ch1)) {

				postfix.add("");
				do {	//Number/Name
					postfix.addLast(postfix.pollLast() + ch1);
					try {ch1 = infix.charAt(i+1);}
					catch (StringIndexOutOfBoundsException e) {break;}
				}while (Character.isLetterOrDigit(ch1) && ++i != 0);


				/* To check if last element is num */
				try {
					Double.parseDouble(postfix.peekLast());

				/* To check if atleast it's a valid func name. */
				}catch (NumberFormatException e) {
					if (type == 'B')
						if (unique.contains(postfix.peekLast()))
							unique.add(postfix.peekLast().toUpperCase());

					else if (!(uniOperators.contains(postfix.peekLast().toLowerCase()) ||
							biOperators.contains(postfix.peekLast().toLowerCase()) ))
						throw new InvalidFunctionException(
						String.format("<Invalid Function Name!>\n%s undefined", postfix.peekLast()));
				}


			/* For Opening Bracket */
			}else if (ch1 == '(') {
				stac.push(ch1+"");

			/* For Closing Bracket */
			}else if (ch1 == ')') {

				while (!stac.peek().equals("("))
					postfix.add( stac.pop());
				stac.pop();

			/* For Operators */
			}else {

				String op = ch1 + "";
				/* For >> << Operators */
				if (ch1 == '<' || ch1 == '>') {
					if (ch2 != ch1)
						throw new InvalidOperatorException(
								String.format("<Invalid Operator!>%s undefined", ch1+ch2));
					op += op;
				}

				while (priority(stac.peek()) >= priority(op))
					postfix.add( stac.pop());

				stac.push(op);

			}
		}

	}

}

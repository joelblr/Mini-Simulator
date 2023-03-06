package calci.transformo;

import errors.*;
import java.util.*;


final class Func {

	private static long gcd(long a, long b) {
		for (long i = (a < b) ? a : b;	i > 0; i--)
			if (a % i == 0 && b % i == 0)
				return i;
		/** @info when one of them is zero */
		return Math.max(a, b);
	}

	protected static double nCr(double n, double r) {

		if (n%1 != 0 || r%1 != 0)
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

		if (n%1 != 0 || r%1 != 0)
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

	public static LinkedList <String> postfix;
	protected static LinkedHashSet<String> unique;
	protected static HashSet<String> operators;
	protected static HashSet<String> uniOperators;
	protected static HashSet<String> biOperators;
	protected static HashSet<String> polyOperators;

	static {
		postfix = new LinkedList<String>();
		unique = new LinkedHashSet<String>();
		operators = new HashSet<String>();
		uniOperators = new HashSet<String>();
		biOperators = new HashSet<String>();
		polyOperators = new HashSet<String>();

		/** @info Math */
		operators.addAll(Arrays.asList("~", "neg", "ceil", "floor", "abs",
				"sqrt", "cbrt", "exp", "log", "ln", "fact",
				"sin", "cos", "tan", "csc", "sec", "cot",
				"asin", "acos", "atan", "acsc", "asec", "acot",
				"sinh", "cosh", "tanh", "csch", "sech", "coth",

				"u-",										/** @info Unary ± */
				"mod", "arg", "conj", "pol", "rec",			/** @info Complex */
				"$", "*", "/", "%", "+", "-",				/** @info Basic */
				"<<", ">>", "&", "^", "|",					/** @info Bitwise */
				"ncr", "npr", "pow",						/** @info Math */
				"round", "max", "min", "avg", "mean", "sum"	/** @info Stats */
				));

		uniOperators.addAll(Arrays.asList("~", "neg", "ceil", "floor", "abs",
				"sqrt", "cbrt", "exp", "log", "ln", "fact",
				"sin", "cos", "tan", "csc", "sec", "cot",
				"asin", "acos", "atan", "acsc", "asec", "acot",
				"sinh", "cosh", "tanh", "csch", "sech", "coth",
				"mod", "arg", "conj","pol",
				"u-"
				));

		biOperators.addAll(Arrays.asList( 
				"$", "*", "/", "%", "+", "-",
				"<<", ">>", "&", "^", "|"
				));

		polyOperators.addAll(Arrays.asList("ncr", "npr", "pow",
				"round", "max", "min", "avg", "mean", "sum", //sd,
				"rec"
				));
	}


	public static LinkedHashSet<String> getUnique() {
		return unique;
	}

	public static LinkedList<String> getPostfix() {
		return postfix;

	}public static void setPostfix(LinkedList <String> pf) {
		postfix = pf;
	}



	public static boolean bracesValidato(String exp) {

		/** @info Create a new stack to store the chars. */
		Stack<Character> T = new Stack<>();

		/** @info Convert string into Char array */
		for(char ch: exp.toCharArray()) {
			/** @info Check ch */
			switch (ch) {
			/** @info Open Bracket then Push it into Stack. */
			case '(':	case '{':	case '[':
				T.push(ch);	break;
			/** @info Close Bracket then Compare* and Pop the item. */
			case ')':	case '}':	case ']':
				if(T.isEmpty() || ch == ')' && T.peek() != '(' ||
				ch == ']' && T.peek() != '[' || ch == '}' && T.peek() != '{')
					return false;
				T.pop();
			}
		}
		/** @info If Stack is not Empty, means we have Unused Brackets. */
		return T.isEmpty();
	}



	private static int priority(String ch) {

		if (ch.equals("(") || ch.equals("|") || ch.equals("^") || ch.equals("&") || ch.equals(">>") || ch.equals("<<"))
			return ch.equals("(") ? 0 : ch.equals("|") ? 1 : ch.equals("^") ? 2 : ch.equals("&") ? 3 : 4 ;
		else if (ch.equals("+") || ch.equals("-"))
			return 5;
		else if (ch.equals("$") || ch.equals("~") || ch.equals("*") || ch.equals("/") || ch.equals("%"))
			return ch.equals("$") ? 7 : ch.equals("~") ? 9 : 6;
		else if (ch.equals("!"))
			return 9;
		else if (ch.equals("u-"))
			return 10;
		/** @info Function Names */
		return 9;
	}


	public static void findPostfix(String infix, char type) {

		if (!bracesValidato(infix))
			throw new BadExpression("<BAD EXPRESSION!>\nMisMatching Parenthesis.");

		postfix.clear();
		/** @info Boolean Expression. */
		if (type == 'B') {
			infix = infix.replace("+", "|").replace("*", "&").replace(".", "&");
			infix = infix.replace("!", "~").replace(" ", "").replace(" ", "");
			unique.clear();

		}/** @info Power a ^ b */
		else {
			infix = infix.replace("^", "$").replace(" ", "");
		}

		Stack <String> stac = new Stack <String>();
		char[] itr = infix.toCharArray();

		for (int i = 0; i < infix.length(); i++) {

			char ch0 = (i > 0) ? itr[i-1] : '(';
			char ch1 = itr[i];
			char ch2 = (i < infix.length()-1) ? itr[i+1] : ')';

			/** @info @Depretiated 
			if (Character.isWhitespace(ch1)) {	
				continue;
			*/

			/** @info Unary Operators */
			if ((ch1 == '+' || ch1 == '-') && (!Character.isLetterOrDigit(ch0)) && (ch0 == '(' || ch0 == ',' || 
				stac.peek() == "u-" ||	/** @edgeCase */
				(operators.contains(stac.peek()) && stac.peek().endsWith(ch0+""))) ) {

				if (ch1 == '+')/** @ignore*/
					continue;

				String op = "u-";
				if (stac.peek().equals(op))
					stac.pop();
				else
					stac.push(op);

			}/** @info Extracting a num (123) or a func name (sin). */
			else if (Character.isLetterOrDigit(ch1) || ch1 == '.') {

				postfix.add("");
				do {	/** @info Number//Name */
					postfix.addLast(postfix.pollLast() + ch1);
					try {ch1 = infix.charAt(i+1);}
					catch (StringIndexOutOfBoundsException e) {break;}
				}while ((Character.isLetterOrDigit(ch1)  || ch1 == '.') && ++i != 0);


				/** @info To check if last element is num/const//complex Bi, not iB */
				try {
					String constant = postfix.pollLast();

					/** @info π = 3.141592653589793 */
					if (constant == "pi") {
						postfix.add(Math.PI + "");

					}/** @info e = 2.718281828459045 */
					else if(constant == "e") {
						postfix.add(Math.E + "");

					}/** @info i = √-1 */
					else if(constant.endsWith("i")){
						postfix.add(constant);

					}else {
						postfix.add(constant);
						/** @info Number Check and update the ± Sign.*/
						Double.parseDouble(postfix.peekLast());

					}

				}/** @info To check if atleast it's a valid F(x)//Variable. */
				catch (NumberFormatException e) {
//					e.printStackTrace();
					if (type == 'B') {
						if (!unique.contains(postfix.peekLast()))
							unique.add(postfix.peekLast().toUpperCase());

					}else {
						if (! operators.contains(postfix.peekLast().toLowerCase()) )
							throw new BadFunctionException(
							String.format("<Invalid Function Name!>\n%s undefined", postfix.peekLast()));
						else {
							stac.add(postfix.pollLast());
						}
					}
				}

			}/** @info For Commas */
			else if(ch1 == ',') {
				while (!stac.peek().equals("("))
					postfix.add( stac.pop());
				postfix.add( ch1+"");


			}/** @info For Opening Bracket or Commas */
			else if (ch1 == '(') {
				stac.push(ch1+"");

			}/** @info For Closing Bracket */
			else if (ch1 == ')') {

				while (!stac.peek().equals("("))
					postfix.add( stac.pop());
				stac.pop();

			}/** @info For Operators */
			else {

				String op = ch1 + "";
				/** @info For >> << Operators */
				if (ch1 == '<' || ch1 == '>') {
					if (ch2 != ch1)
						throw new InvalidOperatorException(
								String.format("<Invalid Operator!>%s undefined", ch1+ch2));
					op += op;
					i++;
				}

				while (priority(stac.peek()) >= priority(op))
					postfix.add( stac.pop());

				stac.push(op);

			}

		}

	}

}

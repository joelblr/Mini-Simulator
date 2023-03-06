package calci.mode_5;

import calci.*;
import errors.*;
import design.*;
import java.util.*;
import calci.transformo.*;


public class Matrix {

	static HashMap<String, String> subMap = new HashMap<String, String>();

	static {
		subMap.put("0", "₀");subMap.put("1", "₁");subMap.put("2", "₂");subMap.put("3", "₃");
		subMap.put("4", "₄");subMap.put("5", "₅");subMap.put("6", "₆");subMap.put("7", "₇");
		subMap.put("8", "₈");subMap.put("9", "₉");subMap.put("10", "₁₀");
	}


	/*Augmented Matrix, Solution Matrix*/
	double A[][];
	int rows, cols, rank;

	/*1. Unique, 2. Many, 3. No Solution*/
	double U[], V[][];	String W[];



	public void getMatirx() {

		List<String> stdin = 
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Dimensions of SOLE", "<tag>", "Y"),
				new DLabel("ENTER NUMBER OF UNKNOWNS:  ", "<input>", "C W: "),
				new DLabel("ENTER NUMBER OF LINEAR EQUATIONS:  ", "<input>", "C W: "),
				new DLabel("", "<base>", "")
			);
		cols = Integer.parseInt(stdin.get(0).trim()) + 1;
		rows = Integer.parseInt(stdin.get(1).trim());

		if (!(0 < cols && cols < 11)) {
			throw new LimitExceededException("<LIMITS EXCEEDED!>\n0 < No. of Unknowns < 10");
		}if (!(0 < rows && rows < 10)) {
			throw new LimitExceededException("<LIMITS EXCEEDED!>\n0 < No. Linear Eqns < 10");
		}

		String l0 = "", l1 = "", row;
		for (int i = 1; i < cols; i++) {
			String alpha = (char)(64+i)+"";
			l0 += alpha + "x" + subMap.get(i+"") + " ";
			l1 += alpha + ", ";

		}l0 = Design.joinWith(" + ", l0.split(" ")) + " = Y";
		l1 += "Y";

		row = "ENTER VALUES OF "+cols+" COEFFs FOR "+rows+" LINEAR EQUATIONS: "+l1;
		Design.printBox(70, new DLabel("", "<top>", ""),
			new DLabel(l0, "<tag>", "Y"),
			new DLabel(row, "", "C W: "+"R P, ".repeat(cols)),
			new DLabel("", "<joint>", "")
		);

		LinkedList<DLabel> eqns = new LinkedList<DLabel>();
		for (int i = 1; i <= rows; i++) {
			row = "Eqn "+i+": ";
			eqns.add(new DLabel(row, "<input>", "C W:"));

		}eqns.add(new DLabel("", "<base>", ""));
		stdin = Design.printBox(70, eqns.toArray(new DLabel[0]));


		A = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			String[] lineI = stdin.get(i).split(",");
			if(lineI.length != cols)
				throw new InputsFreqException(
						String.format("<DIMENSION ERROR!>\nExpected %d Nums, but got %d", cols, lineI.length));
			for (int j = 0; j < cols; j++) {
				try {
					A[i][j] = Double.parseDouble(lineI[j]);

				}catch (NumberFormatException e) {
					A[i][j] = Computo.solveScientific(lineI[j]);
				}
			}
		}

	}


	public void printMatirx(Object M[]) {
		for (int i = 0; i < M.length; i++)
			System.out.print(M[i] + " ");
		System.out.println();

	}public void printMatirx(double M[][]) {
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M[i].length; j++)
				System.out.format("%8.2f", M[i][j]);
			System.out.println();
		}System.out.println();
	}


	public void solveSLE() {
		this.getMatirx();
		this.findEchelon();
		this.findConsistency();
		this.findSolution();
		this.displaySolution();
	}


	private void displaySolution() {

		int p = Setup.getPrecision();
		boolean isFrac = Setup.getNumberType();
		LinkedList<DLabel> labels = new LinkedList<DLabel>();
		String ans = "";

		if (0 < rank && rank < cols-1) {//Infinity

			for (int i = 0; i < cols-1; i++) {
				String row = "";
				for (int k = 0; k < cols-rank-1; k++) {
					if (V[i][k] == 0.0)		continue;
					if (isFrac)//Fraction
					row += String.format("(%s)%s + ", Fractions.toFractions(V[i][k]), "k"+subMap.get(k+1+""));
					else//Decimal
					row += String.format("(%."+p+"f)%s + ", V[i][k], "k"+subMap.get(k+1+""));

				}if (V[i][cols-rank-1] != 0.0) {//constant
					int k = cols-rank-1;
					if (isFrac)//Fraction
					row += String.format("(%s)", Fractions.toFractions(V[i][k]));
					else//Decimal
					row += String.format("(%."+p+"f)", V[i][k]);
				}

				ans = String.format("%s  =  %s", "x"+subMap.get(i+1+""), row);
				labels.add(new DLabel(ans, "", "R W= G"));
			}

		}else if (rank == cols-1) {//Unique
			for (int i = 1; i < cols; i++) {
				if (isFrac)//Fraction
					ans = String.format("x%s = %s", subMap.get(i+""), Fractions.toFractions(U[i-1]));
				else//Decimal
					ans = String.format("x%s = %8."+p+"f", subMap.get(i+""), U[i-1]);
				labels.add(new DLabel(ans, "", "R W= G"));
			}

		}else {//NULL
			for (int i = 1; i < cols; i++) {
				ans = String.format("x%d = %s", i, W[i-1]);
				labels.add(new DLabel(ans, "", "R W= G"));
			}
		}

		labels.addFirst(new DLabel("Solutions", "<tag>", "Y"));
		labels.addFirst(new DLabel("", "<top>", ""));
		labels.add(new DLabel("", "<base>", ""));
		Design.printBox(0, labels.toArray(new DLabel[0]));

	}


	/* Ri ⟷ Rj */
	private void rearrangeRows(int idx) {

		LinkedList<Integer> zeroIdx = new LinkedList<Integer>();

		for (int i = idx; i < rows; i++)
			if (A[i][idx] == 0.0)
				zeroIdx.add(i);

		int k = rows-1;
		for (int row : zeroIdx) {
			double tmp[] = A[row];
			A[row] = A[k];
			A[k] = tmp;
			k--;
		}
	}

	/* Gauss Elimination: Echelon Form */
	private void findEchelon() {
		for (int i = 0; i < rows-1; i++) {

			rearrangeRows(i);

			for (int k = i+1; k < rows; k++) {
				double factor;
				try {
					if (A[k][i] == 0.0 || A[i][i] == 0.0)
						throw new Exception();
					factor = A[k][i]/A[i][i];

				}catch (Exception e) {
					continue;
				}
	
				/* Row Transformation	R₂ → R₂ - F * R₁ */
				for (int j = i; j < cols; j++)
					A[k][j] -= A[i][j] * factor;
			}
		}
	}	


	/* Checks if the given Row is a ZeroRow or not */
	private boolean checkZeroRow(int row, int k) {
		for (int j = 0; j < cols-k; j++)
			if (A[row][j] != 0.0)
				return false;
		return true;
	}

	/* Consistency of System of Linear Equations */
	private void findConsistency() {
		/* Rank of Coefficient Matirx*/
		int rankC = rows;
		for (int i = rows-1; i >= 0; i--) {
			if (checkZeroRow(i, 1))
				rankC--;
		}

		/* Rank of Augumented Matirx*/
		int rankA = rows;
		for (int i = rows-1; i >= 0; i--) {
			if (checkZeroRow(i, 0))
				rankA--;
		}

		rank = (rankC == rankA) ? rankA : -1;

	}


	/* Compute the Solution based on Rank*/
	private void findSolution() {

		if (rank > 0) {

			/*Unique Solution*/
			if (rank == cols -1) {

				U = new double[cols-1];
				for (int i = rank-1; i >= 0; i--) {
					double b = 0.0;
					/* aX + b = c;  to find b*/
					for (int j = cols-2; j > i; j--) {
						b += A[i][j] * U[j];
					}
					U[i] = (A[i][cols-1] - b) / (A[i][i]);
				}

			/*Infinite Solutions*/
			}else {

				V = new double[cols-1][cols-rank];

				for (int i = rank, k = 0; i < cols-1; i++, k++)
					V[i][k] = 1;

				for (int i = rank-1; i >= 0; i--) {

					double b[] = new double[cols-rank];
					/* aX + b[] = c;  to find b[]*/
					for (int j = cols-2; j > i; j--)
						for (int k = 0; k < cols-rank; k++)
							b[k] -= A[i][j] * V[j][k];

					b[cols-rank-1] += A[i][cols-1];

					for (int k = 0; k < cols-rank; k++)
						V[i][k] = b[k] / A[i][i];

				}

			}

		}else {

			/*No Solution*/
			W = new String[cols-1];
			for (int i = cols-2; i >= 0; i--)
				W[i] = "∅";//ϕ
		}

	}
	public static void main(String[] args) {
		

	}


}





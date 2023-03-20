package bescom;

import design.*;
import errors.*;
import java.util.*;



public class Bescom {

	static Double taxRate, facRate;
	static List<Double> units;
	static List<Double> constFC;
	static List<Double> constEC;

	static List<String> months;
	static List<String> fields;


	static {
		taxRate = 0.09; facRate = 0.37;
		units = new LinkedList<Double>();
		constFC = new LinkedList<Double>();
		constEC = new LinkedList<Double>();

		units = Arrays.asList(50.0, 50.0, 100.0);
		constFC = Arrays.asList(100.0, 110.0);
		constEC = Arrays.asList(4.15, 5.60, 7.15, 8.20);

		months = new LinkedList<String>();
		months = Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
				"JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

		//XXX name phno...
		fields = new LinkedList<String>();
		fields = Arrays.asList("totUNITS", "UNITS", "FC", "EC", "FAC", "TAX", "NET");

	}

	/** @idea
	 * JAN	<p>
			->	units:			<br>
			->	unitformat:		<br>
	 		->	fixed charges :	<br>
	 		->	engy charges :	<br>
	 		->	fac charges :	<br>
	 		->	tax charges :	<br>
			->	net amt :		<br>
	...<p>
	 * DEC	<p>
			->	units:			<br>
	 		->	fixed charges :	<br>
	 		->	engy charges :	<br>
	 		->	fac charges :	<br>
	 		->	tax charges :	<br>
			->	net amt :		<br>
	*/
	private HashMap <String, HashMap <String, ArrayList<Double>>> data;

	/** @info Account Details */
	String name, phno, password;


	private void calculateBills(List<String> month) {

		for (String m : month) {

			for (String f : fields) {

				if (data.get(m).get("totUNITS").size() == 0)
					continue;

				switch (f) {

				case "UNITS" :
					data.get(m).get(f).clear();
					double unit = data.get(m).get("totUNITS").get(0);
					if (unit >= 0 && unit <= 50) {
						data.get(m).get(f).add(unit);

					}else if(unit > 50 && unit <= 100) {
						data.get(m).get(f).addAll(Arrays.asList(50.0, unit-50));

					}else if(unit > 100 && unit <= 200) {
						data.get(m).get(f).addAll(Arrays.asList(50.0, 50.0, unit-100));

					}else if(unit>200) {
						data.get(m).get(f).addAll(Arrays.asList(50.0, 50.0, 100.0, unit-200));

					}break;

				case "FC" :
					Iterator<Double> itr0 = constFC.iterator();
					data.get(m).get(f).add(1.00*itr0.next());
					data.get(m).get(f).add(1.25*itr0.next());
					break;

				case "EC" :
					Iterator<Double> ec = constEC.iterator();
					Iterator<Double> itr1 = data.get(m).get("UNITS").iterator();
					while (itr1.hasNext())
						data.get(m).get(f).add(itr1.next()*ec.next());
					break;

				case "FAC" :
					Iterator<Double> itr2 = data.get(m).get("UNITS").iterator();
					data.get(m).get(f).add((allSum(itr2))*facRate);
					break;

				case "TAX" :
					Iterator<Double> itr3 = data.get(m).get("EC").iterator();
					data.get(m).get(f).add((allSum(itr3))*taxRate);
					break;

				case "NET" :
					Iterator<Double> itr11 = data.get(m).get("FC").iterator();
					Iterator<Double> itr22 = data.get(m).get("EC").iterator();
					Iterator<Double> itr33 = data.get(m).get("FAC").iterator();
					Iterator<Double> itr44 = data.get(m).get("TAX").iterator();
					data.get(m).get(f).add(allSum(itr11) + allSum(itr22) + allSum(itr33) + allSum(itr44));
					break;
				}
			}
		}
	}


	private void presetData() {

		data = new HashMap <String, HashMap <String, ArrayList<Double>>>();

		for (String m : months) {

			data.put(m, new HashMap<String, ArrayList<Double>>());

			for (String f : fields)
				data.get(m).put(f, new ArrayList<Double>());

		}
	}


	private void displayUnitsData() {

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Bescom: Current Data", "<tag>", "Y"),
			new DLabel("Month:  Units", "", "C R: P"),
			new DLabel("", "<joint>", ""));

		for (String m : months) {
			double units;
			if (data.get(m).get("totUNITS").size() == 0)
				units = 0;
			else
				units = data.get(m).get("totUNITS").get(0);
			Design.printBox(20, new DLabel(" "+m+":   "+units, "", "C R: P"));
		}
		Design.printBox(20, new DLabel("", "<base>", ""));
	}


	private void updateUnitsData(List<String> stdin) {

		Iterator<String> itr = stdin.iterator();
		for (String m : months) {

			String inp = itr.next();
			if (inp.length() == 0)		continue;

			double val = Math.round(Double.parseDouble(inp));
			data.get(m).put("totUNITS", new ArrayList<Double>(Arrays.asList(val)));
		}
	}



	public void inputUnitDetails(String type) {

		if (type.startsWith("Update"))
			displayUnitsData();

		List<DLabel> list = new LinkedList<DLabel>();

		list.addAll(Arrays.asList(new DLabel("", "<top>", ""),
			new DLabel("Bescom: "+type, "<tag>", "Y"), new DLabel("", "<joint>", "")));

		for (String m : months)
			list.add(new DLabel(m+":  ", "<read>", "P"));
		list.add(new DLabel("", "<base>", ""));

		/** @info Display the List to get unit-details */
		List<String> stdin =
			Design.printBox(0, list.toArray(new DLabel[0]));

		updateUnitsData(stdin);
		calculateBills(months);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Bills Calculated", "", "C"),
			new DLabel("", "<base>", "")
		);
	}


	private void printBill(String month) {

		/** @info Month HashMap */
		HashMap <String, ArrayList<Double>> monthData = data.get(month);

		/** @info Iterators */
		Iterator<Double> FC = constFC.iterator();
		Iterator<Double> EC = constEC.iterator();

		Iterator<Double> TU = monthData.get("totUNITS").iterator();
		Iterator<Double> itrFC = monthData.get("FC").iterator();
		Iterator<Double> itrEC = monthData.get("EC").iterator();
		Iterator<Double> itrU = monthData.get("UNITS").iterator();
		Iterator<Double> itrFAC = monthData.get("FAC").iterator();
		Iterator<Double> itrTAX = monthData.get("TAX").iterator();
		Iterator<Double> itrNET = monthData.get("NET").iterator();

		List<DLabel> bill = new LinkedList<DLabel>();
		bill.addAll(Arrays.asList(
			new DLabel("", "<top>", ""),
			new DLabel("Bescom Bill: "+month, "<tag>", "Y"),
			new DLabel("", "<joint>", ""),

			new DLabel("\tPersonal Details", "<joint>", "P"),
			new DLabel("  Name:  "+this.name, "", "C C: G"),
			new DLabel("  Phno:  "+this.phno, "", "C C: G"),
			new DLabel("", "<blank>", ""),

			new DLabel("\tConsumption Details", "<joint>", "P"),
			new DLabel(String.format("  Units Consumed:  %11.0f", TU.next()), "", "C C: G"),
			new DLabel("", "<blank>", ""),

			new DLabel("\tFixed Charges", "<joint>", "P"),
			new DLabel(String.format("  1 KW      %.2f, %10.2f", FC.next(), itrFC.next()), "", "C R, C R, G"),
			new DLabel(String.format("  1.25 KW   %.2f, %10.2f", FC.next(), itrFC.next()), "", "C R, C R, G"),
			new DLabel("", "<blank>", ""),

			new DLabel("\tEnergy Charges", "<joint>", "P")
		));

		while (itrU.hasNext()) {
			bill.add( new DLabel(
			String.format("  %4.0f, %10.2f, %10.2f", itrU.next(), EC.next(), itrEC.next()),
			"", "C R, C R, G"));
		}

		TU = monthData.get("totUNITS").iterator();
		bill.addAll(Arrays.asList(new DLabel("", "<blank>", ""),
			new DLabel("\tFAC Charges", "<joint>", "P"),
			new DLabel(String.format("  %4.0f, %10.2f, %10.2f", TU.next(), 0.37, itrFAC.next()), "",  "C R, C R, G"),
			new DLabel("", "<blank>", ""),

			new DLabel("\tTAX Charges", "<joint>", "P"),
			new DLabel(String.format("  TAX:  %22.2f", itrTAX.next()), "",  "C C: G"),
			new DLabel("", "<blank>", ""),

			new DLabel("\tDUE Charges", "<joint>", "P"),
			new DLabel(String.format("  Net Amount Due:   %10s", "Rs. "+Math.round(itrNET.next())), "",  "C C: G"),
			new DLabel("", "<base>", "")
		));


		Design.printBox(0, bill.toArray(new DLabel[0]));
	}


	/** @info This Method accepts a Month(ie, "JAN", "FEB"...or "DEC")
	 * and prints all the Details as u see in ur Electricity-BESCOM bill.
	 * Refer the snapshot**
	 * */
	public void printMonthBill() {
 
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom: Month Bill", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Month:  ", "<input>", "C"),
				new DLabel("", "<base>", "")
			);
		String month = stdin.get(0).trim().toUpperCase();

		if (data.containsKey(month) == false)
			throw new BadTransactionException(
				String.format("<BAD '%s' MONTH!>\nEnter First 3 Letters of a Month.", month));

		printBill(month);
	}


	public void printAllBill() {
		for (String month : months)
			if (data.get(month).get("totUNITS").size() != 0)
				printBill(month);
	}


	public void printBillTable() {

		if (data.size() == 0)
			throw new BadTransactionException("<NULL DATA!>\nPlz Enter Monthly Unit-Details.");

		Bescom.fields(1);

		for (String mon : months) {
			if (data.get(mon).get("totUNITS").size() != 0) {
				HashMap <String, ArrayList<Double>> curMon = data.get(mon);

				String uni = String.format("%.0f", allSum(curMon.get("totUNITS").iterator()));
				uni = putComma(uni);
				String fc = String.format("%.2f", allSum(curMon.get("FC").iterator()));
				fc = putComma(fc);
				String ec = String.format("%.2f", allSum(curMon.get("EC").iterator()));
				ec = putComma(ec);
				String fac = String.format("%.2f", allSum(curMon.get("FAC").iterator()));
				fac = putComma(fac);
				String tax = String.format("%.2f", allSum(curMon.get("TAX").iterator()));
				tax = putComma(tax);
				String net = String.format("%.0f", allSum(curMon.get("NET").iterator()));
				net = putComma(net);

				String emp = String.format("%s %5s %s %10s %s %10s %s %10s %s %10s %s %10s %s %10s %s",
					I, mon, I, uni, I, fc, I, ec, I, fac, I, tax, I, net, I);

				Design.printBox(0, 
					new DLabel(mid, "", "G"+I + " P"),
					new DLabel(emp, "", "G"+I + " P")
				);
			}
		}

		Bescom.fields(0);
		Design.printBox(87, new DLabel("", "<base>", ""));

	}


	private String putComma(String s) {
		int k = s.length();
		if (s.indexOf('.') != -1) {
			if (s.length() > 6) 
				s = s.substring(0, k-6) + "," + s.substring(k-6, k);

		}else
			if (k > 3)
				s = s.substring(0, k-3) + "," + s.substring(k-3, k);

		return s;
	}


	private double allSum(Iterator<Double> itr) {

		double sum = 0.0;
		Iterator<Double> I = itr;
		while (I.hasNext())
			sum += I.next();

		return sum;
	}



	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------

	public Bescom(List<String> stdin) {
		this.updateName(stdin.get(0).trim());
		this.updatePhoneNumber(stdin.get(1).trim());
		this.updatePassword(stdin.get(2).trim(), stdin.get(3).trim());
		presetData();

		//
		List<String> stn = new ArrayList<>(Arrays.asList("10", "20", "30", "40", "50", "60", "70", "80", "190", "250", "450", "600"));
		this.updateUnitsData(stn);
	}


	/** @info Basic Set/Get Methods */
	public void updateName(String name) {
		if (name.matches("^[a-z A-Z]{6,30}$") == false)
			throw new BadAccountException("<BAD NAME!>\nAlphabets only, 5 < Length < 31");
		this.name = name;

	}public void updatePhoneNumber(String phno) {
		if (phno.matches("^[0-9]{10}$") == false)
			throw new BadAccountException("<BAD PHONE NUMBER!>\nValid 10 Digit Number Plz.");
		this.phno = phno;

	}public void updatePassword(String password1, String password2) {
		if (password1.length() < 8)
			throw new BadAccountException("<BAD PASSWORD!>\nLength >= 8");
		if (password1.equals(password2) == false)
			throw new BadAccountException("<BAD PASSWORD!>\nPasswords don't Match.");
		this.password = password1;


	}public String getName() {
		return this.name;

	}public String getPhoneNumber() {
		return this.phno;

	}public String getPassword() {
		return this.password;

	}

	//-----------------------------------------------------------------------------------------------
	/** @info Account Methods */
	public static void createAccount(HashMap<String, Bescom> EAccounts) {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom: Create Account", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Phone No.:  ", "<input>", "C"),
				new DLabel("Enter Password:  ", "<hide>", "C"),
				new DLabel("Confirm Password:  ", "<hide>", "C"),
				new DLabel("", "<base>", "")
			);

		Bescom acc = new Bescom(stdin);
		EAccounts.put(acc.getName(), acc);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Created Bescom Account", "", "C"),
			new DLabel("", "<base>", "")
		);

	}


	/**
	 * @info This method helps to Sign-In an Account */
	public static Bescom signInAccount(HashMap<String, Bescom> accList) {

		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom: Sign In", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Password:  ", "<hide>", "C"),
				new DLabel("", "<base>", "")
			);
		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(String.format("<INVALID ACCESS!>\nAccount: %s NOT Found.", name));

		Bescom acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadAccountException(String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Signed In", "", "C"),
			new DLabel("", "<base>", "")
		);

		return acc;
	}

	public Bescom deleteAccount(HashMap<String, Bescom> accList) {
		List<String> stdin = 
			Design.printBox(40,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom: Delete Account", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("Enter Name:  ", "<input>", "C"),
				new DLabel("Enter Password:  ", "<hide>", "C"),
				new DLabel("", "<base>", "")
			);

		String name = stdin.get(0).trim();
		String pass = stdin.get(1).trim();

		if (!accList.containsKey(name))
			throw new BadAccountException(
				String.format("<INVALID ACCESS!>\nAccount: %s NOT Found.", name));

		Bescom acc = accList.get(name);
		if (!acc.getPassword().equals(pass))
			throw new BadAccountException(
				String.format("<BAD PASSWORD!>\nPassword %s Incorrect.", pass));

		accList.remove(name);

		Design.printBox(0,
			new DLabel("", "<top>", ""),
			new DLabel("Successful", "<tag>", "Y"),
			new DLabel("", "<joint>", ""),
			new DLabel("Deleted Boss Account", "", "C"),
			new DLabel("", "<base>", "")
		);

		return null;

	}


	/**
	 * @info This method helps to Sign-Out an Account */
	public Bescom signOutAccount() {
		return null;
	}
	/** @info Account Methods */
	//-----------------------------------------------------------------------------------------------


	//-----------------------------------------------------------------------------------------------
	/** @info Class Fields for Table-Bill */
	public static String top, mid, base, field;
	public static String m, I, lR, lT, lL, rR, rT, rL, M, P, W;

	static {
		m = Design.m; I = Design.I;
		lR = Design.lR; M = Design.M; rR = Design.rR;
		lT = Design.lT; P = Design.P; rT = Design.rT;
		lL = Design.lL; W = Design.W; rL = Design.rL;

		field  = (I +" MONTH "+I) + (" UNITS      "+I);
		field += (" FixedChrge "+I) + (" EnergChrge "+I) + (" FAC Charge "+I) + (" Tax Charge "+I) + (" Due Amount "+I);

		top 	= (lR + m.repeat(1+5+1)+M);
		mid 	= (lT + m.repeat(1+5+1)+P);
		base 	= (lL + m.repeat(1+5+1)+W);

		for (int i = 0; i < 5; i++) {
			top		+= (m.repeat(1+10+1)+M);
			mid		+= (m.repeat(1+10+1)+P);
			base	+= (m.repeat(1+10+1)+W);
		}

		top		+= (m.repeat(1+10+1) + rR);
		mid		+= (m.repeat(1+10+1) + rT);
		base	+= (m.repeat(1+10+1) + rL);
	}


	public static void fields(int flag) {

		if (flag == 1)
		Design.printBox(87,
			new DLabel("", "<top>", ""),
			new DLabel("Stats", "<tag>", "Y"),
			new DLabel(top, "", "G"),
			new DLabel(field, "", "G"+I + " P")
		);

		else	Design.printBox(87, new DLabel(base, "", "G"));
	}

}




package bescom;

import design.*;
import java.util.*;

//
public class Bescom {

//	static Scanner sc = new Scanner(System.in);
//
//	public LinkedHashMap <String, Double> units;
//	String name, phno;//add ur credentials

	////
	final float fixed_charge_0 = 100,
	fixed_charge_1 = 110;
//	BescomMain ob = new BescomMain();

	final double energy_charge_0 = 4.15,
	energy_charge_1 = 5.60,
	energy_charge_2 = 7.15,
	energy_charge_3 = 8.20;

	double amt1=0,amt2=0,amt3=0,amt4=0,total=0,total_amount=0,total_tax=0;

	static Scanner sc = new Scanner(System.in);

	public LinkedHashMap <String, Double> units;
	public LinkedHashMap <String , LinkedHashMap <String, Double>> storage_units;
	String name = null, phno = null, month;//add ur credentials
	double unit;
	int flag = 2;
	///
	

	Bescom() {
		//a string key maps with a double value 
//		units = new LinkedHashMap<String, Double>();
		units = new LinkedHashMap<String, Double>();
		storage_units = new LinkedHashMap <String , LinkedHashMap <String, Double>>();
		/*set all the keys to 0
		 * ie, keys : "JAN", "FEB", "MAR", "APR"...."DEC"
		 * (ie, first 3 letters...)
		 */
	}

	
	
	public void inputMonthBill() {
		//Scan a String from user and print all the details systematically
		if(name == null)
		{
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("PLEASE LOGIN TO CONTINUE", "", "P"),
				new DLabel("", "<base>", "")
			);
//			ob.showMenu();
		}
		else
		{
			List<String> stdin = 
					Design.printBox(0,
						new DLabel("", "<top>", ""),
						new DLabel("Bescom", "<tag>", "Y"),
						new DLabel("", "<joint>", ""),
						new DLabel("ENTER MONTH:   ", "<input>", "P"),
						new DLabel("ENTER NUMBER OF UNITS CONSUMED:   ", "<input>", "P"),
						new DLabel("", "<base>", "")
					);
			month= (stdin.get(0).trim()) ;
			unit = Integer.parseInt(stdin.get(1).trim());
			units.put(month , unit);
			flag = 1;
			Print(flag);
		}
	}

	/*This Method accepts a Month(ie, "JAN", "FEB"...or "DEC")
	 * and prints all the Details as u see in ur Electricity-BESCOM bill.
	 * Refer the snapshot**
	 * */
	public void printMonthBill() {
		//Scan a String from user and print all the details systematically 
		
	}


	/*This Method accepts user details like Name*, PhNo*...(add ur choices)
	 * and store them in the instance variables
	 * */
	public void getCredentials() {
		//Scan the Credentials from the user 
		//Scan the Credentials from the user
		List<String> stdin = 
				Design.printBox(40,
					new DLabel("", "<top>", ""),
					new DLabel("Bescom", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("ENTER YOUR USERNAME:  ", "<input>", "P"),
					new DLabel("ENTER YOUR PHONE NUMBER:   ", "<input>", "P"),
					new DLabel("", "<base>", "")
				);
		String temp_name = (stdin.get(0).trim()) ;
		String temp_phno = (stdin.get(1).trim());
		//TODO
		//if(storage_units.containsKey(temp_name)){
			//Set<String> keys = storage_units.keySet();

			//for(String iterate : keys)
			//{
				 //units= storage_units.get(temp_name);
				 //name = iterate;

			//}
		//}

		if(temp_name!=name && name!=null){
			storage_units.put(name ,units);
			units.clear();
			name = temp_name;
			phno = temp_phno;
		}

		else
		{
			name = temp_name;
			phno = temp_phno;
		}
	}


	/*This Method accepts a Month(ie, "JAN", "FEB"...or "DEC") from the user,
	 * checks if, the month is present in the hashmap-units and accepts the units
	 * from the user and updates the units
	 * else, print/throw an exception
	 * */
	public void updateUnits() {
		//Scan a String Month,
		//if month is valid then accept units to update
		List<String> stdin = 
				Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Bescom", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("ENTER MONTH TO BE UPDATED : ", "<input>", "P"),
					new DLabel("", "<base>", "")
		);

		String update_month = (stdin.get(0).trim()) ;
		if (units.containsKey(update_month)) {
			update(update_month);
			Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Bescom", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("RECORD FOUND", "", "P"),
					new DLabel("UPDATED SUCCESSFULLY", "", "P"),
					new DLabel("", "<base>", "")
		);
		}
		else
		{
			Design.printBox(0,
					new DLabel("", "<top>", ""),
					new DLabel("Bescom", "<tag>", "Y"),
					new DLabel("", "<joint>", ""),
					new DLabel("RECORD NOT FOUND", "", "R"),
					new DLabel("", "<base>", "")
		);
//			ob.showMenu();
		}
	}

	public void update(String update_month)
	{
		List<String> stdin =
			Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel("ENTER UPDATE UNIS:  ", "<input>", "P"),
				new DLabel("", "<base>", "")
	);
		double update_units = Integer.parseInt(stdin.get(0).trim()) ;
		units.put(update_month, update_units);
	}
	/*This Method displays all the bills in a tabulated form like,
	<Month> <Units> <Tax> <FAC Charges> <Fixed Charges> <Energy Charges> <Net Charges>
	you can add to the above fields, but the above is atleast necessary
	 * */
	public void printAllBills() {
		//iterate thru the LinkedHashMap and display
		Print(0);
	}

	public void PrintDetails(String temp_month , double temp_unit)
	{	

		unit = temp_unit;
		Calculate();
		Design.printBox(0,
				new DLabel("", "<top>", ""),
				new DLabel("Bescom", "<tag>", "Y"),
				new DLabel("", "<joint>", ""),
				new DLabel(
				String.format("NAME: "+name+"     PHONE NUMBER: "+phno), "", "G"),
				new DLabel(
				String.format("MONTH:"+temp_month+"    UNITS:"+temp_unit), "", "G"),
				new DLabel("", "<joint>", ""),
				new DLabel(
				String.format("Current Fuel Cost Adjustment Rate : 0.37"), "", "G"),
				new DLabel(
				String.format("BILL PERIOD : 30 DAYS "), "", "G"),
				new DLabel("", "<joint>", ""),

				new DLabel(
				String.format("       FIXED CHARGES (UNIT RATE AMOUNT )       "), "", "G"),
				new DLabel(
				String.format("1 KW     100     100.00"), "", "G"),
				new DLabel(
				String.format("1.25KW   110     137.50"), "", "G"),
				new DLabel("", "<joint>", ""),

				new DLabel(
				String.format("       ENERGY CHARGES (UNIT RATE AMOUNT)       "), "", "G"),
				new DLabel(
				String.format(Double.toString(amt1)+"   4.15   "+Double.toString(amt1*energy_charge_0)), "", "G"),
				new DLabel(
				String.format(Double.toString(amt2)+"   5.60   "+Double.toString(amt2*energy_charge_1)), "", "G"),
				new DLabel(
				String.format(Double.toString(amt3)+"   7.15   "+Double.toString(amt3*energy_charge_2)), "", "G"),
				new DLabel(
				String.format(Double.toString(amt4)+"   8.20   "+Double.toString(amt4*energy_charge_3)), "", "G"),
				new DLabel("", "<joint>", ""),

				new DLabel(
				String.format("       FAC CHARGES (UNIT RATE AMOUNT)       "), "", "G"),
				new DLabel(
				String.format(unit+"  0.37  "+unit*0.37), "", "G"),
				new DLabel("", "<joint>", ""),
				new DLabel(
				String.format("       ADDITIONAL CHARGES       "), "", "G"),
				new DLabel(
				String.format("TAX       "+total_tax), "", "G"),
				new DLabel(
				String.format("TOTAL       "+total_amount), "", "G"),
				new DLabel("", "<joint>", ""),
				new DLabel(
				String.format("NET AMOUNT : "+(total_tax+total_amount)), "", "G"),
				new DLabel(
				String.format("DUE DATE : 30 DAYS "), "", "G"),
				new DLabel("", "<base>", "")
			);


	}

	
	public void Print(int flag) {

		if (flag==1) {
			PrintDetails(month,unit);

		}else {

			Set<String> keys = units.keySet();
			int count = 0;

			for(String iterate : keys) {
				month = iterate;
				unit = units.get(month);
				//unit = units.get(keys);
				PrintDetails(month,unit);
				count++;

			}if (count==0) {

				Design.printBox(0,
						new DLabel("", "<top>", ""),
						new DLabel("Bescom", "<tag>", "Y"),
						new DLabel("", "<joint>", ""),
						new DLabel(" NO ENTRIES EXIST IN THE DATABASE  ", "", "C"),
//						new DLabel(" ERROR!! \t ERROR!! \t ERROR!!\t  ", "", "C"),
						new DLabel("", "<base>", "")
			);
//				BescomMain ob = new BescomMain();
				Cmd.loadingProcess(500);
//				System.out.println();
//				ob.showMenu();

			}

		}

	}


	public void Calculate()
	{
		if (unit>0 && unit<=50)
		{
			amt1=unit;
		}
		else if(unit>50 && unit<=100)
		{
			amt1 = 50;
			amt2 = 100-unit;
		}
		else if(unit>100 && unit<=200)
		{
			amt1 = 50;
			amt2 = 50;
			amt3 = (200-unit);
			amt4 = 0;
		}

		else if(unit>200)
		{
			amt1 = 50;
			amt2=50 ;
			amt3 = 100 ;
			amt4 = (unit - 200);
		}
		total = amt1 + amt2 + amt3 + amt4;
		total_amount = amt1*energy_charge_0 +amt2*energy_charge_1 +amt3*energy_charge_2+amt4*energy_charge_3;
		total_tax = 0.18*total_amount;
	}

}

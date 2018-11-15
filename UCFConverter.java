import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class UCFConverter {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[0].split("\\.")[0] + ".xdc"));
		Pattern p = Pattern.compile("NET \"([\\w\\[\\]]+)\" LOC = ([A-Z]\\d\\d?);");
		String line;
		String[][] info = new String[50][50];
		HashMap<String,String> pins = new HashMap<>();
		int count = 0;
		
		//put conversions into HashMap
		//switches
		pins.put("G18", "J15");
		pins.put("H18", "L16");
		pins.put("K18", "M13");
		pins.put("K17", "R15");
		pins.put("L14", "R17");
		pins.put("L13", "T18");
		pins.put("N17", "U18");
		pins.put("R17", "R13");
		//LEDs
		pins.put("J14", "H17");
		pins.put("J15", "K15");
		pins.put("K15", "J13");
		pins.put("K14", "N14");
		pins.put("E17", "R18");
		pins.put("P15", "V17");
		pins.put("F4", "U17");
		pins.put("R4", "U16");
		//7 segment display
		pins.put("L18", "T10");
		pins.put("F18", "R10");
		pins.put("D17", "K16");
		pins.put("D16", "K13");
		pins.put("G14", "P15");
		pins.put("J17", "T11");
		pins.put("H14", "L18");
		pins.put("C17", "H15");
		//anode
		pins.put("F17", "J17");
		pins.put("H17", "J18");
		pins.put("C18", "T9 ");
		pins.put("F15", "J14");
		//buttons
		pins.put("B18", "N17");
		pins.put("D18", "M18");
		pins.put("E18", "P17");
		pins.put("H13", "M17");
		//clock signal
		pins.put("B8", "E3 ");
		
		while ((line = reader.readLine()) != null) { //read in names and pins from .ucf file
			Matcher m = p.matcher(line);
			m.find();
			info[count][0] = m.group(1); //names assigned
			
			//pins
			info[count++][1] = m.group(2);
		}
		
		for (int i = 0; i < count; i++) { //write the stuff to the new file of the same name, with .xdc extension instead
			writer.write("set_property -dict { PACKAGE_PIN ");
			writer.write(pins.get(info[i][1]));
			writer.write("   IOSTANDARD LVCMOS33 } [get_ports { ");
			writer.write(info[i][0]);
			writer.write(" }];");
			writer.newLine();
		}
		
		writer.close();
	}

}

package darwin;

import java.io.*;
import java.util.ArrayList;

/**
 * The individual creatures in the world are all representatives of some
 * species class and share certain common characteristics, such as the species
 * name and the program they execute. Rather than copy this information into
 * each creature, this data can be recorded once as part of the description for
 * a species and then each creature can simply include the appropriate species
 * reference as part of its internal data structure.
 * <p>
 * 
 * Note: The instruction addresses start at one, not zero.
 */
public class Species {
	private String name;
	private String color;
	private char speciesChar; // the first character of Species name
	private ArrayList<Instruction> program;

	/**
	 * Create a species for the given fileReader. 
	 */
	public Species(BufferedReader fileReader) {
		try {		
			name = fileReader.readLine();
			speciesChar = name.charAt(0);
			color = fileReader.readLine();
			program = new ArrayList<Instruction>();
			readProgramFromStream(fileReader);
  		} catch (IOException e) {
			System.out.println(
				"Could not read file '"
					+ fileReader
					+ "'");
			System.exit(1);
		}
	}

	/**
	 * Helper method for reading species
	 */
	private void readProgramFromStream(BufferedReader fileReader) {
		while (true) {
			String line;
			try {
				line = fileReader.readLine();
			} catch(IOException exp){
				System.out.println("readLine failed");
				break;
			}
			if (line.equals(""))
				break;
			int opCode = stringToOpcode(line);
			int address = -1;
			switch (opCode) {
				case Instruction.HOP :
				case Instruction.LEFT :
				case Instruction.RIGHT :
					break;
				case Instruction.INFECT :
				case Instruction.IFEMPTY :
				case Instruction.IFWALL :
				case Instruction.IFSAME :
				case Instruction.IFENEMY :
				case Instruction.IFRANDOM :
				case Instruction.GO :
					// find start of address number
					int space = line.indexOf(" ");
					if (space == -1) {
						if (opCode == Instruction.INFECT) {
							address = 1;
						} else {
							System.out.println(
								"missing address on program line "
									+ (program.size() + 1));
							System.exit(1);
						}
					} else {
						// find end of address number
						int end = line.indexOf(" ", space + 1);
						if (end == -1)
							end = line.length();
	
						// convert
						try {
							address =
								Integer.parseInt(
									line.substring(space + 1, end));
						} catch (Exception e) {
							System.out.println(
								"bad address '"
									+ line.substring(space + 1)
									+ "' on program line "
									+ (program.size() + 1));
							System.exit(1);
						}
					}
			}
			program.add(new Instruction(opCode, address));
		}
	}
	
	/**
	 * Convert a string into the opcode number. This method will fail if code is
	 * not a valid opcode name. 
	 * @pre code is the name of an opcode. 
	 * @post returns the opcode for the string.
	 */
	private int stringToOpcode(String code) {
		String lowerCode = code.toLowerCase();
		if (lowerCode.startsWith("hop"))
			return Instruction.HOP;
		if (lowerCode.startsWith("left"))
			return Instruction.LEFT;
		if (lowerCode.startsWith("right"))
			return Instruction.RIGHT;
		if (lowerCode.startsWith("infect"))
			return Instruction.INFECT;
		if (lowerCode.startsWith("go"))
			return Instruction.GO;
		if (lowerCode.startsWith("ifenemy"))
			return Instruction.IFENEMY;
		if (lowerCode.startsWith("ifsame"))
			return Instruction.IFSAME;
		if (lowerCode.startsWith("ifwall"))
			return Instruction.IFWALL;
		if (lowerCode.startsWith("ifempty"))
			return Instruction.IFEMPTY;
		if (lowerCode.startsWith("ifrandom"))
			return Instruction.IFRANDOM;
		System.out.println("Bad Instruction: '" + lowerCode + "'");
		System.exit(1);
		return -1;
	}
	
	/**
	* Return the char for the species
	*/
	public char getSpeciesChar() {
		return speciesChar;
	}

	/**
	 * Return the name of the species.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the color of the species.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Return the number of instructions in the program.
	 */
	public int programSize() {
		return program.size();
	}

	/**
	 * Return an instruction from the program.
	 * @pre 1 <= i <= programSize().
	 * @post returns instruction i of the program.
	 */
	public Instruction programStep(int i) {
		return program.get(i-1);	
	}

	/**
	 * Return a String representation of the program.
	 * 
	 * do not change
	 */
	public String programToString() {
		String s = "";
		for (int i = 1; i <= programSize(); i++) {
			s = s + (i) + ": " + programStep(i) + "\n";
		}
		return s;
	}
}

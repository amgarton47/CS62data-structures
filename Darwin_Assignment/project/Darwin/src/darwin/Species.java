package darwin;

import java.io.*;
import java.util.ArrayList;

/**
 * The individual creatures in the world are all representatives of some species
 * class and share certain common characteristics, such as the species name and
 * the program they execute. Rather than copy this information into each
 * creature, this data can be recorded once as part of the description for a
 * species and then each creature can simply include the appropriate species
 * reference as part of its internal data structure.
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
		program = new ArrayList<Instruction>();

		try {
			name = fileReader.readLine();
			color = fileReader.readLine();
			speciesChar = name.charAt(0);

			String line = fileReader.readLine();

			while (!line.equals("")) {

				String[] parsedLine = line.split(" ");
				String instr = parsedLine[0];
				int address = 0;
				int opcode = -1;

				if (parsedLine.length > 1) {
					address = Integer.parseInt(parsedLine[1]);
				}

				switch (instr) {
				case "hop":
					opcode = Instruction.HOP;
					break;
				case "left":
					opcode = Instruction.LEFT;
					break;
				case "right":
					opcode = Instruction.RIGHT;
					break;
				case "infect":
					opcode = Instruction.INFECT;
					break;
				case "ifempty":
					opcode = Instruction.IFEMPTY;
					break;
				case "ifwall":
					opcode = Instruction.IFWALL;
					break;
				case "ifsame":
					opcode = Instruction.IFSAME;
					break;
				case "ifenemy":
					opcode = Instruction.IFENEMY;
					break;
				case "ifrandom":
					opcode = Instruction.IFRANDOM;
					break;
				case "go":
					opcode = Instruction.GO;
					break;
				}

				Instruction i = new Instruction(opcode, address);
				program.add(i);

				line = fileReader.readLine();
			}

		} catch (IOException e) {
			System.out.println("Could not read file '" + fileReader + "'");
			System.exit(1);
		}
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
	 * 
	 * @pre 1 <= i <= programSize().
	 * @post returns instruction i of the program.
	 */
	public Instruction programStep(int i) {
		return program.get(i - 1);
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

	public static void main(String[] args) {
		try {
			BufferedReader fr = new BufferedReader(new FileReader("../Creatures/Flytrap.txt"));

			Species s1 = new Species(fr);
			System.out.println(s1.programToString());
		} catch (Exception e) {
			System.out.println("file read error");
		}

	}

}

import structure5.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The individual creatures in the world are all representatives of some
 * species class and share certain common characteristics, such as the species
 * name and the program they execute. Rather than copy this information into
 * each creature, this data can be recorded once as part of the description for
 * a species and then each creature can simply include the appropriate species
 * pointer as part of its internal data structure.
 * <p>
 *
 * To encapsulate all of the operations operating on a species within this
 * abstraction, this provides a constructor that will read a file containing
 * the name of the creature and its program, as described in the earlier part
 * of this assignment. To make the folder structure more manageable, the
 * species files for each creature are stored in a subfolder named 'creatures'.
 * This, creating the Species for the file Hop.txt will causes the program to
 * read in "creatures/Hop.txt".
 *
 * <p>
 *
 * Note: The instruction addresses start at one, not zero.
 */
public class Species {

  protected String filename;
	protected String name;
	protected String color;
	protected ArrayList<Instruction> program;

	protected void readProgramFromStream(ReadStream r) {
		while (true) {
			String line = r.readLine();
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
                  System.out.println(line);
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
									+ "' in program '"
                  + this.filename
                  + "' on line "
									+ (program.size() + 1));
							System.exit(1);
						}
					}
			}
			program.add(new Instruction(opCode, address));
		}
	}

	/**
	 * Convert a string into the opcode number. This method will fail if s is
	 * not a valid opcode name. @pre s is the name of an opcode. @post returns
	 * the opcode for the string.
	 */
	protected int stringToOpcode(String s) {
		s = s.toLowerCase();
		if (s.startsWith("hop"))
			return Instruction.HOP;
		if (s.startsWith("left"))
			return Instruction.LEFT;
		if (s.startsWith("right"))
			return Instruction.RIGHT;
		if (s.startsWith("infect"))
			return Instruction.INFECT;
		if (s.startsWith("go"))
			return Instruction.GO;
		if (s.startsWith("ifenemy"))
			return Instruction.IFENEMY;
		if (s.startsWith("ifsame"))
			return Instruction.IFSAME;
		if (s.startsWith("ifwall"))
			return Instruction.IFWALL;
		if (s.startsWith("ifempty"))
			return Instruction.IFEMPTY;
		if (s.startsWith("ifrandom"))
			return Instruction.IFRANDOM;
    throw new RuntimeException("Bad instruction: '" + s + "'");
		//System.exit(1);
    // Default:
		//return Instruction.HOP;
	}

	/**
	 * Create a species for the given file. @pre fileName exists in the creatures
	 * subdirectory.
	 */
	public Species(String fileName) {
		try {
      this.filename = "creatures" + java.io.File.separator + fileName;
			ReadStream r = new ReadStream(new FileInputStream(this.filename));
			name = r.readLine();
			color = r.readLine();
			program = new ArrayList<Instruction>();
			readProgramFromStream(r);
		} catch (Exception e) {
			System.out.println(
				"Could not read file '"
					+ "creatures"
					+ java.io.File.separator
					+ fileName
					+ "'");
      System.out.println(e);
			System.exit(1);
		}

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
	 * Return an instruction from the program. @pre 1 <= i <= programSize().
	 * @post returns instruction i of the program.
	 */
	public Instruction programStep(int i) {
    if (i < 1 || i > programSize()) {
      throw new RuntimeException(
          "Bad address " + i + "/" + programSize() + " in file '" + this.filename + "'"
      );
    }
		Assert.pre(i >= 1 && i <= programSize(), "Bad address " + i + "/" + programSize());
		return program.get(i - 1);
	}

	public String toString() {
		return name;
	}

	/**
	 * Return a String representation of the program.
	 */
	public String programToString() {
		String s = "";
		for (int i = 1; i <= programSize(); i++) {
			s = s + (i) + ": " + programStep(i) + "\n";
		}
		return s;
	}

	public static void main(String s[]) {
		Species sp = new Species("Hop.txt");
		System.out.println(sp.getName());
		System.out.println(sp.getColor());
		System.out.println(sp.programToString());
		System.out.println("first step should be hop: " + sp.programStep(1));
		System.out.println("second step should be go 1: " + sp.programStep(2));
	}

}

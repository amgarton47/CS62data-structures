package sortCompare;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unittest adapted from Sean Zhu for CS062 at Pomona College.
 *
 * @author Sean Zhu
 * @author Andi Chen
 * @author Ross A. Wollman
 *
 */
public class Autograder {
	public static final File TESTING_DIR = new File(System.getProperty("testing")); // new File(getAbsoluteFile(File.seperator, System.getProperty("testing")));
	public static final File DEP_DIR = new File(TESTING_DIR, "dependencies"); // dependency directory

	public static final File DREAM_FILE = new File(DEP_DIR, "Ihaveadream.txt");
	public static final File DREAM_FILE_SORTED = new File(DEP_DIR, "data.sorted");

	public static final File SINGLE_FILE_INPUT = new File(DEP_DIR, "presorted01.txt");
	public static final File SINGLE_FILE_EXPECTED = new File(DEP_DIR, "presorted01_expected.txt");

	public static final File MFILES_TWO_INPUT_A = new File(DEP_DIR, "presortedA02.txt");
	public static final File MFILES_TWO_INPUT_B = new File(DEP_DIR, "presortedB02.txt");
	public static final File MFILES_TWO_INPUT_EXPECTED = new File(DEP_DIR, "presorted02_expected.txt");

	public static final File MFILES_ODD_INPUT_A = new File(DEP_DIR, "presortedA03.txt");
	public static final File MFILES_ODD_INPUT_B = new File(DEP_DIR, "presortedB03.txt");
	public static final File MFILES_ODD_INPUT_C = new File(DEP_DIR, "presortedC03.txt");
	public static final File MFILES_ODD_INPUT_EXPECTED = new File(DEP_DIR, "presorted03_expected.txt");

	public static final File MERGE_AB_INPUT_A = new File(DEP_DIR, "presortedA04.txt");
	public static final File MERGE_AB_INPUT_B = new File(DEP_DIR, "presortedB04.txt");
	public static final File MERGE_AB_INPUT_EXPECTED = new File(DEP_DIR, "presorted04_expected.txt");

	public static final File MERGE_BA_INPUT_A = new File(DEP_DIR, "presortedA05.txt");
	public static final File MERGE_BA_INPUT_B = new File(DEP_DIR, "presortedB05.txt");
	public static final File MERGE_BA_INPUT_EXPECTED = new File(DEP_DIR, "presorted05_expected.txt");

	private static final int TEST_MEMORY_DATA_LIMIT = 3;
	private File testingDir, outputFile;
	private ArrayList<File> files;
	private OnDiskSort diskSorter;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("setting up.....");
		testingDir = new File(TESTING_DIR, "_test_run");
		outputFile = new File(testingDir, "data.sorted");
		files = new ArrayList<File>();
		// create a clean testing directory before each test
		if (testingDir.exists())
			deleteDir(testingDir);

		testingDir.mkdir();

		diskSorter = new OnDiskSort(TEST_MEMORY_DATA_LIMIT, testingDir, new MergeSort<String>());
	}

	/**
	 * Evaluates whether all helper methods are successfully linked together to
	 * sort a large input file into an out file, data.sorted
	 *
	 * @throws IOException
	 */
	@Test
	public void sort_fullProcess_Test() throws IOException {
		WordScanner scanner = new WordScanner(DREAM_FILE);
		diskSorter.sort(scanner, outputFile);
		String expected = fileToString(DREAM_FILE_SORTED);
		String actual = fileToString(outputFile);
		assertEquals(expected, actual, "The input file should be sorted in the outfile.");
	}

	/**
	 * Checks to see that the working directory is cleaned up after sorting.
	 *
	 * @throws IOException
	 */
	@Test
	public void sort_cleansUpAfterItself_Test() throws IOException {
		WordScanner scanner = new WordScanner(DREAM_FILE);
		diskSorter.sort(scanner, outputFile);
		String expected = fileToString(DREAM_FILE_SORTED);
		String actual = fileToString(outputFile);

		assertEquals(1, testingDir.list().length, "Working directory should remove temporary files after sorting.");
	}

	/**
	 * Verifies that a student can merge an array of files containing a single
	 * file.
	 *
	 * @throws IOException
	 */
	@Test
	public void mergeFiles_SingleFile_Test() throws IOException {
		files.add(SINGLE_FILE_INPUT);
		diskSorter.mergeFiles(files, outputFile);

		String actual = fileToString(outputFile);
		String expected = fileToString(SINGLE_FILE_EXPECTED);
		assertEquals(expected, actual, "mergeFiles with 1 presorted file should yield the presorted file.");
	}

	/**
	 * Verifies merge files can handle an array of two files.
	 *
	 * @throws IOException
	 */
	@Test
	public void mergeFiles_TwoFile_Test() throws IOException {
		files.add(MFILES_TWO_INPUT_A);
		files.add(MFILES_TWO_INPUT_B);
		diskSorter.mergeFiles(files, outputFile);

		String expected = fileToString(MFILES_TWO_INPUT_EXPECTED);
		String actual = fileToString(outputFile);

		assertEquals(expected, actual, "mergeFiles should merge an array of two files into one file.");
	}

	/**
	 * Verifies mergeFiles can handle an array of an odd number of files.
	 *
	 * @throws IOException
	 */
	@Test
	public void mergeFiles_OddFile_Test() throws IOException {
		files.add(MFILES_ODD_INPUT_A);
		files.add(MFILES_ODD_INPUT_B);
		files.add(MFILES_ODD_INPUT_C);
		diskSorter.mergeFiles(files, outputFile);

		String expected = fileToString(MFILES_ODD_INPUT_EXPECTED);
		String actual = fileToString(outputFile);

		assertEquals(expected, actual, "mergeFiles should merge an array of 3 files into one file.");
	}

	/**
	 * Verifies that merge handles the case when file A finishes before file B.
	 * @throws IOException
	 */
	@Test
	public void merge_AFinishThenB_Test() throws IOException {
		diskSorter.merge(MERGE_AB_INPUT_A, MERGE_AB_INPUT_B, outputFile);

		String expected = fileToString(MERGE_AB_INPUT_EXPECTED);
		String actual = fileToString(outputFile);

		assertEquals(expected, actual, "merge should merge an two files such that A finishes before B.");

	}

	/**
	 * Verifies that merge handles the case when file B finishes before file A.
	 * @throws IOException
	 */
	@Test
	public void merge_BFinishThenA_Test() throws IOException {
		diskSorter.merge(MERGE_BA_INPUT_A, MERGE_BA_INPUT_B, outputFile);

		String expected = fileToString(MERGE_BA_INPUT_EXPECTED);
		String actual = fileToString(outputFile);

		assertEquals(expected, actual, "merge should merge an two files such that B finishes before A.");
	}

	/**
	 * Deletes all files in a directory.
	 *
	 * @pre dir must only contain files -- no directories
	 * @param dir
	 *            Directory with files to be deleted.
	 * @post dir is empty
	 */
	private void clearDirectory(File dir) {
		for (File f : dir.listFiles()) {
			f.delete();
		}
	}

	/**
	 * Clear out the contents of a directory and delete the directory
	 *
	 * @param dir
	 */
	private void deleteDir(File dir) {
		clearDirectory(dir);
		if (dir.exists())
			dir.delete();
	}

	/**
	 * Read file into a string.
	 *
	 * @param file
	 *            text file
	 * @return String contents of the file
	 * @throws IOException
	 */
	private String fileToString(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String contents = "";

		String line = br.readLine();

		while (line != null) {
			contents += line + "\n";
			line = br.readLine();
		}
		return contents;
	}

	public static File getAbsoluteFile(File root, String path) {
	  File file = new File(path);
	  if (file.isAbsolute())
	    return file;

	  if (root == null)
	    return null;

	  return new File(root, path);
	}
}

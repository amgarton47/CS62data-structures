package sortCompare;

import java.io.*;
import java.util.*;

/**
 * Sorts the data on-disk, by sorting the data in small chunks and then merging
 * the data into one larger chunk
 * 
 */
public class OnDiskSort {
	private static final String TEMP_FILE_ENDING = ".tempSorted";
	private static final String TEMP_FILE = "temp" + TEMP_FILE_ENDING;

	private int maxSize;
	private File workingDirectory;
	private Sorter<String> sorter;

	public static void main(String[] args) {
		MergeSort<String> sorter = new MergeSort<String>();
		OnDiskSort diskSorter = new OnDiskSort(1, new File("sorting_run"), sorter);

		WordScanner scanner = new WordScanner(new File("sorting_run//Ihaveadream.txt"));

		System.out.println("running");
		diskSorter.sort(scanner, new File("sorting_run//data.sorted"));
		System.out.println("done");
	}

	/**
	 * Creates a new sorter for sorting string data on disk. The sorter operates by
	 * reading in maxSize worth of data elements (in this case, Strings) and then
	 * sorts them using the provided sorter. It does this chunk by chunk for all of
	 * the data, at each stage writing the sorted data to temporary files in
	 * workingDirectory. Finally, the sorted files are merged together (in pairs)
	 * until there is a single sorted file. The final output of this sorting should
	 * be in outputFile
	 * 
	 * @param maxSize
	 *            the maximum number of items to put in a chunk
	 * @param workingDirectory
	 *            the directory where any temporary files created during sorting
	 *            should be placed
	 * @param sorter
	 *            the sorter to use to sort the chunks in memory
	 */
	public OnDiskSort(int maxSize, File workingDirectory, Sorter<String> sorter) {
		this.maxSize = maxSize;
		this.workingDirectory = workingDirectory;
		this.sorter = sorter;

		// create directory if it doesn't exist
		if (!workingDirectory.exists()) {
			workingDirectory.mkdir();
		}

		// clearOutDirectory(workingDirectory, TEMP_FILE_ENDING);
	}

	/**
	 * Remove all files that that end with fileEnding in workingDirectory
	 * 
	 * If you name all of your temporary files with the same file ending, for
	 * example ".temp_sorted" then it's easy to clean them up using this method
	 * 
	 * @param workingDirectory
	 *            the directory to clear
	 * @param fileEnding
	 *            clear only those files with fileEnding
	 */
	private void clearOutDirectory(File workingDirectory, String fileEnding) {
		for (File file : workingDirectory.listFiles()) {
			if (file.getName().endsWith(fileEnding)) {
				file.delete();
			}
		}
	}

	/**
	 * Generates the full filename based on the working directory and the temporary
	 * file ending
	 * 
	 * @param fileStart
	 *            the prefix of the filename
	 * @return a new file
	 */
	private File getWorkingDirFilename(String fileStart) {
		return new File(workingDirectory.getAbsolutePath() + File.separator + fileStart + TEMP_FILE_ENDING);
	}

	/**
	 * Write the data in dataToWrite to outfile one String per line
	 * 
	 * @param outfile
	 *            the output file
	 * @param dataToWrite
	 *            the data to write out
	 */
	private void writeToDisk(File outfile, ArrayList<String> dataToWrite) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(outfile));

			for (String s : dataToWrite) {
				out.println(s);
			}

			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

	/**
	 * Sort the data in dataReader using an on-disk version of sorting
	 * 
	 * @param dataReader
	 *            an Iterator for the data to be sorted
	 * @param outputFile
	 *            the destination for the final sorted data
	 */
	public void sort(Iterator<String> dataReader, File outputFile) {
		int fileNum = 0;
		ArrayList<String> sortMe = new ArrayList<String>(maxSize);
		ArrayList<File> writtenFiles = new ArrayList<File>();

		while (dataReader.hasNext()) {
			sortMe.add(dataReader.next());

			if (sortMe.size() >= maxSize) {
				sorter.sort(sortMe);
				File outfile = getWorkingDirFilename(Integer.toString(fileNum));
				writeToDisk(outfile, sortMe);
				writtenFiles.add(outfile);
				sortMe = new ArrayList<String>(maxSize);
				fileNum++;
			}
		}

		// if there's still some data left, write it out to a new file
		if (sortMe.size() > 0) {
			sorter.sort(sortMe);
			File outfile = getWorkingDirFilename(Integer.toString(fileNum));
			writeToDisk(outfile, sortMe);
			writtenFiles.add(outfile);
			sortMe = new ArrayList<String>(maxSize);
		}

		System.gc();

		Stopwatch timer1 = new Stopwatch();
		mergeFiles(writtenFiles, outputFile);
		System.out.println("One way merge "+ timer1.elapsedTime()); 

		System.gc();
		Stopwatch timer2 = new Stopwatch();
		mergeFilesLinear(writtenFiles, outputFile);
		System.out.println("Two way merge"+ timer2.elapsedTime()); 

		clearOutDirectory(workingDirectory, TEMP_FILE_ENDING);
	}

	/**
	 * Copy data from fromFile to toFile
	 * 
	 * @param fromFile
	 *            the file to be copied from
	 * @param toFile
	 *            the destination file to be copied to
	 */
	private void copyFile(File fromFile, File toFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fromFile));
			PrintWriter out = new PrintWriter(new FileOutputStream(toFile));

			String line = in.readLine();

			while (line != null) {
				out.println(line);
				line = in.readLine();
			}

			out.close();
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Merges all the Files in sortedFiles into one sorted file, whose destination
	 * is outputFile.
	 * 
	 * @pre All of the files in sortedFiles contained data that is sorted
	 * @param sortedFiles
	 *            a list of files containing sorted data
	 * @param outputFile
	 *            the destination file for the final sorted data
	 */
	protected void mergeFiles(ArrayList<File> sortedFiles, File outputFile) {
		// the easiest way to do this is to have a temporary file that contains all of
		// your merged data so far and then just merge in one more file

		// to start with, we'll just copy the first sortedFile to the temp file and then
		// merge in the remaining files linearly

		if (sortedFiles.size() == 1) {
			copyFile(sortedFiles.get(0), outputFile);
		} else {
			merge(sortedFiles.get(0), sortedFiles.get(1), outputFile);

			File tempFile = new File(workingDirectory.getAbsolutePath() + File.separator + TEMP_FILE);
			copyFile(outputFile, tempFile);

			for (int i = 2; i < sortedFiles.size(); i++) {
				merge(tempFile, sortedFiles.get(i), outputFile);
				copyFile(outputFile, tempFile);
			}
		}
	}

	/**
	 * Extra credit Merges the sortedFiles into outputFile.
	 * 
	 * @pre All of the files in sortedFiles contained data that are sorted
	 * @param sortedFiles
	 *            a list of files containing sorted data
	 * @param outputFile
	 *            the destination file for the final sorted data
	 */
	protected void mergeFilesLinear(ArrayList<File> sortedFiles, File outputFile) {
		File temporary = new File(workingDirectory + File.separator + "linear.tempSorted");
		
        for (int len = 1; len < sortedFiles.size(); len *= 2) {
            for (int lo = 0; lo < sortedFiles.size()-len; lo += len+len) {
                merge(sortedFiles.get(lo), sortedFiles.get(lo+len), temporary);
                copyFile(temporary, sortedFiles.get(lo));
            }
        }
		copyFile(temporary, outputFile);
	}

	/**
	 * Given two files containing sorted strings, one string per line, merge them
	 * into one sorted file
	 * 
	 * @param file1
	 *            file containing sorted strings, one per line
	 * @param file2
	 *            file containing sorted strings, one per line
	 * @param outFile
	 *            destination file for the results of merging the two files
	 */
	protected void merge(File file1, File file2, File outFile) {
		try {
			BufferedReader in1 = new BufferedReader(new FileReader(file1));
			BufferedReader in2 = new BufferedReader(new FileReader(file2));
			PrintWriter out = new PrintWriter(new FileOutputStream(outFile));

			String from1 = in1.readLine();
			String from2 = in2.readLine();

			while (from1 != null && from2 != null) {
				if (from1.compareTo(from2) < 0) {
					out.println(from1);
					from1 = in1.readLine();
				} else {
					out.println(from2);
					from2 = in2.readLine();
				}
			}

			while (from1 != null) {
				out.println(from1);
				from1 = in1.readLine();
			}

			while (from2 != null) {
				out.println(from2);
				from2 = in2.readLine();
			}

			out.close();
			in1.close();
			in2.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

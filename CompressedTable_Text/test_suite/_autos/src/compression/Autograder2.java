package compression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;


public class TestCompression {
	
	protected CompressedTable<String> table;
	
	int rows = 5;
	int cols = 5;
	String defaultValue = "r";
	String newValue1 = "g";
	String newValue2 = "k";


	@Before
	public void setUp() throws Exception {
		table = new CompressedTable<String>(rows, cols, defaultValue);
	}

	@Test
	public void testInitialOutput() {
		String output = "rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testInitialCurDLL() {
		
		//Test that the underlying CurDLL contains only a single node
		assertEquals(table.tableInfo.size(), 1, "Initially, CurDLL should only have 1 node but instead had " + table.tableInfo.size());
		
		//Test that the single node contains the defaultValue at position (0, 0)
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "Initially, CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "Initially, CurDLL should have had a node with "+ defaultValue +" default value but instead was " + table.tableInfo.get(0).theValue);
	}
	
	@Test
	public void testChangeToFirstNode() {
		table.updateInfo(0, 0, newValue1);
		
		//Test that the underlying CurDLL has two nodes (0,0, newValue1) and (0,1, defaultValue)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains newValue1
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, newValue1, "First node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains defaultValue
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, defaultValue, "Second node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);
	
		//Test output
		String output = "grrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testSecondChangeToFirstNode() {
		table.updateInfo(0, 0, newValue1);
		table.updateInfo(0, 0, newValue2);
		
		//Test that the underlying CurDLL has two nodes (0,0, newValue2) and (0,1, defaultValue)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains newValue2
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, newValue2, "First node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains defaultValue
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, defaultValue, "Second node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "krrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testFutileChange() {
		table.updateInfo(0, 0, newValue1);
		table.updateInfo(0, 0, newValue1);
		
		//Test that the underlying CurDLL has two nodes (0,0, newValue1) and (0,1, defaultValue)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains newValue2
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, newValue1, "First node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains defaultValue
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, defaultValue, "Second node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "grrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testChangeLastNode(){
		table.updateInfo(rows-1, cols-1, newValue1);
		
		//Test that the underlying CurDLL has two nodes (0,0, defaultValue) and (rows-1, cols-1, newValue1)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(rows-1, cols-1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrg\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testAddIntermediateNode(){
		table.updateInfo(0, 1, newValue1);
		
		//Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1, newValue1) and (0, 2, defaultValue)
		assertEquals(table.tableInfo.size(), 3, "CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 2, rows, cols), "CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);
		//Test output
		String output = "rgrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testChangeIntermediateNode(){
		table.updateInfo(0, 1, newValue1);
		//change the newly added node to a new value
		table.updateInfo(0, 1, newValue2);
		
		//Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1, newValue2) and (0, 2, defaultValue)
		assertEquals(table.tableInfo.size(), 3, "CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue2
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue2, "Second node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 2, rows, cols), "CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rkrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	
	@Test
	public void testSequentialUpdates() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 2, newValue2);

		//Test that the underlying CurDLL has four nodes (0,0, defaultValue), (0, 1, newValue1), (0,2, newValue2) and (0, 3, defaultValue)
		assertEquals(table.tableInfo.size(), 4, "CurDLL should have had 4 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains newValue2
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 2, rows, cols), "CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, newValue2, "Third node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the fourth node contains defaultValue
		assertEquals(table.tableInfo.get(3).theKey, new RowOrderedPosn(0, 3, rows, cols), "CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(3).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		
		//Test output
		String output = "rgkrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testMultipleChanges() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 3, newValue1);
		table.updateInfo(0, 4, newValue2);
		table.updateInfo(0, 2, defaultValue);
		
		assertEquals(table.tableInfo.size(), 6, "CurDLL should have had 6 nodes but instead had " + table.tableInfo.size());

		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 2, rows, cols), "CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the fourth node contains newValue1
		assertEquals(table.tableInfo.get(3).theKey, new RowOrderedPosn(0, 3, rows, cols), "CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(3).theValue, newValue1, "Fourth node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the fifth node contains newValue2
		assertEquals(table.tableInfo.get(4).theKey, new RowOrderedPosn(0,4, rows, cols), "CurDLL should have had a node for (0,4) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(4).theValue, newValue2, "Fifth node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the sixth node contains defaultValue
		assertEquals(table.tableInfo.get(5).theKey, new RowOrderedPosn(1,0, rows, cols), "CurDLL should have had a node for (1,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(5).theValue, defaultValue, "Sixth node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rgrgk\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);


	}
	
	@Test
	public void testSimpleContraction() {
		table.updateInfo(0, 1, newValue1);
		//change back to defaultValue
		table.updateInfo(0, 1, defaultValue);

		//Test that the underlying CurDLL contains only a single node
		assertEquals(table.tableInfo.size(), 1, "After contraction, CurDLL should only have 1 node but instead had " + table.tableInfo.size());
		
		//Test that the single node contains the defaultValue at position (0, 0)
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "After contraction, CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "After contraction, CurDLL should have had a node with "+ defaultValue +" default value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testIntermediateContraction() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);

		//Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1, newValue1) and (0, 3, defaultValue)
		assertEquals(table.tableInfo.size(), 3, "CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 3, rows, cols), "CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rggrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testSequentialContraction() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 2, defaultValue);

		//Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1, newValue1) and (0, 2, defaultValue)
		assertEquals(table.tableInfo.size(), 3, "CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 1, rows, cols), "CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(0, 2, rows, cols), "CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rgrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testReadMeExample() {
		table.updateInfo(0, 3, newValue1);
		table.updateInfo(0, 4, newValue1);
		table.updateInfo(2, 1, newValue2);
		table.updateInfo(3, 3, newValue1);
		table.updateInfo(4, 2, newValue2);
		
		//Test that the underlying CurDLL has 9 nodes:
//		<Association: Position: (0,0)=r>
//		<Association: Position: (0,3)=g>
//		<Association: Position: (1,0)=r>
//		<Association: Position: (2,1)=k>
//		<Association: Position: (2,2)=r>
//		<Association: Position: (3,3)=g>
//		<Association: Position: (3,4)=r>
//		<Association: Position: (4,2)=k>
//		<Association: Position: (4,3)=r>
		assertEquals(table.tableInfo.size(), 9, "CurDLL should have had 9 nodes but instead had " + table.tableInfo.size());

		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(0, 3, rows, cols), "CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the third node contains defaultValue
		assertEquals(table.tableInfo.get(2).theKey, new RowOrderedPosn(1, 0, rows, cols), "CurDLL should have had a node for (1,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(2).theValue, defaultValue, "Third node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the fourth node contains newValue2
		assertEquals(table.tableInfo.get(3).theKey, new RowOrderedPosn(2, 1, rows, cols), "CurDLL should have had a node for (2,1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(3).theValue, newValue2, "Fourth node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the fifth node contains defaultValue
		assertEquals(table.tableInfo.get(4).theKey, new RowOrderedPosn(2, 2, rows, cols), "CurDLL should have had a node for (2,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(4).theValue, defaultValue, "Fifth node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the sixth node contains newValue1
		assertEquals(table.tableInfo.get(5).theKey, new RowOrderedPosn(3, 3, rows, cols), "CurDLL should have had a node for (3,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(5).theValue, newValue1, "Sixth node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the seventh node contains defaultValue
		assertEquals(table.tableInfo.get(6).theKey, new RowOrderedPosn(3, 4, rows, cols), "CurDLL should have had a node for (3,4) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(6).theValue, defaultValue, "Seventh node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the eighth node contains newValue2
		assertEquals(table.tableInfo.get(7).theKey, new RowOrderedPosn(4, 2, rows, cols), "CurDLL should have had a node for (4,2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(7).theValue, newValue2, "Eighth node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		
		//Test that the ninth node contains defaultValue
		assertEquals(table.tableInfo.get(8).theKey, new RowOrderedPosn(4, 3, rows, cols), "CurDLL should have had a node for (4,3) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(8).theValue, defaultValue, "Ninth node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test output
		String output = "rrrgg\n" + 
				"rrrrr\n" + 
				"rkrrr\n" + 
				"rrrgr\n" + 
				"rrkrr\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}
	
	@Test
	public void testContractionEnd() {
		table.updateInfo(rows-1, cols-1, newValue1);
		table.updateInfo(rows-1, cols-2, newValue1);

		//Test that the underlying CurDLL has two nodes (0,0, defaultValue) and (rows-1, cols-2, newValue1)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(rows-1, cols-2, rows, cols), "CurDLL should have had a node for (rows-1, cols-2) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue1, "Second node should have had "+ newValue1 +" value but instead was " + table.tableInfo.get(0).theValue);

		
		//Test output
		String output = "rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrgg\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
	
	@Test
	public void testUpdateAtEnd() {
		table.updateInfo(rows-1, cols-1, newValue1);
		table.updateInfo(rows-1, cols-1, newValue2);

		//Test that the underlying CurDLL has two nodes (0,0, defaultValue) and (rows-1, cols-1, newValue1)
		assertEquals(table.tableInfo.size(), 2, "CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());
		
		//Test that the first node contains defaultValue
		assertEquals(table.tableInfo.get(0).theKey, new RowOrderedPosn(0, 0, rows, cols), "CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(0).theValue, defaultValue, "First node should have had "+ defaultValue +" value but instead was " + table.tableInfo.get(0).theValue);

		//Test that the second node contains newValue1
		assertEquals(table.tableInfo.get(1).theKey, new RowOrderedPosn(rows-1, cols-1, rows, cols), "CurDLL should have had a node for (rows-1, cols-1) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(table.tableInfo.get(1).theValue, newValue2, "Second node should have had "+ newValue2 +" value but instead was " + table.tableInfo.get(0).theValue);

		
		//Test output
		String output = "rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrr\n" + 
				"rrrrk\n";
		String entireTable = table.entireTable();
		assertEquals(entireTable, output, "entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

}

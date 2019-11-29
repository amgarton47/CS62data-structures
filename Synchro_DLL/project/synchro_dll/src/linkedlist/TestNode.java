package linkedlist;

/**
 * a TestNode is a DLL_Node with a few extra fields to enable
 * list integrity audits.
 */
public class TestNode extends DLL_Node {

		public long value;		// node-unique ID value
		public boolean in_list;	// node should be in list
		public boolean found;	// node has been found in list
		
		public TestNode(long value) {
			this.value = value;
			this.in_list = false;
			this.found = false;
		}
}

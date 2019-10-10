package calc;

import static org.junit.Assert.*;
import javax.swing.JTextField;
import org.junit.Test;
import org.junit.Before;

public class StateTest {
    private JTextField display; // dummy display for testing
    private State st; // sample state object

    // set up state with 2 on top of stack and 9 below it.
    @Before
    public void setUp() {
        display = new JTextField("0");
        st = new State(display);
        st.addDigit(9);
        st.enter();
        st.addDigit(2);
        st.enter();
    }

    // check that if 2 on stack and add digits 7, 5, and then
    // hit enter that display will show "75"
    @Test
    public void testAddDigit() {
        assertEquals("2", display.getText());
        st.addDigit(7);
        assertEquals("7", display.getText());
        st.addDigit(5);
        assertEquals("75", display.getText());
        st.enter();
        assertEquals("75", display.getText());
    }

    // check that if stack has 4, 2, and 9, then executing plus twice
    // gives "6" and then "15".
    @Test
    public void testPlus() {
        st.addDigit(4);
        st.enter();
        st.doOp('+');
        assertEquals("6", display.getText());
        st.doOp('+');
        assertEquals("15", display.getText());
    }

    @Test
    public void testMult() {
        fail("Not yet implemented");
    }

    @Test
    public void testMinus() {
        fail("Not yet implemented");
    }

    @Test
    public void testDiv() {
        fail("Not yet implemented");
    }

    @Test
    public void testEnter() {
        fail("Not yet implemented");
    }

    @Test
    public void testClear() {
        fail("Not yet implemented");
    }

    // check if 2 and 9 on stack that if pop once get "9" in display
    // and if pop again have "0" on stack and remains if pop once more.
    @Test
    public void testPop() {
        st.pop();
        assertEquals("9", display.getText());
        st.pop();
        assertEquals("0", display.getText());
        st.pop();
        assertEquals("0", display.getText());
    }

    // Method exchange should swap top two values so after
    // exchange have 9 on top with 2 underneath.
    @Test
    public void testExchange() {
        st.exchange();
        assertEquals("9", display.getText());
        st.pop();
        assertEquals("2", display.getText());
    }

}

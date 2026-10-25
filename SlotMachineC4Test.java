import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineC4Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    @Test
    public void shouldNotDeleteRebelWheel() {
        machine.addWheel("rebel", 1);
        machine.delWheel(1);

        assertFalse(machine.ok()); 
    }

    @Test
    public void shouldNotSwapRebelWheel() {
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 2);

        machine.swap(1, 2);

        assertFalse(machine.ok()); 
    }

    @Test
    public void shouldCopyStateInLeftyWheel() {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "blue"); 
        machine.spin(2); 
        assertEquals("blue", machine.configuration()[1]);
    }
}

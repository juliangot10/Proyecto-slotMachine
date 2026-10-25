import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineCC4Test {
    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    @Test
    public void accordingApe1Ape2ShouldNotLockRebelWheel() {
        machine.addWheel("rebel", 1);
        machine.lock(1);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        
        machine.placeSymbol(1, "red");
        machine.spin(1); 

        assertTrue(machine.ok());
    }

    @Test
    public void accordingApe1Ape2ShouldToggleShySymbolVisibility() {
        machine.addWheel("normal", 1);
        machine.addSymbol("shy", 1, "green");

        machine.spin(1); 
        assertTrue(machine.ok());
    }
}

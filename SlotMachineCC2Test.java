import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas colectivas para el Wiki de la clase SlotMachineCC2Test.
 * Autores: Julian y Jonathan.
 */
public class SlotMachineCC2Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.makeInvisible();
        
        machine.addWheel(1);
        machine.addWheel(2);
        
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    }

    @Test
    public void accordingApe1Ape2ShouldKeepLockedWheelUnchangedWhenSetSymbolsIsCalled() {
        machine.placeSymbol(1, "red");
        machine.lock(1);

        machine.spin(new String[]{"blue", "red"});

        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingApe1Ape2ShouldFailWhenSpinningWheelByStepsInEmptyMachine() {
        SlotMachine emptyMachine = new SlotMachine();
        emptyMachine.makeInvisible();

        emptyMachine.spin(1, 3);

        assertFalse(emptyMachine.ok());
    }
}

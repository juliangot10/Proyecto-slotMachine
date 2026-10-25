import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad para el Ciclo 2 de SlotMachine.
 * Autores: Julian y Jonathan.
 * Evaluacion en modo invisible de los Requisitos 9, 10, 11 y 12.
 */
public class SlotMachineC2Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        machine = new SlotMachine();
        machine.makeInvisible(); 
        
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    }

    @After
    public void tearDown() {
        machine = null;
    }

    @Test
    public void shouldSwapTwoWheelsSuccessfully() {
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);

        assertTrue(machine.ok());
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertEquals("red", config[1]);
    }

    @Test
    public void shouldNotSwapIfMachineHasLessThanTwoWheels() {
        SlotMachine smallMachine = new SlotMachine();
        smallMachine.makeInvisible();
        smallMachine.addWheel(1);

        smallMachine.swap(1, 1);

        assertFalse(smallMachine.ok());
    }

    @Test
    public void shouldLockWheelAndKeepSymbolIntactOnSpin() {
        machine.placeSymbol(1, "red");
        machine.lock(1);

        for (int i = 0; i < 5; i++) {
            machine.spin(1);
        }

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void shouldUnlockWheelAndAllowChanges() {
        machine.lock(1);
        machine.unlock(1);

        machine.placeSymbol(1, "blue");

        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void shouldSpinWheelBySpecifiedSteps() {
        machine.placeSymbol(1, "red"); 
        machine.spin(1, 2); 

        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);
    }

    @Test
    public void shouldNotSpinLockedWheelBySteps() {
        machine.placeSymbol(1, "red");
        machine.lock(1);

        machine.spin(1, 3);

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }


    @Test
    public void shouldSetSpecificConfigurationSuccessfully() {
        String[] targetConfig = new String[]{"blue", "green", "red"};
        machine.spin(targetConfig);

        assertTrue(machine.ok());
        assertArrayEquals(targetConfig, machine.configuration());
    }

    @Test
    public void shouldFailWhenSetSymbolDoesNotExistInWheel() {
        String[] invalidConfig = new String[]{"yellow", "blue", "green"}; 
        machine.spin(invalidConfig);

        assertFalse(machine.ok());
    }

    @Test
    public void shouldFailWhenSetSymbolsLengthMismatchesWheelsCount() {
        String[] shortConfig = new String[]{"red", "blue"};
        machine.spin(shortConfig);

        assertFalse(machine.ok());
    }
}
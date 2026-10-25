import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineContestTest {

    private SlotMachineContest contest;

    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    @Test
    public void shouldSolveContestSuccessfullyInInvisibleMode() {
        int steps = contest.solve(3);
        assertTrue(steps >= 0);
    }

    @Test
    public void shouldSolveForSingleWheel() {
        int steps = contest.solve(1);
        assertEquals(0, steps);
    }
}
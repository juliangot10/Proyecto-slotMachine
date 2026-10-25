 /**
 * Resuelve y simula el problema de la maraton internacional Slot Machine.
 * Proyecto Inicial - Ciclo No 3
 * 
 * @author Julian y Jonathan
 * @version 1.0 (2026)
 */
public class SlotMachineContest {

    private SlotMachine testingTool;

    public SlotMachineContest() {
        this.testingTool = null;
    }

    /**
     * Soluciona el problema de la maraton.
     * La SlotMachine permanece estrictamente INVISIBLE durante la resolucion.
     * 
     * @param n Numero de ruedas y simbolos.
     * @return Numero de pasos/acciones necesarias para ganar (Jackpot).
     */
    public int solve(int n) {
        testingTool = new SlotMachine(n);
        testingTool.makeInvisible(); 

        int totalSteps = 0;
        String[] currentConfig = testingTool.configuration();
        
        if (currentConfig.length == 0) {
            return 0;
        }

        String targetColor = currentConfig[0];

        for (int wheel = 2; wheel <= n; wheel++) {
            int stepsForWheel = 0;
            while (!testingTool.configuration()[wheel - 1].equalsIgnoreCase(targetColor) && stepsForWheel < n) {
                testingTool.spin(wheel, 1);
                stepsForWheel++;
                totalSteps++;
            }
        }

        return totalSteps;
    }
    
    /**
     * Simula de forma grafica e interactiva las acciones necesarias para ganar.
     * La SlotMachine se hace VISIBLE para la animacion.
     * 
     * @param n Numero de ruedas y simbolos de la maquina a simular.
     */
    public void simulate(int n) {
        testingTool = new SlotMachine(n);
        testingTool.makeVisible();
        String[] config = testingTool.configuration();
        if (config.length == 0) {
            return;
        }

        String targetColor = config[0];

        for (int wheel = 2; wheel <= n; wheel++) {
            while (!testingTool.configuration()[wheel - 1].equalsIgnoreCase(targetColor)) {
                testingTool.spin(wheel, 1);
            }
        }
    }
}

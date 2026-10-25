

/**
 * Clase de prueba automatica para el Ciclo 1.
 */
public class SlotMachineTest {

    public static void main(String[] args) {
        System.out.println("Pruebas ciclo 1");
        
        SlotMachine machine = new SlotMachine();
        System.out.println("1. Creacion de maquina: ok() = " + machine.ok());

        machine.makeVisible();
        System.out.println("2. Simulador visible: ok() = " + machine.ok());

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        System.out.println("3. 3 Ruedas adicionadas: ok() = " + machine.ok());

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        System.out.println("4. Simbolos adicionados: ok() = " + machine.ok());
        
        System.out.println("5. Total simbolos distintos: " + machine.distinctSymbols());

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(3, "red");
        System.out.println("6. ¿Es Jackpot (todos rojos)? " + machine.isJackpot());

        machine.spin();
        System.out.println("7. Giro aleatorio ejecutado: ok() = " + machine.ok());

      
        System.out.println("Pruebas completadas");
    }
}

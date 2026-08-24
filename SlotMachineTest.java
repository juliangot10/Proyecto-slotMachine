public class SlotMachineTest {
    public static void main(String[] args) {
        System.out.println("pruebas simuladas de la maquina");

        SlotMachine máquina = new SlotMachine();
        
        máquina.makeVisible();
        System.out.println("Visibilidad activada. Operacion OK? " + máquina.ok());

        máquina.addWheel(1);
        máquina.addWheel(2);
        máquina.addWheel(3);
        
        máquina.addSymbol(1, "red");
        máquina.addSymbol(1, "blue");

        máquina.addSymbol(2, "red");
        máquina.addSymbol(2, "green");

        máquina.addSymbol(3, "red");
        máquina.addSymbol(3, "yellow");

        System.out.println("\nConfiguracion actual:");
        imprimirArreglo(máquina.configuration());

        System.out.println("Es Jackpot inicial?: " + máquina.isJackpot());

        System.out.println("\nGirando ruedas para buscar la combinacion ganadora");
        máquina.spin(1); 
    
        System.out.println("Configuración tras girar:");
        imprimirArreglo(máquina.configuration());

        boolean gano = máquina.isJackpot();
        System.out.println("Es Jackpot ganador: " + gano);

        System.out.println("\nIntentando borrar una rueda que no existe (Rueda 99)");
        máquina.delWheel(99); 
        System.out.println("Operación ok tras error?: " + máquina.ok()); 
    }

    private static void imprimirArreglo(String[] arreglo) {
        System.out.print("[ ");
        for (String s : arreglo) {
            System.out.print(s + " ");
        }
        System.out.println("]");
    }
}
import java.util.ArrayList;
import shapes.Rectangle;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Simulador de Maquina Tragamonedas (Slot Machine).
 * Proyecto Inicial - Desarrollo Orientado por Objetos (ECI).
 * 
 * Esta clase actua como el controlador principal del simulador,
 * administrando las ruedas, los simbolos, las operaciones de giro,
 * las validaciones de victoria y la representacion grafica.
 * 
 * @author Julian y Jonathan
 * @version 4.0 (2026)
 */
public class SlotMachine {
    
    private Rectangle casing;
    private int casingX = 70; 
    private int casingY = 15;
    private ArrayList<Wheel> wheels;
    private boolean isVisible;
    private boolean lastOperationOk;

    /**
     * Crea una nueva maquina tragamonedas vacia e invisible por defecto.
     */
    public SlotMachine() {
        this.wheels = new ArrayList<Wheel>();
        this.isVisible = false;
        this.lastOperationOk = true;
    }

    /**
     * Crea una maquina tragamonedas con n ruedas y n simbolos distintos.
     * La maquina se inicializa aleatoriamente con n colores de la paleta.
     * 
     * @param n Numero de ruedas y de simbolos de la maquina.
     */
    public SlotMachine(int n) {
        this();
        if (n <= 0) {
            lastOperationOk = false;
            showErrorMessage("El numero de ruedas debe ser mayor a 0.");
            return;
        }

        String[] colorPalette = {
            "red", "blue", "green", "yellow", "orange", 
            "magenta", "cyan", "pink", "gray", "black"
        };

        for (int i = 1; i <= n; i++) {
            addWheel(i);
        }

        for (int i = 0; i < n; i++) {
            String color = colorPalette[i % colorPalette.length];
            addSymbol(i + 1, color);
        }

        spin();
        lastOperationOk = true;
    }

    /**
     * Consulta el estado de exito de la ultima operacion realizada.
     * 
     * @return true si la ultima operacion fue exitosa, false en caso contrario.
     */
    public boolean ok() {
        return lastOperationOk;
    }

    /**
     *Adiciona una nueva rueda de tipo normal en la posicion indicada.
     * 
     * @param pos Posicion donde se insertara la rueda (basada en 1).
     */
    public void addWheel(int pos) {
        addWheel("normal", pos);
    }

    /**
     * Adiciona una rueda del tipo especificado.
     * Tipos soportados: "normal", "lefty", "rebel", "boost".
     * 
     * @param type Tipo de rueda a crear.
     * @param pos Posicion donde se insertara la rueda (basada en 1).
     */
    public void addWheel(String type, int pos) {
        int adjustedPos = adjustPositionForAdd(pos);
        Wheel newWheel;

        if (type.equalsIgnoreCase("lefty")) {
            newWheel = new LeftyWheel();
        } else if (type.equalsIgnoreCase("rebel")) {
            newWheel = new RebelWheel();
        } else if (type.equalsIgnoreCase("boost")) {
            newWheel = new JackpotBoostWheel();
        } else {
            newWheel = new Wheel();
        }

        wheels.add(adjustedPos, newWheel);
        updateLeftyConnections();
        lastOperationOk = true;

        if (isVisible) {
            redraw();
        }
    }

    /**
     * Elimina la rueda ubicada en la posicion indicada.
     * Si la rueda es de tipo Rebel, no permite la eliminacion.
     * 
     * @param pos Posicion de la rueda a eliminar (basada en 1).
     */
    public void delWheel(int pos) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para eliminar.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(pos);
        Wheel target = wheels.get(adjustedPos);

        if (!target.isRemovable()) {
            lastOperationOk = false;
            showErrorMessage("La rueda en esa posicion es Rebel y no se puede eliminar.");
            return;
        }

        Wheel removedWheel = wheels.remove(adjustedPos);
        removedWheel.erase();
        updateLeftyConnections();
        lastOperationOk = true;

        if (isVisible) {
            redraw();
        }
    }

    /**
     * Intercambia la posicion de dos ruedas en la maquina.
     * No permite el intercambio si alguna de las ruedas es de tipo Rebel.
     * 
     * @param wheel1 Posicion de la primera rueda.
     * @param wheel2 Posicion de la segunda rueda.
     */
    public void swap(int wheel1, int wheel2) {
        if (wheels.size() < 2) {
            lastOperationOk = false;
            showErrorMessage("Se requieren al menos 2 ruedas para realizar un intercambio.");
            return;
        }

        int pos1 = adjustPositionForAccess(wheel1);
        int pos2 = adjustPositionForAccess(wheel2);

        if (pos1 == pos2) {
            lastOperationOk = true;
            return;
        }

        if (!wheels.get(pos1).isSwappable() || !wheels.get(pos2).isSwappable()) {
            lastOperationOk = false;
            showErrorMessage("Una de las ruedas es Rebel y no permite intercambios.");
            return;
        }

        Wheel temp = wheels.get(pos1);
        wheels.set(pos1, wheels.get(pos2));
        wheels.set(pos2, temp);

        updateLeftyConnections();
        lastOperationOk = true;

        if (isVisible) {
            redraw();
        }
    }

    /**
     * Bloquea/fija la rueda en la posicion indicada.
     * 
     * @param wheel Posicion de la rueda a fijar (basada en 1).
     */
    public void lock(int wheel) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas en la maquina para fijar.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(wheel);
        wheels.get(adjustedPos).lock();
        lastOperationOk = true;
    }

    /**
     * Desbloquea/suelta la rueda en la posicion indicada.
     * 
     * @param wheel Posicion de la rueda a soltar (basada en 1).
     */
    public void unlock(int wheel) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas en la maquina para soltar.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(wheel);
        wheels.get(adjustedPos).unlock();
        lastOperationOk = true;
    }

    /**
     * Adiciona un simbolo de tipo normal a todas las ruedas.
     * 
     * @param pos Posicion del simbolo dentro de cada rueda (basada en 1).
     * @param color Nombre del color en formato CSS.
     */
    public void addSymbol(int pos, String color) {
        addSymbol("normal", pos, color);
    }

    /**
     * Adiciona un simbolo del tipo especificado a todas las ruedas.
     * Tipos soportados: "normal", "ephemeral", "shy".
     * 
     * @param type Tipo de simbolo a crear.
     * @param pos Posicion del simbolo en la rueda (basada en 1).
     * @param color Nombre del color CSS del simbolo.
     */
    public void addSymbol(String type, int pos, String color) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para adicionar el simbolo.");
            return;
        }

        for (Wheel wheel : wheels) {
            int adjustedPos = adjustSymbolPositionForAdd(wheel, pos);
            Symbol newSymbol;

            if (type.equalsIgnoreCase("ephemeral")) {
                newSymbol = new EphemeralSymbol(color);
            } else if (type.equalsIgnoreCase("shy")) {
                newSymbol = new ShySymbol(color);
            } else {
                newSymbol = new Symbol(color);
            }

            wheel.addSymbol(adjustedPos, newSymbol);
        }

        lastOperationOk = true;
        if (isVisible) {
            redraw();
        }
    }

    /**
     * Elimina todas las apariciones del simbolo de las ruedas.
     * 
     * @param symbol Color del simbolo a eliminar.
     */
    public void delSymbol(String symbol) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para eliminar el simbolo.");
            return;
        }

        boolean removed = false;
        for (Wheel wheel : wheels) {
            if (wheel.delSymbol(symbol)) {
                removed = true;
            }
        }

        if (!removed) {
            lastOperationOk = false;
            showErrorMessage("El simbolo '" + symbol + "' no fue encontrado.");
        } else {
            lastOperationOk = true;
            if (isVisible) {
                redraw();
            }
        }
    }

    /**
     * Selecciona un simbolo especifico en la rueda indicada.
     * 
     * @param wheel Numero de la rueda (basado en 1).
     * @param symbol Color del simbolo a seleccionar.
     */
    public void placeSymbol(int wheel, String symbol) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas en la maquina.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(wheel);
        Wheel target = wheels.get(adjustedPos);

        boolean placed = target.placeSymbol(symbol);
        if (!placed) {
            lastOperationOk = false;
            showErrorMessage("El simbolo '" + symbol + "' no existe en la rueda " + wheel + ".");
        } else {
            lastOperationOk = true;
            if (isVisible) {
                redraw();
            }
        }
    }

    /**
     * Gira aleatoriamente una rueda individual.
     * 
     * @param wheel Numero de la rueda a girar (basado en 1).
     */
    public void spin(int wheel) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para girar.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(wheel);
        boolean spun = wheels.get(adjustedPos).spin();

        if (!spun) {
            lastOperationOk = false;
            showErrorMessage("La rueda seleccionada no tiene simbolos.");
        } else {
            lastOperationOk = true;
            if (isVisible) {
                redraw();
            }
        }
    }

    /**
     * Rota la rueda indicada un numero especificado de pasos.
     * Muestra animacion paso a paso si la maquina esta visible.
     * 
     * @param wheel Numero de la rueda (basado en 1).
     * @param steps Pasos a avanzar.
     */
    public void spin(int wheel, int steps) {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para girar.");
            return;
        }

        int adjustedPos = adjustPositionForAccess(wheel);
        Wheel target = wheels.get(adjustedPos);

        if (target.getSymbols().isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("La rueda no tiene simbolos.");
            return;
        }

        if (isVisible && !target.isLocked()) {
            for (int i = 0; i < steps; i++) {
                target.spin(1);
                redraw();
                pause(120);
            }
        } else {
            target.spin(steps);
        }

        lastOperationOk = true;
    }

    /**
     * Gira aleatoriamente todas las ruedas de la maquina.
     */
    public void spin() {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para girar.");
            return;
        }

        boolean allOk = true;
        for (Wheel wheel : wheels) {
            if (!wheel.spin()) {
                allOk = false;
            }
        }

        if (!allOk) {
            lastOperationOk = false;
            showErrorMessage("Una o mas ruedas estan vacias y no giraron.");
        } else {
            lastOperationOk = true;
            if (isVisible) {
                redraw();
            }
        }
    }

    /**
     * Establece los simbolos visibles de todas las ruedas segun un arreglo dado.
     * 
     * @param setSymbols Arreglo con los colores deseados para cada rueda.
     */
    public void spin(String[] setSymbols) {
        if (wheels.isEmpty() || setSymbols == null || setSymbols.length != wheels.size()) {
            lastOperationOk = false;
            showErrorMessage("La cantidad de simbolos no coincide con las ruedas.");
            return;
        }

        for (int i = 0; i < wheels.size(); i++) {
            Wheel w = wheels.get(i);
            if (!w.isLocked()) {
                boolean found = false;
                for (String c : w.getSymbolColors()) {
                    if (c.equalsIgnoreCase(setSymbols[i])) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    lastOperationOk = false;
                    showErrorMessage("El simbolo '" + setSymbols[i] + "' no existe en la rueda " + (i + 1) + ".");
                    return;
                }
            }
        }

        for (int i = 0; i < wheels.size(); i++) {
            Wheel w = wheels.get(i);
            if (!w.isLocked()) {
                w.placeSymbol(setSymbols[i]);
            }
        }

        lastOperationOk = true;
        if (isVisible) {
            redraw();
        }
    }

    /**
     * Retorna todos los colores de los simbolos contenidos en la maquina.
     * 
     * @return Arreglo de String con todos los colores.
     */
    public String[] symbols() {
        ArrayList<String> allColors = new ArrayList<String>();
        for (Wheel wheel : wheels) {
            allColors.addAll(wheel.getSymbolColors());
        }
        lastOperationOk = true;
        return allColors.toArray(new String[0]);
    }

    /**
     * Retorna la cantidad de colores unicos presentes en toda la maquina.
     * 
     * @return Numero entero de colores distintos.
     */
    public int distinctSymbols() {
        Set<String> unique = new HashSet<String>();
        for (Wheel wheel : wheels) {
            for (String c : wheel.getSymbolColors()) {
                unique.add(c.toLowerCase());
            }
        }
        lastOperationOk = true;
        return unique.size();
    }

    /**
     * Retorna la configuracion de colores visibles de izquierda a derecha.
     * 
     * @return Arreglo de String con los colores visibles actuales.
     */
    public String[] configuration() {
        ArrayList<String> config = new ArrayList<String>();
        for (Wheel wheel : wheels) {
            String visibleColor = wheel.getVisibleSymbolColor();
            if (visibleColor != null) {
                config.add(visibleColor);
            }
        }
        lastOperationOk = true;
        return config.toArray(new String[0]);
    }

    /**
     * Consulta si la configuracion visible actual es ganadora (Jackpot).
     * 
     * @return true si todas las ruedas muestran exactamente el mismo color, false si no.
     */
    public boolean isJackpot() {
        if (wheels.isEmpty()) {
            lastOperationOk = false;
            showErrorMessage("No hay ruedas para evaluar un jackpot.");
            return false;
        }

        String[] currentConfig = configuration();
        if (currentConfig.length != wheels.size()) {
            lastOperationOk = false;
            showErrorMessage("Una o mas ruedas no tienen simbolos.");
            return false;
        }

        String primerColor = currentConfig[0];
        for (int i = 1; i < currentConfig.length; i++) {
            String colorActual = currentConfig[i];
            boolean sonIguales = colorActual.equalsIgnoreCase(primerColor);

            if (sonIguales == false) {
                lastOperationOk = true;
                return false;
            }
        }

        lastOperationOk = true;
        return true;
    }

    /**
     * Hace visible el simulador grafico en la pantalla.
     */
    public void makeVisible() {
        this.isVisible = true;
        redraw();
        lastOperationOk = true;
    }

    /**
     * Hace invisible la interfaz grafica del simulador.
     */
    public void makeInvisible() {
        this.isVisible = false;
        
        if (casing != null) {
            casing.makeInvisible();
        }
        
        for (Wheel wheel : wheels) {
            wheel.erase();
        }
        lastOperationOk = true;
    }

    /**
     * Finaliza la ejecucion del simulador de la maquina tragamonedas.
     */
    public void exit() {
        makeInvisible();
        System.exit(0);
    }

    /**
     * Actualiza la referencia interna de las ruedas Lefty con su vecina izquierda.
     */
    private void updateLeftyConnections() {
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i) instanceof LeftyWheel) {
                LeftyWheel lefty = (LeftyWheel) wheels.get(i);
                if (i > 0) {
                    lefty.setLeftWheel(wheels.get(i - 1));
                } else {
                    lefty.setLeftWheel(null);
                }
            }
        }
    }

    /**
     * Redibuja la representacion grafica de todas las ruedas en pantalla.
     */
    private void redraw() {
        if (wheels.isEmpty()) {
            if (casing != null) {
                casing.makeInvisible();
            }
            return;
        }

        int startX = 70;  
        int startY = 100; 
        int wheelSpacing = 70;
        
        int totalWidth = (wheels.size() * wheelSpacing) + 30;
        int totalHeight = 90;

        int targetCasingX = startX - 15;
        int targetCasingY = startY - 20;

        if (casing == null) {
            casing = new Rectangle();
            casing.changeColor("blue"); 
        }

        casing.changeSize(totalHeight, totalWidth);

        int deltaX = targetCasingX - this.casingX;
        int deltaY = targetCasingY - this.casingY;

        casing.moveHorizontal(deltaX);
        casing.moveVertical(deltaY);

        this.casingX = targetCasingX;
        this.casingY = targetCasingY;

        casing.makeVisible();
        
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).draw(startX + (i * wheelSpacing), startY);
        }
    }

    private int adjustPositionForAdd(int pos) {
        if (pos < 1) return 0;
        if (pos > wheels.size() + 1) return wheels.size();
        return pos - 1;
    }

    private int adjustPositionForAccess(int pos) {
        if (pos < 1) return 0;
        if (pos > wheels.size()) return wheels.size() - 1;
        return pos - 1;
    }

    private int adjustSymbolPositionForAdd(Wheel wheel, int pos) {
        int size = wheel.getSymbols().size();
        if (pos < 1) return 0;
        if (pos > size + 1) return size;
        return pos - 1;
    }

    private void showErrorMessage(String message) {
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

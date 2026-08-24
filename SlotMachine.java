import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Clase principal del simulador de la máquina tragamonedas.
 */
public class SlotMachine {
    private ArrayList<Wheel> ruedas;
    private Rectangle marcoExterior;
    private boolean isVisible;
    private boolean ok;
    
    /**
     * una máquina tragamonedas 
     */
    public SlotMachine() {
        this.ruedas = new ArrayList<>();
        this.isVisible = false;
        this.ok = true;
        
        this.marcoExterior = new Rectangle();
        this.marcoExterior.changeColor("magenta");
        this.marcoExterior.changeSize(200, 300);
    }
    
    /**
     * Muestra una alerta emergente solo si la máquina está visible.
     */
    private void mostrarError(String mensaje) {
        this.ok = false;
        if (this.isVisible) {
            JOptionPane.showMessageDialog(null, mensaje, "Error en SlotMachine", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Hace visible el simulador en pantalla.
     */
    public void makeVisible() {
        this.isVisible = true;
        this.marcoExterior.makeVisible();
        reorganizarRuedas();
        
        for (Wheel r : ruedas) {
            r.makeVisible();
        }
        this.ok = true;
    }
    
    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible() {
        this.isVisible = false;
        this.marcoExterior.makeInvisible();
        for (Wheel r : ruedas) {
            r.makeInvisible();
        }
        this.ok = true;
    }
    
    /**
     * Reposiciona las ruedas horizontalmente dentro de la tragamonedas.
     */
    private void reorganizarRuedas() {
        int xInicial = 70;
        int yInicial = 80;
        int separacion = 50;

        for (int i = 0; i < ruedas.size(); i++) {
            ruedas.get(i).setPosition(xInicial + (i * separacion), yInicial);
            if (this.isVisible) {
                ruedas.get(i).makeVisible();
            }
        }
    }
    
    /**
     * Adiciona una rueda en la posición indicada.
     */
    public void addWheel(int pos) {
        Wheel nuevaRueda = new Wheel();
        int index = pos - 1;

        if (index < 0) {
            index = 0;
        } else if (index > ruedas.size()) {
            index = ruedas.size();
        }

        ruedas.add(index, nuevaRueda);
        this.ok = true;
    }
    
    /**
     * Elimina la rueda en la posición indicada.
     */
    public void delWheel(int pos) {
        int index = pos - 1;
        if (index >= 0 && index < ruedas.size()) {
            Wheel eliminada = ruedas.remove(index);
            eliminada.makeInvisible();
            reorganizarRuedas();
            this.ok = true;
        } else {
            mostrarError("No existe ninguna rueda en la posición: " + pos);
        }
    }
    
    /**
     * Adiciona un símbolo a la rueda en la posición indicada.
     */
    public void addSymbol(int pos, String color) {
        int index = pos - 1;
        if (index >= 0 && index < ruedas.size()) {
            Wheel ruedaSeleccionada = ruedas.get(index);
            int totalSimbolos = ruedaSeleccionada.getSimbolos().size();
            int nuevaPosicion = totalSimbolos + 1;
            ruedaSeleccionada.addSymbol(nuevaPosicion, color);
            
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Gira todas las ruedas una posición.
     */
    public void spin() {
        if (!ruedas.isEmpty()) {
            for (Wheel r : ruedas) {
                r.spin();
            }
            this.ok = true;
        } else {
            this.ok = false;
        }
    }
    
    /**
     * Gira una rueda específica indicada por su posición (basada en 1).
     */
    public void spin(int wheel) {
        int index = wheel - 1;
        if (index >= 0 && index < ruedas.size()) {
            ruedas.get(index).spin();
            this.ok = true;
        } else {
            mostrarError("No se puede girar. La rueda " + wheel + " no existe.");
        }
    }
    
    /**
     * Retorna los colores de los símbolos visibles en todas las ruedas de izquierda a derecha.
     */
    public String[] configuration() {
        String[] config = new String[ruedas.size()];
        for (int i = 0; i < ruedas.size(); i++) {
            config[i] = ruedas.get(i).getVisibleColor();
        }
        this.ok = true;
        return config;
    }
    
    /**
     * Consulta si la configuracion actual es la ganadora osea todo los colores iguales.
     */
    public boolean isJackpot() {
        if (ruedas.isEmpty()) {
            mostrarError("La máquina no tiene ruedas configuradas.");
            return false;
        }
        
        String primerColor = ruedas.get(0).getVisibleColor();
        if (primerColor.equals("")) {
            mostrarError("La primera rueda no tiene símbolos.");
            return false;
        }

        for (Wheel r : ruedas) {
            if (!r.getVisibleColor().equals(primerColor)) {
                this.ok = true;
                return false; 
            }
        }
        
        if (this.isVisible) {
            this.marcoExterior.changeColor("yellow"); 
        }
        this.ok = true;
        return true; 
    }
    
    /**
     * Indica si se logró realizar la ultima operacion.
     */
    public boolean ok() {
        return this.ok;
    }
    
    
    
}
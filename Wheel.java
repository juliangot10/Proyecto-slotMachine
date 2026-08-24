import java.util.ArrayList;
/**
 * Write a description of class Wheel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wheel {
    private ArrayList<Symbol> simbolos;
    private int actualIndex;
    private Rectangle fondoRueda;
    private int x;
    private int y;
    
    public Wheel() {
        this.simbolos = new ArrayList<>();
        this.actualIndex = 0;
        
        this.fondoRueda = new Rectangle();
        this.fondoRueda.changeColor("white");
        this.fondoRueda.changeSize(120, 40); 
        
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.fondoRueda.slowMoveHorizontal(x - 60);
        this.fondoRueda.slowMoveVertical(y - 50);

        actualizarSimboloVisible();
    }
    
    public void makeVisible() {
        this.fondoRueda.makeVisible();
        if (!simbolos.isEmpty()) {
            simbolos.get(actualIndex).makeVisible();
        }
    }
    
    public void makeInvisible() {
        this.fondoRueda.makeInvisible();
        for (Symbol s : simbolos) {
            s.makeInvisible();
        }
    }
    
    /**
    * Hace girar la rueda un paso adelante.
    */
    public void spin() {
        if (!simbolos.isEmpty()) {
            simbolos.get(actualIndex).makeInvisible();
            actualIndex = (actualIndex + 1) % simbolos.size();
            actualizarSimboloVisible();
        }
    }
    
    private void actualizarSimboloVisible() {
        if (!simbolos.isEmpty()) {
            Symbol visible = simbolos.get(actualIndex);
            visible.setPosition(this.x + 5, this.y + 40);
            visible.makeVisible();
        }
    }
    
    /**
     * Retorna el color del símbolo que se está viendo en este momento.
     */
    public String getVisibleColor() {
        if (simbolos.isEmpty()) {
            return "";
        }
        return simbolos.get(actualIndex).getColor();
    }
    
    /**
     * Agrega un simbolo en una posición especifica de la rueda.
     */
    public void addSymbol(int pos, String color) {
        Symbol nuevo = new Symbol(color);
        int indiceInsertar = pos - 1;
    
        if (indiceInsertar < 0) {
            indiceInsertar = 0;
        } else if (indiceInsertar > simbolos.size()) {
            indiceInsertar = simbolos.size();
            actualizarSimboloVisible();
        }
        simbolos.add(indiceInsertar, nuevo);
    }

    public ArrayList<Symbol> getSimbolos() {
        return this.simbolos;
    }
        
    
}
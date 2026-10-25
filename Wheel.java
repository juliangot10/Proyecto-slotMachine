import shapes.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Clase Base para las Ruedas del simulador.
 */
public class Wheel {
    protected ArrayList<Symbol> symbols;
    protected int visibleIndex;
    protected Rectangle frame;
    protected int x;
    protected int y;
    protected boolean isLocked;

    public Wheel() {
        this.symbols = new ArrayList<Symbol>();
        this.visibleIndex = 0;
        this.frame = new Rectangle();
        this.frame.changeSize(60, 50);
        this.frame.changeColor("black");
        this.x = 70;
        this.y = 15;
        this.isLocked = false;
    }

    public ArrayList<Symbol> getSymbols() { 
        return symbols; 
    }
    
    public int getVisibleIndex() { 
        return visibleIndex; 
    }
    
    public void setVisibleIndex(int index) {
        if (!symbols.isEmpty()) {
            this.visibleIndex = index % symbols.size();
        }
    }

    public void lock() { 
        this.isLocked = true; 
    }
    public void unlock() { 
        this.isLocked = false; 
    }
    public boolean isLocked() { 
        return isLocked; 
    }

    public boolean isRemovable() { 
        return true; 
    } 
        
    public boolean isSwappable() { 
        return true; 
    } 

    public void addSymbol(int pos, Symbol symbol) {
        symbols.add(pos, symbol);
    }

    public boolean delSymbol(String color) {
        boolean removed = false;
        Iterator<Symbol> it = symbols.iterator();
        while (it.hasNext()) {
            Symbol s = it.next();
            if (s.getColor().equalsIgnoreCase(color)) {
                it.remove();
                removed = true;
            }
        }
        if (removed && !symbols.isEmpty() && visibleIndex >= symbols.size()) {
            visibleIndex = symbols.size() - 1;
        } else if (symbols.isEmpty()) {
            visibleIndex = 0;
        }
        return removed;
    }

    public boolean spin() {
        if (symbols.isEmpty()) return false;
        if (isLocked) return true;
        Random random = new Random();
        this.visibleIndex = random.nextInt(symbols.size());
        notifySymbolSelected();
        return true;
    }

    public boolean spin(int steps) {
        if (symbols.isEmpty()) return false;
        if (isLocked) return true;
        visibleIndex = (visibleIndex + steps) % symbols.size();
        if (visibleIndex < 0) visibleIndex += symbols.size();
        notifySymbolSelected();
        return true;
    }

    public boolean placeSymbol(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                this.visibleIndex = i;
                notifySymbolSelected();
                return true;
            }
        }
        return false;
    }

    protected void notifySymbolSelected() {
        Symbol visible = getVisibleSymbol();
        if (visible != null) {
            visible.onSelected();
        }
    }

    public ArrayList<String> getSymbolColors() {
        ArrayList<String> colors = new ArrayList<String>();
        for (Symbol s : symbols) colors.add(s.getColor());
        return colors;
    }

    public String getVisibleSymbolColor() {
        Symbol visible = getVisibleSymbol();
        return (visible != null) ? visible.getColor() : null;
    }

    public Symbol getVisibleSymbol() {
        if (symbols.isEmpty()) return null;
        return symbols.get(visibleIndex);
    }

    public void draw(int newX, int newY) {
        int deltaX = newX - this.x;
        int deltaY = newY - this.y;
        frame.moveHorizontal(deltaX);
        frame.moveVertical(deltaY);
        this.x = newX;
        this.y = newY;
        frame.makeVisible();

        Symbol current = getVisibleSymbol();
        if (current != null) {
            current.draw(newX + 10, newY + 15);
        }
    }

    public void erase() {
        frame.makeInvisible();
        Symbol current = getVisibleSymbol();
        if (current != null) {
            current.erase();
        }
    }
}

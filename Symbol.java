
/**
 * Write a description of class Symbol here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Symbol {
    private String color;
    private Circle figura;
    private int x;
    private int y;
    
    public Symbol(String color) {
        this.color = color;
        this.figura = new Circle();
        this.figura.changeColor(color);
        this.figura.changeSize(30);
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.figura.slowMoveHorizontal(x - 20); 
        this.figura.slowMoveVertical(y - 60);
    }
    
    public String getColor() {
        return this.color;
    }
    
    public void makeVisible() {
        this.figura.makeVisible();
    }
    
    public void makeInvisible() {
        this.figura.makeInvisible();
    }
}
    
    
    
    
    

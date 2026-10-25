 import shapes.Circle;

/**
 * Representa un simbolo identificado por un color en la maquina.
 */
public class Symbol {
    protected String color;
    protected Circle circle;
    protected int x;
    protected int y;
    protected int size;
    protected boolean isVisible;

    /**
     * Crea un simbolo con el color especificado.
     * @param color Nombre del color.
     */
    public Symbol(String color) {
        this.color = color;
        this.size = 30;
        this.isVisible = true;
        this.circle = new Circle();
        this.circle.changeColor(color);
        this.circle.changeSize(size);
        this.x = 20;
        this.y = 15;
    }

    public String getColor() {
        return color;
    }
    
    public void onSelected() {
    }

    public void setColor(String color) {
        this.color = color;
        this.circle.changeColor(color);
    }

    public void draw(int newX, int newY) {
        int deltaX = newX - this.x;
        int deltaY = newY - this.y;
        circle.moveHorizontal(deltaX);
        circle.moveVertical(deltaY);
        this.x = newX;
        this.y = newY;

        if (isVisible) {
            circle.makeVisible();
        } else {
            circle.makeInvisible();
        }
    }

    public void erase() {
        circle.makeInvisible();
    }
}

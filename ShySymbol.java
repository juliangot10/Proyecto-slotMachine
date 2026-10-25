 /**
 * Simbolo Shy: Alterna su estado de visible a invisible cada vez que es seleccionado.
 */
public class ShySymbol extends Symbol {

    public ShySymbol(String color) {
        super(color);
    }

    @Override
    public void onSelected() {
        this.isVisible = !this.isVisible; 
    }
}

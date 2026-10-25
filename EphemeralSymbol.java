 /**
 * Simbolo Ephemeral: Decrementa su tamaño en cada giro hasta quedar como un punto.
 */
public class EphemeralSymbol extends Symbol {

    public EphemeralSymbol(String color) {
        super(color);
    }

    @Override
    public void onSelected() {
        if (size > 6) {
            size -= 6; 
            circle.changeSize(size);
        }
    }
}

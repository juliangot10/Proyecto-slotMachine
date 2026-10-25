/**
 * Rueda Rebel: No se deja bloquear, ni intercambiar, ni eliminar.
 */
public class RebelWheel extends Wheel {

    public RebelWheel() {
        super();
        this.frame.changeColor("red"); 
    }

    @Override
    public void lock() {
        this.isLocked = false;
    }

    @Override
    public boolean isRemovable() {
        return false; 
    }

    @Override
    public boolean isSwappable() {
        return false; 
    }
}

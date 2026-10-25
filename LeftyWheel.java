/**
 * Rueda Lefty: Si tiene una rueda a su izquierda, al girar copia su estado.
 */
public class LeftyWheel extends Wheel {
    
    private Wheel leftWheel;

    public LeftyWheel() {
        super();
        this.frame.changeColor("blue"); 
    }

    public void setLeftWheel(Wheel left) {
        this.leftWheel = left;
    }

    @Override
    public boolean spin() {
        if (symbols.isEmpty()) return false;
        if (isLocked) return true;

        if (leftWheel != null && !leftWheel.getSymbols().isEmpty()) {
            this.visibleIndex = leftWheel.getVisibleIndex() % symbols.size();
            notifySymbolSelected();
            return true;
        }
        return super.spin();
    }
}

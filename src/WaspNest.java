public class WaspNest implements ICoordinates {
    private int x;
    private int y;
    private int wasps;

    public WaspNest(int x, int y, int wasps) {
        this.x = x;
        this.y = y;
        this.wasps = wasps;
    }

    public WaspNest(WaspNest waspNest) {
        this.x = waspNest.x;
        this.y = waspNest.y;
        this.wasps = waspNest.wasps;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWasps() {
        return wasps;
    }

    public void setWasps(int wasps) {
        this.wasps = wasps;
    }

    /**
     * Αφαιρεί το {@code killedWasps} από τον αριθμό των σφηκών που έχει η φωλιά.
     * Εάν το αποτέλεσμα είναι μικρότερο του 0 τότε η φωλιά έχει 0 σφήκες.
     *
     * @param killedWasps Ο αριθμός των σφηκών που θα αφαιρεθούν
     */
    public void killWasps(int killedWasps) {
        wasps -= killedWasps;

        if (wasps < 0) {
            wasps = 0;
        }
    }

}

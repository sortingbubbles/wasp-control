/**
 * Created by Mike on 19/4/2014.
 */
public class WaspNest implements ICoordinates {
    private int x;
    private int y;
    private int wasps;

    public WaspNest(int x, int y, int wasps) {
        this.x = x;
        this.y = y;
        this.wasps = wasps;
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

    public void killWasps(int killedWasps) {
        wasps -= killedWasps;

        if (wasps < 0) {
            wasps = 0;
        }
    }

}

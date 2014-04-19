/**
 * Created by Mike on 19/4/2014.
 */
public class WaspNest {
    private int x;
    private int y;
    private int wasps;

    public WaspNest(int x, int y, int wasps) {
        if (x < 0 || x > 100) {
            throw new IllegalArgumentException("x is out of range. Valid range is 0 ... 100");
        }

        if (y < 0 || y > 100) {
            throw new IllegalArgumentException("y is out of range. Valid range is 0 ... 100");
        }

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
}

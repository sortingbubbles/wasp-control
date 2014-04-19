/**
 * Created by Mike on 19/4/2014.
 */
public class Bomb implements ICoordinates {
    private int x;
    private int y;

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

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

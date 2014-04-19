import java.util.ArrayList;

/**
 * Created by Mike on 19/4/2014.
 */
public class MapController {
    private ArrayList<WaspNest> map;
    private double maxDist;

    public MapController(int maxX, int maxY) {
        map = new ArrayList<WaspNest>();
        maxDist = distance(0, 0, maxX, maxY);
    }

    public void addWaspNet(WaspNest waspNest) {
        map.add(waspNest);
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private double distance(ICoordinates point1, ICoordinates point2) {
        return distance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    private double getBombNestKills(WaspNest waspNest, Bomb bomb) {
        return waspNest.getWasps() * (maxDist / (20 * distance(waspNest, bomb) + 0.00001));
    }

    public double getBombTotalKills(Bomb bomb) {
        double kills = 0;
        for (WaspNest waspNest : map) {
            kills += getBombNestKills(waspNest, bomb);
            waspNest.killWasps((int)Math.round(kills));
        }

        return kills;
    }
}

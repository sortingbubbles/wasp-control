import java.util.ArrayList;

/**
 * Created by Mike on 19/4/2014.
 */
public class MapController {
    private ArrayList<WaspNest> map;
    private double maxDist;

    public MapController() {
        map = new ArrayList<WaspNest>();
        maxDist = 0;
    }

    public void addWaspNet(WaspNest waspNest) {
        map.add(waspNest);
        maxDist = calcMaxDist();
    }

    private double distance(ICoordinates point1, ICoordinates point2) {
        return Math.sqrt(Math.pow((point1.getX() - point2.getX()), 2) + Math.pow((point1.getY() - point2.getY()), 2));
    }

    private double calcMaxDist() {
        int size = map.size();
        double maxDist = 0;

        if (size >= 2) {
            for(int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    double temp = distance(map.get(i), map.get(j));
                    if (temp > maxDist) {
                        maxDist = temp;
                    }
                }
            }
        }

        return maxDist;
    }

}

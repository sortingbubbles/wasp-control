import java.util.ArrayList;
import java.util.HashMap;

public class MapController {
    /**
     * Πίνακας με την τοποθεσία των σφηκοφωλιών
     */
    private ArrayList<WaspNest> map;

    /**
     * Αντίγραφο του πίνακα με την τοποθεσία των σφηκοφωλιών (χρησιμοποιείται για την επαναφορά)
     */
    private HashMap<Integer, ArrayList<WaspNest>> mapCopies;

    private int totalNests;



    private int[][] waspStates;

    /**
     * Μέγιστη απόσταση μεταξύ δύο σημείων στον χάρτη
     */
    private double maxDist;

    private int maxX;
    private int maxY;

    /**
     * Δημιουργία ελεγκτή του χάρτη
     * @param maxX Το μέγιστο πλάτος του χάρτη
     * @param maxY Το μέγιστο ύψος του χάρτη
     */
    public MapController(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        map = new ArrayList<>();
        mapCopies = new HashMap<>();
        mapCopies.put(0, new ArrayList<WaspNest>());

        maxDist = Distance.getDistance(0, 0, maxX, maxY);
        totalNests = 0;
    }

    /**
     * Προσθήκη μιας σφηκοφωλιάς στον χάρτη
     * @param waspNest Η σφηκοφωλιά που θα προστεθεί
     */
    public void addWaspNet(WaspNest waspNest) {
        map.add(waspNest);
        mapCopies.get(0).add(new WaspNest(waspNest));
        totalNests++;
    }



    /**
     * Υπολογίζει πόσες σφήκες θα σκοτωθούν από την φωλιά {@code waspNest} αν σκάσει η βόμβα {@code bomb}
     * @param waspNest Η φωλιά που μας ενδιαφέρει
     * @param bomb Η βόμβα που θα σκάσει
     * @return Τον αριθμό των σφηκών που θα πεθάνουν
     */
    private double getBombNestKills(WaspNest waspNest, Bomb bomb) {
        double kills = waspNest.getWasps() * (maxDist / (20 * Distance.getDistance(waspNest, bomb) + 0.00001));

        if (kills >= waspNest.getWasps()) {
            return waspNest.getWasps();
        } else {
            return kills;
        }
    }

    /**
     * Υπολογίζει πόσες σφήκες θα πεθάνουν συνολικά αν σκάσει η βόμβα {@code bomb}
     * @param bomb Η βόμβα που θα σκάσει
     * @return Τον συνολικό αριθμό των σφηκών που θε πεθάνουν
     */
    public double getBombTotalKills(Bomb bomb) {
        double kills = 0;
        for (int i = 0; i < totalNests; i++) {
            WaspNest waspNest = map.get(i);
            kills += getBombNestKills(waspNest, bomb);
            waspNest.killWasps((int)Math.round(kills));
        }

        return kills;
    }

    public void initSave(int totalCheckpoints) {
        waspStates = new int[totalCheckpoints][map.size()];
    }

    public void saveMap(int checkpoint) {
        for (int i = 0; i < totalNests; i++) {
            waspStates[checkpoint][i] = map.get(i).getWasps();
        }
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    /**
     * Επιστρέφει τον αριθμό των ζωντανών σφηκών στην αρχική τους κατάσταση
     */
    public void restoreMap(int checkpoint) {
        for (int i = 0; i < totalNests; i++) {
            map.get(i).setWasps(waspStates[checkpoint][i]);
        }
    }
}

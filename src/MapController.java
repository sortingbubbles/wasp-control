import java.util.ArrayList;

public class MapController {
    /**
     * Πίνακας με την τοποθεσία των σφηκοφωλιών
     */
    private ArrayList<WaspNest> map;

    /**
     * Αντίγραφο του πίνακα με την τοποθεσία των σφηκοφωλιών (χρησιμοποιείται για την επαναφορά)
     */
    private ArrayList<WaspNest> mapCopy;

    /**
     * Μέγιστη απόσταση μεταξύ δύο σημείων στον χάρτη
     */
    private double maxDist;

    /**
     * Δημιουργία ελεγκτή του χάρτη
     * @param maxX Το μέγιστο πλάτος του χάρτη
     * @param maxY Το μέγιστο ύψος του χάρτη
     */
    public MapController(int maxX, int maxY) {
        map = new ArrayList<WaspNest>();
        maxDist = distance(0, 0, maxX, maxY);
    }

    /**
     * Προσθήκη μιας σφηκοφωλιάς στον χάρτη
     * @param waspNest Η σφηκοφωλιά που θα προστεθεί
     */
    public void addWaspNet(WaspNest waspNest) {
        map.add(waspNest);
        mapCopy.add(waspNest);
    }

    /**
     * Υπολογίζει την απόσταση μεταξύ δύο σημείων
     * @param x1 Η τετμημένη του πρώτου σημείου
     * @param y1 Η τεταγμένη του πρώτου σημείου
     * @param x2 Η τετμημένη του δεύτερου σημείου
     * @param y2 Η τεταγμένη του δεύτερου σημείου
     * @return Την απόσταση των δύο σημείων
     */
    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Υπολογίζει την απόσταση μεταξύ δύο σημείων
     * @param point1 Το πρώτο σημείο
     * @param point2 Το δεύτερο σημείο
     * @return Την απόσταση των σημείων {@code point1} με {@code point2}
     */
    private double distance(ICoordinates point1, ICoordinates point2) {
        return distance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    /**
     * Υπολογίζει πόσες σφήκες θα σκοτωθούν από την φωλιά {@code waspNest} αν σκάσει η βόμβα {@code bomb}
     * @param waspNest Η φωλιά που μας ενδιαφέρει
     * @param bomb Η βόμβα που θα σκάσει
     * @return Τον αριθμό των σφηκών που θα πεθάνουν
     */
    private double getBombNestKills(WaspNest waspNest, Bomb bomb) {
        return waspNest.getWasps() * (maxDist / (20 * distance(waspNest, bomb) + 0.00001));
    }

    /**
     * Υπολογίζει πόσες σφήκες θα πεθάνουν συνολικά αν σκάσει η βόμβα {@code bomb}
     * @param bomb Η βόμβα που θα σκάσει
     * @return Τον συνολικό αριθμό των σφηκών που θε πεθάνουν
     */
    public double getBombTotalKills(Bomb bomb) {
        double kills = 0;
        for (WaspNest waspNest : map) {
            kills += getBombNestKills(waspNest, bomb);
            waspNest.killWasps((int)Math.round(kills));
        }

        return kills;
    }

    /**
     * Επιστρέφει τον αριθμό των ζωντανών σφηκών στην αρχική τους κατάσταση
     */
    public void restoreMap() {
        for (int i = 0; i < map.size(); i++) {
            map.get(i).setWasps(mapCopy.get(i).getWasps());
        }
    }
}

public class Distance {
    private static double[][][][] distanceCache;

    /**
     * Υπολογίζει όλες τις πιθανές αποστάσεις. Απαιτεί πολύ μνήμη.
     * @param maxX Μήκος του χάρτη
     * @param maxY Πλάτος του χάρτη
     */
    @Deprecated
    public static void initDistance(int maxX, int maxY) {
        System.out.println("Calculating distances...");
        distanceCache = new double[maxX+1][maxY+1][maxX+1][maxY+1];
        System.out.println(distanceCache[0][0][0][0]);
        for (int x1 = 0; x1 <= 100; x1++) {
            for (int y1 = 0; y1 <= 100; y1++) {
                for (int x2 = 0; x2 <= 100; x2++) {
                    for (int y2 = 0; y2 <= 100; y2++) {
                        if (distanceCache[x2][y2][x1][y1] == 0) {
                            distanceCache[x1][y1][x2][y2] = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                            distanceCache[x2][y2][x1][y1] = distanceCache[x1][y1][x2][y2];
                        }
                    }
                }
            }
        }
        System.out.println("Distances calculated.");
    }

    /**
     * Υπολογίζει την απόσταση μεταξύ δύο σημείων
     * @param x1 Η τετμημένη του πρώτου σημείου
     * @param y1 Η τεταγμένη του πρώτου σημείου
     * @param x2 Η τετμημένη του δεύτερου σημείου
     * @param y2 Η τεταγμένη του δεύτερου σημείου
     * @return Την απόσταση των δύο σημείων
     */
    public static double getDistance(int x1, int y1, int x2, int y2) {
//        if (distanceCache[x1][y1][x2][y2] == 0) {
//            distanceCache[x1][y1][x2][y2] = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
//        }
//        return distanceCache[x1][y1][x2][y2];
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Υπολογίζει την απόσταση μεταξύ δύο σημείων
     * @param point1 Το πρώτο σημείο
     * @param point2 Το δεύτερο σημείο
     * @return Την απόσταση των σημείων {@code point1} με {@code point2}
     */
    public static double getDistance(ICoordinates point1, ICoordinates point2) {
        return getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }
}

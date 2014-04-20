import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String args[]) {
        MapController mapController = new MapController(100, 100);

        mapController.addWaspNet(new WaspNest(25, 65, 100));
        mapController.addWaspNet(new WaspNest(23, 8, 200));
        mapController.addWaspNet(new WaspNest(7, 13, 327));
        mapController.addWaspNet(new WaspNest(95, 53, 440));
        mapController.addWaspNet(new WaspNest(3, 3, 450));
        mapController.addWaspNet(new WaspNest(54, 56, 639));
        mapController.addWaspNet(new WaspNest(67, 78, 650));
        mapController.addWaspNet(new WaspNest(32, 4, 678));
        mapController.addWaspNet(new WaspNest(24, 76, 750));
        mapController.addWaspNet(new WaspNest(66, 89, 801));
        mapController.addWaspNet(new WaspNest(84, 4, 945));
        mapController.addWaspNet(new WaspNest(34, 23, 967));

        System.out.println("begin " + System.currentTimeMillis());

        double best = 0;

        for (int x1 = 0; x1 < 100; x1++) {
            for (int y1 = 0; y1 < 100; y1++) {
                Bomb bomb1 = new Bomb(x1, y1);
                for (int x2 = 0; x2 < 100; x2++) {
                    for (int y2 = 0; y2 < 100; y2++) {
                        Bomb bomb2 = new Bomb(x2, y2);
                        writeToFile("currPosition.txt", x1 + "," + y1 + "," + x2 + "," + y2 + ",0,0", false);
                        for (int x3 = 0; x3 < 100; x3++) {
                            for (int y3 = 0; y3 < 100; y3++) {

                                Bomb bomb3 = new Bomb(x3, y3);

                                double total = 0;
                                total += mapController.getBombTotalKills(bomb1);
                                total += mapController.getBombTotalKills(bomb2);
                                total += mapController.getBombTotalKills(bomb3);

                                if (total > best) {
                                    System.out.println(new StringBuilder().append("Found new best position: (").append(bomb1.getX()).append(", ").append(bomb1.getY()).append("), (").append(bomb2.getX()).append(", ").append(bomb2.getY()).append("), (").append(bomb3.getX()).append(", ").append(bomb3.getY()).append("): ").append(total).toString());
                                    writeToFile("bestSoFar.txt", new StringBuilder().append("(").append(bomb1.getX()).append(", ").append(bomb1.getY()).append("), (").append(bomb2.getX()).append(", ").append(bomb2.getY()).append("), (").append(bomb3.getX()).append(", ").append(bomb3.getY()).append("): ").append(total).toString(), false);
                                    best = total;
                                }

                                mapController.restoreMap();
                                //System.out.println(new StringBuilder().append(bomb1.getX()).append(",").append(bomb1.getY()).append(",").append(bomb2.getX()).append(",").append(bomb2.getY()).append(",").append(bomb3.getX()).append(",").append(bomb3.getY()).append(",").append(total).toString());
                            }
                        }
                    }
                }
                System.out.println("y1 = " + y1 + " time:" + System.currentTimeMillis());
            }
            System.out.println("x1 = " + x1 + " time:" + System.currentTimeMillis());
        }
    }

    public static boolean writeToFile(String filename, String contents, boolean append) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, append)))) {
            out.print(contents);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}

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

        int x1Args = 0;
        int y1Args = 0;
        int x2Args = 0;
        int y2Args = 0;
        double best = 0;

        if (args.length == 5) {
            x1Args = Integer.parseInt(args[0]);
            y1Args = Integer.parseInt(args[1]);
            x2Args = Integer.parseInt(args[2]);
            y2Args = Integer.parseInt(args[3]);
            best = Double.parseDouble(args[4]);
        }

        System.out.println("begin " + System.currentTimeMillis());

        Bomb bomb1 = new Bomb(0, 0);
        Bomb bomb2 = new Bomb(0, 0);
        Bomb bomb3 = new Bomb(0, 0);

        for (int x1 = 0; x1 < 100; x1++) {
            if (x1Args > x1) {
                x1 = x1Args;
                x1Args = 0;
            }
            bomb1.setX(x1);
            for (int y1 = 0; y1 < 100; y1++) {
                if (y1Args > y1) {
                    y1 = y1Args;
                    y1Args = 0;
                }
                bomb1.setY(y1);
                double totalBomb1 = mapController.getBombTotalKills(bomb1);
                mapController.saveMap(1);
                for (int x2 = 0; x2 < 100; x2++) {
                    if (x2Args > x2) {
                        x2 = x2Args;
                        x2Args = 0;
                    }
                    bomb2.setX(x2);
                    for (int y2 = 0; y2 < 100; y2++) {
                        if (y2Args > y2) {
                            y2 = y2Args;
                            y2Args = 0;
                        }
                        bomb2.setY(y2);
                        double totalBomb2 = totalBomb1 + mapController.getBombTotalKills(bomb2);
                        mapController.saveMap(2);
                        writeToFile("currPosition.txt", x1 + "," + y1 + "," + x2 + "," + y2 + ",0,0", false);
                        for (int x3 = 0; x3 < 100; x3++) {
                            bomb3.setX(x3);
                            for (int y3 = 0; y3 < 100; y3++) {
                                bomb3.setY(y3);

                                double totalBomb3 = totalBomb2 + mapController.getBombTotalKills(bomb3);

                                if (totalBomb3 > best) {
                                    System.out.println(new StringBuilder().append("Found new best position: (").append(bomb1.getX()).append(", ").append(bomb1.getY()).append("), (").append(bomb2.getX()).append(", ").append(bomb2.getY()).append("), (").append(bomb3.getX()).append(", ").append(bomb3.getY()).append("): ").append(totalBomb3).toString());
                                    writeToFile("bestSoFar.txt", new StringBuilder().append("(").append(bomb1.getX()).append(", ").append(bomb1.getY()).append("), (").append(bomb2.getX()).append(", ").append(bomb2.getY()).append("), (").append(bomb3.getX()).append(", ").append(bomb3.getY()).append("): ").append(totalBomb3).toString(), false);
                                    best = totalBomb3;
                                }

                                mapController.restoreMap(2);
                                //System.out.println(new StringBuilder().append(bomb1.getX()).append(",").append(bomb1.getY()).append(",").append(bomb2.getX()).append(",").append(bomb2.getY()).append(",").append(bomb3.getX()).append(",").append(bomb3.getY()).append(",").append(total).toString());
                            }
                        }
                        mapController.restoreMap(1);
                    }
                }
                mapController.restoreMap(0);
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

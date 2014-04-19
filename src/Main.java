/**
 * Created by Mike on 19/4/2014.
 */
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

        System.out.println(mapController.getMaxDist());
    }
}

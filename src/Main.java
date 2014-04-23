import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUM_THREADS = 4;

    public static void main(String args[]) {
        Distance.initDistance(100, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

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

            //FIXME: Check range
            for (int i = 0; i < NUM_THREADS; i++) {
                Runnable worker = new MyTask(x1Args + i, y1Args, x2Args, y2Args);
                executor.execute(worker);
            }
            x1Args += 4;
        }

        for (int i = x1Args; i <= 100; i++) {
            Runnable worker = new MyTask(i, 0, 0, 0);
            executor.execute(worker);
        }
    }
}

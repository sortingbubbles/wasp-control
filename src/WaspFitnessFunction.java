import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class WaspFitnessFunction extends FitnessFunction {
    private MapController mapController;

    public WaspFitnessFunction(MapController mapController) {
        this.mapController = mapController;
    }

    @Override
    protected double evaluate(IChromosome a_subject) {
        double totalKills;
        totalKills = mapController.getBombTotalKills(getBombFromChromosome(a_subject, 0));
        totalKills += mapController.getBombTotalKills(getBombFromChromosome(a_subject, 1));
        totalKills += mapController.getBombTotalKills(getBombFromChromosome(a_subject, 2));

        mapController.restoreMap(0);

        return totalKills;
    }

    public static Bomb getBombFromChromosome(IChromosome potentialSolution, int position) {
        int x = (Integer) potentialSolution.getGene(position * 2).getAllele();
        int y = (Integer) potentialSolution.getGene(position * 2 + 1).getAllele();

        return new Bomb(x, y);
    }
}

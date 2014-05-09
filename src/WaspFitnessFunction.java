import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class WaspFitnessFunction extends FitnessFunction {
    private MapController mapController;

    public WaspFitnessFunction(MapController mapController) {
        this.mapController = mapController;
    }

    /**
     * Επιστρέφει το πόσες σφήκες σκοτώνει το συγκεκριμένο χρωμόσωμα
     *
     * @param a_subject Το χρωμόσωμα
     * @return Πόσες σφήκες σκοτώνει το συγκεκριμένο χρωμόσωμα
     */
    @Override
    protected double evaluate(IChromosome a_subject) {
        double totalKills;
        totalKills = mapController.getBombTotalKills(getBombFromChromosome(a_subject, 0)); //έκρηξη πρώτης βόμβας
        totalKills += mapController.getBombTotalKills(getBombFromChromosome(a_subject, 1)); //έκρηξη δεύτερης βόμβας
        totalKills += mapController.getBombTotalKills(getBombFromChromosome(a_subject, 2)); //έκρηξη τρίτης βόμβας

        mapController.restoreMap(0); // επαναφορά του χάρτη στην αρχική του κατάσταση

        return totalKills;
    }

    /**
     * Παράγει αντικείμενο τύπου Bomb από χρωμόσωμα
     *
     * @param potentialSolution Το χρωμόσωμα
     * @param position          Ο αριθμός της βόμβας που ζητείται
     * @return Η βόμβα που βρίσκεται στη θέση position
     */
    public static Bomb getBombFromChromosome(IChromosome potentialSolution, int position) {
        int x = (Integer) potentialSolution.getGene(position * 2).getAllele();
        int y = (Integer) potentialSolution.getGene(position * 2 + 1).getAllele();

        return new Bomb(x, y);
    }
}

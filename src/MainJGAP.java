import org.jgap.*;
import org.jgap.event.EventManager;
import org.jgap.impl.*;

import java.io.File;

public class MainJGAP {
    /**
     * Το μέγεθος του πληθυσμού
     */
    private static final int POPULATION_SIZE = 100;

    /**
     * Πόσες φορές θα γίνει δειγματοληψία για τον υπολογισμό του μέσου όρου (όπου χρειάζεται)
     */
    private static final int SAMPLES = 100;

    /**
     * Μέγιστος αριθμός γενεών
     */
    private static final int MAX_EVOLUTIONS = 1000;

    /**
     * Υπολογίζει την λύση του προβλήματος
     *
     * @param conf           Αντικείμενο ρυθμίσεων
     * @param populationSize Μέγεθος πληθυσμού
     * @param maxEvolutions  Μέγιστος αριθμός γενεών
     * @param mapController  Χάρτης με τις τοποθεσίες των σφηκοφολιών
     * @return Πίνακας που περιέχει το καλύτερο χρωμόσωμα κάθε γενιάς
     * @throws Exception
     */
    public static IChromosome[] exterminate(Configuration conf, int populationSize, int maxEvolutions, MapController mapController) throws Exception {
        IChromosome[] results = new IChromosome[maxEvolutions]; // Πίνακας που περιέχει το καλύτερο χρωμόσωμα κάθε γενιάς

        // Το χρωμόσωμα θα αποτελέιται από 6 γονίδια. Τα δύο πρώτα θα αποτελούν τις συντεταγμένες της πρώτης βόμβας,
        // τα επόμενα δύο τις συντεταγμένες της δεύτερης βόμβας, και τα υπόλοιπα δύο τις συντεταγμένες της τρίτης βόμβας
        Gene[] bombGenes = new Gene[6];
        bombGenes[0] = new IntegerGene(conf, 0, mapController.getMaxX()); //x1
        bombGenes[1] = new IntegerGene(conf, 0, mapController.getMaxY()); //y1
        bombGenes[2] = new IntegerGene(conf, 0, mapController.getMaxX()); //x2
        bombGenes[3] = new IntegerGene(conf, 0, mapController.getMaxY()); //y2
        bombGenes[4] = new IntegerGene(conf, 0, mapController.getMaxX()); //x3
        bombGenes[5] = new IntegerGene(conf, 0, mapController.getMaxY()); //y3

        IChromosome bombsChromosome = new Chromosome(conf, bombGenes);
        conf.setSampleChromosome(bombsChromosome);

        // Ορίζεται το μέγεθος του πληθυσμού
        conf.setPopulationSize(populationSize);

        // Δημιουργείται ο τυχαίος πληθυσμός
        Genotype population = Genotype.randomInitialGenotype(conf);

        for (int i = 0; i < maxEvolutions; i++) {
            population.evolve(); // Εξέλιξη του πληθυσμού
            IChromosome bestSolutionSoFar = population.getFittestChromosome(); // Παίρνουμε το καλύτερο χρωμόσωμα...
            results[i] = bestSolutionSoFar; // ... και το τοποθετούμε στον πίνακα με τα καλύτερα χρωμοσώματα κάθε γενιάς
        }
        return results;
    }

    /**
     * Δημιουργεί αντικείμενο που περιγράφει τις παραμέτρους που θα χρησιμοποιηθούν για την επίλυση του προβλήματος
     *
     * @param mapController Χάρτης με τις τοποθεσίες των σφηκοφολιών
     * @param crossoverRate Ποσοστό ανασυνδυασμού
     * @param mutationRate  Ποσοστό μετάλλαξης
     * @return Αντικείμενο ρυθμήσεων
     * @throws InvalidConfigurationException
     */
    public static Configuration getConfiguration(MapController mapController, double crossoverRate, int mutationRate) throws InvalidConfigurationException {
        //Δημιουργία αντικειμένου ρυθμήσεων
        Configuration conf = new Configuration();
        try {
            conf.setBreeder(new GABreeder());
            conf.setRandomGenerator(new StockRandomGenerator());
            conf.setEventManager(new EventManager());
            conf.setMinimumPopSizePercent(0);
            //
            conf.setSelectFromPrevGen(1.0d);
            conf.setKeepPopulationSizeConstant(true);
            conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
            conf.setChromosomePool(new ChromosomePool());
            conf.addGeneticOperator(new CrossoverOperator(conf, crossoverRate)); // ορισμός ποσοστού ανασυνδυασμού
            conf.addGeneticOperator(new MutationOperator(conf, mutationRate)); // ορισμός ποσοστού μετάλλαξης
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException("Κάτι πήγε στραβά!");
        }

        conf.setPreservFittestIndividual(true); // ενεργοποίηση ελιτισμού
        conf.setKeepPopulationSizeConstant(false); // σταθερό μέγεθος πληθυσμού

        // ορισμός συνάρτησης καταλληλότητας
        FitnessFunction myFunc = new WaspFitnessFunction(mapController);
        conf.setFitnessFunction(myFunc);

        return conf;
    }

    public static void main(String[] args) throws Exception {
//        Distance.initDistance(100, 100);
        MapController mapController = new MapController(new File("map.csv")); // φόρτωμα του χάρτη

        mapController.initSave(1); // αρχικοποίηση της δυνατότητας αποθήκευσης της κατάστασης του χάρτη
        mapController.saveMap(0); // αποθήκευση του χάρτη στη μνήμη

        printBestSolution(mapController);
    }

    /**
     * Εκτελεί τον γενετικό αλγόριθμο και εμφανίζει την καλύτερη λύση που βρέθηκε
     *
     * @param mapController Χάρτης με τις τοποθεσίες των σφηκοφολιών
     * @throws Exception
     */
    private static void printBestSolution(MapController mapController) throws Exception {
        Configuration conf = getConfiguration(mapController, 0.80d, 12);
        BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(conf, 0.90d);
        bestChromsSelector.setDoubletteChromosomesAllowed(true);
        conf.addNaturalSelector(bestChromsSelector, false);

        IChromosome results[] = exterminate(conf, POPULATION_SIZE, MAX_EVOLUTIONS, mapController);
        IChromosome bestSolution = results[results.length - 1];
        System.out.println("Η λύση που βρέθηκε σκοτώνει " + (int) bestSolution.getFitnessValue() + " σφήκες αν οι βόμβες τοποθετηθούν στις θέσεις:");
        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolution, 0));
        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolution, 1));
        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolution, 2));

    }

    /**
     * Εκτελεί τον γενετικό αλγόριθμο και εμφανίζει τον μέσο όρο του fitness κάθε γενιάς
     *
     * @param mapController Χάρτης με τις τοποθεσίες των σφηκοφολιών
     * @throws Exception
     */
    private static void averageSelectorFitness(MapController mapController) throws Exception {
        double avg[] = new double[MAX_EVOLUTIONS];

        double total[] = new double[MAX_EVOLUTIONS];
        for (int j = 0; j < SAMPLES; j++) {
            Configuration.reset();

            Configuration conf = getConfiguration(mapController, 0.80d, 12);
            BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(conf, 0.90d);
            bestChromsSelector.setDoubletteChromosomesAllowed(true);
            conf.addNaturalSelector(bestChromsSelector, false);

//            NaturalSelector weightedRouletteSelector = new WeightedRouletteSelector(conf);
//            conf.addNaturalSelector(weightedRouletteSelector, false);

//            TournamentSelector tournamentSelector = new TournamentSelector(conf, 10, 0.8);
//            conf.addNaturalSelector(tournamentSelector, false);

            IChromosome results[] = exterminate(conf, POPULATION_SIZE, MAX_EVOLUTIONS, mapController);
            for (int i = 0; i < MAX_EVOLUTIONS; i++) {
                total[i] += results[i].getFitnessValue();
            }
            System.out.println(j);
        }
        for (int i = 0; i < MAX_EVOLUTIONS; i++) {
            avg[i] = total[i] / SAMPLES;
            System.out.println(i + "," + avg[i]);
        }
    }

    /**
     * Εκτελεί τον γενετικό αλγόριθμο και εμφανίζει τον μέσο όρο του καλύτερου fitness, αλλάζοντας κάθε φορά
     * το ποσοστό ανασυνδυασμού
     *
     * @param mapController Χάρτης με τις τοποθεσίες των σφηκοφολιών
     * @throws Exception
     */
    private static void crossoverRateTest(MapController mapController) throws Exception {
        final int POPULATION_SIZE = 20;
        final int SAMPLES = 1000;
        final int MAX_EVOLUTIONS = 10;

        double avg[] = new double[101];
        for (int i = 1; i <= 100; i++) {
            double total = 0;
            for (int j = 0; j < SAMPLES; j++) {
                Configuration.reset();

                Configuration conf = getConfiguration(mapController, i / 100.0, 12);
                BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(conf, 0.90d);
                bestChromsSelector.setDoubletteChromosomesAllowed(true);
                conf.addNaturalSelector(bestChromsSelector, false);

                total += exterminate(conf, POPULATION_SIZE, MAX_EVOLUTIONS, mapController)[MAX_EVOLUTIONS - 1].getFitnessValue();
            }
            avg[i] = total / SAMPLES;
            System.out.println(i + "," + avg[i]);
        }
    }
}

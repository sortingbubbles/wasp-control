import org.jgap.*;
import org.jgap.audit.EvolutionMonitor;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class MainJGAP {
    public static EvolutionMonitor m_monitor;
    private static final int MAX_ALLOWED_EVOLUTIONS = 1000;

    public static double exterminate(Configuration conf, int populationSize, int maxEvolutions, MapController mapController, boolean a_doMonitor) throws Exception {

        if (a_doMonitor) {
            // Turn on monitoring/auditing of evolution progress.
            // --------------------------------------------------
            m_monitor = new EvolutionMonitor();
            conf.setMonitor(m_monitor);
        }
        // Now we need to tell the Configuration object how we want our
        // Chromosomes to be setup. We do that by actually creating a
        // sample Chromosome and then setting it on the Configuration
        // object. As mentioned earlier, we want our Chromosomes to each
        // have four genes, one for each of the coin types. We want the
        // values (alleles) of those genes to be integers, which represent
        // how many coins of that type we have. We therefore use the
        // IntegerGene class to represent each of the genes. That class
        // also lets us specify a lower and upper bound, which we set
        // to sensible values for each coin type.
        // --------------------------------------------------------------
        Gene[] bombGenes = new Gene[6];
        bombGenes[0] = new IntegerGene(conf, 0, mapController.getMaxX()); //x1
        bombGenes[1] = new IntegerGene(conf, 0, mapController.getMaxX()); //y1
        bombGenes[2] = new IntegerGene(conf, 0, mapController.getMaxX()); //x2
        bombGenes[3] = new IntegerGene(conf, 0, mapController.getMaxX()); //y2
        bombGenes[4] = new IntegerGene(conf, 0, mapController.getMaxX()); //x3
        bombGenes[5] = new IntegerGene(conf, 0, mapController.getMaxX()); //y3

        IChromosome bombsChromosome = new Chromosome(conf, bombGenes);
        conf.setSampleChromosome(bombsChromosome);
        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the answer), but the longer it will take to evolve
        // the population (which could be seen as bad).
        // ------------------------------------------------------------
        conf.setPopulationSize(populationSize);

        // Now we initialize the population randomly, anyway (as an example only)!
        // If you want to load previous results from file, remove the next line!
        // -----------------------------------------------------------------------
        Genotype population;
        population = Genotype.randomInitialGenotype(conf);
        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < maxEvolutions; i++) {
//            if (!uniqueChromosomes(population.getPopulation())) {
//                throw new RuntimeException("Invalid state in generation "+i);
//            }
            if (m_monitor != null) {
                population.evolve(m_monitor);
            } else {
                population.evolve();
            }
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("Total evolution time: " + (endTime - startTime)
//                + " ms");

        // -----------------------------------
        IChromosome bestSolutionSoFar = population.getFittestChromosome();
//        System.out.println("The best solution has a fitness value of " +
//                bestSolutionSoFar.getFitnessValue());
//        bestSolutionSoFar.setFitnessValueDirectly(-1);
//        System.out.println("It contains the following: ");
//        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolutionSoFar, 0));
//        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolutionSoFar, 1));
//        System.out.println("\t" + WaspFitnessFunction.getBombFromChromosome(bestSolutionSoFar, 2));

        return bestSolutionSoFar.getFitnessValue();
    }

    public static Configuration getConfiguration(MapController mapController) throws InvalidConfigurationException {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        Configuration conf = new DefaultConfiguration();
        // Care that the fittest individual of the current population is
        // always taken to the next generation.
        // Consider: With that, the pop. size may exceed its original
        // size by one sometimes!
        // -------------------------------------------------------------
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(false);
        // Set the fitness function we want to use, which is our
        // MinimizingMakeChangeFitnessFunction. We construct it with
        // the target amount of change passed in to this method.
        // ---------------------------------------------------------
        FitnessFunction myFunc = new WaspFitnessFunction(mapController);
        conf.setFitnessFunction(myFunc);

        return conf;
    }

    public static void main(String[] args) throws Exception {
        Distance.initDistance(100, 100);
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
        mapController.initSave(1);
        mapController.saveMap(0);



        double avg[] = new double[95];
        for (int i = 5; i < 100; i++) {
            double total = 0;
            for (int j = 0; j < 100; j++) {
                Configuration.reset();
                total += exterminate(getConfiguration(mapController), i, 100, mapController, false);
            }
            avg[i-5] = total/100;
            System.out.println(i + ": " + avg[i-5]);
        }



    }
}

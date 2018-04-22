package population;

import aiUtilities.LearningMaterial;
import genotyp.Genotype;
import genotyp.Pool;
import main.Settings;
import siec.SiecNeuronowa;

import java.util.ArrayList;

public class PopulationController
{
    private int generation = 0;
    private double acceptanceLevel = 0.1;
    private LearningMaterial lm;
    private ArrayList<Genotype> organisms;
    private Population currentPopulation;
    private Population previousPopulation;
    private Genotype bestObject = null;

    public void init()
    {
        Pool pool = Pool.getInstance();
        for(int i = 0; i<Settings.NO_OF_INPUT; i++)
            pool.addNode(Pool.INPUT_LAYER);
        for(int i = 0; i<Settings.NO_OF_OUTPUT; i++)
            pool.addNode(Pool.OUTPUT_LAYER);
        organisms = new ArrayList<>();
        for(int i = 0;i< Settings.NO_OF_OBJECTS;i++)
            organisms.add(new Genotype().startInit());
        lm = new LearningMaterial();
    }
    public PopulationController()
    {

    }

    public int getNO_OF_INPUT() {
        return Settings.NO_OF_INPUT;
    }

    public void setNO_OF_INPUT(int NO_OF_INPUT) {
        Settings.NO_OF_INPUT = NO_OF_INPUT;
    }

    public int getNO_OF_OUTPUT() {
        return Settings.NO_OF_OUTPUT;
    }

    public void setNO_OF_OUTPUT(int NO_OF_OUTPUT) {
        Settings.NO_OF_OUTPUT = NO_OF_OUTPUT;
    }

    public int getNO_OF_OBJECTS() {
        return Settings.NO_OF_OBJECTS;
    }

    public void setNO_OF_OBJECTS(int NO_OF_OBJECTS) {
        Settings.NO_OF_OBJECTS = NO_OF_OBJECTS;
    }

    public double getCHANGE_WEIGHT_CHANCE() {
        return Settings.CHANGE_WEIGHT_CHANCE;
    }

    public void setCHANGE_WEIGHT_CHANCE(double CHANGE_WEIGHT_CHANCE) {
        Settings.CHANGE_WEIGHT_CHANCE = CHANGE_WEIGHT_CHANCE;
    }

    public double getNEW_NODE_CHANCE() {
        return Settings.NEW_NODE_CHANCE;
    }

    public void setNEW_NODE_CHANCE(double NEW_NODE_CHANCE) {
        Settings.NEW_NODE_CHANCE = NEW_NODE_CHANCE;
    }

    public double getNEW_CONNECTION_CHANCE() {
        return Settings.NEW_CONNECTION_CHANCE;
    }

    public void setNEW_CONNECTION_CHANCE(double NEW_CONNECTION_CHANCE) {
        Settings.NEW_CONNECTION_CHANCE = NEW_CONNECTION_CHANCE;
    }

    public void evaluatePopulation()
    {
        ArrayList<Genotype> sortedPopulationByPerformace = new ArrayList<>();
        for(int i=0;i<organisms.size();i++)
        {
            Genotype object = organisms.get(i);
            SiecNeuronowa sn = new SiecNeuronowa(object);
            double score=0;
            for(int c=0;c<4;c++)
            {
                sn.mysl(lm.getLearningData(c));
                ArrayList <Double> results = sn.getWynik();
                ArrayList <Double> wantedResults = lm.getResults(c);
                for(int r=0;r<results.size();r++)
                {
                    double difference = wantedResults.get(r)-results.get(r);
                    //System.out.println("Difference: " + difference + " from: " + wantedResults.get(r) + "-"+results.get(r));
                    score += Math.pow(difference,2);
                }
                //System.out.println("Score: " + score);
            }
            object.setScore(1000/score);
            int counter = 0;
            if(sortedPopulationByPerformace.size()==0) sortedPopulationByPerformace.add(object);
            else {
                boolean added = false;
                for (Genotype g : sortedPopulationByPerformace) {
                    if (g.getScore() < object.getScore()) {
                        {
                            added=true;
                            sortedPopulationByPerformace.add(counter, object);
                            break;
                        }
                    } else counter++;
                }
                if(!added)
                {
                    sortedPopulationByPerformace.add(object);
                }
            }
        }
        organisms = sortedPopulationByPerformace;
        bestObject = organisms.get(0);
        speciate();
        //System.out.println("Generation: " + generation++ + " Organism count: " + organisms.size() + " Species number: " + currentPopulation.getSpeciesNumber() + ", Best: " + organisms.get(0).getScore());
        //currentPopulation.showSpeciesScore();
        organisms = currentPopulation.reproduce();
        //System.out.println("Proceeding to next generation");
    }

    private void speciate()
    {
        Population previous, current;
        if(generation==0)
        {
            previousPopulation = new Population();
            currentPopulation = previousPopulation;
            previous = previousPopulation;
            current = previous;
        }
        else
        {
            previousPopulation = currentPopulation;
            currentPopulation = new Population();
            current = currentPopulation;
            previous = previousPopulation;
        }
        for(Genotype organism : organisms)
        {
            current.addOranismToSpecies(previous.findSpeciesId(organism),organism);
        }
    }

    public void showResultsOfBest()
    {
        System.out.println(bestObject);
        SiecNeuronowa sn = new SiecNeuronowa(bestObject);
        for(int c=0;c<4;c++)
        {
            sn.mysl(lm.getLearningData(c));
            ArrayList <Double> results = sn.getWynik();
            ArrayList <Double> wantedResults = lm.getResults(c);
            System.out.println("Input: " + lm.getLearningData(c).get(0) + "," + lm.getLearningData(c).get(1));

            for(int r=0;r<results.size();r++)
            {
                System.out.println("Output wanted: " + wantedResults.get(0) + ", output got: " + results.get(0));
            }
        }
        System.out.println(bestObject.getScore());
    }

    public Genotype getBest() {
        return bestObject;
    }
}

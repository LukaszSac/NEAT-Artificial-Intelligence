package population;

import Randomizer.Generator;
import genotyp.Crossbreed;
import genotyp.Gen;
import genotyp.Genotype;
import genotyp.mutations.Mutation;
import main.Settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Population
{
    private static int speciesId = 0;
    private int noOfPopulation = 0;
    private HashMap<Integer, ArrayList<Genotype>> species;

    public Population()
    {
        species = new HashMap<>();
    }

    public int findSpeciesId(Genotype g)
    {
        for(Map.Entry<Integer,ArrayList<Genotype>> entry : species.entrySet())
        {
            Integer currentSpeciesId = entry.getKey();
            Genotype currentSpeciesRepresentant = entry.getValue().get(0);
            double distance = DistanceCalculator.distance(currentSpeciesRepresentant,g);
            if(distance<= Settings.DISTANCE_TRESHOLD) return currentSpeciesId;
        }
        return speciesId++;
    }

    public void addOranismToSpecies(int speciesId, Genotype g)
    {
        noOfPopulation ++;
        ArrayList<Genotype> currentSpecies = null;
        if(species.containsKey(speciesId))
            currentSpecies = species.get(speciesId);
        else
        {
            currentSpecies = new ArrayList<>();
            species.put(speciesId,currentSpecies);
        }
        currentSpecies.add(g);
    }

    public int getSpeciesNumber()
    {
        return species.size();
    }

    public void showSpeciesScore()
    {
        for(ArrayList<Genotype> ag : species.values())
        {
            System.out.println("New Population: ");
            for(Genotype g : ag)
            {
                System.out.println(g.getScore());
            }
            System.out.println("");
        }
    }

    public ArrayList<Genotype> reproduce()
    {
        ArrayList<Genotype> champions = new ArrayList<>();
        ArrayList<Genotype> population = new ArrayList<>();
        HashMap<Integer,Double> avgOfSpecies = new HashMap<>();
        double totalPopulationFitnessAverage = 0;
        for(Map.Entry<Integer,ArrayList<Genotype>> entry : species.entrySet())
        {
            double totalScore = 0;
            for(Genotype organism : entry.getValue())
            {
                totalScore+=organism.getScore();
            }
            double averageOfOrganisms = totalScore/entry.getValue().size();
            totalPopulationFitnessAverage+= averageOfOrganisms;
            avgOfSpecies.put(entry.getKey(),averageOfOrganisms);
        }
        for(Map.Entry<Integer,Double> entry : avgOfSpecies.entrySet())
        {
            double noOfOffsprings = entry.getValue();
            noOfOffsprings*=noOfPopulation;
            noOfOffsprings/=totalPopulationFitnessAverage;
            int currentCount = 1;
            //Carry champions
            int championsCount = 0;
            ArrayList<Genotype> organisms = species.get(entry.getKey());
            for(Genotype g : organisms)
            {
                champions.add(g);
                currentCount++;
                if(++championsCount==Settings.CHAMPION_COUNT||championsCount>=Math.round(noOfOffsprings)) break;
            }
            while(currentCount<=Math.round(noOfOffsprings))
            {
                population.add(Crossbreed.cross(organisms.get(Generator.noBetween(0,(int)Math.floor(organisms.size()*Settings.MORTALITY_RATE))),organisms.get(Generator.noBetween(0,(int)Math.floor(organisms.size()*Settings.MORTALITY_RATE)))));
                currentCount++;
            }
            //System.out.println(currentCount + " " + noOfOffsprings);
        }
        for(Genotype g : population)
        {
            Mutation mutation = Generator.getRandomMutation();
            if(mutation!=null)
            {
                g.mutate(mutation);
            }
        }
        for(Genotype g : champions) population.add(g);
        return population;
    }

}



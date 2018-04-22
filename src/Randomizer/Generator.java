package Randomizer;

import genotyp.mutations.AddNewConnection;
import genotyp.mutations.AddNewNode;
import genotyp.mutations.ChangeGeneWeight;
import genotyp.mutations.Mutation;
import main.Settings;

import java.util.Random;
import java.util.Set;

public class Generator
{
    private static Random gen = new Random();
    public static int noBetween(int left, int right)
    {
        return gen.nextInt(right-left+1)+left;
    }

    public static int noBetweenNieWlacznie(int left, int right) throws CannotGenerateNumber
    {
        //System.out.println("Randoming: " + left + " " + right);
        if(right-left<=1) throw new CannotGenerateNumber();
        if(right-left==2) return left+1;
        return gen.nextInt(right-left-2)+left+1;
    }

    public static double noDoubleBetween(double left, double right)
    {
        return (gen.nextDouble()*(right-left+1))+left;
    }


    public static Mutation getRandomMutation()
    {
        if(gen.nextDouble()<=Settings.MUTATION_RATE) {
            double rand = gen.nextDouble();

            if (rand <= Settings.NEW_NODE_CHANCE) return new AddNewNode();
            if (rand <= Settings.NEW_CONNECTION_CHANCE + Settings.NEW_NODE_CHANCE) return new AddNewConnection();
            else return new ChangeGeneWeight();
        }
        return null;
    }
}

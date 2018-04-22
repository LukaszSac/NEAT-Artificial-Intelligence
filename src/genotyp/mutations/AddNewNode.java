package genotyp.mutations;

import Randomizer.CannotGenerateNumber;
import Randomizer.Generator;
import genotyp.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddNewNode implements Mutation
{

    private int randomSetElement(Set<Integer> set)
    {
        int randElement = Generator.noBetween(0,set.size()-1);
        int counter = 0;
        for(Integer i : set)
        {
            if (counter == randElement)
                return i;
            counter++;
        }
        return -1;
    }

    @Override
    public void mutate(Genotype genotyp) {
        Set<Integer> genesAvlb = new HashSet<>();
        for (int i = 0; i < genotyp.getLista().size(); i++) {
            genesAvlb.add(i);
        }
        int random = randomSetElement(genesAvlb);
        genesAvlb.remove(random);
        Gen gen = genotyp.getLista().get(random);
        //System.out.println("Wylosowany: " + gen);
        Pool pool = Pool.getInstance();
        ArrayList<Integer> layers = pool.getLayers();
        int leftLayerOrder = -1, rightLayerOrder = -1;
        int counter = 0;
        //System.out.println("Left and Right Layer: " + gen.fromLayer() + " " + gen.toLayer());
        //if(gen.fromLayer() >= gen.toLayer())System.out.println("Fuck this" + gen);
        boolean found = false;
        for (Integer i : layers) {
            if (i == gen.fromLayer()) {
                leftLayerOrder = counter;
                if (found) break;
                found = true;
            } else if (i == gen.toLayer()) {
                rightLayerOrder = counter;
                if (found) break;
                found = true;
            }
            counter++;
        }
        //System.out.println("Left and Right Layer Order: " + leftLayerOrder + " " + rightLayerOrder);
        int layerBetween = 0;
        try {
            int save;
            save = layerBetween = Generator.noBetweenNieWlacznie(leftLayerOrder, rightLayerOrder);
            counter = 0;
            for (Integer i : layers) {
                if (counter++ == layerBetween)
                {
                    layerBetween = i;
                    break;
                }
            }
            //System.out.println(leftLayerOrder + "-" + rightLayerOrder + " Wylosowane: " + save);
        } catch (CannotGenerateNumber cannotGenerateNumber) {
            //System.out.println("Creating new layer");
            layerBetween = pool.addLayer(gen.fromLayer());

        }
        //System.out.println("New node on layer: " + layerBetween);
        gen.setInactive();
        int newNodeId = pool.addNode(layerBetween);
        pool.addConnection(gen.getFrom(), newNodeId);

        try {
            genotyp.addNewConnection(gen.getFrom(), newNodeId, gen.getSila());
        } catch (ConnectionAlreadyExistsHere connectionAlreadyExistsHere) {
        }

        pool.addConnection(newNodeId, gen.getTo());

        try {
            genotyp.addNewConnection(newNodeId, gen.getTo(), 1);
        } catch (ConnectionAlreadyExistsHere connectionAlreadyExistsHere) {
        }
    }
}

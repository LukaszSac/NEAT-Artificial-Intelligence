package genotyp.mutations;

import Randomizer.Generator;
import genotyp.ConnectionAlreadyExistsHere;
import genotyp.Gen;
import genotyp.Genotype;
import genotyp.Pool;
import main.Settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddNewConnection implements Mutation
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
    public void mutate(Genotype genotyp)
    {
        Set<Integer> layers = genotyp.getLayers();
        layers.remove(Pool.OUTPUT_LAYER);
        int leftLayer = randomSetElement(layers);
        Pool pool = Pool.getInstance();
        ArrayList<Integer> layersIds = pool.getLayers();
        int index = 1;
        for(Integer i : layersIds)
        {
            if(i==leftLayer) break;
            index++;
        }
        Set<Integer>avlb = new HashSet<>();
        for(;index<layersIds.size();index++)
        {
            int layerId = layersIds.get(index);
            if(layers.contains(layerId))
            {
                avlb.add(layerId);
            }
        }
        avlb.add(Pool.OUTPUT_LAYER);
        int rightLayer = randomSetElement(avlb);
        Set<Integer> leftLayerNodesIds = new HashSet<>();
        Set<Integer> rightLayerNodesIds = new HashSet<>();
        if(leftLayer==Pool.INPUT_LAYER) {
            for(int i=0;i<Settings.NO_OF_INPUT;i++)
            {
                leftLayerNodesIds.add(i);
            }
        }
        else {
            for (Gen e : genotyp) {
                if (e.fromLayer() == leftLayer) leftLayerNodesIds.add(e.getFrom());
                if (e.toLayer() == leftLayer) leftLayerNodesIds.add(e.getTo());
            }
        }
        int randomLeftNodeId = randomSetElement(leftLayerNodesIds);
        if(rightLayer==Pool.OUTPUT_LAYER) {
            for(int i=Settings.NO_OF_INPUT;i<Settings.NO_OF_INPUT+Settings.NO_OF_OUTPUT;i++)
            {
                rightLayerNodesIds.add(i);
            }
        }
        for(Gen e : genotyp)
        {
            if(e.getFrom()==randomLeftNodeId)
            {
                if(e.toLayer() == rightLayer)
                    rightLayerNodesIds.remove(e.getTo());
            }
            else
            {
                if(e.toLayer()==rightLayer)
                    rightLayerNodesIds.add(e.getTo());
                if(e.fromLayer()==rightLayer)
                    rightLayerNodesIds.add(e.getFrom());
            }
        }
        if(rightLayerNodesIds.size()==0||leftLayerNodesIds.size()==0);
            //System.out.println("New connection cannot be made");
        else
        {
            int randomRightNodeId = randomSetElement(rightLayerNodesIds);
            try {
                genotyp.addNewConnection(randomLeftNodeId,randomRightNodeId,1);
            } catch (ConnectionAlreadyExistsHere connectionAlreadyExistsHere) {
                //System.out.println("New connection cannot be made - already exists");
            }
        }
    }
}

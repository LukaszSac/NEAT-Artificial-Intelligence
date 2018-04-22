package genotyp.mutations;

import Randomizer.Generator;
import genotyp.Gen;
import genotyp.Genotype;

import java.util.HashSet;
import java.util.Set;

public class ChangeGeneWeight implements Mutation {
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
        double weightChange = Generator.noDoubleBetween(-1.5,1.5);
        gen.setSila(gen.getSila()+weightChange);
    }
}

package genotyp;

import siec.SiecNeuronowa;

public class Crossbreed
{
    public static Genotype cross(Genotype mother, Genotype father)
    {
        double fatherScore = father.getScore();
        double motherScore = mother.getScore();
        GeneList motherList = mother.getLista();
        GeneList fatherList = father.getLista();
        GeneList offspringList = new GeneList();
        int fatherCursor = 0;
        int motherCursor = 0;
        boolean go = true;
        while(go)
        {
            Gen leader = null;
            try {
                Gen f = fatherList.get(fatherCursor);
                Gen m = motherList.get(motherCursor);
                if (f.getIdInnov() == m.getIdInnov()) {
                    if (fatherScore > motherScore)
                        leader = f.copy();
                    else
                        leader = m.copy();
                    motherCursor++;
                    fatherCursor++;
                }
                else if (f.getIdInnov() < m.getIdInnov()) {
                    leader = f.copy();
                    fatherCursor++;
                } else {
                    leader = m.copy();
                    motherCursor++;
                }
            } catch (IndexOutOfBoundsException e)
            {
                if(fatherCursor==fatherList.size()&&motherCursor==motherList.size())break;
                if(fatherCursor>=fatherList.size())
                    leader = motherList.get(motherCursor++).copy();
                else
                    leader = fatherList.get(fatherCursor++).copy();
            }
            //System.out.println("New Offspring: " + leader + " enabled: " +leader.isEnabled());
            offspringList.add(leader);
        }
        return new Genotype(offspringList);
    }
}

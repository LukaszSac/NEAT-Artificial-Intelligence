package population;

import genotyp.Gen;
import genotyp.GeneList;
import genotyp.Genotype;
import main.Settings;

public class DistanceCalculator
{

    public static double distance(Genotype a, Genotype b)
    {
        double avgWeight = 0.0;
        int noOfMatchingGenes=0;
        int noOfDisjointGenes = 0;
        int noOfExcessGenes = 0;
        GeneList aList = a.getLista();
        GeneList bList = b.getLista();
        int n = Math.max(aList.size(),bList.size());
        if(n<=20) n=1;
        int aCursor = 0;
        int bCursor = 0;
        Gen aGen = aList.get(aCursor);
        Gen bGen = bList.get(bCursor);
        boolean aAll = false;
        boolean bAll = false;
        while(true)
        {
            int aInn = aGen.getIdInnov();
            int bInn = bGen.getIdInnov();
            if(aInn<bInn)
            {
                if(aAll)
                {
                    bCursor++;
                    noOfExcessGenes++;
                }
                else
                {
                    aCursor++;
                    noOfDisjointGenes++;
                }
            }
            else if(bInn<aInn)
            {
                if(bAll)
                {
                    aCursor++;
                    noOfExcessGenes++;
                }
                else
                {
                    bCursor++;
                    noOfDisjointGenes++;
                }
            }
            else
            {
                avgWeight+= Math.abs(aGen.getSila()-bGen.getSila());
                noOfMatchingGenes++;
                if(!aAll)
                    aCursor++;
                if(!bAll)
                    bCursor++;
            }
            if(aCursor>=aList.size()) aAll=true;
            else
            {
                aGen = aList.get(aCursor);
            }
            if(bCursor>=bList.size()) bAll=true;
            else
            {
                bGen = bList.get(bCursor);
            }
            if(aAll&bAll) break;
        }

        double excessGenesValue = Settings.COEFFICIENCY_EXCESS_GENES*noOfExcessGenes/n;
        double disjointGenesValue = Settings.COEFFICIENCY_DISJOINT_GENES*noOfDisjointGenes/n;
        double weightDiffValue = Settings.COEFFICIENCY_WEIGHTS*avgWeight/noOfMatchingGenes;
        double distance = excessGenesValue + disjointGenesValue + weightDiffValue;
        return distance;
    }
}

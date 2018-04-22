package genotyp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GeneList implements Iterable<Gen>
{
    private ArrayList<Gen> genes;
    public GeneList()
    {
        genes = new ArrayList<>();
    }

    public void add(Gen e)
    {
        int newid = e.getIdInnov();
        int cursor = 0;
        for(Gen gene : genes)
        {
            int idCursor = gene.getIdInnov();
            if(idCursor > newid) break;
            else cursor ++;
        }
        genes.add(cursor,e);
    }

    public int size()
    {
        return genes.size();
    }

    public Gen get(int i)
    {
        return genes.get(i);
    }

    public String toString()
    {
        String output = "";
        for(Gen gene : genes)
        {
            if(gene.isEnabled())
                output+=gene.getIdInnov() + " from: " + gene.getFrom() + ", to: " + gene.getTo() + ", weight: " + gene.getSila() +"\n";
        }
        return output;
    }
    @Override
    public Iterator<Gen> iterator() {
        return new Iterator<Gen>() {
            private int kursor = 0;
            @Override
            public Gen next() {
                if(this.hasNext()) {
                    Gen gen = genes.get(kursor);
                    kursor ++;
                    return gen;
                }
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext() {
                return genes.size() > kursor;
            }

        };
    }

}

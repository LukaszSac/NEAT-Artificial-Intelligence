package genotyp;

import Randomizer.Generator;
import genotyp.mutations.Mutation;

import java.util.*;

public class Genotype implements Iterable<Gen>
{
    private Set<Integer> layers;
    private double score = 0.0;
    private GeneList lista = null;

    private void init()
    {
        layers = new HashSet<Integer>();
    }
    public Genotype()
    {
        lista = new GeneList();
        init();
    }

    public Genotype(GeneList list)
    {
        this.lista = list;
        init();
        for(Gen e : lista)
        {
            layers.add(e.toLayer());
            layers.add(e.fromLayer());
        }
    }

    public Genotype startInit()
    {
        Pool pool = Pool.getInstance();
        ArrayList<Integer> inputNodes = pool.getLayersNodesIds(Pool.INPUT_LAYER);
        ArrayList<Integer> outputNodes = pool.getLayersNodesIds(Pool.OUTPUT_LAYER);
        int randomInputId = inputNodes.get(Generator.noBetween(0,inputNodes.size()-1));
        int randomOutputId = outputNodes.get(Generator.noBetween(0,outputNodes.size()-1));
        Gen gen = null;
        try {
            gen = addGene(randomInputId,randomOutputId);
        } catch (ConnectionAlreadyExistsHere connectionAlreadyExistsHere) {
        }
        lista.add(gen);
        gen.setSila(Generator.noDoubleBetween(-2,2));
        return this;
    }

    private Gen addGene(int from, int to) throws ConnectionAlreadyExistsHere {
        InformationPackageNewConnection e = Pool.getInstance().addConnection(from,to);
        if(e==null) System.out.println(":/");
        for (Gen g : this)
        {
            if(g.getIdInnov()==e.getIdInnovation())
            {
                throw new ConnectionAlreadyExistsHere();
            }
        }
        //System.out.println("Check in type - receiving, Id: " + e.getIdInnovation() + " from: " + e.getFrom() + " Layer: " + e.getFromLayer() + " To: " + e.getTo() + " Layer: " + e.getToLayer());
        //System.out.println(e);
        Gen gen = new Gen(e);
        layers.add(gen.fromLayer());
        layers.add(gen.toLayer());
        return gen;
    }

    public void addNewConnection(int from, int to, double weight) throws ConnectionAlreadyExistsHere {
        Gen gen = addGene(from,to);
        lista.add(gen);
        gen.setSila(weight);
    }

    public Set<Integer> getLayers() {
        return layers;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void dodajNowyGen(Gen gen)
    {
        lista.add(gen);
    }

    public void mutate(Mutation mutation)
    {
        mutation.mutate(this);
    }

    public void prezentuj()
    {
        System.out.println(lista);
    }

    @Override
    public String toString()
    {
        return lista.toString();
    }

    public GeneList getLista() {
        return lista;
    }

    @Override
    public Iterator<Gen> iterator() {
        return new Iterator<Gen>() {
            private int kursor = 0;
            @Override
            public boolean hasNext() {
                return lista.size() > kursor;
            }

            @Override
            public Gen next() {
                if(this.hasNext()) {
                    Gen gen = lista.get(kursor);
                    kursor ++;
                    return gen;
                }
                throw new NoSuchElementException();
            }
        };
    }
}

package siec;

import genotyp.Gen;
import genotyp.Genotype;
import main.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class SiecNeuronowa
{
    private HashMap <Integer, Neuron> neurony = null;
    private Genotype genotyp = null;
    private ArrayList<Neuron> wyjscia = null;
    private ArrayList<Neuron> wejscia = null;

    private double Score;

    public SiecNeuronowa(Genotype genotyp) {
        this.genotyp = genotyp;
        neurony = new HashMap<>();
        wyjscia = new ArrayList<>();
        wejscia = new ArrayList<>();
        for(int i=0;i<Settings.NO_OF_INPUT;i++)
        {
            wejscia.add(getNeuron(i));
        }
        for(int i=Settings.NO_OF_INPUT;i<Settings.NO_OF_INPUT+Settings.NO_OF_OUTPUT;i++)
        {
            wyjscia.add(getNeuron(i));
        }
        for (Gen gen : genotyp) {
            if (gen.isEnabled())
            {
                int wejscieId = gen.getFrom();
                int wyjscieId = gen.getTo();
                Neuron wejscie = getNeuron(wejscieId), wyjscie = getNeuron(wyjscieId);
                Polaczenie polaczenie = new Polaczenie(gen.getSila(), wyjscie);
                wejscie.addPolaczenie(polaczenie);
            }
        }
    }

    private Neuron getNeuron(int id)
    {
        Neuron neuron;
        if(!neurony.containsKey(id))
            neurony.put(id,neuron = new Neuron(id));
        else
            neuron = neurony.get(id);
        return neuron;
    }

    public void mysl(ArrayList<Double> wektorWejsc)
    {
        int i=0;
        for(;i<Settings.NO_OF_INPUT;i++)
        {
            Neuron neuron = wejscia.get(i);
            neuron.setWartosc(wektorWejsc.get(i));
            neuron.przeslijDalej();
        }
    }

    public ArrayList<Double> getWynik()
    {
        ArrayList<Double> wyniki = new ArrayList<>();
        for(int i=0;i<Settings.NO_OF_OUTPUT;i++)
        {
            wyniki.add(matma.Sigmoid.sigmoidValue(wyjscia.get(i).getWartosc()));
        }
        return wyniki;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public Genotype getGenotyp()
    {
        return genotyp;
    }
}

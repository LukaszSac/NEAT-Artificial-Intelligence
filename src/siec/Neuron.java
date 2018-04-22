package siec;

import matma.Sigmoid;

import java.util.ArrayList;

public class Neuron
{

    public static double activationTreshold = 0.6;
    private int id = -1;
    private ArrayList<Polaczenie> wyjscia = null;
    private int iloscWyjsc = 0;
    private double wartosc = 0;
    private int iloscDostarczonych = 0;
    public Neuron(int id)
    {
        this.id = id;
        wyjscia = new ArrayList<>();
    }

    public void addPolaczenie(Polaczenie polaczenie)
    {
        wyjscia.add(polaczenie);
    }

    public void addIloscWyjsc()
    {
        iloscWyjsc ++;
    }
    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    public double getWartosc()
    {
        return wartosc;
    }

    public void doslijWartosc(double wartosc)
    {
        this.wartosc +=wartosc;
        iloscDostarczonych++;
        if(iloscDostarczonych==iloscWyjsc)
            przeslijDalej();
    }

    public void przeslijDalej()
    {
        wartosc = Sigmoid.sigmoidValue(wartosc);
        for(Polaczenie polaczenie : wyjscia)
            polaczenie.przeslij(wartosc);
    }
}

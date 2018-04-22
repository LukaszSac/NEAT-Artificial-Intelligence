package siec;

public class Polaczenie
{
    private final double moc;
    private Neuron neuron;

    public Polaczenie(double moc, Neuron neuron)
    {
        this.moc = moc;
        this.neuron = neuron;
        neuron.addIloscWyjsc();
    }

    public void przeslij(double wejscie)
    {
        neuron.doslijWartosc(moc*wejscie);
    }
}

package genotyp;

public class Gen
{
    InformationPackageNewConnection infPachakge;
    private double sila;
    private boolean enabled = true;

    public int getFrom() {
        return infPachakge.getFrom();
    }

    public int getTo() {
        return infPachakge.getTo();
    }

    public double getSila() {
        return sila;
    }

    public int getIdInnov() {
        return infPachakge.getIdInnovation();
    }

    public Gen(InformationPackageNewConnection infPackage)
    {
        this.infPachakge = infPackage;
        //System.out.println("Check in Gene, Id: " + getIdInnov() + " from: " + getFrom() + " Layer: " + fromLayer() + " To: " + getTo() + " Layer: " + toLayer());
    }

    public Gen copy()
    {
        Gen newGene = new Gen(infPachakge);
        newGene.setSila(getSila());
        newGene.enabled = enabled;
        return newGene;
    }

    public void setSila(double sila) {
        this.sila = sila;
    }

    public int fromLayer()
    {
        return  infPachakge.getFromLayer();
    }

    public int toLayer()
    {
        return infPachakge.getToLayer();
    }

    public InformationPackageNewConnection getInfPachakge() {
        return infPachakge;
    }

    public void setInactive()
    {
        enabled = false;
    }
    public void setActive()
    {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString()
    {
        return infPachakge.toString();
    }
}

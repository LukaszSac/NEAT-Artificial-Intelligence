package genotyp;

public class InformationPackageNewConnection
{
    private int idInnovation;
    private int from, fromLayer;
    private int to, toLayer;
    public InformationPackageNewConnection()
    {

    }

    public int getIdInnovation() {
        return idInnovation;
    }

    public void setIdInnovation(int idInnovation) {
        this.idInnovation = idInnovation;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getFromLayer() {
        return fromLayer;
    }

    public void setFromLayer(int fromLayer) {
        this.fromLayer = fromLayer;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getToLayer() {
        return toLayer;
    }

    public void setToLayer(int toLayer) {
        this.toLayer = toLayer;
    }

    @Override
    public String toString()
    {
        return "Id: " + idInnovation + ", From(" +fromLayer+"): " + from + ", To("+toLayer+"): "+ to;
    }
}

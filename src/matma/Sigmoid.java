package matma;

public class Sigmoid
{

    public static double sigmoidValue(double value)
    {
        return 1/(1+Math.pow(Math.E,-value));
    }
}

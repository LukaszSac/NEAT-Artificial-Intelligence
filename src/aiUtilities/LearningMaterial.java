package aiUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LearningMaterial
{

    private ArrayList <ArrayList<Double>> learningDatas;
    private ArrayList <ArrayList<Double>> results;

    public LearningMaterial()
    {
        learningDatas= new ArrayList<>();
        results = new ArrayList<>();
        init();
    }

    public void init()
    {
        /*
        File file = new File("data\\learning_data.lrn");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext())
        {
            ArrayList <Double> currentLearningData = new ArrayList<>();
            learningDatas.add(currentLearningData);
        }
        */
        ArrayList<Double> firstData = new ArrayList<>();
        firstData.add(0.0);
        firstData.add(0.0);
        ArrayList<Double> firstResultData = new ArrayList<>();
        firstResultData.add(0.0);
        learningDatas.add(firstData);
        results.add(firstResultData);
        firstData = new ArrayList<>();
        firstData.add(1.0);
        firstData.add(0.0);
        firstResultData = new ArrayList<>();
        firstResultData.add(1.0);
        learningDatas.add(firstData);
        results.add(firstResultData);
        firstData = new ArrayList<>();
        firstData.add(0.0);
        firstData.add(1.0);
        firstResultData = new ArrayList<>();
        firstResultData.add(1.0);
        learningDatas.add(firstData);
        results.add(firstResultData);
        firstData = new ArrayList<>();
        firstData.add(1.0);
        firstData.add(1.0);
        firstResultData = new ArrayList<>();
        firstResultData.add(0.0);
        learningDatas.add(firstData);
        results.add(firstResultData);
    }

    public ArrayList<Double> getLearningData(int index)
    {
        return learningDatas.get(index);
    }

    public ArrayList<Double> getResults(int index) {
        return results.get(index);
    }
}

package main;

import population.PopulationController;

import java.util.ArrayList;

public class Main
{
    public static void showIds(ArrayList<Integer> ids, boolean showOrder)
    {
        int counter = 0;
        if(showOrder)
            for(Integer id : ids)
                System.out.println("Order: " + counter++ + ", Id: " + id);
        else
            for(Integer id : ids)
                System.out.println("Id: " + id);
        System.out.print("\n");
    }


    public static void main(String[] args)
    {
        PopulationController pc = new PopulationController();
        pc.setNO_OF_INPUT(2);
        pc.setNO_OF_OBJECTS(200);
        pc.setNO_OF_OUTPUT(1);
        pc.setCHANGE_WEIGHT_CHANCE(0.8);
        pc.setNEW_CONNECTION_CHANCE(0.15);
        pc.setNEW_NODE_CHANCE(0.05);
        pc.init();
        for(int i=0;i<100;i++) {
            System.out.println("Generation: " + i);
            pc.evaluatePopulation();

        }
        pc.showResultsOfBest();
    }
}

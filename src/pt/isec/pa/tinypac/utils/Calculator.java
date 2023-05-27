package pt.isec.pa.tinypac.utils;

import java.util.Random;

public class Calculator {
    public Calculator(){}

    public double distanceBetweenPoints(Position p1,Position p2){
        //System.out.println("P1: " + p1 + "\nP2: " + p2);
        return Math.sqrt((p2.y() - p1.y()) * (p2.y() - p1.y()) + (p2.x() - p1.x()) * (p2.x() - p1.x()));
    }

    public int randomNumber(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }
    public int randomNumberBetweenValues(int min,int max){
        return min+(int)(Math.random() * (max-min));
    }
}

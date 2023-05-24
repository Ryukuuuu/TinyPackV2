package pt.isec.pa.tinypac.utils;

import java.util.Random;

public class Calculator {
    public Calculator(){}

    public double distanceBetweenPoints(Position p1,Position p2){
        return Math.sqrt(Math.pow((p1.y()+p2.y()),2)+Math.pow((p1.x()+p2.x()),2));
    }

    public int randomNumber(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }
    public int randomNumberBetweenValues(int min,int max){
        int debug = min+(int)(Math.random() * (max-min));
        System.out.println("DEBUGGGGGG->"+debug);
        return debug;
    }
}

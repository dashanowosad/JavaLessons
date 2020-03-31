package ru.sibsutise;

import java.util.ArrayList;

public class Fact {
    private Double result;
    private Double x;
    private ArrayList<Double> a = new ArrayList<>();

    public Fact(Double x){
        this.result = this.fact(x);
        this.x = x;
        System.out.println(this.result);
    }

    public Double fact(Double x) {
        try {
            a.add(x);

            if (x == 1 || x == 0)
                return 1.0;
            return x * fact(x - 1);
        } catch (StackOverflowError e) {
            e.getMessage();
            System.out.println("EXCEPTION: StackOverflow" );
        }
        return 1.0;
    }
}

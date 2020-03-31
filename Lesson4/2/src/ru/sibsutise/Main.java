package ru.sibsutise;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final void main (String[] ars){
        ArrayList <Number> A = new ArrayList<>();
        String symbol = null;
        Calc calc = new Calc();

        System.out.println("Write, what you wont to cal:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt())
            A.add(scanner.nextInt());
        else if (scanner.hasNextDouble())
            A.add(scanner.nextDouble());
        else {
            System.out.println("Wrong first number");
            System.exit(-1);
        }

        if(scanner.hasNext())
            symbol = scanner.next();
        else{
            System.out.println("Wrong symbol!");
            System.exit(-1);
        }

        if (scanner.hasNextInt())
            A.add(scanner.nextInt());
        else if (scanner.hasNextDouble())
            A.add(scanner.nextDouble());
        else {
            System.out.println("Wrong second number");
            System.exit(-1);
        }
       // calc.add(A.get(0), A.get(1));

        //System.out.println(A.get(0) + symbol + A.get(1));

        switch (symbol){
            case "+":{
                calc.sum(A.get(0),A.get(1));
                break;
            }
            case "-":{
                calc.sub(A.get(0),A.get(1));
                break;
            }
            case "*":{
                calc.mul(A.get(0),A.get(1));
                break;
            }
            case "/":{
                calc.del(A.get(0),A.get(1));
                break;
            }
            default:{
                System.out.println("Wrong symbol!");
                break;
            }
        }
    }
}

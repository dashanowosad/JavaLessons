package ru.sibsutise;


public class Main {

    public static final void main(String ars[]){
        Runnable runnable = () -> {System.out.println("Something text");};
        Thread thread = new Thread (runnable);
        thread.start();
    }
}

package ru.sibsutise;


import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final void main(String ars[]){

        Long start, stop;

        Resourse tmp = new Resourse();
        Thread thread = new Thread(new MyThread(tmp));

        start = System.nanoTime();
        thread.start();
        stop = System.nanoTime() - start;

        System.out.println("Time of synchronized = " + stop + " nanoseconds");


        Resourse tmp2 = new Resourse();
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new Thread(new MyTread2(tmp,lock));

        start = System.nanoTime();
        thread1.start();
        stop = System.nanoTime() - start;

        System.out.println("Time of reentranLock = " + stop + " nanoseconds");
    }
}

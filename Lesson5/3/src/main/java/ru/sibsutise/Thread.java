package ru.sibsutise;

import java.util.concurrent.locks.ReentrantLock;

class Provider extends Thread {

    Resourse resourse;

    Provider(Resourse r){
        this.resourse = r;
    }
    @Override
    public void run() {
        this.resourse.plus();
    }
}


class Consumer extends Thread {

    Resourse resourse;
    Consumer(Resourse r){
        this.resourse = r;
    }
    @Override
    public void run() {
        this.resourse.minus();
    }
}


class Resourse{
    private Integer res = 0;
    public Long Start = 0l;
    public Long Stop = 0l;

    public synchronized void plus(){
        this.res += 1000;
        System.out.println(this.res);
        notifyAll();

    }
    public synchronized void minus(){
        if (res == 0 ) {
            try {
                this.Start = System.currentTimeMillis();
                wait();
                this.Stop = System.currentTimeMillis() - this.Start;
            } catch (InterruptedException e) {
            }
        }

        if (res >= 250) this.res -= 250;
        System.out.println(this.res);

    }

}
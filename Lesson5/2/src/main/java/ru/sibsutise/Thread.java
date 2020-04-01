package ru.sibsutise;

import java.util.concurrent.locks.ReentrantLock;

class MyThread implements Runnable{

    Resourse resourse;

    public MyThread(Resourse r){
        this.resourse = r;
    }
    @Override
    public void run() {

        synchronized (resourse) {
            for (int i = 0; i < 30; ++i) {
                this.resourse.i = 1 + this.resourse.i;
                //System.out.println(this.resourse.i);
            }

        }
    }
}


class MyTread2 implements Runnable{

    Resourse resourse;
    ReentrantLock lock;

    public MyTread2(Resourse resourse, ReentrantLock lock){
        this.resourse = resourse;
        this.lock = lock;
    }

    @Override
    public void run() {
        this.lock.lock();
        for(int i = 0; i < 30; ++i)
            this.resourse.i = this.resourse.i + 1;
        this.lock.unlock();
    }
}

class Resourse{
    Integer i = 0;
}



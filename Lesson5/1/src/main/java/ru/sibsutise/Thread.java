package ru.sibsutise;

import java.util.concurrent.locks.ReentrantLock;

class Provider implements Runnable{

    Resourse resourse;
    ReentrantLock lock;
    Provider(Resourse r, ReentrantLock l){
        this.resourse = r;
        this.lock = l;
    }
    @Override
    public void run() {
        this.lock.lock();
        this.resourse.res +=1000;
        System.out.println(this.resourse.res);
        this.lock.unlock();
    }
}


class Consumer implements Runnable{

    Resourse resourse;
    ReentrantLock lock;
    Consumer(Resourse r, ReentrantLock l){
        this.resourse = r;
        this.lock = l;
    }
    @Override
    public void run() {
        this.lock.lock();
        if (this.resourse.res >= 250) this.resourse.res -=250;
        System.out.println(this.resourse.res);
        this.lock.unlock();
    }
}


class Resourse{
    public Integer res = 0;

}
package ru.sibsutise;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final void main(String args[]) throws InterruptedException {

        Integer i = -1,j;
        Integer time = 0;
        Boolean flag = true;
        Long ZeroTimeStart = Long.valueOf(0), ZeroTimeStop = Long.valueOf(0);
        Resourse res = new Resourse();
        ReentrantLock lock = new ReentrantLock();

        Thread provider = new Thread(new Provider(res, lock));
        ArrayList<Thread> consumers = new ArrayList<>();

        while(true) {

            if (time % 2 == 0 || time == 0) {
                Thread consumer = new Thread(new Consumer(res, lock));
                consumers.add(consumer);
                ++i;
            }

            time++;

            provider.run();
            provider.sleep(1000);

         for(j = 0; j < consumers.size(); ++j) {
             if (res.res == 0 && flag) {
                 ZeroTimeStart = System.currentTimeMillis();
                 flag = false;
             }
             else if (!flag){
                 ZeroTimeStop += System.currentTimeMillis() - ZeroTimeStart;
                 flag = true;
             }


             consumers.get(j).run();
             consumers.get(j).sleep(1000);
         }

          System.out.println(consumers.size() + "   " + time + "   " + ZeroTimeStop);


        }

    }
}

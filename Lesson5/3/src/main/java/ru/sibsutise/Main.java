package ru.sibsutise;

public class Main {
    public static  void main(String args[]) throws InterruptedException {

        Integer i = 1,j;
        Integer time = 0;
        Boolean flag = true;
        Long ZeroTimeStart = Long.valueOf(0), ZeroTimeStop = Long.valueOf(0);
        Resourse res = new Resourse();

        while(true) {


            time++;
            Thread provider = new Thread(new Provider(res));
            provider.start();
            provider.sleep(1000);


            for(j = 0; j < i; ++j){
                Thread consumer = new Thread(new Consumer(res));
                consumer.start();
                consumer.sleep(1000);
            }
            if(time % 2 == 0)
                ++i;

          if (res.Stop != 0) System.out.println("Zero Time = " + res.Stop % 1000 + " seconds");


        }

    }
}

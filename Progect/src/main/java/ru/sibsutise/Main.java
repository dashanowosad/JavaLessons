package ru.sibsutise;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


class Base{
    public Base(){}

    //private String status;
    private ArrayList <String> Spis;


    public ArrayList<String> getSpis() {
        return Spis;
    }

    public void setSpis(ArrayList<String> spis) {
        Spis = spis;
    }
}


public class Main {
    public static final void main (String args[]) throws IOException {
        ServerSocket server = new ServerSocket(5000);

        while(true){
            Socket client = server.accept();
            Thread socket = new Thread(new MyThread(client));
            socket.start();

        }
    }
}

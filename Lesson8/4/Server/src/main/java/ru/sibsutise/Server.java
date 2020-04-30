package ru.sibsutise;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    public static final void main(String args[]) throws IOException {

        ArrayList <Integer> Ports = new ArrayList<>();

        DatagramSocket socket = new DatagramSocket(7070);
        byte[] buf = new byte[65000];

        DatagramPacket packet = new DatagramPacket(buf,buf.length);

        while(true){
            socket.receive(packet);
            byte[] data = packet.getData();
            String str = new String(data);

            System.out.println(str);

            //str = str.toUpperCase();
            buf = str.getBytes();

            if(! Ports.contains(packet.getPort())){
                Ports.add(packet.getPort());
            }
            //System.out.println(packet.getPort());
            for(int i = 0; i < Ports.size(); ++i) {
                DatagramPacket packet1 = new DatagramPacket(buf, buf.length, packet.getAddress(), Ports.get(i));

                if (Ports.get(i) != packet.getPort()) socket.send(packet1);
            }
        }
    }
}

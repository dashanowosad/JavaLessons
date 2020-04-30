package ru.sibsutise;

import java.io.IOException;
import java.net.*;

public class Server {
    public static final void main(String args[]) throws IOException {
        MulticastSocket socket = new MulticastSocket(8080);
        socket.joinGroup(InetAddress.getByName("225.32.7.0"));
        byte[] buf = new byte[65000];

        DatagramPacket packet = new DatagramPacket(buf,buf.length);

        while(true){
            socket.receive(packet);
            byte[] data = packet.getData();
            String str = new String(data);

            System.out.println(str);

            //str = str.toUpperCase();
            buf = str.getBytes();

            DatagramPacket packet1 = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());

            socket.send(packet1);
        }
    }
}

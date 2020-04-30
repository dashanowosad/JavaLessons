package ru.sibsutise;

import java.io.IOException;
import java.net.*;

import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

public class Client {
    public static final void main(String args[]) throws IOException {

        DatagramSocket socket = new DatagramSocket(7272);

        Process proc = Runtime.getRuntime().exec("df -Th");

        Scanner scanner = new Scanner(proc.getInputStream());
        String str = "Client 1\n";
        while(scanner.hasNextLine()) {
            str += scanner.nextLine() + "\n";
        }
        //System.out.println(scanner.nextLine());
        byte[] data = str.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.31.255"), 7070);

        socket.send(packet);

        while(true) {

            byte[] buf = new byte[65000];
            DatagramPacket packet1 = new DatagramPacket(buf, buf.length);
            socket.receive(packet1);

            String str1 = new String(buf);
            System.out.println(str1);

        }
    }
}

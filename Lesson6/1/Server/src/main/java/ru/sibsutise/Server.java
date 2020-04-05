package ru.sibsutise;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
;
import java.util.Scanner;





public class Server {
    public static final void main (String args[]) throws IOException {
        ServerSocket server = new ServerSocket(5000, 0, InetAddress.getByName("0.0.0.0"));

    Socket client = server.accept();
    Scanner in = new Scanner(client.getInputStream());
    PrintWriter out = new PrintWriter(client.getOutputStream());

    out.write("Hello new client!");
    out.flush();


    //out.println(in.next());


        server.close();
    }

}

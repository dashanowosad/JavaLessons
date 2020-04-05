package ru.sibsutise;



import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final void main(String args[]) throws IOException {
        Socket client = new Socket("192.168.31.190", 5000);

        Scanner in = new Scanner(client.getInputStream());
        PrintWriter out = new PrintWriter(client.getOutputStream());

        out.println("Hello server");
        out.flush();

    while(in.hasNextLine()) {
       String str = in.nextLine();
       out.println(str.toUpperCase());
       out.flush();

    }


        client.close();
    }
}

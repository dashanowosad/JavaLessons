package ru.sibsutise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


class Base{
    public Base(){}

    //private String status;
    private ArrayList <String> Spis;


    /*public String getStatus() {
        return status;
    }*/

    /*public void setStatus(String status) {
        this.status = status;
    }*/


    public ArrayList<String> getSpis() {
        return Spis;
    }

    public void setSpis(ArrayList<String> spis) {
        Spis = spis;
    }
}

class MyThread implements Runnable{
    private Socket client;
    private PrintWriter out;
    private Scanner in;
    private MongoCollection<Document> collection;

    public MyThread(Socket client) throws IOException {
        this.client = client;
        this.out = new PrintWriter(client.getOutputStream());
        this.in = new Scanner(client.getInputStream());

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db =  mongoClient.getDatabase("users");

        this.collection = db.getCollection("users");
    }

    private String JsonToString() throws JsonProcessingException{
        ObjectMapper mappper = new ObjectMapper();
        Base base = new Base();
        //base.setStatus("OK");

        ArrayList <String> str = new ArrayList<>();
        for(Document doc: this.collection.find()) {
            str.add("<dt>" +doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>");
            //System.out.println(doc.toString());
        }
            base.setSpis(str);

        return mappper.writeValueAsString(base);
    }

    private String Check() throws JsonProcessingException {
        String str = null;
        String find = null;

        String str1 = null, str2 = null;
        String sign = null;

        BasicDBObject searchObj = new BasicDBObject();
        BasicDBObject changeObj = new BasicDBObject();
        Integer number1 = null, number2 = null;

        ArrayList <String> S = new ArrayList<>();

        String s = "";
        if(in.hasNext()){
            str = in.nextLine();



            find = str.substring(str.indexOf('&') + 1, str.length());
            find = find.substring(0, find.indexOf(' '));

            //System.out.println(find);

            if(str.contains("/get_all_users")) {
                s = JsonToString();
                s = s.replace("{\"spis\":[\"","").replace("\",\"", "").replace("\"]}","");
                //System.out.println(s.toString());
                out.write(s.toString());
            }
            else if(str.contains("/find")){
                if(!str.contains("gt") && !str.contains("eq")){
                    str1 = find.substring(0, find.indexOf('&'));
                    str2 = find.substring(find.indexOf('&') + 1, find.length());

                    System.out.println(str1);
                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, number1);
                    }
                    else
                        searchObj.append(str1, str2);

                    for (Document doc : this.collection.find(searchObj))
                        s += "<dt>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";

                    if(s.length() == 0)
                        s +="<dt>" + "Not found" + "</dt>";

                    System.out.println(s);
                    out.write(s);
                }
            }

        }

        return s;
    }

    @Override
    public synchronized  void run(){
        try {
            String str = Check();

            writeResponse(str);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error HTTP  " + e.getMessage().toString());
        }
        finally {
            try{
                this.client.close();
            }
            catch (IOException e){
                System.out.println("Error close client");
            }
        }
    }

    private void writeResponse(String str) throws IOException, URISyntaxException {
        FileReader html = new FileReader("src/main/resources/index.html");
        Scanner scan = new Scanner(html);
        String tmp = scan.nextLine();
        while(scan.hasNext())
            tmp += scan.nextLine();

        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2020-03-04\r\n" +
                //"Content-Type: application/json\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length:" + tmp.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response;
        result +=   tmp;

        System.out.println(result);
        if (str.length() == 0) out.write(result);
        out.flush();
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

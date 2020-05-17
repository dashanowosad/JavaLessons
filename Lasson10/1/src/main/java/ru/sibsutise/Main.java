package ru.sibsutise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


class Base{
    public Base(){}

    private String status;
    private ArrayList <String> Spis;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


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
        base.setStatus("OK");

        ArrayList <String> str = new ArrayList<>();
        for(Document doc: this.collection.find())
            str.add(doc.toString().replace("Document","").replace("{","").replace("}",""));
        base.setSpis(str);

        return mappper.writeValueAsString(base);
    }

    private void Check(){
        String str = null;
        String find = null;

        String str1 = null, str2 = null, str3 = null;
        String sign = null;

        BasicDBObject searchObj = new BasicDBObject();
        BasicDBObject changeObj = new BasicDBObject();
        Integer number1 = null, number2 = null;

        if(in.hasNext()){
            str = in.nextLine();

            find = str.substring(str.indexOf('&') + 1, str.length());
            find = find.substring(0, find.indexOf(' '));

            if(str.contains("info"))
                ;

            if(str.contains("remove")){
                if(!find.contains("$lt")&& !find.contains("$gt") && !find.contains("$eq")){
                    str1 = find.substring(0,find.indexOf('&'));
                    str2 = find.substring(find.indexOf('&') + 1, find.length());

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, number1);
                    }
                    else
                        searchObj.append(str1, str2);

                    this.collection.deleteMany(searchObj);

                }

                else {
                    str1 = find.substring(0,find.indexOf('&'));
                    find = find.replace(str1 + '&',"");
                    sign = find.substring(0,find.indexOf('&'));
                    find = find.replace(sign + '&',"");
                    str2 = find.substring(0,find.length());

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, new BasicDBObject(sign,number1));
                    }
                    else
                        searchObj.append(str1, new BasicDBObject(sign,str2));

                    this.collection.deleteMany(searchObj);

                }
            }
            else if (str.contains("update")){
                if(!find.contains("$lt")&& !find.contains("$gt") && !find.contains("$eq")) {
                    str1 = find.substring(0, find.indexOf('&'));
                    find = find.replace(str1 + '&', "");
                    str2 = find.substring(0, find.indexOf('&'));
                    find = find.replace(str1 + '&', "");
                    str3 = find.substring(0, find.indexOf('&'));

                    if (str2.matches("[0-9]+") && str3.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        number2 = Integer.valueOf(str3);

                        searchObj.append(str1, number1);
                        changeObj.append("$set", new BasicDBObject(str1, number2));
                    } else {
                        searchObj.append(str1, str2);
                        changeObj.append("$set", new BasicDBObject(str1, str3));
                    }

                    this.collection.updateMany(searchObj, changeObj);
                }
                else{
                    str1 = find.substring(0, find.indexOf('&'));
                    find = find.replace(str1 + '&', "");
                    sign = find.substring(0, find.indexOf('&'));
                    find = find.replace(sign + '&', "");
                    str2 = find.substring(0, find.indexOf('&'));
                    find = find.replace(str2 + '&', "");
                    str3 = find;

                    System.out.println(str1+ " "+ sign + " "+ str2 + " " +str3);

                    if (str2.matches("[0-9]+") && str3.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        number2 = Integer.valueOf(str3);

                        searchObj.append(str1, new BasicDBObject(sign, number1));
                        changeObj.append("$set", new BasicDBObject(str1, number2));
                    }

                    else{
                        searchObj.append(str1, new BasicDBObject(sign, str2));
                        changeObj.append("$set", new BasicDBObject(str1, str3));
                    }

                   this.collection.updateMany(searchObj,changeObj);

                }

            }

        }
    }

    @Override
    public synchronized  void run(){
        try {
            Check();

            writeResponse(JsonToString());
        } catch (IOException e) {
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

    private void writeResponse(String str) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2020-03-04\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length:" + str.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + str;
        //System.out.println(str);
        out.write(result);
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

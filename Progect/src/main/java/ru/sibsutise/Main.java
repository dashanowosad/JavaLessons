package ru.sibsutise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

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

import java.util.Comparator;


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


        //BasicDBObject sortObj = new BasicDBObject();
        //sortObj.append("name",1);
        ArrayList <String> str = new ArrayList<>();
        int  i= 0;
        for(Document doc: this.collection.find()) {
            str.add("<dt id =" + i+ " onclick=UpdateModal(this)>" +doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>");
            //System.out.println(doc.toString());
            ++i;
        }
            base.setSpis(str);





        return mappper.writeValueAsString(base);
    }

    private String Check() throws JsonProcessingException  {
        String str = null;
        String find = null;

        String str1 = null, str2 = null;
        String sign = null;

        BasicDBObject searchObj = new BasicDBObject();
        BasicDBObject changeObj = new BasicDBObject();
        BasicDBObject sortObj = new BasicDBObject();

        Integer number1 = null, number2 = null;

        ArrayList <String> S = new ArrayList<>();

        String s = "";



        if(in.hasNext()){
            str = in.nextLine();


            find = str.substring(str.indexOf('&') + 1, str.length());
            find = find.substring(0, find.indexOf(' '));

            if(str.contains("/get_all_users")) {
                s = JsonToString();
                s = s.replace("{\"spis\":[\"","").replace("\",\"", "").replace("\"]}","");
                out.write(s.toString());
            }
            else if (str.contains("/sort_by_name")){

                sortObj.append("name",1);
                int i = 0;
                for (Document doc : this.collection.find().sort(sortObj)) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }
                out.write(s);

            }

            else if (str.contains("/sort_by_age")){

                sortObj.append("age",1);
                int i = 0;
                for (Document doc : this.collection.find().sort(sortObj)) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }
                out.write(s);

            }
            else if(str.contains("/find")){

                if(!str.contains("%3E") && !str.contains("%3C")){
                    str1 = find.substring(0, find.indexOf('&'));
                    str2 = find.substring(find.indexOf('&') + 1, find.length());

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, number1);
                    }
                    else
                        searchObj.append(str1, str2);
                }

                else{
                    str1 = find.substring(0, find.indexOf('&'));
                    sign = find.substring(find.indexOf('&') + 1, find.indexOf('&') + 4);
                    str2 = find.substring(find.indexOf('&') + 4, find.length());

                    if(sign.equals("%3C"))
                        sign = "$lt";
                    else if (sign.equals("%3E"))
                        sign = "$gt";

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, new BasicDBObject(sign,number1));
                    }

                }
                int i = 0;
                for (Document doc : this.collection.find(searchObj)) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }
                if(s.length() == 0)
                    s +="<dt>" + "Not found" + "</dt>";

                out.write(s);

            }

            else if (str.contains("/delete")){
                if(!str.contains("%3E") && !str.contains("%3C")){
                    str1 = find.substring(0, find.indexOf('&'));
                    str2 = find.substring(find.indexOf('&') + 1, find.length());

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, number1);
                    }
                    else
                        searchObj.append(str1, str2);
                    System.out.println(searchObj);
                }

                else{
                    str1 = find.substring(0, find.indexOf('&'));
                    sign = find.substring(find.indexOf('&') + 1, find.indexOf('&') + 4);
                    str2 = find.substring(find.indexOf('&') + 4, find.length());

                    if(sign.equals("%3C"))
                        sign = "$lt";
                    else if (sign.equals("%3E"))
                        sign = "$gt";

                    if(str2.matches("[0-9]+")) {
                        number1 = Integer.valueOf(str2);
                        searchObj.append(str1, new BasicDBObject(sign,number1));
                    }

                }
                this.collection.deleteMany(searchObj);

                int i = 0;
                for (Document doc : this.collection.find()) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }

                if(s.length() == 0)
                    s +="<dt>" + "Not found and not delete" + "</dt>";

                out.write(s);
            }

            else if(str.contains("/insert")){
                find = find.replace("%20","");


                String[] tmp = find.split("&");

                Document newDoc = new Document();

                for (int i = 1; i < tmp.length; i+=2){
                    if (tmp[i].contains(","))
                        tmp[i] = '[' + tmp[i] + ']';

                    if(tmp[i].matches("[0-9]+")){
                        Integer num = Integer.parseInt(tmp[i]);
                        newDoc.append(tmp[i - 1], num);
                    }
                    else
                        newDoc.append(tmp[i - 1], tmp[i]);
                }

                collection.insertOne(newDoc);


                int i = 0;
                for (Document doc : this.collection.find()) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }

                out.write(s);
            }

            else if (str.contains("/update")){
                find = find.replace("=",",%20");
                String [] tmp = find.split(",%20");


                this.collection.deleteOne(new Document(tmp[0], new ObjectId(tmp[1])));
                System.out.println(searchObj);

                Document newDoc = new Document();

                for (int i = 3; i < tmp.length; i+=2){
                    if (tmp[i].contains(","))
                        tmp[i] = '[' + tmp[i] + ']';

                    if(tmp[i].matches("[0-9]+")){
                        Integer num = Integer.parseInt(tmp[i]);
                        newDoc.append(tmp[i - 1], num);
                    }
                    else
                        newDoc.append(tmp[i - 1], tmp[i]);
                }

                collection.insertOne(newDoc);


                int i = 0;
                for (Document doc : this.collection.find()) {
                    s += "<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString().replace("Document", "").replace("{", "").replace("}", "") + "</dt>";
                    ++i;
                }

                out.write(s);
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
        String s;

        FileReader css = new FileReader("src/main/resources/style.css");
        Scanner scan = new Scanner(css);
        String CSS = "<style>";
        while(scan.hasNext())
            CSS += scan.nextLine();
        CSS += "</style>";

        FileReader js = new FileReader("src/main/resources/js.js");
        scan = new Scanner(js);
        String JS = "<script>";
        while(scan.hasNext())
            JS += scan.nextLine();
        JS += "</script>";

        FileReader html = new FileReader("src/main/resources/index.html");
         scan = new Scanner(html);
        String HTML= scan.nextLine();
        while(scan.hasNext()) {
            s = scan.nextLine();
            HTML += s;

            if(s.equals("</head>"))
                HTML = HTML + CSS + JS;
        }



        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2020-03-04\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length:" + HTML.length() + "\r\n" +
                "Connection: close\r\n\r\n";


        String result = response ;
        result +=   HTML;

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

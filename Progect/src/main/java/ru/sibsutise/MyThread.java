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
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyThread implements Runnable{
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

    private String JsonToString() throws JsonProcessingException {
        ObjectMapper mappper = new ObjectMapper();
        Base base = new Base();

        ArrayList<String> str = new ArrayList<>();
        int  i= 0;
        for(Document doc: this.collection.find()) {
            str.add("<dt id =" + i + " onclick=UpdateModal(this)>" + doc.toString()
                    .replace("Document", "")
                    .replace("{", "")
                    .replace("}", "") + "</dt>");
            ++i;
        }
        base.setSpis(str);
        return mappper.writeValueAsString(base);
    }

    private String Check() throws JsonProcessingException  {
        String str = null;
        String find = null;
        String response = "";

        if(in.hasNext()){
            str = in.nextLine();
            find = str.substring(str.indexOf('&') + 1);
            find = find.substring(0, find.indexOf(' '));
            if(str.contains("&")){
                str = str.substring(str.indexOf('/'), str.indexOf('&'));
            }
            switch (str){
                case "/get_all_users": {
                    response = GetAllUsers();
                    break;
                }
                case "/sort_by_name": {
                    response = SortByName();
                    break;
                }
                case "/sort_by_age":{
                    response = SortByAge();
                    break;
                }
                case "/find": {
                    response = FindDocuments(find);
                    break;
                }
                case "/delete": {
                    response = DeleteDocuments(find);
                    break;
                }
                case "/insert": {
                    response = InsertDocumets(find);
                    break;
                }
                case "/update": {
                    response = UpdateDocuments(find);
                    break;
                }
            }
        }

        return response;
    }

    private String UpdateDocuments(String find) {
        StringBuilder response = new StringBuilder();
        BasicDBObject searchObj = new BasicDBObject();
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
            response.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }

        out.write(response.toString());
        return response.toString();
    }

    private String InsertDocumets(String find) {
        StringBuilder response = new StringBuilder();
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
            response.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }

        out.write(response.toString());
        return response.toString();
    }

    private String DeleteDocuments(String find) {
        StringBuilder response = new StringBuilder();
        String tmpStr1 = null;
        String tmpStr2 = null;
        String sign = null;
        int tmpNumber, i = 0;
        BasicDBObject searchObj = new BasicDBObject();
        if(!find.contains("%3E") && !find.contains("%3C")){
            tmpStr1 = find.substring(0, find.indexOf('&'));
            tmpStr2 = find.substring(find.indexOf('&') + 1, find.length());

            if(tmpStr2.matches("[0-9]+")) {
                tmpNumber = Integer.parseInt(tmpStr2);
                searchObj.append(tmpStr1, tmpNumber);
            }
            else
                searchObj.append(tmpStr1, tmpStr2);
        }
        else{
            tmpStr1 = find.substring(0, find.indexOf('&'));
            sign = find.substring(find.indexOf('&') + 1, find.indexOf('&') + 4);
            tmpStr2 = find.substring(find.indexOf('&') + 4, find.length());

            if(sign.equals("%3C"))
                sign = "$lt";
            else if (sign.equals("%3E"))
                sign = "$gt";

            if(tmpStr2.matches("[0-9]+")) {
                tmpNumber = Integer.parseInt(tmpStr2);
                searchObj.append(tmpStr1, new BasicDBObject(sign,tmpNumber));
            }

        }
        this.collection.deleteMany(searchObj);
        i = 0;
        for (Document doc : this.collection.find()) {
            response.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }
        if(response.length() == 0)
            response.append("<dt>" + "Not found" + "</dt>");

        out.write(response.toString());
        return response.toString();
    }

    private String FindDocuments(String find) {
        StringBuilder response = new StringBuilder();
        String tmpStr1 = null;
        String tmpStr2 = null;
        String sign = null;
        int tmpNumber, i = 0;
        BasicDBObject searchObj = new BasicDBObject();
        if(!find.contains("%3E") && !find.contains("%3C")){
            tmpStr1 = find.substring(0, find.indexOf('&'));
            tmpStr2 = find.substring(find.indexOf('&') + 1, find.length());

            if(tmpStr2.matches("[0-9]+")) {
                tmpNumber = Integer.parseInt(tmpStr2);
                searchObj.append(tmpStr1, tmpNumber);
            }
            else
                searchObj.append(tmpStr1, tmpStr2);
        }

        else{
            tmpStr1 = find.substring(0, find.indexOf('&'));
            sign = find.substring(find.indexOf('&') + 1, find.indexOf('&') + 4);
            tmpStr2 = find.substring(find.indexOf('&') + 4, find.length());

            if(sign.equals("%3C"))
                sign = "$lt";
            else if (sign.equals("%3E"))
                sign = "$gt";

            if(tmpStr2.matches("[0-9]+")) {
                tmpNumber = Integer.parseInt(tmpStr2);
                searchObj.append(tmpStr1, new BasicDBObject(sign,tmpNumber));
            }

        }
        for (Document doc : this.collection.find(searchObj)) {
            response.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }
        if(response.length() == 0)
            response.append("<dt>" + "Not found" + "</dt>");

        out.write(response.toString());
        return response.toString();
    }

    private String SortByAge() {
        StringBuilder tmpString = new StringBuilder();
        BasicDBObject sortObj = new BasicDBObject();
        sortObj.append("age",1);
        int i = 0;
        for (Document doc : this.collection.find().sort(sortObj)) {
            assert tmpString != null;
            tmpString.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }

        assert tmpString != null;
        out.write(tmpString.toString());
        return tmpString.toString();
    }

    String GetAllUsers() throws JsonProcessingException {
        String response;
        response = JsonToString();
        response = response.replace("{\"spis\":[\"","")
                .replace("\",\"", "")
                .replace("\"]}","");
        out.write(response.toString());
        return response;
    }

    String SortByName(){
        StringBuilder tmpString = new StringBuilder();
        BasicDBObject sortObj = new BasicDBObject();
        sortObj.append("name",1);
        Integer i = 0;
        for (Document doc : this.collection.find().sort(sortObj)) {
            assert false;
            tmpString.append("<dt id =")
                    .append(i)
                    .append(" onclick=UpdateModal(this)>")
                    .append(doc.toString()
                            .replace("Document", "")
                            .replace("{", "")
                            .replace("}", ""))
                    .append("</dt>");
            ++i;
        }
        assert false;
        out.write(tmpString.toString());
        return tmpString.toString();
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

        String result = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2020-03-04\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length:" + HTML.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        result +=   HTML;

        System.out.println(result);
        if (str.length() == 0) out.write(result);
        out.flush();
    }

}
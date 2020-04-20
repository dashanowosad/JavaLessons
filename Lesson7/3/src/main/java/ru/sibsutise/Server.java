package ru.sibsutise;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


class FileData{
    public FileData(){}

    private Integer id;
    private String status;
    private ArrayList <String> files;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> data) {
        this.files = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

class MyThread implements Runnable {
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private File file;

    public MyThread(Socket client) throws IOException {
        this.client = client;
        this.in =  new Scanner (this.client.getInputStream());
        this.out = new PrintWriter(client.getOutputStream());

        this.file = new File("/home/dasha/JavaLessons/Lesson7/3");
    }

    private String JsonToString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FileData data = new FileData();
        data.setId(1);
        data.setStatus("OK");
        ArrayList <String> str = new ArrayList<>();
        Collections.addAll(str, file.list());
        data.setFiles(str);
        return mapper.writeValueAsString(data);
    }


    private void Check() throws IOException {
        String str = null;
        String file = null;
        String path = null;

        if(in.hasNextLine()) {
            str = in.nextLine();

            file = str.substring(str.indexOf('&') + 1, str.length());
            file = file.substring(0, file.indexOf(' '));
            path = this.file + "/" + file;
            //System.out.println(file);

            if (str.contains("info")) {
                ;
            }
            else if(str.contains("delete")){
                Path p = Path.of(path);
                Files.deleteIfExists(p);
            }
            else if(str.contains("copy")){
                Path path1 = Path.of(path);
                String file2 = "/home/dasha/JavaLessons/Lesson7/" + file;
                Path path2 = Path.of(file2);
                Files.copy(path1,path2, StandardCopyOption.REPLACE_EXISTING);
            }
            else if(str.contains("move")){
                Path path1 = Path.of(path);
                String file2 = "/home/dasha/JavaLessons/Lesson7/" + file;
                Path path2 = Path.of(file2);
                Files.move(path1,path2, StandardCopyOption.REPLACE_EXISTING);
            }

        }

    }

    @Override
    public synchronized void run() {
        try {

            Check();

            writeResponse(JsonToString());
        } catch (IOException e) {
            System.out.println("Error HTTP  " + e.getMessage().toString());
        } finally{
            try{
                this.client.close();
            } catch (IOException e) {
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


class Server {
    public static final void main (String args[]) throws IOException {
        ServerSocket server = new ServerSocket(5000);

        while(true) {
            Socket client = server.accept();
            Thread socket = new Thread(new MyThread(client));
            socket.start();
        }
        //server.close();
    }

}
package ru.sibsutise;

import java.io.*;
import java.util.Scanner;

public class Developer extends User implements CSV{
    protected String language;
    private String Name[];
    private String Email[];
    private String Language[];
    public Developer(String name, String email, String language){
        super(name, email);
        this.language = language;
    }
    public Developer (String name, String language){
        super(name);
        this.language = language;
    }


    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }

    public void Delete(){
        String str[] = new String[3];
        str[0] = this.name;
        str[1] = this.email;
        str[2] = this.language;
        this.fromCSV(str);
    }

    @Override
    public String toCSV() {
        try {
            PrintWriter fw = new PrintWriter(new FileWriter("res/dev.csv", true));
            fw.write(this.name +";" +this.email + ";" + this.language + "\n");
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void fromCSV(String[] str) {
        try {
            FileWriter fw = new FileWriter("res/dev1.csv");
            FileReader fr = new FileReader("res/dev.csv");
            Scanner in = new Scanner(fr);
            while(in.hasNext()){
                String s = in.nextLine();
                String A[] = s.split(";");
                if(A[0].equals(str[0]) && A[1].equals(str[1]) && A[2].equals(str[2]));
                else
                    fw.write(s + "\n");

            }
            fr.close();
            fw.close();
            File dev = new File("res/dev.csv");
            dev.delete();
            File dev1 = new File("res/dev1.csv");
            dev1.renameTo(dev);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void FillCSV() {
        Integer i;
        this.MasOfName();
        this.MasOfEmail();
        this.MassOfLanguage();
        try {
            FileWriter fw = new FileWriter("res/dev.csv");
            for(i = 0; i < 1000; i++)
                fw.write(this.Name[i] + ";" +this.Email[i] + ";" + this.Language[i] + "\n");
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void MasOfName(){
        Integer i;
        this.Name = new String [1000];
        this.Name[0]="Таша";
        this.Name[1]="Андрей";
        this.Name[2]="Михаил";
        this.Name[3]="Евгения";
        this.Name[4]="Лидия";
        this.Name[5]="Костя";
        this.Name[6]="Антон";
        this.Name[7]="Вячеслав";
        this.Name[8]="Дмитрий";
        this.Name[9]="Аделина";

        for(i = 10; i < 1000; i++){
            this.Name[i] = this.Name[(int)(Math.random() * 10)];
        }
    }

    private void MasOfEmail(){
        Integer i;
        this.Email = new String [1000];
        this.Email[0]="progerNumberOne@gmail.com";
        this.Email[1]="rjcnz1999@mail.ru";
        this.Email[2]="oruzekolu@mail.ru";
        this.Email[3]="wasydanny@yandex.ru";
        this.Email[4]="mneNadoelo@yandex.ru";
        this.Email[5]="Slozno@gmail.com";
        this.Email[6]="NeHochuRabotat@yandex.ru";
        this.Email[7]="fjeif@yandex.ru";
        this.Email[8]="Blin@gmail.com";
        this.Email[9]="Ura@mail.list";

        for(i = 10; i < 1000; i++){
            this.Email[i] = this.Email[(int)(Math.random() * 10)];
        }
    }

    private void MassOfLanguage(){
        Integer i;
        this.Language = new String [1000];
        this.Language[0] ="Java";
        this.Language[1] ="C++";
        this.Language[2] ="C#";
        this.Language[3] ="Swift";
        this.Language[4] ="Python";
        this.Language[5] ="Scratch";
        this.Language[6] ="Ruby";
        this.Language[7] ="Go";
        this.Language[8] ="PHP";
        this.Language[9] ="Perl";

        for(i = 10; i < 1000; i++){
            this.Language[i] = this.Language[(int)(Math.random() * 10)];
        }

    }
}

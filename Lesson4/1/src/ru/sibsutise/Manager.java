package ru.sibsutise;

import java.io.*;
import java.util.*;

public class Manager extends User implements CSV {
    protected String number;
    private String Name[];
    private String Email[];
    private String Number[];
    private Integer Size = 20;

    private Integer Fact[], Target[];
    public Double IndexKPI[];

    public Manager (){
        super();
        Random r = new Random();
        this.Fact = new Integer[3];
        this.Target = new Integer[3];

        for(int i = 0; i < 3; ++i){
            this.Fact[i] = 10 + r.nextInt(35);
            this.Target[i] = 10 + r.nextInt(35);
            //System.out.println(Fact[i]+ "   "+ Target[i]);
        }
        this.KPI();

    }
    public Manager(String name, String email, String number){
        super(name,email);
        this.number = number;
    }
    public Manager(String name, String number){
        super(name);
        this.number = number;
    }

    public void setNumber(String number){
        this.number = number;
    }
    public String getNumber(){
        return this.number;
    }

    public void Delete(){
        String str[] = new String[3];
        str[0] = this.name;
        str[1] = this.email;
        str[2] = this.number;
        this.fromCSV(str);
    }

    @Override
    public String toCSV() {
        try {
            PrintWriter fw = new PrintWriter(new FileWriter("res/manager.csv", true));
            fw.write(this.name +";" +this.email + ";" + this.number + "\n");
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void fromCSV(String str[]) {
        try {
            FileWriter fw = new FileWriter("res/manager1.csv");
            FileReader fr = new FileReader("res/manager.csv");
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
            File manager = new File("res/manager.csv");
            manager.delete();
            File manager1 = new File("res/manager1.csv");
            manager1.renameTo(manager);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void FillCSV() {
        Integer i;
        this.MasOfName();
        this.MasOfEmail();
        this.MasOfNumber();
        try {
            FileWriter fw = new FileWriter("res/manager.csv");
            for(i = 0; i < 1000; i++)
                fw.write(this.Name[i] + ";" +this.Email[i] + ";" + this.Number[i] + "\n");
            fw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void  MasOfName(){
        Integer i;
        this.Name = new String [this.Size];
        this.Name[0] = "Павел";
        this.Name[1] = "Артём";
        this.Name[2] = "Екатерина";
        this.Name[3] = "Михаил";
        this.Name[4] = "Тимур";
        this.Name[5] = "Рита";
        this.Name[6] = "Мария";
        this.Name[7] = "Анастасия";
        this.Name[8] = "Кристина";
        this.Name[9] = "Александр";

        for(i = 10; i < this.Size; i++){
            this.Name[i] = this.Name[(int)(Math.random() * 10)];
        }
        /*for(i = 0; i < 1000; i++)
            System.out.println(Name[i] + "\n");*/
    }
    private void  MasOfEmail(){
        Integer i;
        this.Email = new String [this.Size];
        this.Email[0] = "super@gmail.com";
        this.Email[1] = "WiNnEr@yandex.ru";
        this.Email[2] = "KrutoyChel@mail.list";
        this.Email[3] = "Princessa@gmail.com";
        this.Email[4] = "RussianPerson@mail.ru";
        this.Email[5] = "Hbnf@yandex.ru";
        this.Email[6] = "Lider@gmail.com";
        this.Email[7] = "King@yandex.ru";
        this.Email[8] = "NesuDobro@mail.ru";
        this.Email[9] = "fktrcfylh@ya.com";

        for(i = 10; i < this.Size; i++){
            this.Email[i] = this.Email[(int)(Math.random() * 10)];
        }
    }
    private void MasOfNumber(){
        Integer i;
        this.Number = new String [this.Size];
        this.Number[0] = "8-946-589-23-84";
        this.Number[1] = "+7-563-423-49-65";
        this.Number[2] = "8-589-475-25-25";
        this.Number[3] = "+7-654-236-25-95";
        this.Number[4] = "8-523-458-69-36";
        this.Number[5] = "8-999-485-67-99";
        this.Number[6] = "8-235-123-15-44";
        this.Number[7] = "+7-659-666-42-61";
        this.Number[8] = "+7-659-149-00-02";
        this.Number[9] = "8-012-230-56-87";

        for(i = 10; i < this.Size; i++){
            this.Number[i] = this.Number[(int)(Math.random() * 10)];
        }

    }

    public  void KPI (){
        Double KPI[] = new Double[3];
        this.IndexKPI = new Double[Size];

        for(int i = 0; i < Size; ++i){
            this.IndexKPI[i] = 0.0;
            KPI[0] =  Math.random() * (0.7 - 0.5) + 0.5;
            KPI[1] = Math.random() * (1 - KPI[0] - 0.3) + 0.3;
            KPI[2] = 1 - KPI[0] - KPI[1];
            //System.out.println(KPI[0] + "   " + KPI[1] + "   " + KPI[2]);
            for(int j = 0; j < 3; ++j)
                this.IndexKPI[i] += this.IndexKPI(KPI[j], j);
        }

    }
    private <T extends Number> double IndexKPI(T kpi, Integer i){
        return (kpi.doubleValue() * this.Fact[i]) / this.Target[i];
    }

}

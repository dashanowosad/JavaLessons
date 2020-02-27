package ru.sibsutise;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*Manager man = new Manager("feef","efewf"); //Fill files
        man.FillCSV();

        Developer dev = new Developer("rfrf","efw");
        dev.FillCSV();*/
        Scanner in = new Scanner(System.in);
        Integer menu, choice, HaveEmail;
        String email = "", name ="", number ="", language="";
	    System.out.println("1.Developer's menu");
	    System.out.println("2.Manager's menu");
        System.out.println("3.Exit");

	    menu = in.nextInt();
	    switch(menu){
            case 1:{ //Developer
                System.out.println("This person has email?");
                System.out.println("1.Yes");
                System.out.println("2.No");

                HaveEmail = in.nextInt();
                /*System.out.println("1.Add new developer");
                System.out.println("2.Find and delete developer");*/

                switch(HaveEmail){
                    case 1:{
                        System.out.print("Name:");
                        name = in.next();
                        System.out.print("\nEmail: ");
                        email = in.next();
                        System.out.print("\nLanguage: ");
                        language = in.next();
                        break;
                    }
                    case 2:{
                        System.out.print("Name:");
                        name = in.next();
                        System.out.print("\nLanguage: ");
                        number = in.next();
                        break;
                    }
                    default:{
                        System.out.println("Wrong input");
                        break;
                    }
                }
                System.out.println("\n1.Add new developer");
                System.out.println("2.Find and delete developer");

                choice = in.nextInt();
                switch(choice){
                    case 1:{
                        Developer developer = new Developer(name, email,language);
                        developer.toCSV();
                        break;
                    }
                    case 2:{
                        Developer developer = new Developer(name, email,language);
                        developer.Delete();
                    }
                    default:{
                        System.out.println("Wrong input");
                        break;
                    }
                }
                break;
            }
            case 2:{ //Manager
                System.out.println("This person has email?");
                System.out.println("1.Yes");
                System.out.println("2.No");

                HaveEmail = in.nextInt();

                switch(HaveEmail){
                    case 1:{
                        System.out.print("Name:");
                        name = in.next();
                        System.out.print("\nEmail: ");
                        email = in.next();
                        System.out.print("\nNumber: ");
                        number = in.next();
                        break;
                    }
                    case 2:{
                        System.out.print("Name:");
                        name = in.next();
                        System.out.print("\nNumber: ");
                        number = in.next();
                        break;
                    }
                    default:{
                        System.out.println("Wrong input");
                        break;
                    }
                }
                System.out.println("\n1.Add new manager");
                System.out.println("2.Find and delete manager");

                choice = in.nextInt();
                switch(choice){
                    case 1:{
                        Manager manager = new Manager(name,email,number);
                        manager.toCSV();
                        break;
                    }
                    case 2:{
                        Manager manager = new Manager(name,email,number);
                        manager.Delete();
                    }
                    default:{
                        System.out.println("Wrong input");
                        break;
                    }
                }
                break;
            }
            case 3:{
                break;
            }
            default:{
                System.out.println("Wrong input");
                break;
            }
        }
    }
}

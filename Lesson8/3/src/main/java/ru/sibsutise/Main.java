package ru.sibsutise;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Manager man = new Manager();
        man.IndexKPI.stream().sorted().forEach(System.out::println);

    }
}

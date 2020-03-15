package ru.sibsutise;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        long startAdd, stopAdd, startRemove, stopRemove;
        Manager man = new Manager("Gfer","wef");
//SetHash

        startAdd = System.currentTimeMillis();
        man.FillHashSet();
        stopAdd = System.currentTimeMillis() - startAdd;
        startRemove = System.currentTimeMillis();
        man.RemoveHashSet();
        stopRemove = System.currentTimeMillis() - startRemove;
        System.out.println("HashSet Add time = " + stopAdd + " milliseconds\n" + "HashSet Remove time = " + stopRemove + " milliseconds\n");

//TreeHash (need Comparable)

        startAdd = System.currentTimeMillis();
        man.FillTreeSet();
        stopAdd = System.currentTimeMillis() - startAdd;
        startRemove = System.currentTimeMillis();
        man.RemoveTreeSet();
        stopRemove = System.currentTimeMillis() - startRemove;
        System.out.println("TreeSet Add time = " + stopAdd + " milliseconds\n" + "TreeSet Remove time = " + stopRemove + " milliseconds\n");

//ArrayList
        startAdd = System.currentTimeMillis();
        man.FillArrayList();
        stopAdd = System.currentTimeMillis() - startAdd;
        startRemove = System.currentTimeMillis();
        man.RemoveArrayList();
        stopRemove = System.currentTimeMillis() - startRemove;
        System.out.println("ArrayList Add time = " + stopAdd + " milliseconds\n" + "ArrayList Remove time = " + stopRemove + " milliseconds\n");

//LinkedList
        startAdd = System.currentTimeMillis();
        man.FillLinkedList();
        stopAdd = System.currentTimeMillis() - startAdd;
        startRemove = System.currentTimeMillis();
        man.RemoveLinkedList();
        stopRemove = System.currentTimeMillis() - startRemove;
        System.out.println("LinkedList Add time = " + stopAdd + " milliseconds\n" + "LinkedList Remove time = " + stopRemove + " milliseconds\n");
    }

}

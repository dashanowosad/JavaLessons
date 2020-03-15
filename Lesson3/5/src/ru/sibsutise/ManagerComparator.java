package ru.sibsutise;

import java.util.Comparator;

class ManagerNameComparator implements Comparator <Manager> {
    @Override
    public int compare(Manager o1, Manager o2) {
        return o1.name.compareTo(o2.name);
    }
}


class ManagerEmailComparator implements Comparator <Manager> {

    @Override
    public int compare(Manager o1, Manager o2) {
        if(o1.getEmail().equals(o2.getEmail()))
            return 1;
        else if(o1.getEmail().equals(o2.getEmail()))
            return -1;
        else
            return 0;
    }
}

class ManagerNumberComparator implements Comparator <Manager>{

    @Override
    public int compare(Manager o1, Manager o2) {
        if(o1.getName().equals(o2.getNumber()))
            return 1;
        else if(o1.getNumber().equals(o2.getNumber()))
            return -1;
        else
            return 0;
    }
}
package ru.sibsutise;


class  Calc <T extends Number> {
    private T a;
    private T b;
    private T c;

    public  <T extends Number> Calc () {}

    public static <T extends Number>  void sum(T a, T b){
        System.out.println(a.doubleValue() + b.doubleValue());
    }

    public static <T extends  Number> void sub(T a, T b){
        System.out.println(a.doubleValue() - b.doubleValue());
    }

    public static <T extends  Number> void mul(T a, T b){
        System.out.println(a.doubleValue() * b.doubleValue());
    }

    public static <T extends  Number> void del(T a, T b){
        System.out.println(a.doubleValue() / b.doubleValue());
    }

}

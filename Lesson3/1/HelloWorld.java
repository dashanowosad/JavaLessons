class HelloWorld{
  public static final void main(String args[]){
    Long start, stop;
    start = System.currentTimeMillis();
    for(Integer i = 0; i < 100; ++i)
      System.out.println("Hello world!");
    stop = System.currentTimeMillis();
    System.out.println(stop - start + " milliseconds");
  }
}



public class Fact {

    public Fact(){
    }
    public Integer factorial(Integer n){
        if (n < 0)
            return 0;
        else if (n == 1)
            return 1;
        else
            return n * this.factorial(n - 1);
    }

}

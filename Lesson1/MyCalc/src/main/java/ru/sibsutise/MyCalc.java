
import java.util.Scanner;

public class MyCalc{
  public static final void main(String ars[]){
    Scanner in = new Scanner(System.in);


while(true){

  System.out.println("Push 1 to calculate");
  System.out.println("Push 0 to exit of program");

  Integer choice = in.nextInt();
  if(choice == 0)
    break;

  Double first, second, result = 0.0;
  String simbol;

  System.out.println("Please write what you want to calculate whith spacing:");
  first = in.nextDouble();
  simbol = in.next();
  second = in.nextDouble();

  switch(simbol){
    case "+":
      result = first + second;
      break;
    case "-":
      result = first - second;
      break;
    case "*":
      result = first * second;
      break;
    case "/":
      if (second == 0){
        System.out.println("Error: division by 0");
        return;
      }
      result = first/second;
      break;
  }
  System.out.println("Your result = " + result);
}
}

}

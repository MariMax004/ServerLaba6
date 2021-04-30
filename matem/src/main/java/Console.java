import java.util.Scanner;

public class Console {
    Scanner in = new Scanner(System.in);

    public void outText(String mess){
        System.out.println(mess);
    }

    public void outQuestion(String mess){
        System.out.print(mess);
    }

    public String inputText(){
        return in.nextLine();
    }

}

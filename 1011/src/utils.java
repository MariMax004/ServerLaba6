import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class utils {

    public static boolean print_prompts = true;
    public static ArrayList<String> LastCommands = new ArrayList<String>();
    public static RouteCollection LoadCollection(String filename){
        return null;
    }

    public static String RunScript(String filename, RouteCollection collection){

        try{
            File f = new File(filename);
            FileInputStream fin = new FileInputStream(f);
            Scanner input = new Scanner(fin);
            loop: while(input.hasNextLine()) {
                print_prompts = false;
                String result = Dispatcher.parseCommand(input, collection);
                if(result.equalsIgnoreCase("exit")) return "exit";
                System.out.println(result);
            }
        } catch (Exception e) {
            print_prompts = true;
            return "Ошибка выполнения скрипта";
        }
        print_prompts = true;
        return "Скрипт обработан успешно";
    }
}

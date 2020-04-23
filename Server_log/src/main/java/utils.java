import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

public class utils {

    public static boolean print_prompts = true;
    public static String input_file = "default_save";
    public static ArrayList<String> LastCommands = new ArrayList<String>();
    public static ArrayList <String> Script = new ArrayList();

    public static RouteCollection LoadCollection(String filename) {
        return null;
    }

    public static String RunScript(String filename, RouteCollection collection, History history) {
        try {
            File f = new File(filename);
            FileInputStream fin = new FileInputStream(f);
            Scanner input = new Scanner(fin);
            while (input.hasNextLine()) {
                print_prompts = false;
                //String result = Dispatcher.parseCommand(input, collection, filename, history);
                //if (result.equalsIgnoreCase("exit")) return "exit";
                //System.out.println(result);
            }
        } catch (Exception e) {
            print_prompts = true;
            return "Ошибка выполнения скрипта";
        }
        print_prompts = true;
        history.popScript(filename);
        return "Скрипт обработан успешно";
    }

    public static String getCheckSum(String fileName){
        try {
            byte[] buffer= new byte[8192];
            int count;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            bis.close();

            byte[] hash = digest.digest();
            BigInteger bigInt = new BigInteger(1,hash);
            return (bigInt.toString(16));
        } catch (Exception e) {
            return "Ошибка вычисления контрольной суммы";
        }



    }
}

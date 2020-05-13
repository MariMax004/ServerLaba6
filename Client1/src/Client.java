import CollectionObjects.Route;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        try {

            if (args.length<2) {
                System.out.println("Введите адрес сервера и порт");
                return;
            }

            String addr = args[0];
            int port = 0;
            try {
                port = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Порт должен быть числом");
                return;
            }

            ConnectModule connection = new ConnectModule(addr, port);

            Scanner input = new Scanner(System.in);
            History history = new History();

            //считываем команды
            loop:
            while (true) {
                try {
                    System.out.println("Введите команду: ");
                    String result = Dispatcher.parseCommand(input, history, connection);// запускаем статичный метод из диспатчер
                    if (result.equalsIgnoreCase("exit")){
                        break loop;
                    }

                    System.out.println(result);
                }catch (Exception e) {
                    System.out.println("Ошибка при выполнении команды");

                }
            }
        } finally {
            return;
        }
    }
}
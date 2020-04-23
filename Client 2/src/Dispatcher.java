import CollectionObjects.Route;

import java.awt.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Dispatcher {

    /**
     * Метод выводит в стандартный поток вывода строку со списком команд
     */
    private static String help = "help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add : добавить новый элемент в коллекцию\n" +
            "update id : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove id : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "add_if_max : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
            "remove_greater : удалить из коллекции все элементы, превышающие заданный\n" +
            "history : вывести последние 10 команд (без их аргументов)\n" +
            "remove_any_by_distance distance : удалить из коллекции один элемент, значение поля distance которого эквивалентно заданному\n" +
            "count_by_distance distance : вывести количество элементов, значение поля distance которых равно заданному\n" +
            "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку";

    /**
     * Метод осуществляет проверку на корректность вводимых данных и выводит в стандартный поток вывода информацию о каманде
     *
     * @param input объект класса Scanner
     * @return строка с информацией о выполненной команде
     */


    public static String parseCommand(Scanner input, History history, ConnectModule connection) {
        //считываем команду из консоли
        String line = input.nextLine();


        if (!line.isEmpty()) {//проверяем не пустая ли
            StringTokenizer t = new StringTokenizer(line);
            String command = t.nextToken();
            utils.LastCommands.add(command);// команду добавляем в статичный лист
            if (utils.LastCommands.size() > 10) utils.LastCommands.remove(0);
            //выполняем код для конкретноый команды
            switch (command) {
                case "exit":
                    return "exit";
                case "help":
                    return connection.runCommand(new Container("help"));
                case "info":
                    return  connection.runCommand(new Container("info"));
                case "show":
                    return  connection.runCommand(new Container("show"));
                case "add":
                {
                    Route newRoute = Commands.inputRoute(0,input);
                    return connection.runCommand(new Container("add",newRoute));
                }

                case "update":
                    if (t.hasMoreTokens())
                        return connection.runCommand(Commands.update(t.nextToken(), input));
                    else return "Формат команды: update id";
                case "remove":
                    if (t.hasMoreTokens()){
                        return connection.runCommand(Commands.remove(t.nextToken()));}
                    else return "Формат команды: remove id";
                case "clear":
                    return connection.runCommand(new Container("clear"));

              case "execute_script":
                        if (t.hasMoreTokens()) {
                            String new_file = t.nextToken();
                            if (history.addScript(new_file)) {
                                return utils.RunScript(new_file, history,connection);
                            } else return "Скрипт не может вызывать сам себя";
                        } else return "Формат команды: execute_script file_name";
                case "add_if_max":
                    return connection.runCommand(Commands.add_if_max(input));
                case "remove_greater":
                    if (t.hasMoreTokens())
                        return connection.runCommand(Commands.remove_greater(t.nextToken()));
                    else return "Формат команды: remove_greater distance";
                case "history":
                    return connection.runCommand(new Container("history"));
                case "remove_any_by_distance":
                    if (t.hasMoreTokens())
                        return connection.runCommand(Commands.remove_any_by_distance(t.nextToken()));
                    else return "Формат команды: remove_any_by_distance distance";
                case "count_by_distance":
                    if (t.hasMoreTokens())
                        return connection.runCommand(Commands.count_by_distance(t.nextToken()));
                    else return "Формат команды: count_by_distance distance";
                case "filter_contains_name":
                    if (t.hasMoreTokens())
                        return connection.runCommand(Commands.filter_contains_name(t.nextToken()));
                    else return "Формат команды: filter_contains_name name";

            }
        }
        return "Ошибка выполнения команды: " + line;
    }
}

import CollectionObjects.Route;

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
     * @param collection объект класса RouteCollection
     * @return строка с информацией о выполненной команде
     */
    public static String parseCommand(Container container, RouteCollection collection) {
        //считываем команду из консоли
        String command = container.getCommand();

        if (!command.isEmpty()) {//проверяем не пустая ли
            utils.LastCommands.add(command);// команду добавляем в статичный лист
            if (utils.LastCommands.size() > 10) utils.LastCommands.remove(0);
            //выполняем код для конкретноый команды
            switch (command) {
                case "help":
                    return help;
                case "info":
                    return Commands.info(collection);
                case "show":
                    return Commands.show(collection);
                case "add": {
                    Route newRoute = container.getRoute();
                    newRoute.setId(collection.getNextCounter());
                    return Commands.add(collection, newRoute);
                }

                case "update":
                    return Commands.update(collection, container);

                case "remove":

                    return Commands.remove(collection, container);
                case "clear":
                    return Commands.clear(collection);
              /*   case "save":
                    return Commands.save(collection, "save");
                case "execute_script":
                            if (history.addScript(container.getParametrString())) {
                                return utils.RunScript(container.getParametrString(), collection, history);
                            }/*/
                case "add_if_max":
                    return Commands.add_if_max(collection, container);
                case "remove_greater":
                    return Commands.remove_greater(collection, container.getParametr());
                case "history":
                    return Commands.history(collection);
                case "remove_any_by_distance":
                    return Commands.remove_any_by_distance(collection, container.getParametr());
                case "count_by_distance":
                    return Commands.count_by_distance(collection, container.getParametr());
                case "filter_contains_name":
                    return Commands.filter_contains_name(collection, container.getParametrString());
            }
        }
            return "Ошибка ввода команды";
        }
}
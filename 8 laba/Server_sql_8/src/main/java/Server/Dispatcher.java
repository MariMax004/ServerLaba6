package Server;

import Server.CollectionObjects.Route;
import javafx.scene.AmbientLight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dispatcher {

    static final Logger logger = LogManager.getRootLogger();

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

    private static String help_login = "help : вывести справку по доступным командам\n" +
            "login username password : произвести процедуру авторизации на сервере\n" +
            "register username password: зарегистрировать нового пользователя";
    /**
     * Метод осуществляет проверку на корректность вводимых данных и выводит в стандартный поток вывода информацию о каманде
     *
     * @param container объект класса Server.Container
     * @return строка с информацией о выполненной команде
     */
    public static Answer parseCommand(Container container) {
        //считываем команду из консоли
        String command = container.getCommand();


        if (!command.isEmpty()) {//проверяем не пустая ли
            if (!command.equalsIgnoreCase("show")) utils.LastCommands.add(command);// команду добавляем в статичный лист
            if (utils.LastCommands.size() > 10) utils.LastCommands.remove(0);

            //logger.info("Логин: " + container.getLogin() + " пароль: " + container.getPassword());

            //если команда регистрация то регистрируем
            if (command.equalsIgnoreCase("register")){
                return new Answer(Commands.register(container));
            }

            //проверяем имя пользоваетля и пароль
            if(!Commands.check_login(container) && !command.equalsIgnoreCase("register")){
                if (command.equalsIgnoreCase("help_login")) return new Answer(help_login);

                return new Answer("Неверный логин или пароль");
            }

            //выполняем код для конкретноый команды
            switch (command) {
                case "help":
                    return new Answer(help);
                case "info":
                    return new Answer(Commands.info());
                case "show":
                    return new Answer("Список отправлен", Commands.show());
                case "add": {
                    Route newRoute = container.getRoute();
                    return new Answer(Commands.add(newRoute));
                }
                case "update":
                    return new Answer(Commands.update(container));
                case "remove":
                    return new Answer(Commands.remove(container));
                case "clear":
                    return new Answer(Commands.clear(container));
              /*   case "save":
                    return Server.Commands.save(collection, "save");
                case "execute_script":
                            if (history.addScript(container.getParametrString())) {
                                return Server.utils.RunScript(container.getParametrString(), collection, history);
                            }/*/
                case "add_if_max":
                    return new Answer(Commands.add_if_max(container));
                case "remove_greater":
                    return new Answer(Commands.remove_greater(container));
                case "history":
                    return new Answer(Commands.history());
                case "remove_any_by_distance":
                    return new Answer(Commands.remove_any_by_distance(container));
                case "count_by_distance":
                    return new Answer(Commands.count_by_distance(container.getParametr()));
                case "filter_contains_name":
                    return new Answer("Список отправлен", Commands.filter_contains_name(container.getParametrString()));
                case "login":
                    return new Answer("Авторизация успешна");
            }
        }
            return new Answer("Ошибка ввода команды");
        }
}
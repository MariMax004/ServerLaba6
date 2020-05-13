import CollectionObjects.Coordinates;
import CollectionObjects.Location_from;
import CollectionObjects.Location_to;
import CollectionObjects.Route;

import javax.xml.bind.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Scanner;
/**
 *Класс Commands хранит в себе информацию и назначения всех команд
 */
public class Commands {

    /**
     * Метод выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     * @param collection  обьект класса RouteCollection
     * @return  отформатированная строка с информацией о коллекции
     */
    public static String info(RouteCollection collection) {
        return "Тип коллекции: HashSet элементов Route \n" +
                "Дата инициализации: " + collection.getTime() + "\n" +
                "Количество элементов в коллекции: " + collection.getSet().size();
    }

    /**
     * Метод выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     * @param collection  обьект класса RouteCollection
     * @return  строка с информацией об элементах коллекции
     */
    public static String show(RouteCollection collection) {
        collection.getSet();
        Iterator<Route> elements = collection.getSet().iterator();
        while (elements.hasNext())
            System.out.println(elements.next().showInfo());
        return "Конец информации о коллекции";
    }
    /**
     * Метод  добавляет новый элемент в коллекцию
     * @param collection  обьект класса RouteCollection
     * @param input объект типа Scanner
     * @return  строка с информацией об элементах коллекции
     */
    public static String add(RouteCollection collection, Scanner input) {
        collection.getSet().add(inputRoute(collection.getNextCounter(), input));
        return "Элемент успешно добавлен";
    }


    /**
     * Метод обновляет значение элемента коллекции, id которого равен заданному
     * @param id  уникальный идентификатор элемента
     * @param input объект типа Scanner
     * @return  строка с информацией об элементах коллекции
     */
   /*/ public static Container update( String id, Scanner input) {

            Route new_route = inputRoute(Integer.parseInt(id), input);

            return new Container("update", new_route);

    }/*/
    public static Container update( String id, Scanner input) {

        Route new_route = inputRoute(Integer.parseInt(id), input);

        return new Container("update", new_route);

    }
    /**
     * Метод удаляет элемент из коллекции по его id
     * @param id  уникальный идентификатор элемента
     * @return  строка с информацией об элементах коллекции
     */
    public static Container remove(String id) {
        Route dummy = new Route(Integer.parseInt(id));
        return new Container("remove",dummy);
    }
    /**
     * Метод очищает коллекцию
     * @param collection  обьект класса RouteCollection
     * @return  строка с информацией об элементах коллекции
     */
    public static String clear(RouteCollection collection) {
        collection.getSet().clear();
        return "Коллекция очищена";
    }

    /**
     * Метод  сохранить коллекцию в файл
     * @param collection  обьект класса RouteCollection
     * @param filename  строка с названием файла
     * @return filename строка с информацией об элементах коллекции
     */
    public static String save(RouteCollection collection, String filename) {
        //писать результат сериализации будем в Writer(StringWriter)
        try{
            BufferedWriter xml_writer = new BufferedWriter( new FileWriter(utils.input_file));
            //создание объекта Marshaller, который выполняет сериализацию
            JAXBContext context = JAXBContext.newInstance(RouteCollection.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            marshaller.marshal(collection, xml_writer);
            xml_writer.flush();
            xml_writer.close();

            //считаем контрольную сумму и записываем на диск
            BufferedWriter checksum_writer = new BufferedWriter( new FileWriter(utils.input_file+".md5"));
            checksum_writer.write(utils.getCheckSum(utils.input_file));
            checksum_writer.flush();
            checksum_writer.close();
        } catch (Exception e) {
            return "Ошибка сохранения";
        }
        return "Файл сохранен";

    }

    /**
     * Метод добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     * @param input объект типа Scanner
     * @return  строка с информацией об элементах коллекции
     */
    public static Container add_if_max(Scanner input) {
        Route new_route = inputRoute(0, input);

        return new Container("add_if_max", new_route);
    }
    /**
     * Метод  удаляет из коллекции все элементы, превышающие заданный
     * @param d значение параметра строки
     * @return  строка с информацией об элементах коллекции
     */
    public static Container remove_greater(String d) {
        double dist = Double.parseDouble(d);

        return new Container("remove_greater", dist);
    }
    /**
     * Метод выводит последние 10 команд (без их аргументов)
     * @param collection  обьект класса RouteCollection
     * @return  строка с информацией о командах
     */
    public static String history(RouteCollection collection) {
        System.out.println("Список последних команд: ");
        for (int i = 1; i < utils.LastCommands.size(); i++) {
            System.out.println(i + ". " + utils.LastCommands.get(i - 1));
        }
        return "Список команд выведен успешно";
    }
    /**
     * Метод удаляет из коллекции один элемент, значение поля distance которого эквивалентно заданному
     * @param d значение параметра строки
     * @return  строка с информацией об элементах коллекции
     */
    public static Container remove_any_by_distance(String d) {
        double dist = Double.parseDouble(d);

        return new Container("remove_any_by_distance", dist);

    }
    /**
     * Метод  выводит количество элементов, значение поля distance которых равно заданному
     * @param d значение параметра строки
     * @return  строка с информацией об элементах коллекции
     */
    public static Container count_by_distance(String d) {
        double dist = Double.parseDouble(d);

        return new Container("count_by_distance",dist);
    }
    /**
     * Метод выводит элементы, значение поля name которых содержит заданную подстроку
     * @param name значение параметра строки
     * @return  строка с информацией об элементах коллекции
     */
    public static Container filter_contains_name(String name) {
        System.out.println("Элементы, содежращие фрагмент в name:");
        return new Container("filter_contains_name",name);
    }
    /**
     * Метод формирует маршрут с заданными пользователем координатами и названиями начала и конца пути
     * @param id  уникальный идентификатор элемента
     * @param input объект класса Scanner
     * @return  новый обьект с id, name, coord, now, from, to
     */
    public static Route inputRoute(int id, Scanner input) {
//        private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
//        private String name; //Поле не может быть null, Строка не может быть пустой
        String name = "";
        while (name.isEmpty()) {
            if (utils.print_prompts) System.out.println("Пожалуйста введите имя маршрута: ");
            name = input.nextLine();
        }

        //private Coordinates coordinates; //Поле не может быть null
        if (utils.print_prompts) System.out.println("Пожалуйста X координат: ");
        int coord_x = getInt(input);

        if (utils.print_prompts) System.out.println("Пожалуйста Y координат: ");
        int coord_y = getInt(input);

        Coordinates coord = new Coordinates(coord_x, coord_y);
        //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        ZonedDateTime now = ZonedDateTime.now();

        if (utils.print_prompts) System.out.println("Пожалуйста X начала пути: ");
        double from_x = getDouble(input);

        if (utils.print_prompts) System.out.println("Пожалуйста Y начала пути: ");
        long from_y = getLong(input);

        String from_name = "";
        while (from_name.isEmpty()) {
            if (utils.print_prompts) System.out.println("Пожалуйста название начала пути: ");
            from_name = input.nextLine();
        }

        Location_from from = new Location_from(from_x, from_y, from_name);

        if (utils.print_prompts) System.out.println("Пожалуйста X конца пути: ");
        long to_x = getLong(input);

        if (utils.print_prompts) System.out.println("Пожалуйста Y конца пути: ");
        int to_y = getInt(input);

        if (utils.print_prompts) System.out.println("Пожалуйста Z конца пути: ");
        int to_z = getInt(input);

        String to_name = "";
        while (to_name.isEmpty()) {
            if (utils.print_prompts) System.out.println("Пожалуйста название конца пути: ");
            to_name = input.nextLine();
        }
        Location_to to = new Location_to(to_x, to_y, to_z, to_name);


        return new Route(id, name, coord, now, from, to);
    }
    /**
     * Метод осуществляет проверку на корректность вводимых данных и переводит строку в формат int
     * @param in объект класса Scanner
     * @return  результат в формате int
     */
    public static int getInt(Scanner in) {
        int result = 0;
        String n="";
        while (!in.hasNextInt()) {
            System.out.println("(введите коодинату в формате int)");
            in.next();
        }
        result = in.nextInt();
        in.nextLine();
        return result;
    }
    /**
     * Метод осуществляет проверку на корректность вводимых данных и переводит строку в формат double
     * @param in объект класса Scanner
     * @return  результат в формате int
     */
    public static double getDouble(Scanner in) {
        double result = 0.d;
        String n="";
        while (!in.hasNextDouble()) {
            System.out.println("(введите коодинату в формате double)");
            in.next();
        }
        result = in.nextDouble();
        in.nextLine();
        return result;
    }

    public static long getLong(Scanner in) {
        long result = 0;
        String n="";
        while (!in.hasNextLong()) {
            System.out.println("(введите коодинату в формате long)");
            in.next();
        }
        result = in.nextLong();
        in.nextLine();
        return result;
    }
}

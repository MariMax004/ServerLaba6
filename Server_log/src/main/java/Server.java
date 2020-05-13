import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Server {

    private static Selector selector;
    private static ServerSocketChannel serverSocket;

    static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        */
        if (args.length<2) {
            System.out.println("Пожалуйста введите порт и имя файла в коммандной строке");
            logger.error("Порт или имя файла не было введено");
            return;
        }

        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e){
            return;
        }

        logger.info("Порт " + port);

        utils.input_file = args[1];
        //инициализация коллекции
        RouteCollection collection = new RouteCollection();


        //пытаемся загрузить файл с диска
        try{
            //getting the xml file to read
            File file = new File(utils.input_file);

            if(file.exists()){
                logger.error("Файл найден. Попытка загрузки из файла");
                try {
                    String checksum_new = utils.getCheckSum(utils.input_file);
                    BufferedReader checksum_reader = new BufferedReader(new InputStreamReader(new FileInputStream(utils.input_file+".md5")));
                    String checksum_old = checksum_reader.readLine();
                    checksum_reader.close();
                    if(checksum_new.equalsIgnoreCase(checksum_old)){
                        BufferedReader xml_reader = new BufferedReader(new InputStreamReader(new FileInputStream(utils.input_file)));
                        //задаем контекст библиотеки JAXB
                        JAXBContext jContext = JAXBContext.newInstance(RouteCollection.class);
                        //создаем объект парсера XML
                        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
                        //загружаем сохраненные данные из XML в коллекцию
                        collection = (RouteCollection) unmarshallerObj.unmarshal(xml_reader);
                        logger.info("Файл загружен успешно");
                    } else
                        logger.error("Файл был изменен и не может быть загружен");
                } catch (Exception e) {
                   logger.error("Ошибка парсинга XML");
                }
            } else
            {
               logger.error("Файл не найден. Новый файл создан");
                file.createNewFile();
            }
        }catch(Exception e){
            logger.error("Ошибка чтения из файла");
        }

        ConsoleReader cn = new ConsoleReader(collection);
        cn.start();

        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(port));
       logger.info("Сервер запущен");


        while (true) {
            try{
                logger.info("Ожидаем соединения");
                Selector selector = accept();
                logger.info("Соединение принято, считываем команду");
                ByteBuffer buffer = ByteBuffer.allocate(16384);
                SocketChannel channel = null;
                while (channel == null){
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    for (SelectionKey key : selectedKeys) {
                        if(key.isReadable()){
                            channel = (SocketChannel)key.channel();
                            channel.read(buffer);
                            buffer.flip();
                            channel.register(selector, SelectionKey.OP_WRITE);
                            break;
                        }
                    }
                }

                //Чтение размера строки с командой

                byte[] bytes = new byte[buffer.limit()];//buffer.array();
                buffer.get(bytes);

                ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));

                Container container = new Container("empty");
                try {
                    container = (Container) inputStream.readObject();
                } catch (EOFException eofex) {
                    //ничего не делаем
                }

                if(!container.getCommand().equalsIgnoreCase("empty")){
                    System.out.println("Считано " + bytes.length + " байт");
                    System.out.println("Команда: " + container.getCommand());
                    Answer answer = new Answer(Dispatcher.parseCommand(container, collection));// запускаем статичн

                    buffer.clear();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutput out = new ObjectOutputStream(bos);
                    out.writeObject(answer);
                    out.flush();

                    bytes = bos.toByteArray();
                    buffer = ByteBuffer.wrap(bytes);

                    channel = null;
                    while (channel == null){
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        for (SelectionKey key : selectedKeys) {
                            if(key.isWritable()){
                                channel = (SocketChannel)key.channel();
                                while(buffer.hasRemaining()) {
                                    channel.write(buffer);
                                }
                                break;
                            }
                        }
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                channel.socket().close();
                channel.close();
                selector.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static Selector accept() throws IOException {
        selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for (SelectionKey key : selectedKeys) {
                if (key.isAcceptable()) {
                    key.cancel();
                    SocketChannel client = serverSocket.accept();
                    client.configureBlocking(false);
                    client.register(selector,SelectionKey.OP_READ);
                    return selector;
                }
            }
        }
    }
}
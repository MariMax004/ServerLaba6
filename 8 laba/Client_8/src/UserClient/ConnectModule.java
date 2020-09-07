package UserClient;

import Server.Answer;
import Server.CollectionObjects.Route;
import Server.Container;
import Server.RouteRow;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectModule {

    String  host;
    int port;

    public ConnectModule(String h, int p) {
        this.host = h;
        this.port = p;
    }

    public String runCommand(Container command) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream objOutpusStream = new ObjectOutputStream(bos);
            objOutpusStream.writeObject(command);
            objOutpusStream.flush();
            objOutpusStream.close();
            byte[] bytes = bos.toByteArray();

            Socket s = new Socket();
            s.connect(new InetSocketAddress(host, port), 5);

            BufferedOutputStream outputStream = new BufferedOutputStream(s.getOutputStream());
            outputStream.write(bytes);
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
            Answer answer = (Answer) inputStream.readObject();
            s.close();
            return  answer.getResult();
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Ошибка соединения");
        }
        return "Ошибка выполнения команды";
    }

    public List<RouteRow> runShow() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream objOutpusStream = new ObjectOutputStream(bos);
            objOutpusStream.writeObject(new Container("show"));
            objOutpusStream.flush();
            objOutpusStream.close();
            byte[] bytes = bos.toByteArray();

            Socket s = new Socket();
            s.connect(new InetSocketAddress(host, port), 5);

            BufferedOutputStream outputStream = new BufferedOutputStream(s.getOutputStream());
            outputStream.write(bytes);
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
            Answer answer = (Answer) inputStream.readObject();
            s.close();
            return  answer.getRouteRowList();
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Ошибка соединения");
        }
        return new ArrayList<RouteRow>();
    }
}

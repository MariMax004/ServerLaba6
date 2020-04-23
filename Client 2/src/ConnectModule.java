import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectModule {

    public ConnectModule() {

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
            s.connect(new InetSocketAddress("127.0.0.1", 9999), 5);

            BufferedOutputStream outputStream = new BufferedOutputStream(s.getOutputStream());
            outputStream.write(bytes);
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
            Answer answer = (Answer) inputStream.readObject();
            return  answer.getResult();
        } catch (Exception e){
            System.err.println("Ошибка соединения");
        }
        return "Ошибка выполнения команды";
    }
}

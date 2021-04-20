package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.0.107",8188);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true){
                String response = in.readUTF(); // Читаем ответ сервера
                System.out.println(response);
                Scanner scanner = new Scanner(System.in);
                String request = scanner.next();
                out.writeUTF(request); // Отправляем сообщение серверу
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

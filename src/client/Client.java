package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) { // Основной поток
        try {
            Socket socket = new Socket("192.168.0.107", 8188);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String response = null; // Читаем ответ сервера
                        try {
                            response = in.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(response);
                    }
                }
            });
            thread.start();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String request = scanner.nextLine();
                out.writeUTF(request); // Отправляем сообщение серверу
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

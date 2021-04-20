package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>(); // Колекция пользователей
        try {
            ServerSocket serverSocket = new ServerSocket(8188);
            System.out.println("Сервер запущен");
            int i = 0;

            while (true) {
                Socket socket = serverSocket.accept(); //Ожидаем клиента
                System.out.println("Клиент подключился");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            out.writeUTF("Введите имя: ");
                            String userName = in.readUTF();
                            User user = new User(userName, socket);
                            users.add(user);
                            out.writeUTF(userName + " добро пожаловать на сервер!");
                            while (true) {
                                String request = in.readUTF(); //Ждем сообщение от клиента
                                System.out.println(userName+" : "+request);
                                for (User user1 : users) {
                                    if ()
                                    user1.sendMessage(userName + " : " + request);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

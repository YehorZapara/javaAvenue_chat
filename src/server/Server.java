package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<User> users = new ArrayList<>(); // Коллекция пользователей
    public static void main(String[] args) { // Основной поток
        try {
            ServerSocket serverSocket = new ServerSocket(8188);
            System.out.println("Сервер запущен");
            while (true){
                Socket socket = serverSocket.accept(); // Ожидаем клиента
                User user = new User(socket);
                System.out.println("Клиент подключился");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                user.setOos(oos);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                user.getOos().writeObject("Введите имя: ");
                String userName = in.readUTF();
                user.setUserName(userName);
                users.add(user);
                sendUserList();
                user.getOos().writeObject(userName+" добро пожаловать на сервер!");
                Thread thread = new Thread(new Runnable() { // Поток для клиента
                    @Override
                    public void run() {
                        try {
                            while (true){
                                String request = in.readUTF(); // Ждём сообщение от клиента
                                System.out.println(userName+": "+request);
                                for (User user1 : users){
                                    if(user1.equals(user)) continue;
                                    user1.sendMessage(user.getUserName()+": "+request);
                                }
                            }
                        }catch (Exception e){
                            System.out.println(user.getUserName()+" покинул чат");
                            users.remove(user);
                            for (User user1:users) {
                                try {
                                    user1.getOos().writeObject("Пользователь "+user.getUserName()+" покинул чат");
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            sendUserList();
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendUserList(){
        String usersName = "**userList**";
        for (User user:users) {
            usersName += "//"+user.getUserName();//     **userList**//user1//user2//user3//user4
        }
        for (User user: users) {
            try {
                user.getOos().writeObject(usersName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
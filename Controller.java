package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    private void connect() {
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
                            textArea.appendText(response + "\n");
                            System.out.println(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            thread.start();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        String request = scanner.nextLine();
                        try {
                            out.writeUTF(request); // Отправляем сообщение серверу
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread1.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
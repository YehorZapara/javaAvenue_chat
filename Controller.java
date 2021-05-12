package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Controller {
    Socket socket;
    DataOutputStream out;
    @FXML
    TextArea textAreaUserList;
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    private void send(){
        String text = textField.getText();
        try {
            out.writeUTF(text);
            textField.clear();
            textField.requestFocus();
            textArea.appendText("Вы: "+text+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void connect(){
        try {
            socket = new Socket("localhost",8188);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            String response = ois.readObject().toString(); // Ждём сообщение от сервера
                            if(response.indexOf("**userList**") == 0){
                                String[] usersName= response.split("//");
                                textAreaUserList.clear();
                                for (String userName:usersName) {
                                    textAreaUserList.appendText(userName+"\n");
                                }
                            }else{
                                textArea.appendText(response+"\n");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
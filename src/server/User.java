package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class User {
    private String userName;
    private Socket socket;
    private UUID uuid;
    private ObjectOutputStream oos;

    public User(Socket socket) {
        this.userName = "Гость";
        this.socket = socket;
        this.uuid = UUID.randomUUID();
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public boolean equals(User user) {
        return (this.uuid.toString().equals(user.uuid.toString()));
    }
    public String getUserName() { return userName;}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Socket getSocket() { return socket; }
    public void sendMessage(String msg){
        try {
            this.getOos().writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
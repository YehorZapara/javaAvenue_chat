package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class User {
    private String userName;
    private Socket socket;
    private UUID uuid;

    public User(String userName, Socket socket) {
        this.userName = userName;
        this.socket = socket;
        this.uuid = UUID.randomUUID();
    }
    public String getUserName() { return userName; }
    public Socket getSocket() { return socket; }

    public void sendMessage(String msg){
        try {
            DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

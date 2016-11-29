package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by MIRIK on 16.05.2016.
 */

public class User implements Serializable {
    private String userName;
    private Socket socket = new Socket();
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public User () {

    }

    public User(Socket s) {
        this.socket = s;
    }

    public User (String userName) {
        this.userName = userName;
    }

    public User (String userName, Socket s) {
        this.socket = s;
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String string) {
        this.userName = string;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setIn(ObjectInputStream ois) {
        this.in = ois;
    }

    public void setOut (ObjectOutputStream oos) {
        this.out = oos;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void sendMsg(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String s) {
        try {
            out.writeObject(new Message(userName, s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) object;
        return this.userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return this.userName.hashCode();
    }

}

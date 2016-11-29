package server;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by MIRIK on 16.05.2016.
 */

public class Message implements Serializable {
    private String msg;
    private String userName;
    private Date time;

    public  Message() {

    }

    public Message(String user, String msg) {
        this.time = new Date();
        this.userName = user;
        this.msg = msg;
    }

    public void sendMsgTo (User user) {
        try {
            user.getOut().writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAuthor() {
        return userName;
    }

    @Override
    public String toString() {
        return "[" + DateFormat.getTimeInstance(DateFormat.MEDIUM).format(time) + "] " + userName + ": " + msg;
    }

}

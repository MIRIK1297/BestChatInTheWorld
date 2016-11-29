package client.gui;

import client.gui.view.ClientView;
import server.Message;

import javax.swing.*;

/**
 * Created by MIRIK on 22.05.2016.
 */
public class Controller {
    private Message msg = new Message();
    private String msgText;
    private ClientView jFrame;
    private ChatClient chatClient;

    public void setjFrame(ClientView jFrame) {
        this.jFrame = jFrame;
    }

    public void connect(String userName, String serverAddress, String serverPort) {
        chatClient = new ChatClient(userName, serverAddress, Integer.parseInt(serverPort), jFrame);
    }

    public void newMsg(String s) {
        chatClient.sendMsg(s);
    }
}

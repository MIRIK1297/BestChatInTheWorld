package client.gui;

import javax.swing.*;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

import client.gui.view.ClientView;
import server.*;

/**
 * Created by MIRIK on 28.04.2016.
 */


public class ChatClient implements Runnable {
    private static int serverPort;
    private static User user;
    private static String serverAddress;
    private static String userName;
    //private Scanner scan = new Scanner(System.in);
    private static ClientView view;
    private static Controller controller = new Controller();
    private Thread chatThread;
    private ClientView jFrame;
    private Message msg;

    public ChatClient(String userName, String serverAddress, int serverPort, ClientView jFrame) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.userName = userName;
        this.jFrame = jFrame;
        chatThread = new Thread(this, "ChatClient");
        chatThread.start();
    }

    public static void main(String[] args) {
//        ChatClient client = new ChatClient();
//        Message msg;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view = new ClientView(controller);
            }
        });

/*        try {
            InetAddress ipAddress = InetAddress.getByName(view.getDialog().getServerIP());
            user = new User(view.getDialog().getUserName(), new Socket(ipAddress, Integer.parseInt(view.getDialog().getServerPort())));
            user.setOut(new ObjectOutputStream(user.getSocket().getOutputStream()));
            user.setIn(new ObjectInputStream(user.getSocket().getInputStream()));
            user.getOut().writeObject(new Message(user.getUserName(), "I'm here."));

            new ChatClientInput(user.getIn());

            while (true) {
                msg = new Message(user.getUserName(), scan.nextLine());
                user.sendMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {
        try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            user = new User(userName, new Socket(ipAddress, serverPort));
            user.setOut(new ObjectOutputStream(user.getSocket().getOutputStream()));
            user.getOut().flush();
            user.setIn(new ObjectInputStream(user.getSocket().getInputStream()));
            user.getOut().writeObject(new Message(user.getUserName(), "I'm here!"));
            jFrame.setIsConnected(true);

            while (true) {
                msg = (Message) user.getIn().readObject();
                jFrame.printMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            jFrame.printMsg(new Message("Console","Can't reach the server."));
        }
    }

    public void sendMsg(String s) {
        user.sendMsg(s);
    }

    /*
        private static class ChatClientOutput implements Runnable {
        private ObjectOutputStream oos;
        private Thread cco;
        private Message msg;

        private ChatClientOutput(ObjectOutputStream oos) {
            this.oos = oos;
        }

        @Override
        public void run() {
            try {
                user.getOut().writeObject(new Message(user.getUserName(), "I'm here."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ChatClientInput implements Runnable {
        private ObjectInputStream ois;
        private Thread cci;
        private Message msg;

        private ChatClientInput(ObjectInputStream ois) {
            this.ois = ois;
            cci = new Thread(this, "ChatClientInput");
            cci.start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    msg = (Message) ois.readObject();
                    view.printMsg(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
        }
    }
    */
}

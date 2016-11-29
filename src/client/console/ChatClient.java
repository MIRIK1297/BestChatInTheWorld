package client.console;

import client.gui.Controller;
import client.gui.view.ClientView;
import server.Message;
import server.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by MIRIK on 28.04.2016.
 */


public class ChatClient {
    private static final int serverPort = 3228;
    private static User user;
    private static String serverAddress = "217.197.4.18";
    private static String userName;
    private static Scanner scan = new Scanner(System.in);
    private static ClientView view;
    private static Controller controller = new Controller();


    public static void main(String[] args) {
        Message msg;

       try {
            InetAddress ipAddress = InetAddress.getByName(serverAddress);
            user = new User(scan.nextLine(), new Socket(ipAddress, serverPort));
            user.setOut(new ObjectOutputStream(user.getSocket().getOutputStream()));
            user.setIn(new ObjectInputStream(user.getSocket().getInputStream()));
            user.getOut().writeObject(new Message(user.getUserName(), "I'm here."));

            new ChatClientInput(user.getIn());

            while (true) {
                msg = new Message(user.getUserName(), scan.nextLine());
                user.sendMsg(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ChatClientInput implements Runnable {
        private ObjectInputStream in;
        private Thread cci;
        private Message msg;

        private ChatClientInput(ObjectInputStream in) {
            this.in = in;
            cci = new Thread(this, "ChatClientInput");
            cci.start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    msg = (Message) in.readObject();
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
        }
    }
}

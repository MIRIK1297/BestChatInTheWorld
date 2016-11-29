package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by MIRIK on 28.04.2016.
 */

public class ChatServer {
    private static HashSet<User> userList = new HashSet<>();
    private static HashSet<User> onlineClients = new HashSet<>();
    private static User server = new User("Server");

    public static void main(String[] args) throws IOException {
        int port = 3228;
        Socket socket;

        ServerSocket serverSocket = new ServerSocket(port);
        try {
            System.out.println("TheBestChatInTheWorld is started.\nWaiting for a client...");
            while(true) {
                socket = serverSocket.accept();
                new ClientStream(socket);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static class ClientStream implements Runnable{
        private Thread clientThread;
        private User user;
        private Message msg;

        private ClientStream(Socket socket) {
            user = new User(socket);
            clientThread = new Thread(this, "ClientStream");
            clientThread.start();
        }

        private synchronized void sendAll(Message msg) {
            for (User user: onlineClients) {
                user.sendMsg(msg);
            }
        }

        private void close() {
            try {
                user.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                user.setIn(new ObjectInputStream(user.getSocket().getInputStream()));
                user.setOut(new ObjectOutputStream(user.getSocket().getOutputStream()));
                msg = (Message) user.getIn().readObject();
                user.setUserName(msg.getAuthor());
                if (!userList.contains(user)) {
                    userList.add(user);
                }
                msg = new Message(server.getUserName(), user.getUserName() + " connected to chat.");
                new Message(server.getUserName(), "Welcome to TheBestChatInTheWorld!").sendMsgTo(user);
                sendAll(msg);
                System.out.println(msg.toString());
                onlineClients.add(user);

                while (true) {
                    msg = (Message) user.getIn().readObject();
                    System.out.println(msg.toString());
                    this.sendAll(msg);
                }
            } catch (IOException e) {
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            } finally {
                onlineClients.remove(user);
                msg = new Message(server.getUserName(), user.getUserName() + " left the chat.");
                sendAll(msg);
                System.out.println(msg.toString());
                close();
            }
        }

    }

}

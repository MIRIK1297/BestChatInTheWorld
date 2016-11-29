package client.gui.view;

import client.gui.Controller;
import server.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by MIRIK on 22.05.2016.
 */

public class ClientView extends JFrame {
    private JButton sendButton;
    private JTextField msgField;
    private JTextArea chatHistory;
    private JScrollPane chatScroll;
    private ConnectDialog cDialog;
    private JPanel msgArea;
    private Controller controller;
    private boolean isConnected = false;

    public ClientView(Controller controller) {
        super("TheBestChatInTheWorld");
        this.controller = controller;
        controller.setjFrame(this);

        createMenu();

        setLocation(500, 500);
        setSize(350, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        msgArea = new JPanel(new FlowLayout());
        msgArea.add(msgField = new JTextField(24));
        msgArea.add(sendButton = new JButton("Send"));

        sendButton.addActionListener(event -> {
            if (isConnected) {
                controller.newMsg(msgField.getText());
                msgField.setText("");
            }
        });

        msgField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == '\n' && isConnected) {
                    controller.newMsg(msgField.getText());
                    msgField.setText("");
                }

            }
        });

        chatHistory = new JTextArea(15, 20);
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);

        chatScroll = new JScrollPane(chatHistory);

        add(chatScroll, BorderLayout.NORTH);
        add(new JLabel("Message:"), BorderLayout.CENTER);
        add(msgArea, BorderLayout.SOUTH);

        setVisible(true);

    }

    public void printMsg(Message msg) {
        chatHistory.append(msg.toString() + '\n');
    }

    public void setIsConnected(boolean b) {
        isConnected = b;
    }

    public ConnectDialog getDialog() {
        return cDialog;
    }

    private void createMenu() {
        JMenuBar mbar = new JMenuBar();
        setJMenuBar(mbar);

        JMenu file = new JMenu("File");
        mbar.add(file);

        JMenuItem connect = new JMenuItem("Connect");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(connect);
        file.add(exit);

        JMenu about = new JMenu("About");
        mbar.add(about);

        connect.addActionListener(event -> {
            cDialog = new ConnectDialog();
            cDialog.setVisible(true);
            cDialog.getConnectButton().addActionListener(ivent -> {
                controller.connect(cDialog.getUserName(), cDialog.getServerIP(), cDialog.getServerPort());
                cDialog.setVisible(false);
            });
        });


        exit.addActionListener(event -> {
            System.exit(0);
        });
    }
}

package client.gui.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by MIRIK on 22.05.2016.
 */
public class ConnectDialog extends JDialog {
    private JTextField userName;
    private JTextField serverIP;
    private JTextField serverPort;
    private JPanel dialog;
    private JButton connectButton;


    public ConnectDialog() {
        dialog = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();

        setSize(200, 130);
        setLocation(500, 500);

        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("User name:"));
        panel.add(userName = new JTextField());
        panel.add(new JLabel("Sever IP:"));
        panel.add(serverIP = new JTextField("localhost"));
        panel.add(new JLabel("Port:"));
        panel.add(serverPort = new JTextField("3228"));

        dialog.add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        connectButton = new JButton("Connect");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> {
            this.setVisible(false);
        });

        buttonPanel.add(connectButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        add(dialog);
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getServerIP() {
        return serverIP.getText();
    }

    public String getServerPort() {
        return serverPort.getText();
    }

    public JButton getConnectButton() {
        return connectButton;
    }
}

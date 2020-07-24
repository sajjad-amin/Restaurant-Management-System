package com.sajjadamin.restaurantmanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Auth {

    private final String username, password;

    Auth(String username, String password){
        this.username = username;
        this.password = password;
        initComponent();
    }

    private void initComponent() {
        ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
        JFrame frame = new JFrame();
        frame.setTitle("Login");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,400,200);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        GUI.createLabel("Username", new int[]{40,50,100,20}, frame);
        GUI.createLabel("Password", new int[]{40,80,100,20}, frame);
        JTextArea user = new JTextArea();
        user.setBounds(140,50,220,20);
        frame.add(user);
        JTextArea pass = new JTextArea();
        pass.setBounds(140,80,220,20);
        frame.add(pass);
        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(280,120,80,25);
        frame.add(loginBtn);
        loginBtn.addActionListener((ActionEvent ae)->{
            try {
                doAuthenticate(frame,user.getText(),pass.getText());
            } catch (SQLException ex) {
                Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void doAuthenticate(JFrame frame, String user, String pass) throws SQLException{
        if (username.equals(user) && password.equals(pass)) {
            Database db = new Database();
            db.createTable();
            new GUI();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null,"Username or Password incurrect","Login Failed",JOptionPane.ERROR_MESSAGE);
        }
    }
}

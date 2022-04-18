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
        frame.setBounds(0,0,300,160);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);
        frame.setLayout(null);
        GUI.createLabel("Username", new int[]{10,20,80,20}, frame);
        GUI.createLabel("Password", new int[]{10,50,80,20}, frame);
        JTextField user = new JTextField();
        user.setBounds(80,20,190,20);
        frame.add(user);
        JPasswordField pass = new JPasswordField();
        pass.setBounds(80,50,190,20);
        frame.add(pass);
        JButton loginBtn = new JButton("Log in");
        loginBtn.setBounds(190,80,80,25);
        frame.add(loginBtn);
        loginBtn.addActionListener((ActionEvent ae)->{
            try {
                char[] pswd = pass.getPassword();
                StringBuilder password = new StringBuilder();
                for(char c : pswd){
                    password.append(c);
                }
                doAuthenticate(frame,user.getText(),password.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        frame.setVisible(true);
    }
    private void doAuthenticate(JFrame frame, String user, String pass) throws SQLException{
        if (username.equals(user) && password.equals(pass)) {
            Database db = new Database();
            db.createTable();
            new GUI();
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null,"Username or Password incorrect","Login Failed",JOptionPane.ERROR_MESSAGE);
        }
    }
}

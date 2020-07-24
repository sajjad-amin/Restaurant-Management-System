package com.sajjadamin.restaurantmanagementsystem;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection connect() {
        Connection conn = null;
        try {
            //create database file
            String path = System.getProperty("user.dir")+ File.separator+"database"+File.separator+"database.db";
            File database = new File(path);
            database.getParentFile().mkdirs();
            database.createNewFile();
            // db parameters
            String url = "jdbc:sqlite:database/database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean createTable() throws SQLException{
        Statement stmt = connect().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS food (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, item TEXT, price INTEGER, quantity INTEGER);";
        return stmt.execute(sql);
    }

    public ArrayList getAllData() throws SQLException{
        ArrayList<DataList> list = new ArrayList();
        String sql = "SELECT * FROM food";
        Statement stmt = connect().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            String item = rs.getString("item");
            int price = rs.getInt("price");
            int quantity = rs.getInt("quantity");
            list.add(new DataList(id,item,price,quantity));
        }
        return list;
    }

    public void addData(String item, int price, int quantity) throws SQLException {
        String sql = "INSERT INTO food(item, price, quantity) VALUES(?,?,?)";
        PreparedStatement ps = connect().prepareStatement(sql);
        ps.setString(1, item);
        ps.setInt(2, price);
        ps.setInt(3, quantity);
        ps.executeUpdate();
    }

    public void updateData(int id, String item, int price, int quantity) throws SQLException {
        String sql = "UPDATE food SET item = ?, price = ?, quantity = ? WHERE id = ?";
        PreparedStatement ps = connect().prepareStatement(sql);
        ps.setString(1, item);
        ps.setInt(2, price);
        ps.setInt(3, quantity);
        ps.setInt(4, id);
        ps.executeUpdate();
    }

    public void deleteData(int id) throws SQLException {
        String sql = "DELETE FROM food WHERE id = ?";
        PreparedStatement ps = connect().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

}

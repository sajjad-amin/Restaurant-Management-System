package com.sajjadamin.restaurantmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI {

    JFrame frame, popupFrame;
    ImageIcon icon;
    Container container;
    JTabbedPane tabbedPane;
    JPanel foodPanel, optionsPanel;
    DefaultTableModel billTableModel, foodTableModel, optionTableModel;
    JTable foodTable, billInfoTable, optionTable;
    Database db;


    GUI() throws SQLException {
        initComponents();
        this.db = new Database();
        showDataIntoFoodTable();
        showDataIntoOptionTable();
    }

    private void initComponents() {
        //Icon
        icon = new ImageIcon(getClass().getResource("icon.png"));
        //Main frame
        frame = new JFrame();
        frame.setTitle("Green Canteen");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,840,500);
        frame.setLocationRelativeTo(frame);
        frame.setResizable(false);
        frame.setVisible(true);
        //Container
        container = frame.getContentPane();
        container.setLayout(null);
        //Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10,0,800,450);
        container.add(tabbedPane);
        //main panels
        foodPanel = new JPanel();
        foodPanel.setLayout(null);
        optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        //Tabs
        tabbedPane.addTab(" Order ", foodPanel);
        foodPanelComponents();
        tabbedPane.addTab(" Manage ", optionsPanel);
        optionPanelComponents();
    }

    private void optionPanelComponents() {
        createLabel("Manage Food",new int[]{590,0,100,30},optionsPanel);
        createLabel("Item : ",new int[]{460,50,100,20},optionsPanel);
        createLabel("Price : ",new int[]{460,80,100,20},optionsPanel);
        createLabel("Quantity : ",new int[]{460,110,100,20},optionsPanel);
        //Option text fields
        JTextField optionId = new JTextField();
        JTextField optionItem = new JTextField();
        optionItem.setBounds(560,50,200,20);
        optionsPanel.add(optionItem);
        JTextField optionPrice = new JTextField();
        optionPrice.setBounds(560,80,200,20);
        optionsPanel.add(optionPrice);
        JTextField optionQuantity = new JTextField();
        optionQuantity.setBounds(560,110,200,20);
        optionsPanel.add(optionQuantity);
        //Option butons
        JButton optionAdd = new JButton("Add");
        optionAdd.setBounds(460,160,100,20);
        optionsPanel.add(optionAdd);
        JButton optionUpdate = new JButton("Update");
        optionUpdate.setBounds(570,160,100,20);
        optionsPanel.add(optionUpdate);
        optionsPanel.add(optionAdd);
        JButton optionDelete = new JButton("Delete");
        optionDelete.setBounds(680,160,100,20);
        optionsPanel.add(optionDelete);
        //Handle click event
        optionAdd.addActionListener((ActionEvent ae)->{
            try {
                addFood(optionItem, optionPrice, optionQuantity);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                clearFields(optionId,optionItem,optionPrice,optionQuantity);
            }
        });
        optionUpdate.addActionListener((ActionEvent ae)->{
            try {
                updateFood(optionId.getText(), optionItem.getText(), optionPrice.getText(), optionQuantity.getText());
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                clearFields(optionId,optionItem,optionPrice,optionQuantity);
            }
        });
        optionDelete.addActionListener((ActionEvent ae)->{
            try {
                deleteFood(optionId);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                clearFields(optionId,optionItem,optionPrice,optionQuantity);
            }
        });
        //Option table
        String[] optionTableCol = {"","Item","Price","Quanitiy"};
        optionTableModel = new DefaultTableModel(optionTableCol,0);
        optionTable = new JTable(optionTableModel);
        optionTable.getColumnModel().getColumn(0).setWidth(0);
        optionTable.getColumnModel().getColumn(0).setMinWidth(0);
        optionTable.getColumnModel().getColumn(0).setMaxWidth(0);
        JScrollPane optionTableScroll = new JScrollPane(optionTable);
        optionTableScroll.setBounds(0,0,450,425);
        optionsPanel.add(optionTableScroll);
        optionTableRowClick(optionId, optionItem, optionPrice, optionQuantity);
    }

    private void clearFields(JTextField optionId, JTextField optionItem, JTextField optionPrice, JTextField optionQuantity){
        optionId.setText("");
        optionItem.setText("");
        optionPrice.setText("");
        optionQuantity.setText("");
    }

    private void addFood(JTextField optionItem, JTextField optionPrice, JTextField optionQuantity) throws SQLException{
        if (isInt(optionPrice.getText()) && isInt(optionQuantity.getText())) {
            String item = optionItem.getText();
            int price = Integer.parseInt(optionPrice.getText());
            int quantity = Integer.parseInt(optionQuantity.getText());
            db.addData(item, price, quantity);
            showDataIntoFoodTable();
            showDataIntoOptionTable();
        } else {
            JOptionPane.showMessageDialog(null,"ID, Price and Quantity must be decimal number","Failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFood(String optionId, String optionItem, String optionPrice, String optionQuantity) throws SQLException {
        if (isInt(optionId) && isInt(optionPrice) && isInt(optionQuantity)) {
            int id = Integer.parseInt(optionId);
            String item = optionItem;
            int price = Integer.parseInt(optionPrice);
            int quantity = Integer.parseInt(optionQuantity);
            db.updateData(id, item, price, quantity);
            showDataIntoFoodTable();
            showDataIntoOptionTable();
        } else {
            JOptionPane.showMessageDialog(null,"ID, Price and Quantity must be decimal number","Failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFood(JTextField optionId) throws SQLException {
        if (isInt(optionId.getText())) {
            int id = Integer.parseInt(optionId.getText());
            db.deleteData(id);
            showDataIntoFoodTable();
            showDataIntoOptionTable();
        } else {
            JOptionPane.showMessageDialog(null,"ID, Price and Quantity must be decimal number","Failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void foodPanelComponents() {
        createLabel("Billing Info",new int[]{570,0,800,30},foodPanel);
        //Clear button
        JButton printBtn = new JButton("Print");
        printBtn.setBounds(700,390,80,20);
        foodPanel.add(printBtn);
        //Print button
        JButton removeBtn = new JButton("Clear");
        removeBtn.setBounds(610,390,80,20);
        foodPanel.add(removeBtn);
        //billing info table
        String[] billTableCol = {"", "Item", "Quantity", "Price"};
        billTableModel = new DefaultTableModel(billTableCol,0);
        billInfoTable = new JTable(billTableModel);
        JScrollPane billScroll = new JScrollPane(billInfoTable);
        billInfoTable.getColumnModel().getColumn(0).setWidth(0);
        billInfoTable.getColumnModel().getColumn(0).setMinWidth(0);
        billInfoTable.getColumnModel().getColumn(0).setMaxWidth(0);
        billScroll.setBounds(410,30,370,350);
        foodPanel.add(billScroll);
        removeBtn.addActionListener((ActionEvent ae)->{
            billTableModel.setRowCount(0);
        });
        printBtn.addActionListener((ActionEvent ae) -> {
            // Print invoice
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            Book book = new Book();
            MessageFormat header = new MessageFormat("Green Canteen");
            book.append(billInfoTable.getPrintable(JTable.PrintMode.NORMAL,header,null), printerJob.defaultPage());
            printerJob.setPageable(book);
            try {
                printerJob.print();
                // Update stock
                for (int i = 0; i < billInfoTable.getRowCount() - 1; i++) {
                    String id = billInfoTable.getValueAt(i,0).toString();
                    String item = billInfoTable.getValueAt(i,1).toString();
                    int quantity = Integer.parseInt(billInfoTable.getValueAt(i,2).toString());
                    String price = billInfoTable.getValueAt(i,3).toString();
                    try {
                        DataList dl = db.getData(Integer.parseInt(id));
                        String newQuantity = Integer.toString(dl.getQuantity() - quantity);
                        updateFood(id,item,price,newQuantity);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                try {
                    showDataIntoFoodTable();
                    showDataIntoOptionTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });
        //food table
        String[] foodTableCol = {"", "Item", "Price", "Available"};
        foodTableModel = new DefaultTableModel(foodTableCol,0);
        foodTable = new JTable(foodTableModel);
        foodTable.getColumnModel().getColumn(0).setWidth(0);
        foodTable.getColumnModel().getColumn(0).setMinWidth(0);
        foodTable.getColumnModel().getColumn(0).setMaxWidth(0);
        foodTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                foodTableRowClick();
            }
        });
        JScrollPane foodTableScroll = new JScrollPane(foodTable);
        foodTableScroll.setBounds(0,0,400,425);
        foodPanel.add(foodTableScroll);
    }

    private void foodTableRowClick(){
        int numberOfRow = foodTable.getSelectedRow();
        String id = foodTableModel.getValueAt(numberOfRow,0).toString();
        String item = foodTableModel.getValueAt(numberOfRow, 1).toString();
        String price = foodTableModel.getValueAt(numberOfRow, 2).toString();
        String available = foodTableModel.getValueAt(numberOfRow, 3).toString();
        //Add food
        popupFrame = new JFrame();
        popupFrame.setTitle("Add Item");
        popupFrame.setBounds(0,0,410,210);
        popupFrame.setLocationRelativeTo(popupFrame);
        popupFrame.setResizable(false);
        popupFrame.setLayout(null);
        popupFrame.setVisible(true);
        createLabel("Item : ", new int[]{10,10,100,20},popupFrame);
        createLabel(item, new int[]{110,10,100,20},popupFrame);
        createLabel("Price : ", new int[]{10,40,100,20},popupFrame);
        createLabel(price, new int[]{110,40,100,20},popupFrame);
        createLabel("Quantity : ", new int[]{10,70,100,20},popupFrame);

        String[] hasItem = new String[Integer.parseInt(available)];
        for (int i = 1; i <= Integer.parseInt(available); i++) {
            hasItem[i-1] = Integer.toString(i);
        }
        JComboBox quantity = new JComboBox(hasItem);
        quantity.setBounds(110,70,100,20);
        popupFrame.add(quantity);
        createLabel("Total cost : ", new int[]{10,100,100,20},popupFrame);
        JLabel totalCostAmount = new JLabel(price);
        totalCostAmount.setBounds(110,100,100,20);
        popupFrame.add(totalCostAmount);
        quantity.addActionListener((ActionEvent ae) -> {
            String x = Integer.toString(Integer.parseInt(price) * Integer.parseInt(quantity.getSelectedItem().toString()));
            totalCostAmount.setText(x);
        });
        JButton addItem = new JButton("Add");
        addItem.setBounds(300,130,80,30);
        popupFrame.add(addItem);
        addItem.addActionListener((ActionEvent ae) -> {
            Object[] row = {id, item, quantity.getSelectedItem(), Integer.toString(Integer.parseInt(price) * Integer.parseInt(quantity.getSelectedItem().toString()))};
            int lastRow = billInfoTable.getRowCount();
            if (lastRow != 0) {
                billTableModel.removeRow(lastRow - 1);
            }
            billTableModel.addRow(row);
            int total = 0;
            for (int i = 0; i < billInfoTable.getRowCount(); i++) {
                total += Integer.parseInt(billTableModel.getValueAt(i, 3).toString());
            }
            Object[] totalAmountRow = {"","","Total :",Integer.toString(total)};
            billTableModel.addRow(totalAmountRow);
            popupFrame.dispose();
        });
    }

    public static void createLabel(String title, int[] bounds, Container container){
        JLabel optionTitle = new JLabel(title);
        optionTitle.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
        container.add(optionTitle);
    }

    private void optionTableRowClick(JTextField optionId, JTextField optionItem, JTextField optionPrice, JTextField optionQuantity) {
        optionTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                int rowIndex = optionTable.getSelectedRow();
                optionId.setText(optionTableModel.getValueAt(rowIndex, 0).toString());
                optionItem.setText(optionTableModel.getValueAt(rowIndex, 1).toString());
                optionPrice.setText(optionTableModel.getValueAt(rowIndex, 2).toString());
                optionQuantity.setText(optionTableModel.getValueAt(rowIndex, 3).toString());
            }
        });
    }

    private void showDataIntoFoodTable() throws SQLException {
        foodTableModel.setRowCount(0);
        ArrayList<DataList> list = db.getAllData();
        for (int i = 0; i < list.size(); i++) {
            foodTableModel.addRow(new Object[]{
                    list.get(i).getId(),
                    list.get(i).getItem(),
                    list.get(i).getPrice(),
                    list.get(i).getQuantity(),
            });
        }
    }

    private void showDataIntoOptionTable() throws SQLException {
        optionTableModel.setRowCount(0);
        ArrayList<DataList> list = db.getAllData();
        for (int i = 0; i < list.size(); i++) {
            optionTableModel.addRow(new Object[]{
                    list.get(i).getId(),
                    list.get(i).getItem(),
                    list.get(i).getPrice(),
                    list.get(i).getQuantity(),
            });
        }
    }

    private boolean isInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}

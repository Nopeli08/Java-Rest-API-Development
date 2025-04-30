/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javarestapi.latihan6;

/**
 *
 * @author ITBSS
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookForm extends JFrame {
    
    //LOAD FROM API
    private void loadDataFromAPI() {
    try {
        URL url = new URL ( " http://localhost:4567/api/books");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod ("GET");
        
        BufferedReader in = new BufferedReader (new InputStreamReader(conn.getInputStream()));
        String json = in.lines(). collect (Collectors.joining ());
        in.close () ;
        
        List <Book> books = new Gson (). fromJson (json, new TypeToken <List<Book>> () {}. getType ());
        
        //kosongkan tabel
        tableModel.setRowCount(0);
        
        //Tambahkan Data Baru
        for (Book book : books){
          Object[] row = {book.getId(), book.getTitle(), book.getAuthor ()};
    tableModel.addRow(row);
    } 
    } catch (Exception e){
    JOptionPane.showMessageDialog (this, "Gagal mengambil data : \n" + e.getMessage ());
     }
    }
    

    private JTable table;
    private DefaultTableModel tableModel;
    private final String [] columnNames = { "ID", "Title", "Author"};
    
    public BookForm() {
        setTitle("Book Manager GUI");
        setSize(600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel (columnNames, 0);
        table = new JTable (tableModel);

        JScrollPane scrollPane = new JScrollPane (table);
       add (scrollPane, BorderLayout. CENTER);

        JButton refreshButton = new JButton ("Refresh");
        refreshButton.addActionListener (e -> loadDataFromAPI ());

        JPanel bottomPanel = new JPanel ();
        bottomPanel.add(refreshButton);
        add (bottomPanel, BorderLayout.SOUTH);

        loadDataFromAPI() ;
    }
        
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                BookForm gui;
                try {
                    gui = new BookForm();
                    gui.setVisible(true);
                } catch (Exception e) {
                    Logger.getLogger(BookForm.class.getName()).log(Level.SEVERE, null, e);
                }
            });
    } 
}

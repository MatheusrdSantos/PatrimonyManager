/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public interface CRUD {
    
    static boolean create(String query){
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:assets/database.db")){
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    static ArrayList<ArrayList<String>> get(String query){
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:assets/database.db")){
            PreparedStatement stmt = connection.prepareStatement(query);
            
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData metadata = resultSet.getMetaData();
            int columns_count = metadata.getColumnCount();
            while (resultSet.next()) {
                ArrayList<String> line = new ArrayList<String>();
                for(int i =0; i<columns_count; i++){
                    line.add(resultSet.getString(i+1));
                }
                arr.add(line);
            }
            
            return arr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
            return arr;
        }
    }
    
    static boolean update(String query){
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:assets/database.db")){
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}

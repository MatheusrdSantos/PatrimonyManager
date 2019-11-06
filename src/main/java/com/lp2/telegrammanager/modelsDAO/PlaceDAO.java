/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.modelsDAO;
import com.lp2.telegrammanager.models.Place;
import com.lp2.telegrammanager.interfaces.CRUD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public class PlaceDAO implements CRUD{
    private static String table = "places";
    
    public static boolean save(Place place){
        String query = "INSERT INTO "+ table + "(name, description) values ('"+place.name+"', '"+ place.description+"')";
        return CRUD.create(query);
    }
    
    public static ArrayList<Place> getAll(){
        ArrayList<ArrayList<String>> result = CRUD.get("Select * from "+ table);
        ArrayList<Place> places = new ArrayList<Place>();
        for(ArrayList<String> line: result){
            Place p = new Place(Integer.parseInt(line.get(0)), line.get(1),line.get(2));
            places.add(p);
            
        }
        return places;
    }

    public static Place getById(int id){
        String query = "Select * from "+table+" where id = "+id;
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Place> places = new ArrayList<Place>();
        for(ArrayList<String> line: result){
            Place p = new Place(Integer.parseInt(line.get(0)), line.get(1),line.get(2));
            places.add(p);
        }
        
        try{
            return places.get(0);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public static String getField(int fieldIndex){
        String query = "PRAGMA table_info("+ table +")";
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        String field = "";
        int count = 0;
        
        for(ArrayList<String> line: result){
            if(count == fieldIndex+1){
                field = line.get(1);                
            }
            count++;
        }
        
        return field;
    }
}

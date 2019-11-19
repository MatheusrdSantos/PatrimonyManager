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
    
    /**
     * Método responsável pela inserção de um local no banco
     * @param Place category
     * @return boolean - Resultado da operação (true em caso de sucesso e false em caso e erro)
     */
    public static boolean save(Place place){
        String query = "INSERT INTO "+ table + "(name, description) values ('"+place.name+"', '"+ place.description+"')";
        return CRUD.create(query);
    }
       
    /**
     * Método responsável pela recuperação de todos os locais do banco
     * @param 
     * @return ArrayList<Place> - Locais existentes no banco de dados
     */
    public static ArrayList<Place> getAll(){
        ArrayList<ArrayList<String>> result = CRUD.get("Select * from "+ table);
        ArrayList<Place> places = new ArrayList<Place>();
        for(ArrayList<String> line: result){
            Place p = new Place(Integer.parseInt(line.get(0)), line.get(1),line.get(2));
            places.add(p);
            
        }
        return places;
    }
    
    /**
     * Método responsável pela recuperação de um local com base em seu id
     * @param int id
     * @return Place - Local que possui o id informado
     */
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
    
    /**
     * Método responsável pela recuperação do nome da coluna em um index específicado da tabela no banco de dados
     * @param int fieldIndex
     * @return String - Nome da coluna referente ao index
     */
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

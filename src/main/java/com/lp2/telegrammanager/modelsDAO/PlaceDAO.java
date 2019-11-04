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
/**
 *
 * @author mathe
 */
public class PlaceDAO implements CRUD{
    private static String table = "places";
    
    public static boolean save(Place place){
        String query = "INSERT INTO "+ table + "(name, description) values ('"+place.name+"', '"+ place.description+"')";
        CRUD.create(query);
        return true;
    }
}

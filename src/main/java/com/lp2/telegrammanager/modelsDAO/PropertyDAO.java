/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.modelsDAO;

import com.lp2.telegrammanager.interfaces.CRUD;
import com.lp2.telegrammanager.models.Place;
import com.lp2.telegrammanager.models.Property;
import com.lp2.telegrammanager.models.Category;
import java.util.ArrayList;
/**
 *
 * @author mathe
 */
public class PropertyDAO implements CRUD{
    private static String table = "properties";
    
    /**
     * Método responsável pela inserção de um bem no banco
     * @param Property property
     * @return boolean - Resultado da operação (true em caso de sucesso e false em caso e erro)
     */
    public static boolean save(Property property){
        String query = "INSERT INTO "+ table + "(code, name, description, place_id, category_id) VALUES ('"+
                property.code+"', '"+
                property.name+"', '"+
                property.description+"', "+
                property.place.id+", "+
                property.category.id+")";
        
        return CRUD.create(query);
    }
    
    /**
     * Método responsável pela recuperação de todos os bens do banco
     * @param 
     * @return ArrayList<Property> - Bens existentes no banco de dados
     */
    public static ArrayList<Property> getAll(){
        ArrayList<ArrayList<String>> result = CRUD.get("Select * from "+ table);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = (Place) PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = (Category) CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1),line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        return properties;
    }
    
    /**
     * Método responsável pela recuperação de um bem com base em seu id
     * @param int id
     * @return Property - Bem que possui o id informado
     */
    public static Property getById(int id){
        String query = "Select * from "+table+" where id = "+id;
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1), line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        try{
            return properties.get(0);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    /**
     * Método responsável pela recuperação de um bem com base em seu código
     * @param String code
     * @return Property - Bem que possui o código informado
     */
    public static Property getByCode(String code){
        String query = "Select * from "+table+" where code = '"+code+"'";
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1), line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        try{
            return properties.get(0);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    /**
     * Método responsável pela recuperação de um bem com base em seu nome
     * @param String name
     * @return Property - Bem que possui o nome informado
     */
    public static ArrayList<Property> getByName(String name){
        String query = "Select * from "+table+" where name like '%"+name+"%'";
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1), line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        
        return properties;
    }
    
    /**
     * Método responsável pela recuperação de um bem com base em sua descrição
     * @param String description
     * @return Property - Bem que possui a descrição informada
     */
    public static ArrayList<Property> getByDescription(String description){
        String query = "Select * from "+table+" where description like '%"+description+"%'";
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1), line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        
        return properties;
    }
    
    /**
     * Método responsável pela recuperação dos bens agrupados por local e por categoria
     * @param 
     * @return ArrayList<Property> - Bens agrupados por local e categoria
     */
    public static ArrayList<Property> getAllGrouped(){
        ArrayList<ArrayList<String>> result = CRUD.get("select * from "+ table +" order by place_id, category_id, name asc");
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = (Place) PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = (Category) CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1),line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        return properties;
    }
    
    /**
     * Método responsável pela recuperação de bens com base em seu place_id
     * @param int place_id
     * @return ArrayList<Property> - Bens que possuem o place_id informado
     */
    public static ArrayList<Property> getByPlace(int place_id){
        ArrayList<ArrayList<String>> result = CRUD.get("select * from "+ table +" where place_id = "+ place_id);
        ArrayList<Property> properties = new ArrayList<Property>();
        for(ArrayList<String> line: result){
            Place place = (Place) PlaceDAO.getById(Integer.parseInt(line.get(4)));
            Category category = (Category) CategoryDAO.getById(Integer.parseInt(line.get(5)));
            Property p = new Property(Integer.parseInt(line.get(0)), line.get(1),line.get(2), line.get(3), place, category);
            properties.add(p);
        }
        return properties;
    }
    
    /**
     * Método responsável pela atualização de um bem no banco de dados
     * @param Property property
     * @return boolean - Resultado da operação (true em caso de sucesso e false em caso e erro)
     */
    public static boolean update(Property property){
        String query = "UPDATE "+table+" SET code = '"+
            property.code+"', "+
            "name = '"+
            property.name+"', "+
            "description = '"+
            property.description+"', "+
            "place_id = "+
            property.place.id+", "+
            "category_id = "+
            property.category.id+
            " WHERE id = "+property.id;
        return CRUD.update(query);
    }
    
}

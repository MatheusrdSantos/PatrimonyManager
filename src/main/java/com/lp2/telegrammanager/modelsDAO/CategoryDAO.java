/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.modelsDAO;

import com.lp2.telegrammanager.interfaces.CRUD;
import com.lp2.telegrammanager.models.Category;
import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public class CategoryDAO implements CRUD{
    private static String table = "categories";
    
    /**
     * Método responsável pela inserção de uma categoria no banco
     * @param Category category
     * @return boolean - Resultado da operação (true em caso de sucesso e false em caso e erro)
     */
    public static boolean save(Category category){
        String query = "INSERT INTO "+ table + "(name, description) values ('"+category.name+"', '"+ category.description+"')";
        return CRUD.create(query);
    }
    
    /**
     * Método responsável pela recuperação de todas as categorias do banco
     * @param 
     * @return ArrayList<Category> - Categorias existentes no banco de dados
     */
    public static ArrayList<Category> getAll(){
        ArrayList<ArrayList<String>> result = CRUD.get("Select * from "+ table);
        
        ArrayList<Category> categories = new ArrayList<Category>();
        for(ArrayList<String> line: result){
            Category c = new Category(Integer.parseInt(line.get(0)), line.get(1),line.get(2));
            categories.add(c);
            
        }
        return categories;
    }
    
    
    /**
     * Método responsável pela recuperação de uma categoria com base em seu id
     * @param int id
     * @return Category - Categorias que possui o id informado
     */
    public static Category getById(int id){
        String query = "Select * from "+table+" where id = "+id;
        ArrayList<ArrayList<String>> result = CRUD.get(query);
        ArrayList<Category> categories = new ArrayList<Category>();
        for(ArrayList<String> line: result){
            Category c = new Category(Integer.parseInt(line.get(0)), line.get(1),line.get(2));
            categories.add(c);
        }
        
        try{
            return categories.get(0);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
}

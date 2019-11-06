/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.models;

/**
 *
 * @author mathe
 */
public class Category {
    public int id;
    public String name;
    public String description;
    
    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public Category(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return 
                "========== CATEGORIA ==========\n"+
                "ID: "+ id +"\n"+
                "NOME: "+ name +"\n"+
                "DESCRIÇÃO: "+ description +"\n";
    }
}

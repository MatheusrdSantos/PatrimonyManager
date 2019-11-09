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
public class Property extends Printable{
    public int id;
    public String code;
    public String name;
    public String description;
    public Place place;
    public Category category;
    
    public Property(String code, String name, String description, Place place, Category category) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.place = place;
        this.category = category;
    }

    public Property(int id, String code, String name, String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Property(int id, String code, String name, String description, Place place, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.place = place;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return print();
    }    

    @Override
    public String printAttributes() {
        return 
                "========== BEM ==========\n"+
                "ID: "+ id +"\n"+
                "CÓD: "+ code +"\n"+
                "NOME: "+ name +"\n"+
                "DESCRIÇÃO: "+ description +"\n";
    }

    @Override
    public String printRelationships() {
        return "LOCAL: " + place.name +"\n"+
                "CATEGORIA: "+ category.name +"\n";
    }
    
}

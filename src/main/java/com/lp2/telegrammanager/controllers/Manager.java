/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.telegrammanager.controllers;

import java.util.List;
import java.util.ArrayList;
import com.pengrad.telegrambot.TelegramBot;
//import com.pengrad.telegrambot.TelegramBotAdapter;
import java.util.Scanner;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.GetChat;

import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.request.GetWebhookInfo;
import com.pengrad.telegrambot.response.SendResponse;
import com.pengrad.telegrambot.UpdatesListener;
import io.github.cdimascio.dotenv.Dotenv;
import com.lp2.telegrammanager.modelsDAO.PlaceDAO;
import com.lp2.telegrammanager.modelsDAO.CategoryDAO;
import com.lp2.telegrammanager.modelsDAO.PropertyDAO;
import com.lp2.telegrammanager.models.Place;
import com.lp2.telegrammanager.models.Category;
import com.lp2.telegrammanager.models.Property;
import java.util.HashMap;
import java.util.Map;
import com.lp2.telegrammanager.exceptions.SyntaxException;
import com.lp2.telegrammanager.exceptions.InvalidDataException;

/**
 *
 * @author Davis Victor
 */
public class Manager {
    private static Manager instance;
    private final TelegramBot bot;
    private final Dotenv dotenv;
    private Map<Long, String> chats;
    
    /**
     * 
     * Construtor privado da classe Manager
    */
    
    private Manager(){
        this.dotenv = Dotenv.load();
        this.bot = new TelegramBot(dotenv.get("BOT_TOKEN"));
        this.chats = new HashMap<Long, String>();
    }
    
    /**
     * Método estático responsável pelo retorno da estância única da classe Manager
     * @return
     */
    public static synchronized Manager getInstance(){
        if(instance == null)
            instance = new Manager();
        
        return instance;
    }
    
    public void printMenu(){
        System.out.println("Menu :)");
    }
    
    
    /**
     * Método responsável pelo recebimento de novos comandos no bot
     */
    public void run(){
        this.bot.setUpdatesListener(updates -> {
            updates.forEach(update ->{
                try{
                    this.processCommand(update);
                }catch(SyntaxException e){
                    long chatId = update.message().chat().id();
                    bot.execute(new SendMessage(chatId, e.getMessage()));
                }catch(InvalidDataException e){
                    long chatId = update.message().chat().id();
                    bot.execute(new SendMessage(chatId, e.getMessage()));
                }catch(NumberFormatException e){
                    long chatId = update.message().chat().id();
                    bot.execute(new SendMessage(chatId, "Formato inválido para o id!"));
                }
                
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
    
    
    /**
     * Método responsável pela eliminação de caracteres vazios " " do início de uma String
     * @param String text
     * @return String - texto sem os espaços vazios iniciais
     */
    private String removeInitialSpaces(String text){
        while(text.startsWith(" ")){
            text = text.substring(1);
        }
        return text;
    }
    
    /**
     * Método responsável pelo processamento dos comandos recebidos pelo bot através do chat
     * @param Update update
     * @return
     */
    private void processCommand(Update update) throws SyntaxException, InvalidDataException{
        String command = update.message().text();
        long chatId = update.message().chat().id();
        
        if(command.equals("/newplace")){
           String response = "Insira as informações do local no seguinte formato: \n";
           
           String format = "nome: nome do local\n"+
                   "descrição: descrição do local\n";
           
           bot.execute(new SendMessage(chatId, response));
           bot.execute(new SendMessage(chatId, format));
           this.chats.put(chatId, command);
           return;
        }else if(command.equals("/newcategory")){
           String response = "Insira as informações no seguinte formato: \n";
                   
           String format = "nome: nome da categoria\n"+
                   "descrição: descrição da categoria\n";
           bot.execute(new SendMessage(chatId, response));
           bot.execute(new SendMessage(chatId, format));
           this.chats.put(chatId, command);
           return;
        }else if(command.equals("/newproperty")){
           String response = "Insira as informações no seguinte formato: \n";
                  
           String format = "cod: codigo do bem\n"+
                   "nome: nome do bem\n"+
                   "descrição: descrição do bem\n"+
                   "local: id do local\n"+
                   "categoria: id da categoria\n"; 
           bot.execute(new SendMessage(chatId, response));
           bot.execute(new SendMessage(chatId, format));
           this.chats.put(chatId, command);
           return;
        }else if(command.equals("/listplaces")){
            String response = "";
            ArrayList<Place> places = PlaceDAO.getAll();
            for(Place p: places){
                response = response.concat(p.toString()).concat("\n");
            }
            if(places.isEmpty()){
                response = "Não existem locais cadastrados!";
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }else if(command.equals("/listproperties")){
            String response = "";
            ArrayList<Property> properties = PropertyDAO.getAll();
            for(Property p: properties){
                response = response.concat(p.toString()).concat("\n");
            }
            if(properties.isEmpty()){
                response = "Não existem bens cadastrados!";
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }else if(command.equals("/listcategories")){
            String response = "";
            ArrayList<Category> categories = CategoryDAO.getAll();
            for(Category c: categories){
                response = response.concat(c.toString()).concat("\n");
            }
            if(categories.isEmpty()){
                response = "Não existem categorias cadastradas!";
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }else if(command.equals("/findpropbycode")){
            String response = "Insira o código do bem: \n";
            bot.execute(new SendMessage(chatId, response));
            this.chats.put(chatId, command);
            return;
        }else if(command.equals("/findpropbyname")){
            String response = "Insira o nome do bem: \n";
            bot.execute(new SendMessage(chatId, response));
            this.chats.put(chatId, command);
            return;
        }else if(command.equals("/findpropbydesc")){
            String response = "Insira a descrição do bem: \n";
            bot.execute(new SendMessage(chatId, response));
            this.chats.put(chatId, command);
            return;
        }else if(command.equals("/findpropbyplace")){
            String response = "Insira o id do local: \n";
            bot.execute(new SendMessage(chatId, response));
            this.chats.put(chatId, command);
            return;
        }else if(command.equals("/moveproperty")){
            String response = "Insira as informações no seguinte formato: \n"+
                              "código do bem -> id do novo local\n";
            bot.execute(new SendMessage(chatId, response));
            this.chats.put(chatId, command);
            return;
        }else if(command.equals("/report")){
            String response = "";
            String place_aux = "";
            String category_aux = "";
            String property_aux = "";
            ArrayList<Property> properties = PropertyDAO.getAllGrouped();
            for(Property property: properties){
                //response = response.concat(property.getCode() + " | " + property.getName() + " | " + property.getDescription() + " | " + property.getCategory().getName() + " | " + property.getPlace().getName()).concat("\n");
                
                if(!place_aux.equals(property.getPlace().getName())){
                    response = response.concat("> " + property.getPlace().getName()).concat("\n");
                    category_aux = "";
                }
                if(!category_aux.equals(property.getCategory().getName())){
                    response = response.concat("----> " + property.getCategory().getName()).concat("\n");
                    property_aux = "";
                }
                if(!property_aux.equals(property.getName())){
                    response = response.concat("--------> " + property.getName()).concat("\n");
                }
                
                place_aux = property.getPlace().getName();
                category_aux = property.getCategory().getName();
                property_aux = property.getName();
            }
            if(properties.size() == 0){
                response = "Não existem bens cadastrados!";
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }
        
        String existingCommand = this.chats.get(chatId);
        if(existingCommand != null ){
            if(existingCommand.equals("/newplace")){
                /*String response = "Insira as informações do local no seguinte formato: \n"+
                    "name: nome do local\n"+
                    "descrição: descrição do local\n";*/
                 
                String lines[] = command.split("\n");
                if(lines.length!=2){
                    throw new SyntaxException("O comando não contém duas linhas");
                }
                
                int paramIndex = lines[0].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na primeira linha");
                }
                
                String name = lines[0].substring(paramIndex+1, lines[0].length());
                name = this.removeInitialSpaces(name);
                
                paramIndex = lines[1].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na segunda linha");
                }
                
                String description = lines[1].substring(paramIndex+1, lines[1].length());
                description = this.removeInitialSpaces(description);
                
                Place place = new Place(name, description);
                
                PlaceDAO.save(place);
                
                bot.execute(new SendMessage(chatId, "Local cadastrado com sucesso!"));
                this.chats.put(chatId, command);
                return;
            }else if(existingCommand.equals("/newcategory")){
                String lines[] = command.split("\n");
                if(lines.length!=2){
                    throw new SyntaxException("O comando não contém duas linhas");
                }
                
                int paramIndex = lines[0].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na primeira linha");
                }
                
                String name = lines[0].substring(paramIndex+1, lines[0].length());
                name = this.removeInitialSpaces(name);
                
                paramIndex = lines[1].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na segunda linha");
                }
                
                String description = lines[1].substring(paramIndex+1, lines[1].length());
                description = this.removeInitialSpaces(description);
                
                Category category = new Category(name, description);
                
                CategoryDAO.save(category);
                
                bot.execute(new SendMessage(chatId, "Categoria cadastrada com sucesso!"));
                this.chats.put(chatId, command);
                return;
            }else if(existingCommand.equals("/newproperty")){
                String lines[] = command.split("\n");
                if(lines.length!=5){
                    throw new SyntaxException("O comando não contém cinco linhas"); 
                }
                
                int paramIndex = lines[0].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na primeira linha");
                }
                
                String code = lines[0].substring(paramIndex+1, lines[0].length());
                code = this.removeInitialSpaces(code);
                
                paramIndex = lines[1].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na segunda linha");
                }
                
                String name = lines[1].substring(paramIndex+1, lines[1].length());
                
                name = this.removeInitialSpaces(name);
                
                paramIndex = lines[2].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na terceira linha");
                }
                
                String description = lines[2].substring(paramIndex+1, lines[2].length());
                description = this.removeInitialSpaces(description);
                
                paramIndex = lines[3].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na quarta linha");
                }
                
                String place_id = lines[3].substring(paramIndex+2, lines[3].length());
                place_id = this.removeInitialSpaces(place_id);
                
                Place place = PlaceDAO.getById(Integer.parseInt(place_id));
                if(place == null){
                    throw new InvalidDataException("Não existe local com este id: "+ place_id);
                }
                
                paramIndex = lines[4].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na quinta linha");
                }
                
                String category_id = lines[4].substring(paramIndex+2, lines[4].length());
                category_id = this.removeInitialSpaces(category_id);
                
                Category category = CategoryDAO.getById(Integer.parseInt(category_id));
                
                if(category == null){
                    throw new InvalidDataException("Não existe categoria com este id: "+ category_id);
                }
                
                Property property = new Property(code, name, description, place, category);
                
                PropertyDAO.save(property);
                
                bot.execute(new SendMessage(chatId, "Bem cadastrado com sucesso!"));
                this.chats.put(chatId, command);
                return;
            }else if(existingCommand.equals("/findpropbycode")){
                String property_code = command;
                Property property = PropertyDAO.getByCode(property_code);
                
                if(property == null){
                    throw new InvalidDataException("Não extiste bem com o código: "+ property_code);
                }else{
                    bot.execute(new SendMessage(chatId, property.toString()));
                    this.chats.put(chatId, command);
                }
                
                return;
            }else if(existingCommand.equals("/findpropbyname")){
                String property_name = command;
                ArrayList<Property> properties = PropertyDAO.getByName(property_name);
                
                if(properties.isEmpty()){
                    this.chats.put(chatId, command);                    
                    throw new InvalidDataException("Não extiste bem com o nome: "+ property_name);
                }else{
                    String response = "";                    
                    for (Property property : properties) {
                        response = response.concat(property.toString()).concat("\n");
                    }
                    
                    bot.execute(new SendMessage(chatId, response));
                    this.chats.put(chatId, command);
                }
                
                return;
            }else if(existingCommand.equals("/findpropbydesc")){
                String property_name = command;
                ArrayList<Property> properties = PropertyDAO.getByDescription(property_name);
                
                if(properties.isEmpty()){
                    this.chats.put(chatId, command);
                    throw new InvalidDataException("Não extiste bem com a descrição: "+ property_name);
                }else{
                    String response = "";                    
                    for (Property property : properties) {
                        response = response.concat(property.toString()).concat("\n");
                    }
                    
                    bot.execute(new SendMessage(chatId, response));
                    this.chats.put(chatId, command);
                }
                
                return;
            }else if(existingCommand.equals("/findpropbyplace")){
                int place_id = Integer.parseInt(command);
                Place place = PlaceDAO.getById(place_id);
                if(place == null){
                    this.chats.put(chatId, command);
                    throw new InvalidDataException("Não existe local com este id: "+ place_id);
                }else{
                    ArrayList<Property> properties = PropertyDAO.getByPlace(place_id);

                    if(properties.isEmpty()){
                        this.chats.put(chatId, command);
                        throw new InvalidDataException("Não extiste bens no local: "+ place.getName());
                    }else{
                        String response = "";                    
                        for (Property property : properties) {
                            response = response.concat(property.toString()).concat("\n");
                        }

                        bot.execute(new SendMessage(chatId, response));
                        this.chats.put(chatId, command);
                    }
                }
                
                return;
            }else if(existingCommand.equals("/moveproperty")){
                String lines[] = command.split("\n");
                if(lines.length > 1){
                    throw new SyntaxException("O comando possui mais de uma linha"); 
                }

                int paramIndex = lines[0].indexOf("->");
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na primeira linha");
                }

                String property_code = lines[0].substring(0, paramIndex);
                int place_id = Integer.parseInt(lines[0].substring(paramIndex+2, lines[0].length()));
                
                Property property = PropertyDAO.getByCode(property_code);
                if(property == null){
                    this.chats.put(chatId, command);
                    throw new InvalidDataException("Não existe bem com este código: "+ property_code);
                }else{
                    Place place = PlaceDAO.getById(place_id);
                    if(place == null){
                        this.chats.put(chatId, command);
                        throw new InvalidDataException("Não existe local com este id: "+ place_id);
                    }else{
                        property.setPlace(place);
                        if(PropertyDAO.update(property)){
                            String response = "Bem '"+ property.getName() +"' movido para local '"+ place.getName() +"'";
                            bot.execute(new SendMessage(chatId, response));
                            this.chats.put(chatId, command);
                        }else{
                            this.chats.put(chatId, command);
                            throw new InvalidDataException("Não foi possível mover o bem: "+ property.getName());
                        }
                    }
                }                
                return;
            }else{
                String response = "Desculpe, não entendi sua mensagem!";
                bot.execute(new SendMessage(chatId, response));
                return;
            }
        }else{
            String response = "Desculpe, não entendi sua mensagem!";
            bot.execute(new SendMessage(chatId, response));
            return;
        }
    } 
   
}

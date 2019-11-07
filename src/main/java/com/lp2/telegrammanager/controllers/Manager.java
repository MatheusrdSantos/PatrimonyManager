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

/**
 *
 * @author Davis Victor
 */
public class Manager {
    private static Manager instance;
    private final TelegramBot bot;
    private final Dotenv dotenv;
    private Map<Long, String> chats; 
    
    private Manager(){
        this.dotenv = Dotenv.load();
        this.bot = new TelegramBot(dotenv.get("BOT_TOKEN"));
        this.chats = new HashMap<Long, String>();
    }
    
    // Create your bot passing the token received from @BotFather
        /*
        List<Long> ids = new ArrayList<Long>();
        ids.add((long)928146843);*/
        //arnaldo
        //ids.add((long)732622998);
        //davis
        //ids.add((long)858916406);
        //858916406
        
        // Register for updates
        /**/
        // Send messages
        /*Scanner input = new Scanner(System.in).useDelimiter("\n");
        while(true){
            System.out.print("Enter text: ");
            String myString = input.next();
            for (long id: ids){
                bot.execute(new SendMessage(id, myString));
            }
        }*/

    /**
     *
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
    
    public void run(){
        this.bot.setUpdatesListener(updates -> {
            updates.forEach(update ->{
                try{
                    this.processCommand(update);
                }catch(SyntaxException e){
                    long chatId = update.message().chat().id();
                    bot.execute(new SendMessage(chatId, e.getMessage()));
                }
                
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
    
    private void processCommand(Update update) throws SyntaxException{
        String command = update.message().text();
        long chatId = update.message().chat().id();
        
        if(command.equals("/newplace")){
           String response = "Insira as informações do local no seguinte formato: \n"+
                   "name: nome do local\n"+
                   "descrição: descrição do local\n";
           bot.execute(new SendMessage(chatId, response));
           this.chats.put(chatId, command);
           return;
        }else if(command.equals("/listplaces")){
            String response = "";
            ArrayList<Place> places = PlaceDAO.getAll();
            for(Place p: places){
                response = response.concat(p.toString()).concat("\n");
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }
        else if(command.equals("/listproperties")){
            String response = "";
            ArrayList<Property> properties = PropertyDAO.getAll();
            for(Property p: properties){
                response = response.concat(p.toString()).concat("\n");
            }
            bot.execute(new SendMessage(chatId, response));
            return;
        }else if(command.equals("/listcategories")){
            String response = "";
            ArrayList<Category> categories = CategoryDAO.getAll();
            for(Category c: categories){
                response = response.concat(c.toString()).concat("\n");
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
                    // throws syntax error 
                }
                
                int paramIndex = lines[0].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na primeira linha");
                }
                
                String name = lines[0].substring(paramIndex+1, lines[0].length());
                
                
                paramIndex = lines[1].indexOf(":");
                
                if(paramIndex == -1){
                    throw new SyntaxException("Erro de sintaxe na segunda linha");
                }
                
                String description = lines[1].substring(paramIndex+1, lines[1].length());
                
                Place place = new Place(name, description);
                
                PlaceDAO.save(place);
                
                bot.execute(new SendMessage(chatId, "Localização cadastrada com sucesso!"));
                this.chats.put(chatId, command);
                return;
            }
        }else{
            String response = "Desculpe, não entendi sua mensagem!";
            bot.execute(new SendMessage(chatId, response));
            return;
        }
    } 
   
}

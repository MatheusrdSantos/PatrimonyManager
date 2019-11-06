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

/**
 *
 * @author Davis Victor
 */
public class Manager {
    private static Manager instance;
    private final TelegramBot bot;
    private final Dotenv dotenv;
    
    private Manager(){
        this.dotenv = Dotenv.load();
        this.bot = new TelegramBot(dotenv.get("BOT_TOKEN"));
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
                this.processCommand(update);
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
    
    private void processCommand(Update update){
        String command = update.message().text();
        long chatId = update.message().chat().id();
        if(command.equals("/newPlace")){
            
        }else if(command.equals("/listPlaces")){
            String response = "";
            ArrayList<Place> places = PlaceDAO.getAll();
            for(Place p: places){
                response.concat(p.toString());
            }
            
            bot.execute(new SendMessage(chatId, response));
        }
        if(update.message().text().equals("/teste")){
            
        }
        System.out.println(chatId);
    } 
   
}

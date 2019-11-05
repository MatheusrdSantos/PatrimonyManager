/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mathe
 */
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Place place = (Place) PlaceDAO.getById(2);
        if(place != null){
            System.out.println("Get: "+place.toString());
        }
        
        Category category = (Category) CategoryDAO.getById(1);
        if(category != null){
            System.out.println("get: "+category.toString());
        }
        
        //Property property = new Property("1234", "Computador da NASA", "Computador descrição", place, category);
        //PropertyDAO.save(property);
        Property property = (Property) PropertyDAO.getById(1);
        property.name = "Teste";
        property.description = "Descrip";
        property.place = place;
        PropertyDAO.update(property);
        
        
        //Dotenv dotenv = Dotenv.load();
        
        // Create your bot passing the token received from @BotFather
        /*TelegramBot bot = new TelegramBot(dotenv.get("BOT_TOKEN"));
        List<Long> ids = new ArrayList<Long>();
        ids.add((long)928146843);*/
        //arnaldo
        //ids.add((long)732622998);
        //davis
        //ids.add((long)858916406);
        //858916406
        
        // Register for updates
        /*bot.setUpdatesListener(updates -> {
            // ... process updates
            // return id of last processed update or confirm them all
            updates.forEach(update ->{
                long chatId = update.message().chat().id();
                SendResponse response = bot.execute(new SendMessage(chatId, "Trabalho de corno!"));
                System.out.println(chatId);
                System.out.println(update.message().text());
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });*/
        // Send messages
        /*Scanner input = new Scanner(System.in).useDelimiter("\n");
        while(true){
            System.out.print("Enter text: ");
            String myString = input.next();
            for (long id: ids){
                bot.execute(new SendMessage(id, myString));
            }
        }*/   
    }
    
}

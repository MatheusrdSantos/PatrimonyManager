/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mathe
 */

import com.lp2.telegrammanager.controllers.Manager;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Place place = (Place) PlaceDAO.getById(2);
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
        PropertyDAO.update(property);*/
        
        Manager.getInstance().run(); //singleton
        
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

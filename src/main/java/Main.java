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
        Manager.getInstance().run(); //singleton  
    }
    
}

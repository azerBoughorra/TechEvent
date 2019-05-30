/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techevent;

import edu.esprit.models.Location;
import edu.esprit.services.implementation.LocationService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ben-o
 */
public class TechEvent  {
    
   
    public static void main(String[] args) {
         try (InputStream input = new FileInputStream("Database.properties")) {

           // Properties prop = new Properties();

            // load a properties file
            //prop.load(input);

            // get the property value and print it out
            /*System.out.println(prop.getProperty("url"));
            System.out.println(prop.getProperty("user"));
            System.out.println(prop.getProperty("password"));*/
            
            LocationService l = new LocationService();
            l.create(new Location(1,"test",36.10,10.10,2087));

        } catch (IOException ex) {
            
        }
        //launch(args);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techevent;

import edu.esprit.models.Entreprise;
import edu.esprit.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ben-o
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Connection connexion;
         connexion=DBConnection.getInstance().getConnection();
        
        
         String req = "select * from entreprise";
         Statement stm = connexion.createStatement();
         ResultSet rst = stm.executeQuery(req);
         Entreprise e = null;
         while (rst.next()) {
         e = new Entreprise(rst.getInt("ENTREPRISE_ID_PK"), 
         rst.getString("ENTREPRISE_NAME"));
            
         }
         System.out.println(e);
        
    }

}

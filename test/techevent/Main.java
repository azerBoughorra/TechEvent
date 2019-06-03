/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techevent;

import edu.esprit.models.Entreprise;
import edu.esprit.models.Report;
import edu.esprit.utils.DBConnection;
import edu.esprit.utils.ServiceManager;
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
        Report r = ServiceManager.getInstance().getReportService().find(1);
        System.out.println(r);
        
    }

}

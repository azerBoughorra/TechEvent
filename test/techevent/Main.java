/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techevent;



import edu.esprit.models.Entreprise;
import edu.esprit.models.Participation;
import edu.esprit.models.RoleUser;
import edu.esprit.models.User;
import edu.esprit.services.implementation.UserService;
import edu.esprit.utils.ServiceManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        System.out.println(ServiceManager.getInstance().getUserService().find(2));        
        RoleUser r = ServiceManager.getInstance().getRoleUserService().find(5);
        ServiceManager.getInstance().getRoleUserService().delete(r);
        System.out.println(r);
    }

}

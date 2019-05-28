/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techevent;

import edu.esprit.models.Location;
import edu.esprit.models.RoleUser;
import edu.esprit.utils.ServiceManager;



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

        Location l=ServiceManager.getInstance().getLocationService().find(1);
        l.setZipCode(666);
        ServiceManager.getInstance().getLocationService().edit(l);
        System.out.println(ServiceManager.getInstance().getLocationService().findAll());
    }

}

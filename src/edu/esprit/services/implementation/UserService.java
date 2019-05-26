/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services.implementation;

import edu.esprit.models.Participation;
import edu.esprit.models.User;
import edu.esprit.services.IUserService;
import edu.esprit.services.ServiceUtils;
import edu.esprit.services.exeptions.ComposedIDExeption;
import edu.esprit.utils.ServiceManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService extends ServiceUtils implements IUserService {

    @Override
    public User find(int id) throws ComposedIDExeption {
        Optional<User> o = findAll().stream()
                .filter(c -> c.getId()== id)
                .findFirst();
        return o.isPresent() ? o.get() : null;

    }

    @Override
    public List<User> findAll() {
        List<User> l = new ArrayList<>();
        try {
            ResultSet rs = executeSelect("select * from user_account where isdeleted=0");
            while (rs.next()) {
                User user = new User(rs.getInt("USER_ID_PK"),
                        rs.getString("USER_EMAIL"), rs.getString("USER_NAME"), rs.getString("USER_LAST_NAME"),
                        rs.getString("USER_LOGIN"),rs.getString("USER_PASSWORD"),
                        new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("USER_BIRTHDATE")),
                        rs.getString("USER_ADRESS"),rs.getString("USER_PHOTO_URL"),
                        ServiceManager.getInstance().getEntrepriseService().find(rs.getInt("USER_ENTREPRISE_ID_FK")),
                        ServiceManager.getInstance().getRoleUserService().find(rs.getInt("USER_ROLE_ID_FK"))
                );
                user.setReports(ServiceManager.getInstance().getReportService().findByUser(rs.getInt("USER_ID_PK")));
                l.add(user);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @Override
    public boolean create(User obj) {
        String sql = "insert into user_account (`USER_ID_PK`,"
                + "`USER_EMAIL,"
                + "`USER_NAME`,"
                + "`USER_LASTNAME`,"
                + "`USER_LOGIN`,"
                + "`USER_PASSWORD`,"
                + "`USER_BIRTHDATE`,"
                + "`USER_ADRESS`,"
                + "`USER_PHOTO_URL`,"
                + "`USER_ENTREPRISE_ID_FK`,"
                + "`USER_ROLE_ID_FK`,"
                + "`ISDELETED`)"
                + "values ("
                + obj.getId()
                + "," + obj.getEmail()
                + ",'" + obj.getName()
                + "','" + obj.getLastName()
                + "'," + obj.getLogin()
                + "," + obj.getPassword()
                + ",'" + obj.getBirthday()
                + ",'" + obj.getAdress()
                + ",'" + obj.getPhotoURL()
                + ",'" + obj.getEntreprise().getId()
                + ",'" + obj.getRole().getId()
                + "',0"
                + ")";

        boolean r = execute(sql);

        for (Participation p : obj.getParticipations()) {
            if (!ServiceManager.getInstance().getParticipationService().create(p)) {
                r = false;
            }
        }
        
        return r;
    }

    @Override
    public boolean edit(User obj) {
        String req = "UPDATE user_account"
                + "SET"
                + "`USER_EMAIL` = '" + obj.getEmail() + ","
                + "`USER_NAME` = '" + obj.getName() + ","
                + "`USER_LASTNAME` = '" + obj.getLastName() + ","
                + "`USER_ADRESS` = '" + obj.getAdress() + ","
                + "`USER_LOGIN` = '" + obj.getLogin() + ","
                + "`USER_PASSWORD` = '" + obj.getPassword()+ ","
                + "' WHERE `USER_ID_PK` ='" + obj.getId() + "'";

        return execute(req);
    }

    @Override
    public boolean delete(User obj) {
        return execute("update user_account set isdeleted='1' where USER_ID_PK=" + obj.getId());
    }

    
}

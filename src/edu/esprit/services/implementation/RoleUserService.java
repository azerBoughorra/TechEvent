/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services.implementation;

import edu.esprit.models.RoleUser;
import edu.esprit.services.IRoleUserService;
import edu.esprit.services.ServiceUtils;
import edu.esprit.services.exeptions.ComposedIDExeption;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azer
 */
public class RoleUserService extends ServiceUtils implements IRoleUserService {

@Override
    public RoleUser find(int id) throws ComposedIDExeption {
        Optional<RoleUser> o = findAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return o.isPresent() ? o.get() : null;
    }

    @Override
    public List<RoleUser> findAll() {
        List<RoleUser> l = new ArrayList<>();
        try {
            ResultSet rs = executeSelect("select * from role_user where isdeleted=0");
            while (rs.next()) {
                RoleUser u = new RoleUser(
                        rs.getInt("ROLE_ID"),
                        rs.getString("ROLE_DESCRIPTION")
                );
                l.add(u);
            }
        } catch (Exception ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @Override
    public boolean create(RoleUser obj) {
        String sql = "insert into role_user ("
                + "`ROLE_DESCRIPTION`"
                + ")"
                + "values ("
                + "'" + obj.getDescription()
                + "',0"
                + "')";
        return execute(sql);
    }

    @Override
    public boolean edit(RoleUser obj) {
        String sql = "update role_user set "
                + "ROLE_DESCRIPTION='" + obj.getDescription()
                + "' where ROLE_ID=" + obj.getId();
        return execute(sql);
    }

    @Override
    public boolean delete(RoleUser obj) {
        String sql = "update role_user set "
                + "isdeleted=1"
                + "where ROLE_ID=" + obj.getId();
        return execute(sql);
    }    


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services.implementation;

import edu.esprit.models.Comment;
import edu.esprit.services.ICommentService;
import edu.esprit.services.ServiceUtils;
import edu.esprit.services.exeptions.ComposedIDExeption;
import edu.esprit.utils.ServiceManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azer
 */
public class CommentService extends ServiceUtils implements ICommentService {

    @Override
    public List<Comment> findByEventId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comment find(int id) throws ComposedIDExeption {
        return findAll().stream().filter(c -> c.getId() == id).findFirst().get();
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> l = new ArrayList<>();
        try {
            ResultSet rs = executeSelect("select * from event_comment where isdelete=0");
            while (rs.next()) {
                Comment c = new Comment(rs.getInt("EVENT_COMMENT_ID_PK"),
                        ServiceManager.getInstance().getEventService().find(rs.getInt("EVENT_COMMENT_EVENT_ID_FK")),
                        ServiceManager.getInstance().getUserService().find(rs.getInt("EVENT_COMMENT_USER_ID_FK")),
                        rs.getString("EVENT_COMMENT_BODY"));
                l.add(c);
            }
        } catch (Exception ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @Override
    public boolean create(Comment obj) {
        String sql = "insert into event_comment (`EVENT_COMMENT_ID_PK`,"
                + "`EVENT_COMMENT_EVENT_ID_FK`,"
                + "`EVENT_COMMENT_USER_ID_FK`,"
                + "`EVENT_COMMENT_BODY`,"
                + "`ISDELETED`)"
                + "values ("
                + obj.getId()
                + "," + obj.getEvent().getId()
                + "," + obj.getUser().getId()
                + ",'" + obj.getBody()
                + "',0"
                + ")";

        return execute(sql);

    }

    @Override
    public boolean edit(Comment obj) {
        String req = "UPDATE event_comment"
                + "SET"
                + "`EVENT_COMMENT_BODY` = '" + obj.getBody()
                + "' WHERE `EVENT_COMMENT_ID_PK` ='" + obj.getId() + "'";

        return execute(req);

    }

    @Override
    public boolean delete(Comment obj) {
        return execute("update event_comment set isdeleted='1' where EVENT_COMMENT_ID_PK=" + obj.getId());
    }

}

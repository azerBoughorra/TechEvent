package edu.esprit.entities;

import edu.esprit.entities.Events;
import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(EventComment.class)
public class EventComment_ { 

    public static volatile SingularAttribute<EventComment, String> eventCommentBody;
    public static volatile SingularAttribute<EventComment, UserAccount> eventCommentUserIdFk;
    public static volatile SingularAttribute<EventComment, Integer> eventCommentIdPk;
    public static volatile SingularAttribute<EventComment, Events> eventCommentEventIdFk;
    public static volatile SingularAttribute<EventComment, Short> isdeleted;

}
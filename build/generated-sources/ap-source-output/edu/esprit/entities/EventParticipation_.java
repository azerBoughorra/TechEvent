package edu.esprit.entities;

import edu.esprit.entities.EventParticipationPK;
import edu.esprit.entities.Events;
import edu.esprit.entities.RoleParticipation;
import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(EventParticipation.class)
public class EventParticipation_ { 

    public static volatile SingularAttribute<EventParticipation, UserAccount> userAccount;
    public static volatile SingularAttribute<EventParticipation, RoleParticipation> roleParticipationFk;
    public static volatile SingularAttribute<EventParticipation, Short> isdeleted;
    public static volatile SingularAttribute<EventParticipation, EventParticipationPK> eventParticipationPK;
    public static volatile SingularAttribute<EventParticipation, Events> events;

}
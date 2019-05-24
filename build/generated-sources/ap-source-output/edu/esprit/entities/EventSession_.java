package edu.esprit.entities;

import edu.esprit.entities.EventSessionPK;
import edu.esprit.entities.Events;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-17T11:20:29")
@StaticMetamodel(EventSession.class)
public class EventSession_ { 

    public static volatile SingularAttribute<EventSession, String> eventSessionDescription;
    public static volatile SingularAttribute<EventSession, EventSessionPK> eventSessionPK;
    public static volatile SingularAttribute<EventSession, String> eventSessionName;
    public static volatile SingularAttribute<EventSession, Short> isdeleted;
    public static volatile SingularAttribute<EventSession, Events> events;

}
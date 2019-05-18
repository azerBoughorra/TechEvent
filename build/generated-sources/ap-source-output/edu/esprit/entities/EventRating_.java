package edu.esprit.entities;

import edu.esprit.entities.EventRatingPK;
import edu.esprit.entities.Events;
import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-17T11:20:29")
@StaticMetamodel(EventRating.class)
public class EventRating_ { 

    public static volatile SingularAttribute<EventRating, Double> eventRate;
    public static volatile SingularAttribute<EventRating, UserAccount> userAccount;
    public static volatile SingularAttribute<EventRating, EventRatingPK> eventRatingPK;
    public static volatile SingularAttribute<EventRating, Short> isdeleted;
    public static volatile SingularAttribute<EventRating, Events> events;

}
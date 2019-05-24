package edu.esprit.entities;

import edu.esprit.entities.Events;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-17T11:20:29")
@StaticMetamodel(EventLocation.class)
public class EventLocation_ { 

    public static volatile SingularAttribute<EventLocation, Double> eventLocationLat;
    public static volatile SingularAttribute<EventLocation, Double> eventLocationLong;
    public static volatile ListAttribute<EventLocation, Events> eventsList;
    public static volatile SingularAttribute<EventLocation, String> eventLocationName;
    public static volatile SingularAttribute<EventLocation, Double> eventLocationZipCode;
    public static volatile SingularAttribute<EventLocation, Short> isdeleted;
    public static volatile SingularAttribute<EventLocation, Integer> eventLocationIdPk;

}
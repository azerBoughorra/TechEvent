package edu.esprit.entities;

import edu.esprit.entities.Events;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-17T11:20:29")
@StaticMetamodel(EventCategory.class)
public class EventCategory_ { 

    public static volatile ListAttribute<EventCategory, Events> eventsList;
    public static volatile SingularAttribute<EventCategory, Integer> eventCategoryIdPk;
    public static volatile SingularAttribute<EventCategory, Short> isdeleted;
    public static volatile SingularAttribute<EventCategory, String> eventCategoryName;

}
package edu.esprit.entities;

import edu.esprit.entities.EventCategory;
import edu.esprit.entities.EventComment;
import edu.esprit.entities.EventLocation;
import edu.esprit.entities.EventParticipation;
import edu.esprit.entities.EventRating;
import edu.esprit.entities.EventSession;
import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-17T11:20:29")
@StaticMetamodel(Events.class)
public class Events_ { 

    public static volatile ListAttribute<Events, EventComment> eventCommentList;
    public static volatile SingularAttribute<Events, EventLocation> eventLocationIdFk;
    public static volatile SingularAttribute<Events, Integer> eventIdPk;
    public static volatile SingularAttribute<Events, Short> isdeleted;
    public static volatile ListAttribute<Events, EventSession> eventSessionList;
    public static volatile SingularAttribute<Events, String> eventTitle;
    public static volatile SingularAttribute<Events, String> eventPhotoUrl;
    public static volatile SingularAttribute<Events, EventCategory> eventCategoryIdFk;
    public static volatile ListAttribute<Events, EventRating> eventRatingList;
    public static volatile SingularAttribute<Events, UserAccount> eventOrganisatorIdFk;
    public static volatile SingularAttribute<Events, String> eventDescription;
    public static volatile SingularAttribute<Events, Double> eventPrice;
    public static volatile ListAttribute<Events, EventParticipation> eventParticipationList;
    public static volatile SingularAttribute<Events, Short> eventFree;

}
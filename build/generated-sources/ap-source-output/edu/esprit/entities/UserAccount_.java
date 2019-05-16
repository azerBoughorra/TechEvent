package edu.esprit.entities;

import edu.esprit.entities.Entreprise;
import edu.esprit.entities.EventComment;
import edu.esprit.entities.EventParticipation;
import edu.esprit.entities.EventRating;
import edu.esprit.entities.Events;
import edu.esprit.entities.Report;
import edu.esprit.entities.RoleUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile ListAttribute<UserAccount, EventComment> eventCommentList;
    public static volatile SingularAttribute<UserAccount, String> userPassword;
    public static volatile ListAttribute<UserAccount, Events> eventsList;
    public static volatile ListAttribute<UserAccount, Report> reportList;
    public static volatile SingularAttribute<UserAccount, String> userName;
    public static volatile SingularAttribute<UserAccount, String> userAdress;
    public static volatile SingularAttribute<UserAccount, Short> isdeleted;
    public static volatile SingularAttribute<UserAccount, String> userPhotoUrl;
    public static volatile SingularAttribute<UserAccount, String> userLogin;
    public static volatile SingularAttribute<UserAccount, RoleUser> userRoleIdFk;
    public static volatile SingularAttribute<UserAccount, Entreprise> userEntrepriseIdFk;
    public static volatile SingularAttribute<UserAccount, String> userLastName;
    public static volatile ListAttribute<UserAccount, EventRating> eventRatingList;
    public static volatile ListAttribute<UserAccount, EventParticipation> eventParticipationList;
    public static volatile SingularAttribute<UserAccount, Integer> userIdPk;
    public static volatile SingularAttribute<UserAccount, Short> userActivated;
    public static volatile SingularAttribute<UserAccount, String> userEmail;
    public static volatile SingularAttribute<UserAccount, Short> userBlocked;
    public static volatile SingularAttribute<UserAccount, String> userBirthdate;

}
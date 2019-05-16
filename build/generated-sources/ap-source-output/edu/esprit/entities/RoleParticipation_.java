package edu.esprit.entities;

import edu.esprit.entities.EventParticipation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(RoleParticipation.class)
public class RoleParticipation_ { 

    public static volatile SingularAttribute<RoleParticipation, String> roleParticipationDescription;
    public static volatile ListAttribute<RoleParticipation, EventParticipation> eventParticipationList;
    public static volatile SingularAttribute<RoleParticipation, Integer> roleParticipationId;
    public static volatile SingularAttribute<RoleParticipation, Short> isdeleted;

}
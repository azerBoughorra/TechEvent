package edu.esprit.entities;

import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(RoleUser.class)
public class RoleUser_ { 

    public static volatile SingularAttribute<RoleUser, Integer> roleIdPk;
    public static volatile ListAttribute<RoleUser, UserAccount> userAccountList;
    public static volatile SingularAttribute<RoleUser, String> roleDescription;
    public static volatile SingularAttribute<RoleUser, Short> isdeleted;

}
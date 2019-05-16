package edu.esprit.entities;

import edu.esprit.entities.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T23:11:13")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, String> reportBody;
    public static volatile SingularAttribute<Report, Integer> reportTargetId;
    public static volatile SingularAttribute<Report, String> reprotTarget;
    public static volatile SingularAttribute<Report, Integer> reportIdPk;
    public static volatile SingularAttribute<Report, Short> isdeleted;
    public static volatile SingularAttribute<Report, UserAccount> reporterIdFk;

}
package golfdetails;

import golfdetails.GolfDevice;
import golfdetails.GolfStats;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-12T21:32:49")
@StaticMetamodel(GolfUser.class)
public class GolfUser_ { 

    public static volatile CollectionAttribute<GolfUser, GolfDevice> golfDeviceCollection;
    public static volatile SingularAttribute<GolfUser, String> userLastName;
    public static volatile SingularAttribute<GolfUser, String> userPwd;
    public static volatile SingularAttribute<GolfUser, String> userEmail;
    public static volatile CollectionAttribute<GolfUser, GolfStats> golfStatsCollection;
    public static volatile SingularAttribute<GolfUser, String> userFirstName;
    public static volatile SingularAttribute<GolfUser, String> userName;
    public static volatile SingularAttribute<GolfUser, String> userRole;
    public static volatile SingularAttribute<GolfUser, Integer> userId;

}
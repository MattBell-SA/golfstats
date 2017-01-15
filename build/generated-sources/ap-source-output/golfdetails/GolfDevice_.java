package golfdetails;

import golfdetails.GolfUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-30T17:16:53")
@StaticMetamodel(GolfDevice.class)
public class GolfDevice_ { 

    public static volatile SingularAttribute<GolfDevice, GolfUser> deviceUserId;
    public static volatile SingularAttribute<GolfDevice, String> deviceIdentification;
    public static volatile SingularAttribute<GolfDevice, Integer> deviceId;
    public static volatile SingularAttribute<GolfDevice, String> deviceName;

}
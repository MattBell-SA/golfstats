package golfdetails;

import golfdetails.GolfUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-12-30T17:16:53")
@StaticMetamodel(GolfStats.class)
public class GolfStats_ { 

    public static volatile SingularAttribute<GolfStats, Date> statsDatetime;
    public static volatile SingularAttribute<GolfStats, Integer> statsScore;
    public static volatile SingularAttribute<GolfStats, Double> statsTemp;
    public static volatile SingularAttribute<GolfStats, String> statsCourseName;
    public static volatile SingularAttribute<GolfStats, GolfUser> statsUserId;
    public static volatile SingularAttribute<GolfStats, String> statsCompType;
    public static volatile SingularAttribute<GolfStats, Double> statsWind;
    public static volatile SingularAttribute<GolfStats, Integer> statsFairways;
    public static volatile SingularAttribute<GolfStats, Integer> statsId;
    public static volatile SingularAttribute<GolfStats, String> statsCondition;
    public static volatile SingularAttribute<GolfStats, Integer> statsGreens;
    public static volatile SingularAttribute<GolfStats, Integer> statsPutts;

}
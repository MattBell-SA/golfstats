package golfdetails;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "golf_stats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GolfStats.findAll", query = "SELECT g FROM GolfStats g"),
    @NamedQuery(name = "GolfStats.findByStatsId", query = "SELECT g FROM GolfStats g WHERE g.statsId = :statsId"),
    @NamedQuery(name = "GolfStats.findTop10ByStatsUserId", query = "SELECT g FROM GolfStats g WHERE g.statsUserId.userId = :statsUserId ORDER BY g.statsScore"),
    @NamedQuery(name = "GolfStats.findByStatsUserId", query = "SELECT g FROM GolfStats g WHERE g.statsUserId.userId = :statsUserId ORDER BY g.statsDatetime"),
    @NamedQuery(name = "GolfStats.findByStatsCourseName", query = "SELECT g FROM GolfStats g WHERE g.statsCourseName = :statsCourseName"),
    @NamedQuery(name = "GolfStats.findByStatsDatetime", query = "SELECT g FROM GolfStats g WHERE g.statsDatetime = :statsDatetime"),
    @NamedQuery(name = "GolfStats.findByStatsScore", query = "SELECT g FROM GolfStats g WHERE g.statsScore = :statsScore"),
    @NamedQuery(name = "GolfStats.findByStatsCompType", query = "SELECT g FROM GolfStats g WHERE g.statsCompType = :statsCompType"),
    @NamedQuery(name = "GolfStats.findByStatsPutts", query = "SELECT g FROM GolfStats g WHERE g.statsPutts = :statsPutts"),
    @NamedQuery(name = "GolfStats.findByStatsGreens", query = "SELECT g FROM GolfStats g WHERE g.statsGreens = :statsGreens"),
    @NamedQuery(name = "GolfStats.findByStatsFairways", query = "SELECT g FROM GolfStats g WHERE g.statsFairways = :statsFairways"),
    @NamedQuery(name = "GolfStats.findByStatsTemp", query = "SELECT g FROM GolfStats g WHERE g.statsTemp = :statsTemp"),
    @NamedQuery(name = "GolfStats.findByStatsWind", query = "SELECT g FROM GolfStats g WHERE g.statsWind = :statsWind")})
public class GolfStats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stats_id")
    private Integer statsId;
    @Basic(optional = false)
    @Column(name = "stats_course_name")
    private String statsCourseName;
    @Basic(optional = false)
    @Column(name = "stats_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statsDatetime;
    @Basic(optional = false)
    @Column(name = "stats_score")
    private int statsScore;
    @Basic(optional = false)
    @Column(name = "stats_comp_type")
    private String statsCompType;
    @Basic(optional = false)
    @Column(name = "stats_putts")
    private int statsPutts;
    @Basic(optional = false)
    @Column(name = "stats_greens")
    private int statsGreens;
    @Basic(optional = false)
    @Column(name = "stats_fairways")
    private int statsFairways;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stats_temp")
    private Double statsTemp;
    @Column(name = "stats_wind")
    private Double statsWind;
    @Column(name = "stats_cond")
    private String statsCondition;
    @JoinColumn(name = "stats_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private GolfUser statsUserId;

    public GolfStats() {
    }

    public GolfStats(Integer statsId) {
        this.statsId = statsId;
    }

    public GolfStats(Integer statsId, String statsCourseName, Date statsDatetime, int statsScore, String statsCompType, int statsPutts, int statsGreens, int statsFairways) {
        this.statsId = statsId;
        this.statsCourseName = statsCourseName;
        this.statsDatetime = statsDatetime;
        this.statsScore = statsScore;
        this.statsCompType = statsCompType;
        this.statsPutts = statsPutts;
        this.statsGreens = statsGreens;
        this.statsFairways = statsFairways;
    }

    public Integer getStatsId() {
        return statsId;
    }

    public void setStatsId(Integer statsId) {
        this.statsId = statsId;
    }

    public String getStatsCourseName() {
        return statsCourseName;
    }

    public void setStatsCourseName(String statsCourseName) {
        this.statsCourseName = statsCourseName;
    }

    public Date getStatsDatetime() {
        return statsDatetime;
    }

    public void setStatsDatetime(Date statsDatetime) {
        this.statsDatetime = statsDatetime;
    }

    public int getStatsScore() {
        return statsScore;
    }

    public void setStatsScore(int statsScore) {
        this.statsScore = statsScore;
    }

    public String getStatsCompType() {
        return statsCompType;
    }

    public void setStatsCompType(String statsCompType) {
        this.statsCompType = statsCompType;
    }

    public int getStatsPutts() {
        return statsPutts;
    }

    public void setStatsPutts(int statsPutts) {
        this.statsPutts = statsPutts;
    }

    public int getStatsGreens() {
        return statsGreens;
    }

    public void setStatsGreens(int statsGreens) {
        this.statsGreens = statsGreens;
    }

    public int getStatsFairways() {
        return statsFairways;
    }

    public void setStatsFairways(int statsFairways) {
        this.statsFairways = statsFairways;
    }

    public Double getStatsTemp() {
        return statsTemp;
    }

    public void setStatsTemp(Double statsTemp) {
        this.statsTemp = statsTemp;
    }

    public Double getStatsWind() {
        return statsWind;
    }

    public void setStatsWind(Double statsWind) {
        this.statsWind = statsWind;
    }
    
    public String getStatsCondition() {
        return statsCondition;
    }

    public void setStatsCondition(String statsCondition) {
        this.statsCondition = statsCondition;
    }    

    public GolfUser getStatsUserId() {
        return statsUserId;
    }

    public void setStatsUserId(GolfUser statsUserId) {
        this.statsUserId = statsUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statsId != null ? statsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GolfStats)) {
            return false;
        }
        GolfStats other = (GolfStats) object;
        if ((this.statsId == null && other.statsId != null) || (this.statsId != null && !this.statsId.equals(other.statsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + statsId;
    }
    
}

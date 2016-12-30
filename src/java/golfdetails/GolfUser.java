package golfdetails;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "golf_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GolfUser.findAll", query = "SELECT g FROM GolfUser g"),
    @NamedQuery(name = "GolfUser.findByUserId", query = "SELECT g FROM GolfUser g WHERE g.userId = :userId"),
    @NamedQuery(name = "GolfUser.findByUserName", query = "SELECT g FROM GolfUser g WHERE g.userName = :userName"),
    @NamedQuery(name = "GolfUser.findByUserPwd", query = "SELECT g FROM GolfUser g WHERE g.userPwd = :userPwd"),
    @NamedQuery(name = "GolfUser.findByUserFirstName", query = "SELECT g FROM GolfUser g WHERE g.userFirstName = :userFirstName"),
    @NamedQuery(name = "GolfUser.findByUserLastName", query = "SELECT g FROM GolfUser g WHERE g.userLastName = :userLastName"),
    @NamedQuery(name = "GolfUser.findByUserEmail", query = "SELECT g FROM GolfUser g WHERE g.userEmail = :userEmail"),
    @NamedQuery(name = "GolfUser.findByUserRole", query = "SELECT g FROM GolfUser g WHERE g.userRole = :userRole")})
public class GolfUser implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceUserId")
    private Collection<GolfDevice> golfDeviceCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "user_pwd")
    private String userPwd;
    @Basic(optional = false)
    @Column(name = "user_first_name")
    private String userFirstName;
    @Basic(optional = false)
    @Column(name = "user_last_name")
    private String userLastName;
    @Basic(optional = false)
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_role")
    private String userRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statsUserId")
    private Collection<GolfStats> golfStatsCollection;

    public GolfUser() {
    }

    public GolfUser(Integer userId) {
        this.userId = userId;
    }

    public GolfUser(Integer userId, String userName, String userPwd, String userFirstName, String userLastName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @XmlTransient
    public Collection<GolfStats> getGolfStatsCollection() {
        return golfStatsCollection;
    }

    public void setGolfStatsCollection(Collection<GolfStats> golfStatsCollection) {
        this.golfStatsCollection = golfStatsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GolfUser)) {
            return false;
        }
        GolfUser other = (GolfUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + userId;
    }

    @XmlTransient
    public Collection<GolfDevice> getGolfDeviceCollection() {
        return golfDeviceCollection;
    }

    public void setGolfDeviceCollection(Collection<GolfDevice> golfDeviceCollection) {
        this.golfDeviceCollection = golfDeviceCollection;
    }
    
}

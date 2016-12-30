package golfdetails;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "golf_device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GolfDevice.findAll", query = "SELECT g FROM GolfDevice g"),
    @NamedQuery(name = "GolfDevice.findByDeviceId", query = "SELECT g FROM GolfDevice g WHERE g.deviceId = :deviceId"),
    @NamedQuery(name = "GolfDevice.findByDeviceName", query = "SELECT g FROM GolfDevice g WHERE g.deviceName = :deviceName"),
    @NamedQuery(name = "GolfDevice.findByDeviceIdentification", query = "SELECT g FROM GolfDevice g WHERE g.deviceIdentification = :deviceIdentification")})
public class GolfDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id")
    private Integer deviceId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "device_name")
    private String deviceName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "device_identification")
    private String deviceIdentification;
    @JoinColumn(name = "device_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private GolfUser deviceUserId;

    public GolfDevice() {
    }

    public GolfDevice(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public GolfDevice(Integer deviceId, String deviceName, String deviceIdentification) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceIdentification = deviceIdentification;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIdentification() {
        return deviceIdentification;
    }

    public void setDeviceIdentification(String deviceIdentification) {
        this.deviceIdentification = deviceIdentification;
    }

    public GolfUser getDeviceUserId() {
        return deviceUserId;
    }

    public void setDeviceUserId(GolfUser deviceUserId) {
        this.deviceUserId = deviceUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GolfDevice)) {
            return false;
        }
        GolfDevice other = (GolfDevice) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "golfdetails.GolfDevice[ deviceId=" + deviceId + " ]";
    }
    
}

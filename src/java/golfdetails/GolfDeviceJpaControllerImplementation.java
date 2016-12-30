package golfdetails;

import java.util.List;
import javax.persistence.EntityManagerFactory;

public class GolfDeviceJpaControllerImplementation extends GolfDeviceJpaController{

    public GolfDeviceJpaControllerImplementation(EntityManagerFactory emf) {
        super(emf);
    }
    
    public GolfDevice findDeviceByUserIDDeviceID(String userName, String deviceName) {
        List<GolfDevice> golfDeviceList = this.findGolfDeviceEntities();
        GolfDevice golfDevice = new GolfDevice();
        
        for (GolfDevice deviceList : golfDeviceList) {
            if (deviceList.getDeviceUserId().getUserName().equals(userName) 
                    && deviceList.getDeviceName().equals(deviceName) ) {
                golfDevice = deviceList;
                break;
            }
        }
        
        return golfDevice;

    }
    
}

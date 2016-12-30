package golfdetails;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateEntityManager {

    private static EntityManagerFactory emf = null;
    
        public static EntityManager getEntityManager() {
        
            if (emf == null) {
                Map addedOrOverridenProperties = new HashMap();
                
                String openShiftDBHost = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
                String openShiftDBPort = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
                String jdbcPassword = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
                String jdbcUser = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
                
                if (openShiftDBHost == null) {
                    openShiftDBHost = "localhost";
                }
                
                if (openShiftDBPort == null) {
                    openShiftDBPort = "3306";
                }
                
                if (jdbcPassword == null) {
                    jdbcPassword = "admin";
                }

                if (jdbcUser == null) {
                    jdbcUser = "root";
                }
                
                String jdbcUrl = "jdbc:mysql://" + openShiftDBHost + ":" + openShiftDBPort + "/golfstats?zeroDateTimeBehavior=convertToNull";

                addedOrOverridenProperties.put("javax.persistence.jdbc.url", jdbcUrl);
                addedOrOverridenProperties.put("javax.persistence.jdbc.password", jdbcPassword);
                addedOrOverridenProperties.put("javax.persistence.jdbc.user", jdbcUser);

                emf = Persistence.createEntityManagerFactory("GolfStatsApplicationJSPPU", addedOrOverridenProperties);
            }

            return emf.createEntityManager();
    }
    
    
}

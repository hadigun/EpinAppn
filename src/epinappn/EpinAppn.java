/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epinappn;

import com.bluechip.datasource.Pool;
import com.bluechip.epin.model.EpinResultSet;
import com.bluechip.epin.model.QueryTaskRqt;
import java.sql.SQLException;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Diamonddemola
 */
public class EpinAppn {

    /**
     * @param args the command line arguments
     */
    private static final Log log = LogFactory.getLog(EpinAppn.class);
    public static final String APP_NAME = "EPINWS";
    private static ArrayList<EpinResultSet> rsErs;
    private static ArrayList<QueryTaskRqt> rsQtr;
    private static ArrayList ersResponse = new ArrayList();
    private static ArrayList qtrResponse = new ArrayList();

    public static void main(String[] args) throws SQLException {
        // TODO code application logic here

        rsErs = Pool.getRecordFromActivationJobsTable();
        ersResponse = Pool.processFromActivationJobsTable(rsErs);
        System.out.println("Response count " + ersResponse.size());
        Pool.updateAllResponse(ersResponse);

        System.out.println("Now Check Query Status....");
        // Check the status of submitted jobs
        rsQtr = Pool.getActivationJobsSubmitted();
        if (rsQtr == null) {
            System.out.println("No response to process at this time");
        } else {
            qtrResponse = Pool.processActivationJobsSubmitted(rsQtr);
            Pool.updateQueryTaskResponse(qtrResponse);
        }

    }
}

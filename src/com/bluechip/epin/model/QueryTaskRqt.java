/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluechip.epin.model;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diamonddemola
 */
public class QueryTaskRqt  implements Serializable, ResultSetConverter<QueryTaskRqt> {
    
   private String  jobId = null;
   private String uvcJobId = null;
   
   public  QueryTaskRqt(){}
   
   public  QueryTaskRqt(String  jobId, String uvcJobId){
   this.jobId = jobId;
   this.uvcJobId = uvcJobId;
   }
   
   @Override
   public ArrayList<QueryTaskRqt> toObject(ResultSet rs) throws SQLException  {
        ArrayList<QueryTaskRqt> qtr = new ArrayList<QueryTaskRqt>();
        if (rs == null) {
            return qtr ;
        }
        
       try {
            while (rs.next()) {
                QueryTaskRqt r = new QueryTaskRqt(rs.getString("jobid"), rs.getString("uvc_job_id"));
                qtr.add(r);
                System.out.println("QueryTaskRqt:   jobid    " + rs.getLong("jobid")+ "    uvc_job_id     " + rs.getString("uvc_job_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EpinResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return qtr ;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the uvcJobId
     */
    public String getUvcJobId() {
        return uvcJobId;
    }

    /**
     * @param uvcJobId the uvcJobId to set
     */
    public void setUvcJobId(String uvcJobId) {
        this.uvcJobId = uvcJobId;
    }
   
   
}



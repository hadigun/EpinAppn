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
public class EpinResultSet  implements Serializable, ResultSetConverter<EpinResultSet> {
    private long id = 0L;
    private String batchNo = null;
    private String startSerial = null;
    private String endSerial = null;
    private String jobStatus = null;
    private  int actionMode = 0;
    private long jobId = 0L; 
    
  public  EpinResultSet (){}  
  public  EpinResultSet (long id, String batchNo,String startSerial,String endSerial,String jobStatus,int actionMode,long jobId){
  this.id = id;
  this.batchNo = batchNo;
  this.startSerial = startSerial;
  this.endSerial =endSerial;
  this.jobStatus = jobStatus;
  this.actionMode = actionMode;
  this.jobId = jobId;
  } 
    
    
   //@Override
   // @Override
   public ArrayList<EpinResultSet> toObject(ResultSet rs) throws SQLException  {
        ArrayList<EpinResultSet> epinRs = new ArrayList<EpinResultSet>();
        if (rs == null) {
            return epinRs ;
        }
        
       try {
            while (rs.next()) {
                EpinResultSet r = new EpinResultSet(rs.getLong("id"), rs.getString("batch_no"), rs.getString("start_serial"), rs.getString("end_serial"), rs.getString("job_status"), rs.getInt("action_mode"), rs.getLong("jobid"));
                epinRs.add(r);
                System.out.println("gggg   " + rs.getLong("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EpinResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return epinRs ;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the startSerial
     */
    public String getStartSerial() {
        return startSerial;
    }

    /**
     * @param startSerial the startSerial to set
     */
    public void setStartSerial(String startSerial) {
        this.startSerial = startSerial;
    }

    /**
     * @return the endSerial
     */
    public String getEndSerial() {
        return endSerial;
    }

    /**
     * @param endSerial the endSerial to set
     */
    public void setEndSerial(String endSerial) {
        this.endSerial = endSerial;
    }

    /**
     * @return the jobStatus
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus the jobStatus to set
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return the actionMode
     */
    public int getActionMode() {
        return actionMode;
    }

    /**
     * @param actionMode the actionMode to set
     */
    public void setActionMode(int actionMode) {
        this.actionMode = actionMode;
    }

    /**
     * @return the jobId
     */
    public long getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

   

   
  
}

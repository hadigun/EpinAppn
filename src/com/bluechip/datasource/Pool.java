/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluechip.datasource;

import com.bluechip.epin.model.EpinResultSet;
import com.bluechip.epin.model.QueryTaskRqt;
import com.bluechip.epin.model.TaskReturnValue;

import static epinappn.EpinAppn.APP_NAME;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.xml.ws.Holder;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import recharge.server.CustomizeTask;
import recharge.server.CustomizeTaskResponse;
import recharge.server.GetTaskID;
import recharge.server.GetTaskIDResponse;
import recharge.server.ResItemgroupItem;
import recharge.server.TaskParametersItem;
import recharge.server.UvcSoapForRecharge;

/**
 *
 * @author Diamonddemola
 */
public class Pool {

    //  private static final Log log = LogFactory.getLog(Pool.class);
// static UvcSoapForRecharge proxy = new UvcSoapForRecharge() {
//
//     @Override
//     public void queryRechargeCard(String sequence, List<String> operatorID, Holder<Integer> retCode, Holder<List<String>> batchNo, Holder<List<String>> sequence0, Holder<List<Integer>> faceValue, Holder<List<Integer>> currency, Holder<List<String>> cardStartDate, Holder<List<String>> cardStopDate, Holder<List<Integer>> hotCardFlag, Holder<List<String>> topDistributor, Holder<List<String>> curDistributor, Holder<List<String>> tradeTime, Holder<List<String>> rechargeNumber, Holder<List<String>> cardCosID, Holder<List<String>> cardCosName, Holder<List<Integer>> resNum, Holder<List<ResItemgroupItem>> resItemgroup) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public void modifyCardAct(String sequenceList, String operationType, List<String> reason, List<String> operatorID, Holder<Integer> retCode, Holder<List<Integer>> total, Holder<List<Integer>> successNum, Holder<List<Integer>> failNum, Holder<List<Integer>> skipNum, Holder<List<String>> sccessFile, Holder<List<String>> failFile, Holder<List<String>> skipFile) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public void modifyCardLock(String sequenceList, String operationType, List<String> reason, List<String> operatorID, Holder<Integer> retCode, Holder<List<Integer>> total, Holder<List<Integer>> successNum, Holder<List<Integer>> failNum, Holder<List<Integer>> skipNum, Holder<List<Integer>> inexistNum, Holder<List<String>> sccessFile, Holder<List<String>> failFile, Holder<List<String>> skipFile) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public void checkPartPIN(String sequence, String cardPartPIN, Holder<Integer> resultCode, Holder<List<String>> accountPinNumber, Holder<List<Integer>> hotCardFlag) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public int customizeTask(String taskID, int taskType, String startUpTime, String tenantID, String operatorID, int notifyFlag, List<TaskParametersItem> taskParameters) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public void queryTask(Holder<String> taskID, String tenantID, String operatorID, Holder<Integer> retCode, Holder<Integer> taskType, Holder<String> createTime, Holder<String> startUpTime, Holder<String> dealTime, Holder<String> dealEndTime, Holder<Integer> taskStatus, Holder<String> batchNo, Holder<String> startSequence, Holder<String> stopSequence, Holder<String> vcid, Holder<String> deactivationReason, Holder<String> randomListFile, Holder<String> lockReason, Holder<String> failFile, Holder<Integer> failNum, Holder<String> skipFile, Holder<Integer> skipNum, Holder<String> successFile, Holder<Integer> successNum, Holder<Integer> total, Holder<String> cardmakingFile, Holder<List<Integer>> faceValue, Holder<String> cardmanufacturer, Holder<String> cardInformationFile) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
//
//     @Override
//     public void getTaskID(int totalNumber, Holder<Integer> retCode, Holder<String> taskID) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
// };
//    
//    static {
//        try {
//            DBConnectionPool.init(APP_NAME);
//
//            PropertyConfig cfg = DBConnectionPool.getConfig();
//        } catch (Exception ex) {
//       //     log.error(ex);
//            System.exit(1);
//        }
//    }
    // JDBC driver name and database URL
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//10.160.172.111:1521/EPINDEV.etisalatng.com";
    //  Database credentials
    private static final String DB_USER = "SIMPLEX";
    private static final String DB_PASSWORD = "simplex";

    private static Connection conn = null;

    static {
        Connection dbConnection = null;
        /* try {
         Class.forName(DB_DRIVER);
         } catch (ClassNotFoundException e) {
         System.out.println(e.getMessage());
         }
         try {
         dbConnection = DriverManager.getConnection(
         DB_CONNECTION, DB_USER, DB_PASSWORD);
         return dbConnection;

         } catch (SQLException e) {
         System.out.println(e.getMessage());

         }

         return dbConnection;*/

        try {
            OracleDataSource ods = new OracleDataSource();
            Properties p = new Properties();
            InputStream is = null;
            try {
                is = new FileInputStream(System.getProperty("user.home") + File.separatorChar + "bluechiptech/etc/bluechiptech.properties");
                p.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            String dbURL = p.getProperty("simplex.db.url");
            System.out.println("DBURL:.... " + dbURL);

//      ods.setURL("jdbc:oracle:thin:epin/epin@10.160.161.26:1524/epin");
            ods.setURL(dbURL);

            conn = ods.getConnection();
            System.out.println("Connection successfully established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  return dbConnection;
    }

//    public static Connection getDBConnection() throws DBException {
//        System.out.println("connect ....");
//        return DBConnectionPool.getConnection();
//    }
    public static ArrayList<EpinResultSet> getRecordFromActivationJobsTable() throws SQLException {
        //  Connection conn = Pool.getDBConnection();
        ResultSet rs = null;
        CallableStatement cs = null;
        String value = null;
        try {
            cs = conn.prepareCall("{CALL PKG_EPIN_APP.PRC_LIST_ACTIVATION_JOB_RECORD(?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);

            System.out.println("getRecordFromActivationJobsTable  side ------------");
            return new EpinResultSet().toObject(rs);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            //   DBConnectionPool.close();
        }
    }

    public static String getNewTaskID() {
        String taskID = "";
        try {
            TaskReturnValue trvResponse = getTaskID(1);
            System.out.println(" response.getRetCode()    :  " + trvResponse.getRetCode());
            System.out.println(" response.getTaskId()    :  " + trvResponse.getTaskId());

            if (trvResponse.getRetCode() != 0) {
                   throw new Exception("Error. System can not get task id!");
            }
            taskID = trvResponse.getTaskId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return taskID;
    }

    public static String submitRequest(EpinResultSet object, String taskId) {

        String[] batchNumber = new String[1];
        String[] startSequenceNumber = new String[1];
        String[] stopSequenceNumber = new String[1];
        String[] deactivationReason = new String[1];
        String[] lockReason = new String[1];
        String[] stopDate = new String[1];

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Calendar submittedTime = Calendar.getInstance();

        try {
            CustomizeTaskResponse activationResponse = null;
            int taskType = object.getActionMode();
            int notifyFlag = 0;
            batchNumber[0] = object.getBatchNo();
            startSequenceNumber[0] = object.getStartSerial();
            stopSequenceNumber[0] = object.getEndSerial();
            deactivationReason[0] = "Deactivated";
            lockReason[0] = "Locked";
            stopDate[0] = getStopDate();
            String operatorID = "epin";
            CustomizeTask tasks = new CustomizeTask();

            TaskParametersItem taskParameter = new TaskParametersItem();
            taskParameter.getBatchNo().add(batchNumber[0]);
            taskParameter.getStartSequence().add(startSequenceNumber[0]);
            taskParameter.getStopSequence().add(stopSequenceNumber[0]);
            taskParameter.getDeactivateReason().add(deactivationReason[0]);
            taskParameter.getLockReason().add(lockReason[0]);
            taskParameter.getNewStopDate().add(stopDate[0]);

            System.out.println("Batch No :" + batchNumber[0]);
            System.out.println("Start sequence :" + startSequenceNumber[0]);
            System.out.println("Stop sequence :" + stopSequenceNumber[0]);
            System.out.println("Stop date  :" + stopDate[0]);
            System.out.println("taskType   " + taskType);

            List<TaskParametersItem> taskItems = new ArrayList<TaskParametersItem>();
            taskItems.add(taskParameter);

            Calendar cal = Calendar.getInstance();
            cal.add(12, 5);
            String startuptime = dateFormat.format(cal.getTime());
            System.out.println("startuptime :" + startuptime);
            String tenantID = "", retCode = "";
            int response = customizeTask(taskId, taskType, startuptime, tenantID, operatorID, notifyFlag, taskItems);
            System.out.println("  resp   : " + response);

            String submissionTime = dateFormat.format(submittedTime.getTime());

            if (response == 0) {
                retCode = "1";

                return retCode + "|" + taskId + "|" + startuptime + "|" + submissionTime + "|" + object.getJobId() + "|" + object.getActionMode();
            }
            startuptime = "";
            submissionTime = "";
            return response + "|" + taskId + "|" + startuptime + "|" + submissionTime + "|" + object.getJobId() + "|" + object.getActionMode();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<String> processFromActivationJobsTable(ArrayList<EpinResultSet> rs) {
        ArrayList Response = new ArrayList();
        String[] requestParameter = new String[9];
        String response = null;
        String taskID = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String transactionDate = null;
        for (Iterator it = rs.iterator(); it.hasNext();) {
            EpinResultSet object;
            object = (EpinResultSet) it.next();
            taskID = getNewTaskID();
            response = submitRequest(object, taskID);
            System.out.println("The response after submission is " + response);
            Response.add(response);
        }
        return Response;
    }

//    private static HashMap<Integer, String> getTaskID(int totalNumber) {
//        Holder<Integer> retCode = new Holder<Integer>();
//        Holder<String> taskID = new Holder<String>();
//        
//        recharge.server.UvcSoapForRechargeService service = new recharge.server.UvcSoapForRechargeService();
//        recharge.server.UvcSoapForRecharge port = service.getRecharge();
//        port.getTaskID(totalNumber, retCode, taskID);
//        
//        HashMap<Integer, String> hm = new HashMap<>();
//        hm.put(retCode.value, taskID.value);
//        
//        System.out.println(retCode.value+", "+taskID.value);
//        
//        return hm;
//    }
    public static void updateAllResponse(ArrayList<String> allResponse) throws SQLException {
        // Connection conn = Pool.getDBConnection();
        String[] value = null;
        CallableStatement cs = null;
        try {
            for (int i = 0; i < allResponse.size(); i++) {
                System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                value = ((String) allResponse.get(i)).split("\\|");
                cs = conn.prepareCall("{ CALL PKG_EPIN_APP.PRC_UPDATE_ACTIVATN_JOB_RECORD(?,?,?,?,?,?)}");
                cs.setString(1, value[0]); // response 
                cs.setString(2, value[1]); // taskId
                cs.setString(3, value[2]); // startuptime
                cs.setString(4, value[3]); //  submissionTime
                cs.setString(5, value[4]); // jobId
                cs.setString(6, value[5]); // actionMode
                cs.execute();
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("updateAllResponse side --response ----------" + value[0] + " taskId  " + value[1]);
                System.out.println("updateAllResponse side -----startuptime-------" + value[2] + " submissionTime  " + value[3]);
                System.out.println("updateAllResponse side ----jobId--------" + value[4] + " actionMode  " + value[5]);

            }
           
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            //   DBConnectionPool.close();
        }
    }

    public static ArrayList<QueryTaskRqt> getActivationJobsSubmitted() throws SQLException {
        //Connection conn = Pool.getDBConnection();
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL PKG_EPIN_APP.PRC_LIST_ACT_JOB_SUMMITED(?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            rs = (ResultSet) cs.getObject(1);
            System.out.println("getActivationJobsSubmitted  side ------------");
            return new QueryTaskRqt().toObject(rs);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            //   DBConnectionPool.close();
        }
    }

    public static String queryTaskStatus(String taskID, String operator) {

        try {
            System.out.println("before calling Holder..........");
            TaskReturnValue trv = queryTask(taskID, operator);
            System.out.println("queryTaskStatus    side  :" + taskID + "|" + trv.getTaskStatus() + "|" + trv.getRetCode());
            System.out.println("Aftr  the holder  :" + taskID + "|" + trv.getTaskStatus() + "|" + trv.getRetCode());

            return taskID + "|" + trv.getTaskStatus() + "|" + trv.getRetCode();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<String> processActivationJobsSubmitted(ArrayList<QueryTaskRqt> rs) {
        ArrayList allResponse = new ArrayList();
        String[] requestParameter = new String[9];
        String response = null;

        String transactionDate = null;
        for (Iterator it = rs.iterator(); it.hasNext();) {
            QueryTaskRqt object;
            object = (QueryTaskRqt) it.next();
            response = queryTaskStatus(object.getUvcJobId(), "epin");
            response = response + "|" + object.getJobId() + "|" + getDateHHmmss();
            System.out.println(" processActivationJobsSubmitted  side     :" + response);
            allResponse.add(response);

        }
        return allResponse;
    }

    public static void updateQueryTaskResponse(ArrayList<String> allResponse) throws SQLException {

        String[] value = null;
        String jobStatus = null;
        for (int i = 0; i < allResponse.size(); i++) {
            value = ((String) allResponse.get(i)).split("\\|");
            String taskId = value[0];
            int taskStatus = Integer.parseInt(value[1]);
            System.out.println("value[2]    :" + value[2]);
            int retCode = Integer.parseInt(value[2]);
            String jobId = value[3];
            String finishTime = value[4];
            System.out.println("retCode    : " + retCode + "  taskStatus  :" + taskStatus);
            if ((retCode == 0) && (taskStatus == 8)) {
                jobStatus = "C";
                updateQueryTaskCompleted(retCode, finishTime, jobStatus, taskId, jobId);
            } else {
                if ((retCode == 0) && (taskStatus == 4)) {
                    System.out.println(taskId + ": Job undergoing processing...");
                    continue;
                }
                if ((retCode == 0) && (taskStatus == 0)) {
                    System.out.println(taskId + ": Job awaiting processing...");
                    jobStatus = "L";
                    retCode = 1;
                    updateQueryTaskAwaitingProcess(retCode, jobStatus, taskId, jobId);

                }
            }
        }
    }

    public static void updateQueryTaskCompleted(int retCode, String finishTime, String jobStatus, String taskId, String jobId) throws SQLException {
        //  Connection conn = Pool.getDBConnection();
        String[] value = null;
        CallableStatement cs = null;
        try {

            cs = conn.prepareCall("{ CALL PKG_EPIN_APP.PRC_UPDATE_COMPLETED_PROCESS(?,?,?,?,?)}");
            cs.setInt(1, retCode); // response 
            cs.setString(2, finishTime); // taskId
            cs.setString(3, jobStatus); // startuptime
            cs.setString(4, taskId); //  submissionTime
            cs.setString(5, jobId); // jobId
            cs.execute();
            System.out.println("updateQueryTaskCompleted side ------------");

        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            //   DBConnectionPool.close();
        }
    }

    public static void updateQueryTaskAwaitingProcess(int retCode, String jobStatus, String taskId, String jobId) throws SQLException {
        // Connection conn = Pool.getDBConnection();
        String[] value = null;
        CallableStatement cs = null;
        try {

            cs = conn.prepareCall("{ CALL PKG_EPIN_APP.PRC_UPDATE_AWAITING_PROCESS(?,?,?,?)}");
            cs.setInt(1, retCode);
            cs.setString(2, jobStatus);
            cs.setString(3, taskId);
            cs.setString(4, jobId);
            cs.execute();

            System.out.println("updateAllResponse side ------------");

        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            //   DBConnectionPool.close();
        }
    }

    private static TaskReturnValue getTaskID(int totalNumber) {
        Holder<Integer> retCode = new Holder<Integer>();
        Holder<String> taskID = new Holder<String>();

        recharge.server.UvcSoapForRechargeService service = new recharge.server.UvcSoapForRechargeService();
        recharge.server.UvcSoapForRecharge port = service.getRecharge();
        port.getTaskID(totalNumber, retCode, taskID);

        TaskReturnValue trv = new TaskReturnValue();
        trv.setRetCode(retCode.value);
        trv.setTaskId(taskID.value);

        System.out.println(retCode.value + ", " + taskID.value);

        return trv;
    }

    private static int customizeTask(java.lang.String taskID, int taskType, java.lang.String startUpTime, java.lang.String tenantID, java.lang.String operatorID, int notifyFlag, java.util.List<recharge.server.TaskParametersItem> taskParameters) {
        recharge.server.UvcSoapForRechargeService service = new recharge.server.UvcSoapForRechargeService();
        recharge.server.UvcSoapForRecharge port = service.getRecharge();
        return port.customizeTask(taskID, taskType, startUpTime, tenantID, operatorID, notifyFlag, taskParameters);
    }

    private static String getStopDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    private static String getDateHHmmss() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    private static TaskReturnValue queryTask(String taskID, String operatorID) {
        String tenantID = null;
        System.out.println("queryTask.....taskID:  " + taskID);

        Holder<Integer> skipNum = null, successNum = null, total = new Holder<Integer>();
        Holder<String> holderId = new Holder<String>();
        Holder<Integer> retCode = new Holder<Integer>();
        Holder<Integer> taskStatus = new Holder<Integer>();
        //taskStatus = null, failNum = new Holder<Integer>();
        holderId.value = taskID;
        Holder<java.lang.String> startUpTime = null, createTime = null, dealTime = null, dealEndTime = null, batchNo = null, startSequence = new Holder<String>();
        Holder<java.lang.String> stopSequence = null, vcid = null, deactivationReason = null, randomListFile = new Holder<String>();
        Holder<java.lang.String> cardmanufacturer = null, lockReason = null, failFile = null, cardInformationFile = null, skipFile = null, successFile = null, cardmakingFile = new Holder<String>();
        javax.xml.ws.Holder<java.util.List<java.lang.Integer>> faceValue = new Holder<java.util.List<java.lang.Integer>>();

        recharge.server.UvcSoapForRechargeService service = new recharge.server.UvcSoapForRechargeService();
        recharge.server.UvcSoapForRecharge port = service.getRecharge();

        System.out.println("holderId     :" + holderId.value);

        port.queryTask(holderId, new String(), new String(), retCode, new Holder<Integer>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), taskStatus, new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<String>(), new Holder<Integer>(), new Holder<String>(), new Holder<Integer>(), new Holder<String>(), new Holder<Integer>(), new Holder<Integer>(), new Holder<String>(), new Holder<java.util.List<java.lang.Integer>>(), new Holder<String>(), new Holder<String>());

        TaskReturnValue trv = new TaskReturnValue();
        trv.setRetCode(retCode.value);
        trv.setTaskStatus(taskStatus.value);

        System.out.println("retCode =====================++++++  :" + retCode.value + "   taskStatus  :" + taskStatus.value);

        return trv;
    }

}

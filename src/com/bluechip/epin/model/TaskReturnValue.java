/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluechip.epin.model;

/**
 *
 * @author Diamonddemola
 */
public class TaskReturnValue {
    
    private int retCode ;
    private String taskId;
    private int taskStatus;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the taskStatus
     */
    public int getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param taskStatus the taskStatus to set
     */
    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    
    
    
    
    
}

package com.alfresco.activiti.analytics.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ActivitiEventLog.
 */
@MappedSuperclass
public abstract class ActivitiEventAbstract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "log_nr_")
    private String logNr;

    @JsonIgnore
    @Column(name = "type_")
    private String type;

    @Column(name = "proc_def_id_")
    private String processDefinitionId;

    @Column(name = "proc_inst_id_")
    private String processInstanceId;

    @Column(name = "execution_id_")
    private String executionId;

    @Column(name = "task_id_")
    private String taskId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_stamp_")
    private Date timestamp;

    @Column(name = "user_id_")
    private String userId;

    /* For PostgresSQL use the following */
    @Column(name = "data_", columnDefinition = "clob")
    private byte[] data;
    
    /* For other DBs (tested for H2 & Oracle) use the following */
   /* @Column(name = "data_")
    @Lob
    private byte[] data;*/

    public String getLogNr() {
        return logNr;
    }

    public void setLogNr(String logNr) {
        this.logNr = logNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
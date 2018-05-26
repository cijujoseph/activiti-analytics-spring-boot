package com.alfresco.activiti.analytics.conf;

import com.alfresco.activiti.analytics.conf.json.process.AllProcessesMapping;
import com.alfresco.activiti.analytics.conf.json.process.EndState;
import com.alfresco.activiti.analytics.conf.json.task.AllTasksMapping;

import java.util.List;

public class MappingConfiguration {
    private List<EndState> endStateConfig;
    private AllProcessesMapping allProcessConfig;
    private AllTasksMapping allTasksConfig;

    public AllProcessesMapping getAllProcessConfig() {
        return allProcessConfig;
    }

    public void setAllProcessConfig(AllProcessesMapping allProcessesMapping) {
        this.allProcessConfig = allProcessesMapping;
    }

    public List<EndState> getEndStateConfig() {
        return endStateConfig;
    }

    public void setEndStateConfig(List<EndState> endStateList) {
        this.endStateConfig = endStateList;
    }

    public AllTasksMapping getAllTasksConfig() {
        return allTasksConfig;
    }

    public void setAllTasksConfig(AllTasksMapping allTasksMapping) {
        this.allTasksConfig = allTasksMapping;
    }
}

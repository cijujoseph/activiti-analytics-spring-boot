
package com.alfresco.activiti.analytics.conf.json.process;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "processName",
    "processReportsRequired",
    "tableReportsRequired",
    "mappingConfigFileName"
})
public class Process {

    @JsonProperty("processName")
    private String processName;
    @JsonProperty("processReportsRequired")
    private String processReportsRequired;
    @JsonProperty("tableReportsRequired")
    private String tableReportsRequired;
    @JsonProperty("mappingConfigFileName")
    private String mappingConfigFileName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("processName")
    public String getProcessName() {
        return processName;
    }

    @JsonProperty("processName")
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @JsonProperty("processReportsRequired")
    public String getProcessReportsRequired() {
        return processReportsRequired;
    }

    @JsonProperty("processReportsRequired")
    public void setProcessReportsRequired(String processReportsRequired) {
        this.processReportsRequired = processReportsRequired;
    }

    @JsonProperty("tableReportsRequired")
    public String getTableReportsRequired() {
        return tableReportsRequired;
    }

    @JsonProperty("tableReportsRequired")
    public void setTableReportsRequired(String tableReportsRequired) {
        this.tableReportsRequired = tableReportsRequired;
    }

    @JsonProperty("mappingConfigFileName")
    public String getMappingConfigFileName() {
        return mappingConfigFileName;
    }

    @JsonProperty("mappingConfigFileName")
    public void setMappingConfigFileName(String mappingConfigFileName) {
        this.mappingConfigFileName = mappingConfigFileName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

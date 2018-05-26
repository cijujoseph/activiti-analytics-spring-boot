
package com.alfresco.activiti.analytics.conf.json.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alfresco.activiti.analytics.conf.json.common.Mapping;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "processName",
    "createProcesssMappingNode",
    "fileNamePrefix",
    "header",
    "mappings",
    "tables"
})
public class ProcessMapping {

    @JsonProperty("processName")
    private String processName;
    @JsonProperty("createProcesssMappingNode")
    private Boolean createProcesssMappingNode;
    @JsonProperty("fileNamePrefix")
    private String fileNamePrefix;
    @JsonProperty("header")
    private List<String> header = null;
    @JsonProperty("mappings")
    private List<Mapping> mappings = null;
    @JsonProperty("tables")
    private List<Object> tables = null;
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

    @JsonProperty("createProcesssMappingNode")
    public Boolean getCreateProcesssMappingNode() {
        return createProcesssMappingNode;
    }

    @JsonProperty("createProcesssMappingNode")
    public void setCreateProcesssMappingNode(Boolean createProcesssMappingNode) {
        this.createProcesssMappingNode = createProcesssMappingNode;
    }

    @JsonProperty("fileNamePrefix")
    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    @JsonProperty("fileNamePrefix")
    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    @JsonProperty("header")
    public List<String> getHeader() {
        return header;
    }

    @JsonProperty("header")
    public void setHeader(List<String> header) {
        this.header = header;
    }

    @JsonProperty("mappings")
    public List<Mapping> getMappings() {
        return mappings;
    }

    @JsonProperty("mappings")
    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    @JsonProperty("tables")
    public List<Object> getTables() {
        return tables;
    }

    @JsonProperty("tables")
    public void setTables(List<Object> tables) {
        this.tables = tables;
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

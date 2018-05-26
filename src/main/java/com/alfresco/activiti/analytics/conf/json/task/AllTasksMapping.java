
package com.alfresco.activiti.analytics.conf.json.task;

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
    "fileNamePrefix",
    "header",
    "mappings"
})
public class AllTasksMapping {

    @JsonProperty("fileNamePrefix")
    private String fileNamePrefix;
    @JsonProperty("header")
    private List<String> header = null;
    @JsonProperty("mappings")
    private List<Mapping> mappings = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

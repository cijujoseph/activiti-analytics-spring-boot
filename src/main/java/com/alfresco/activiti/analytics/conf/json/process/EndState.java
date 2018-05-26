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
        "newName",
        "originalName",
        "processName"
})
public class EndState {

    @JsonProperty("newName")
    private String newName;
    @JsonProperty("originalName")
    private String originalName;
    @JsonProperty("processName")
    private String processName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("newName")
    public String getNewName() {
        return newName;
    }

    @JsonProperty("newName")
    public void setNewName(String newName) {
        this.newName = newName;
    }

    @JsonProperty("originalName")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("originalName")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @JsonProperty("processName")
    public String getProcessName() {
        return processName;
    }

    @JsonProperty("processName")
    public void setProcessName(String processName) {
        this.processName = processName;
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

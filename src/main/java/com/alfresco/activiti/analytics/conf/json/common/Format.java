
package com.alfresco.activiti.analytics.conf.json.common;

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
        "keepOriginalValue",
        "originalValueFieldName",
        "setIdMetadataFields",
        "originalFormat",
        "targetFormat",
        "formatToWords"
})
public class Format {

    @JsonProperty("keepOriginalValue")
    private Boolean keepOriginalValue;
    @JsonProperty("originalValueFieldName")
    private String originalValueFieldName;
    @JsonProperty("setIdMetadataFields")
    private Boolean setIdMetadataFields;
    @JsonProperty("originalFormat")
    private String originalFormat;
    @JsonProperty("targetFormat")
    private String targetFormat;
    @JsonProperty("formatToWords")
    private Boolean formatToWords;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("keepOriginalValue")
    public Boolean getKeepOriginalValue() {
        return keepOriginalValue;
    }

    @JsonProperty("keepOriginalValue")
    public void setKeepOriginalValue(Boolean keepOriginalValue) {
        this.keepOriginalValue = keepOriginalValue;
    }

    @JsonProperty("originalValueFieldName")
    public String getOriginalValueFieldName() {
        return originalValueFieldName;
    }

    @JsonProperty("originalValueFieldName")
    public void setOriginalValueFieldName(String originalValueFieldName) {
        this.originalValueFieldName = originalValueFieldName;
    }

    @JsonProperty("setIdMetadataFields")
    public Boolean getSetIdMetadataFields() {
        return setIdMetadataFields;
    }

    @JsonProperty("setIdMetadataFields")
    public void setSetIdMetadataFields(Boolean setIdMetadataFields) {
        this.setIdMetadataFields = setIdMetadataFields;
    }

    @JsonProperty("originalFormat")
    public String getOriginalFormat() {
        return originalFormat;
    }

    @JsonProperty("originalFormat")
    public void setOriginalFormat(String originalFormat) {
        this.originalFormat = originalFormat;
    }

    @JsonProperty("targetFormat")
    public String getTargetFormat() {
        return targetFormat;
    }

    @JsonProperty("targetFormat")
    public void setTargetFormat(String targetFormat) {
        this.targetFormat = targetFormat;
    }

    @JsonProperty("formatToWords")
    public Boolean getFormatToWords() {
        return formatToWords;
    }

    @JsonProperty("formatToWords")
    public void setFormatToWords(Boolean formatToWords) {
        this.formatToWords = formatToWords;
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

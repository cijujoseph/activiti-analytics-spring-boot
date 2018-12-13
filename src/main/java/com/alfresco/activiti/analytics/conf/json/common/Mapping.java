
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
    "originalName",
    "name",
    "type",
    "fieldSource",
    "format"
})
public class Mapping {

    @JsonProperty("originalName")
    private String originalName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("fieldSource")
    private String fieldSource;
    @JsonProperty("format")
    private Format format;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("originalName")
    public String getOriginalName() {
        return originalName;
    }

    @JsonProperty("originalName")
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("fieldSource")
    public String getFieldSource() {
        return fieldSource;
    }

    @JsonProperty("fieldSource")
    public void setFieldSource(String fieldSource) {
        this.fieldSource = fieldSource;
    }

    @JsonProperty("format")
    public Format getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(Format format) {
        this.format = format;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "keepOriginalValue",
        "originalValueFieldName",
        "setIdMetadataFields",
        "originalFormat",
        "targetFormat",
        "formatToWords"
    })
    public static class Format {

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
}

package com.alfresco.activiti.analytics.entity;



import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ProcessDefinition.
 */
@Entity
@Table(name = "ACT_RE_PROCDEF")
public class ProcessDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_")
	private String processDefinitionId;

    public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	@JsonIgnore
    @Column(name="key_")
    private String processDefinitionKey;
}
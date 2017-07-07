package com.alfresco.activiti.analytics.conf;

import java.util.List;
import java.util.Map;

public class MappingConfiguration {
	private List<Map<String, Object>> endStateConfigMap;
	private Map<String, Object> allProcessConfigMap;
	private Map<String, Object> allTasksConfigMap;

	public Map<String, Object> getAllProcessConfigMap() {
		return allProcessConfigMap;
	}

	public void setAllProcessConfigMap(Map<String, Object> object) {
		this.allProcessConfigMap = object;
	}

	public List<Map<String, Object>> getEndStateConfigMap() {
		return endStateConfigMap;
	}

	public void setEndStateConfigMap(List<Map<String, Object>> object) {
		this.endStateConfigMap = object;
	}

	public Map<String, Object> getAllTasksConfigMap() {
		return allTasksConfigMap;
	}

	public void setAllTasksConfigMap(Map<String, Object> object) {
		this.allTasksConfigMap = object;
	}
}

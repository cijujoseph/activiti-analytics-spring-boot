package com.alfresco.activiti.analytics;

import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;

public interface CustomAnalyticsEndpoint {

	String fetchWaterMark();

	void updateWaterMark(String watermark);

	void preProcessing();

	void publishProcessDocument(Map<String, Object> processMap, HistoricProcessInstance processInstanceDetails) throws Exception;

	void publishTaskDocument(Map<String, Object> taskMap, HistoricTaskInstance taskInstanceDetails) throws Exception ;
}

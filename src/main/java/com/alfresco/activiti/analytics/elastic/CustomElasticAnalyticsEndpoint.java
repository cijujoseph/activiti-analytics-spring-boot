package com.alfresco.activiti.analytics.elastic;

import com.alfresco.activiti.analytics.CustomAnalyticsEndpoint;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

@Component("customAnalyticsEndpoint")
public class CustomElasticAnalyticsEndpoint implements CustomAnalyticsEndpoint {

	@Autowired
	private ElasticHTTPClient elasticHTTPClient;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Value("${analytics.es.url}")
	private String esUrl;

	@Value("${analytics.es.indexPrefix}")
	private String indexPrefix;

	protected static final Logger logger = LoggerFactory.getLogger(CustomElasticAnalyticsEndpoint.class);

	@SuppressWarnings("unchecked")
	public void publishProcessDocument(Map<String, Object> processMap, HistoricProcessInstance processInstanceDetails) throws Exception {
		String documentId = processInstanceDetails.getProcessDefinitionId() + "-" + processInstanceDetails.getId();
		String indexName = indexPrefix;
		String processState = (String) processMap.get("ProcessState");

		if (processState.equals("Unknown")) {
			// If Status unknown, we need to search for an existing doc in all
			// indexes. Creating the payload for the search
			String idIndexSearchQuery = "{\"query\":{\"term\":{\"_id\":\"" + documentId + "\"}}}";
			// If doc found during search, just update the status. Creating the
			// update request payload below
			String updatePayload = "{\"doc\":{\"ProcessState\":\"Unknown\"}}";

			String searchResponse = elasticHTTPClient.execute(esUrl + "bpmanalyticseventlog*/bpmanalyticsevent/_search",
					idIndexSearchQuery, "POST");
			Map<String, Object> searchResponseMap = null;
			try {
				searchResponseMap = new ObjectMapper().readValue(searchResponse, Map.class);
			} catch (Exception e) {
				logger.error("Error while trying parse the searchResponseMap for documentId: " + documentId);
			}
			if (searchResponseMap != null
					&& (Integer) ((Map<String, Object>) searchResponseMap.get("hits")).get("total") > 0) {
				indexName = (String) ((List<Map<String, Object>>) ((Map<String, Object>) searchResponseMap.get("hits"))
						.get("hits")).get(0).get("_index");
				elasticHTTPClient.execute(esUrl + indexName + "/bpmanalyticsevent/" + documentId + "/_update",
						updatePayload, "POST");
			} else {
				elasticHTTPClient.execute(esUrl + indexName + "/bpmanalyticsevent/" + documentId,
						objectMapper.writeValueAsString(processMap), "PUT");
			}
		} else {

			if (processInstanceDetails.getStartTime() != null) {
				indexName = indexName + '-'
						+ new SimpleDateFormat("yyyy.MM").format(processInstanceDetails.getStartTime());
			}
			elasticHTTPClient.execute(esUrl + indexName + "/bpmanalyticsevent/" + documentId,
					objectMapper.writeValueAsString(processMap), "PUT");
		}

	}

	@Override
	public void publishTaskDocument(Map<String, Object> taskMap, HistoricTaskInstance taskInstanceDetails) throws JsonProcessingException {

		String documentId = taskInstanceDetails.getProcessDefinitionId() + "-"
				+ taskInstanceDetails.getProcessInstanceId() + "-" + taskInstanceDetails.getId();
		String indexName = indexPrefix;

		if (taskInstanceDetails.getStartTime() != null) {
			indexName = indexName + '-' + new SimpleDateFormat("yyyy.MM").format(taskInstanceDetails.getStartTime());
		}
		elasticHTTPClient.execute(esUrl + indexName + "/bpmanalyticsevent/" + documentId,
				objectMapper.writeValueAsString(taskMap), "PUT");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String fetchWaterMark() {
		try {

			String response = elasticHTTPClient.execute(esUrl + "watermarklog/watermarkevent/0", null, "GET");
			Map<String, Object> responseMap = new HashMap<String, Object>();
			// convert JSON string to Map
			responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, Object>>() {
			});
			if (responseMap.get("found").equals(true)) {
				return ((Map<String, String>) responseMap.get("_source")).get("watermark");
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public void updateWaterMark(String watermark) {

		Map<String, String> payload = new HashMap<>();
		payload.put("watermark", watermark);
		try {
			String jsonString = new ObjectMapper().writeValueAsString(payload);
			elasticHTTPClient.execute(esUrl + "watermarklog/watermarkevent/0", jsonString, "POST");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActivitiException("Error while invoking REST Service", e);
		}

	}

	@Override
	public void preProcessing() {
		if (checkIndexTemplate() == false) {
			createIndexTemplate();
		}
	}

	public boolean checkIndexTemplate() {
		try {

			String response = elasticHTTPClient.execute(esUrl + "_template/bpmanalyticsevent_template", null, "GET");
			Map<String, Object> responseMap = new HashMap<String, Object>();
			// convert JSON string to Map
			responseMap = new ObjectMapper().readValue(response, new TypeReference<Map<String, Object>>() {
			});
			if (responseMap.size() > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void createIndexTemplate() {
		try {
			URL indexTemplateFileUrl = Resources.getResource("index-template.json");
			String indexTemplateJSON = Resources.toString(indexTemplateFileUrl, Charsets.UTF_8);
			elasticHTTPClient.execute(esUrl + "_template/bpmanalyticsevent_template", indexTemplateJSON, "PUT");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}

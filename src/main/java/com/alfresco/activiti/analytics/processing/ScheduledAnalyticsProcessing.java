package com.alfresco.activiti.analytics.processing;

import java.util.List;
import java.util.Map;

import com.alfresco.activiti.analytics.CustomAnalyticsEndpoint;
import com.alfresco.activiti.analytics.aps.addon.VariableDataEntityType;
import org.activiti.engine.impl.variable.SerializableType;
import org.activiti.engine.impl.variable.VariableTypes;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledAnalyticsProcessing {

	@Autowired
	protected Watermark watermark;

	@Autowired
	CustomAnalyticsEndpoint customAnalyticsEndpoint;

	@Autowired
	protected ProcessBatchPreparation processBatchPreparation;

	@Autowired
	protected SpringProcessEngineConfiguration processEngine;

	@Autowired
	protected GenerateProcessAndTaskDocs generateProcessAndTaskDocs;

	@Value("${analytics.isEnterprise}")
	private String isEnterprise;

	private static final Logger logger = LoggerFactory.getLogger(ScheduledAnalyticsProcessing.class);

	@Scheduled(cron = "${analytics.pollingInterval}")
	public void analyticsProcessing() throws Exception {

		if (isEnterprise.equals("true")) {
			if (processEngine.getVariableTypes().getVariableType("data-model-type") == null) {
				// Adding the data model type variable type to engine config for
				// APS implementations.
				VariableTypes variableTypes = processEngine.getVariableTypes();
				int serializableIndex = variableTypes.getTypeIndex(SerializableType.TYPE_NAME);
				if (serializableIndex > -1) {
					variableTypes.addType(new VariableDataEntityType(), serializableIndex);
				} else {
					variableTypes.addType(new VariableDataEntityType());
				}
			}
		}

		customAnalyticsEndpoint.preProcessing();
		boolean newEventsExist = true;
		int loopSize = 0;
		while (newEventsExist == true) {
			loopSize++;
			logger.info("processing loop counter - " + loopSize);
			// Fetch latest watermark
			String timeStamp = watermark.fetchWatermark();

			Map<String, Object> processBatchMetadata = processBatchPreparation.getBatchMetadata(timeStamp);

			newEventsExist = (boolean) processBatchMetadata.get("newEventsExist");
			List<Map<String, Object>> processList = (List<Map<String, Object>>) processBatchMetadata
					.get("processIdList");

			if (processList != null) {
				logger.debug("process list is - " + processList.toString());
				for (Map<String, Object> processInstance : processList) {
					try {
						generateProcessAndTaskDocs.execute(processInstance);
					} catch (Exception e) {

					}
				}
				// Update watermark
				watermark.updateWatermark((String) processBatchMetadata.get("toTimestamp"));
			} else {
				logger.info("process list is null");
			}
		}

		logger.info(watermark.fetchWatermark());
	}
}
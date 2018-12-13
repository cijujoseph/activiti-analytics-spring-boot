package com.alfresco.activiti.analytics.processing;

import com.alfresco.activiti.analytics.repository.ActivitiEventLogRepository;
import com.alfresco.activiti.analytics.repository.ProcessedActivitiEventsRepository;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("processBatchPreparation")
public class ProcessBatchPreparation {

    protected static final Logger logger = LoggerFactory.getLogger(ProcessBatchPreparation.class);

    @Value("${analytics.excludedProcessDefinitionKeys}")
    private String excludedProcessDefinitionKeys;

    @Value("${analytics.sql.queryBatchSize}")
    private String queryBatchSize;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Autowired
    private ActivitiEventLogRepository activitiEventLogRepository;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired(required = false)
    private ProcessedActivitiEventsRepository processedActivitiEventsRepository;

    @Value("${analytics.dbType}")
	private String dbType;

    @Value("${analytics.isEnterprise}")
    private String isEnterprise;

    @Value("${analytics.eventSource.isProcessedEventsTable}")
    private String isProcessedEventsTable;

    public Map<String, Object> getBatchMetadata(String lastUpdatedTimestamp) throws ParseException {

        Map<String, Object> batchMetadata = new HashMap<String, Object>();
        String maxTimeStamp = getMostRecentTimestamp(lastUpdatedTimestamp);

        if (maxTimeStamp != null) {
            // Get a list of processes instances
            List<Map<String, Object>> processIdQueryList = getProcessIdList(lastUpdatedTimestamp, maxTimeStamp);
            if (processIdQueryList != null && processIdQueryList.size() > 0) {
                batchMetadata.put("newEventsExist", true);
                batchMetadata.put("toTimestamp", maxTimeStamp);
                batchMetadata.put("processIdList", processIdQueryList);
            } else {
                batchMetadata.put("newEventsExist", false);
            }
            logger.info("done!");

        } else {
            batchMetadata.put("newEventsExist", false);
        }

        return batchMetadata;
    }

    private List<String> getExcludedProcessIds() {

        String[] excludedProcessDefinitionKeyArray = excludedProcessDefinitionKeys.split("\\s*,\\s*");
        List<String> excludedIdList = new ArrayList<String>();
        for (String excludedProcessDefinitionKey : excludedProcessDefinitionKeyArray) {
            List<ProcessDefinition> excludedProcessDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(excludedProcessDefinitionKey).list();
            for (ProcessDefinition excludedProcessDefinition : excludedProcessDefinitions) {
                excludedIdList.add(excludedProcessDefinition.getId());
            }
        }
        if (excludedIdList.size() == 0) {
            excludedIdList.add("");
        }
        return excludedIdList;
    }

    private List<Map<String, Object>> getProcessIdList(String lastUpdatedTimestamp, String maxTimeStamp)
            throws ParseException {
        List<Map<String, Object>> processIdQueryList;
        if (isEnterprise.equals("true") && isProcessedEventsTable.equals("true")) {
            processIdQueryList = processedActivitiEventsRepository.findUniqueProcessList(df.parse(lastUpdatedTimestamp),
                    df.parse(maxTimeStamp), getExcludedProcessIds());
        } else {

            processIdQueryList = activitiEventLogRepository.findUniqueProcessList(df.parse(lastUpdatedTimestamp),
                    df.parse(maxTimeStamp), getExcludedProcessIds());
        }
        logger.info("getProcessIdList() SQL Response: " + processIdQueryList);
        return processIdQueryList;
    }

    private String getMostRecentTimestamp(String lastUpdatedTimestamp) throws ParseException {
        if (isEnterprise.equals("true") && isProcessedEventsTable.equals("true")) {
            if(dbType.equals("PostgreSQL")){
				return processedActivitiEventsRepository.getMaxTimestampPostgres(df.parse(lastUpdatedTimestamp), queryBatchSize,
						getExcludedProcessIds());
			} else {
				return processedActivitiEventsRepository.getMaxTimestamp(df.parse(lastUpdatedTimestamp), queryBatchSize,
						getExcludedProcessIds());
			}
        } else {
            if(dbType.equals("PostgreSQL")){
				return activitiEventLogRepository.getMaxTimestampPostgres(df.parse(lastUpdatedTimestamp), queryBatchSize,
						getExcludedProcessIds());
			} else {
			return activitiEventLogRepository.getMaxTimestamp(df.parse(lastUpdatedTimestamp), queryBatchSize,
					getExcludedProcessIds());
			}
        }
    }

}

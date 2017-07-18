package com.alfresco.activiti.analytics.processing;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("processBatchPreparation")
public class ProcessBatchPreparation {

	protected static final Logger logger = LoggerFactory.getLogger(ProcessBatchPreparation.class);
	
	@Autowired
	private JdbcTemplate activitiJdbcTemplate;

	@Value("${analytics.excludedProcessList}")
	private String excludedProcessList;

	@Value("${analytics.sql.eventTable}")
	private String eventTable;
	
	@Value("${analytics.sql.queryBatchSize}")
	private String queryBatchSize;
	
	@Value("${analytics.sql.dbType}")
	private String dbType;
	

	public Map<String, Object> getBatchMetadata(String lastUpdatedTimestamp) throws SQLException {
		
		Map<String, Object> batchMetadata = new HashMap<String, Object>();
		String maxTimeStamp = getMostRecentTimestamp(lastUpdatedTimestamp);

		if (maxTimeStamp != null) {
			batchMetadata.put("newEventsExist", true);
			batchMetadata.put("toTimestamp", maxTimeStamp);

			// Get a list of processes instances
			List<Map<String, Object>> processIdQueryList = getProcessIdList(lastUpdatedTimestamp,
					maxTimeStamp);

			batchMetadata.put("processIdList", processIdQueryList);
			logger.info("done!");

		} else {
			batchMetadata.put("newEventsExist", false);
		}

	
		return batchMetadata;
	}

	private List<Map<String, Object>> getProcessIdList(String lastUpdatedTimestamp,
			String maxTimeStamp) {

		String excludedProcessListQuery = createExcludedProcessQueryStatement();
		// Get the list of processes.
		// H2 Query. This will need to be modified to work with other databases
		String processAndTaskQuery = "select DISTINCT PROC_INST_ID_ as processInstanceId, "
				+ "PROC_DEF_ID_ as processDefinitionId FROM " + eventTable + " WHERE " +
				// Exclude those processes in the exclude list
				excludedProcessListQuery +
				// Exclude any adhoc task data resulting in null rows
				"PROC_DEF_ID_ IS NOT NULL " +
				// get only those data which has been created since the last
				// processing
				"AND TIME_STAMP_ > '" + lastUpdatedTimestamp + "' " + "AND TIME_STAMP_ <= '" + maxTimeStamp + "'";
		logger.debug("getProcessIdList() SQL: " + processAndTaskQuery);
		List<Map<String, Object>> processIdQueryList = activitiJdbcTemplate.queryForList(processAndTaskQuery);
		logger.debug("getProcessIdList() SQL Response: " + processIdQueryList);
		return processIdQueryList;
	}

	private String getMostRecentTimestamp(String lastUpdatedTimestamp) throws SQLException {

		String excludedProcessListQuery = createExcludedProcessQueryStatement();
		
		String sql;
		if (dbType.equals("PostgreSQL")) {
			sql = "select MAX(TIME_STAMP_) AS TO_TIMESTAMP FROM (SELECT TIME_STAMP_ FROM " +
			// Select from processed table assuming the activiti analytics
			// process is moving data to this table
					eventTable + "  WHERE " +
					// Exclude those processes in the exclude list
					excludedProcessListQuery +
					// Exclude any ad-hoc task data
					"PROC_DEF_ID_ IS NOT NULL " +
					// get only those data which has been created since the last
					// processing
					"AND TIME_STAMP_ > '" + lastUpdatedTimestamp + "' "
					+ " GROUP BY TIME_STAMP_ ORDER BY TIME_STAMP_ ASC limit "+queryBatchSize+") AS SUBQUERY";
		} else {
			// H2 Query. This will need to be modified to work with other databases
			sql = "select MAX(TIME_STAMP_) AS TO_TIMESTAMP FROM (SELECT TOP "+queryBatchSize+" TIME_STAMP_ FROM " +
			// Select from processed table assuming the activiti analytics
			// process is moving data to this table
					eventTable + "  WHERE " +
					// Exclude those processes in the exclude list
					excludedProcessListQuery +
					// Exclude any ad-hoc task data
					"PROC_DEF_ID_ IS NOT NULL " +
					// get only those data which has been created since the last
					// processing
					"AND TIME_STAMP_ > '" + lastUpdatedTimestamp + "' "
					+ " GROUP BY TIME_STAMP_ ORDER BY TIME_STAMP_ ASC)";
		}
		
		logger.debug("getMostRecentTimestamp() SQL: " + sql);
		List<Map<String, Object>> rows = activitiJdbcTemplate.queryForList(sql);
		logger.debug("getMostRecentTimestamp() SQL Response: " + rows);
		return rows.get(0).get("TO_TIMESTAMP") != null
				? ((java.sql.Timestamp) rows.get(0).get("TO_TIMESTAMP")).toString() : null;
	}

	private String createExcludedProcessQueryStatement() {
		List<String> items = Arrays.asList(excludedProcessList.split("\\s*,\\s*"));
		StringBuilder excludedProcessListStringBuilder = new StringBuilder();
		for (String item : items) {
			excludedProcessListStringBuilder.append("PROC_DEF_ID_ NOT LIKE '" + item + "' AND ");
		}
		logger.debug(excludedProcessListStringBuilder.toString());
		return excludedProcessListStringBuilder.toString();
	}

}

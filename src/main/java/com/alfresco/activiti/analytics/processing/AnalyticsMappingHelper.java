package com.alfresco.activiti.analytics.processing;

import com.alfresco.activiti.analytics.conf.json.common.Mapping;
import com.alfresco.activiti.analytics.conf.json.process.EndState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alfresco.activiti.analytics.entity.User;
import com.alfresco.activiti.analytics.repository.UserRepository;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;

/**
 * Helper functions to perform activiti analytics and reporting data mapping
 */
@Component("analyticsMappingHelper")
public class AnalyticsMappingHelper {

    @Autowired(required = false)
    private UserRepository userRepository;

    @Value("${analytics.isEnterprise}")
    private String isEnterprise;

    protected static final Logger logger = LoggerFactory.getLogger(AnalyticsMappingHelper.class);

    @SuppressWarnings("unchecked")
    public Map<String, Object> lookupMapping(Mapping mapping,
                                             HistoricProcessInstance processInstanceDetails, Map<String, Object> processMetaData,
                                             Object taskDefinitionKeys, List<EndState> endStateConfigList,
                                             HistoricTaskInstance taskInstanceDetails, Map<String, Object> taskCompleteEventJSON) throws ParseException {
        logger.debug("mapping start: " + mapping.toString());
        Map<String, Object> transformedMap = new HashMap<String, Object>();
        String fieldSource = mapping.getFieldSource();
        String fieldType = mapping.getType();

        String key = mapping.getOriginalName();
        Object value = null;

        // mapping based on Field Source value
        switch (fieldSource) {
            case "variable":

                value = processInstanceDetails.getProcessVariables().get(key);
                break;
            case "processInstanceMetaData":
                switch (mapping.getName()) {
                    case "ProcessEndState":
                        // Not very accurate if it is a process with multiple end
                        // events! Hence this api has been depricated
                        String endStateValue = processInstanceDetails.getEndActivityId();
                        if (endStateValue != null && processMetaData != null) {
                            // To be converted to java later
                            for (EndState endStateConfig : endStateConfigList) {
                                if (endStateConfig.getOriginalName().equals(processInstanceDetails.getEndActivityId())
                                        && endStateConfig.getProcessName().equals(processMetaData.get("PROCESSNAME"))) {
                                    value = endStateConfig.getNewName();
                                }
                            }
                        } else if (processInstanceDetails.getEndTime() != null) {
                            value = "process-deleted";
                        }
                        value = value != null ? value : processInstanceDetails.getEndActivityId();
                        break;

                    case "BusinessKey":
                        value = processInstanceDetails.getBusinessKey();
                        break;

                    case "ProcessInstanceName":
                        value = processInstanceDetails.getName();
                        break;

                    case "ProcessDefinitionKey":
                        value = processInstanceDetails.getProcessDefinitionKey();
                        break;

                    case "ProcessDefinitionId":
                        value = processInstanceDetails.getProcessDefinitionId();
                        break;

                    case "StartUser":
                        value = processInstanceDetails.getStartUserId();
                        break;

                    case "ProcessDeleteReason":
                        value = processInstanceDetails.getDeleteReason();
                        break;

                    case "SuperProcessInstanceId":
                        value = processInstanceDetails.getSuperProcessInstanceId();
                        break;

                    case "ProcessInstanceId":
                        value = processInstanceDetails.getId();
                        break;

                    case "ProcessStartDate":
                        value = processInstanceDetails.getStartTime();
                        break;

                    case "ProcessEndDate":
                        value = processInstanceDetails.getEndTime();
                        break;

                    case "ProcessDurationInWords":
                        value = processInstanceDetails.getDurationInMillis();
                        break;

                    case "TenantId":
                        value = processInstanceDetails.getTenantId();
                        break;
                }
                break;
            case "processDefinitionMetaData":
                if (processMetaData != null) {
                    value = processMetaData.get(key);
                }
                break;
            case "taskOutcome":
                if (taskInstanceDetails.getEndTime() != null) {
                    if (taskCompleteEventJSON != null) {
                        Map<String, Object> taskCompVariables = ((Map<String, Object>) taskCompleteEventJSON
                                .get("variables"));
                        Object taskOutcome = null;
                        if (taskCompVariables != null && !taskCompVariables.isEmpty()) {
                            taskOutcome = ((Map<String, Object>) taskCompleteEventJSON.get("variables"))
                                    .get("form" + taskInstanceDetails.getFormKey() + "outcome");
                        }
                        value = taskOutcome != null ? (String) taskOutcome : "Complete";
                    } else {
                        value = "Unknown";
                    }
                }
                break;
            case "taskInstanceMetaData":
                switch (mapping.getName()) {
                    case "TaskStartDate":
                        value = taskInstanceDetails.getStartTime();
                        break;
                    case "TaskName":
                        value = taskInstanceDetails.getName();
                        break;
                    case "TaskEndDate":
                        value = taskInstanceDetails.getEndTime();
                        break;
                    case "TaskClaimDate":
                        value = taskInstanceDetails.getClaimTime();
                        break;
                    case "TaskDueDate":
                        value = taskInstanceDetails.getDueDate();
                        break;
                    case "TaskDurationInWords":
                        value = taskInstanceDetails.getDurationInMillis();
                        break;
                    case "TaskDurationSinceClaimedInWords":
                        value = taskInstanceDetails.getWorkTimeInMillis();
                        break;
                    case "TaskDescription":
                        value = taskInstanceDetails.getDescription();
                        break;
                    case "TaskId":
                        value = taskInstanceDetails.getId();
                        break;
                    case "Assignee":
                        value = taskInstanceDetails.getAssignee();
                        break;
                    case "TaskCompleteReason":
                        value = taskInstanceDetails.getDeleteReason();
                        break;
                    case "FormKey":
                        value = taskInstanceDetails.getFormKey();
                        break;
                    case "TaskDefinitionKey":
                        value = taskInstanceDetails.getTaskDefinitionKey();
                        break;
                    case "TaskPriority":
                        value = taskInstanceDetails.getPriority();
                        break;
                }
                break;
//		case "defaultValue":
//			value = mapping.get("defaultValue");
//			break;
            default:
                value = "Mapping Error: no match found";
                break;
        }

        // If original value needs to be kept do it here before we do any
        // transformations next
        if (mapping.getFormat() != null
                && mapping.getFormat().getKeepOriginalValue()) {
            String originalValueKey = mapping.getFormat().getOriginalValueFieldName();
            transformedMap.put(originalValueKey, value);
        }

        // formatting based on Field Type value
        switch (fieldType) {
            case "dateTime":
                String targetFormat = mapping.getFormat().getTargetFormat();
                value = value != null
                        ? ((targetFormat != null) ? new SimpleDateFormat(targetFormat).format(value) : value.toString())
                        : null;
                break;
            case "durationInMills":
                value = value != null ? (mapping.getFormat().getFormatToWords() != null
                        ? DurationFormatUtils.formatDurationWords((Long) value, true, true) : value) : "";
                break;
            case "boolean":
                value = value != null ? value : false;
                break;
            case "activitiUserId":
                if (value != null) {
                    if (mapping.getFormat() != null
                            && mapping.getFormat().getSetIdMetadataFields()) {
                        try {
                            if (isEnterprise.equals("true")) {
                                User user = userRepository.findOne(Long.parseLong(value.toString()));
                                transformedMap.put(mapping.getName() + "FirstName", user.getFirstName());
                                transformedMap.put(mapping.getName() + "LastName", user.getLastName());
                                transformedMap.put(mapping.getName() + "Name", user.getFullName());
                                transformedMap.put(mapping.getName() + "Email", user.getEmail());
                                if (user.getExternalId() == null) {
                                    transformedMap.put(mapping.getName() + "UserId", user.getEmail());
                                } else {
                                    transformedMap.put(mapping.getName() + "UserId", user.getExternalId());
                                }
                                transformedMap.put(mapping.getName() + "TenantId", user.getTenantId());
                            }
                        } catch (Exception e) {
                            logger.debug("error resolving user " + value);
                        }
                    }

                }
                break;
            default:
                break;
        }
        transformedMap.put("value", value);
        logger.debug("mapping end: " + mapping);
        return transformedMap;
    }

}
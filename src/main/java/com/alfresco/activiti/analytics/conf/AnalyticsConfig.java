package com.alfresco.activiti.analytics.conf;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Creating Activiti Datasoure and Mapping Configuration
 */
@Configuration("analyticsConfig")
public class AnalyticsConfig {

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsConfig.class);

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource activitiDataSource() {
		logger.debug("creating activitiDataSource bean");
		return DataSourceBuilder.create().build();
	}
	
	@Bean
    public JdbcTemplate activitiJdbcTemplate(DataSource activitiDataSource) {
        return new JdbcTemplate(activitiDataSource);
    }

	@SuppressWarnings("unchecked")
	@Bean
	public MappingConfiguration mappingConfiguration() {
		logger.debug("creating mappingConfiguration bean");
		MappingConfiguration mappingConf = new MappingConfiguration();
		try {
			URL endStateConfigUrl = Resources.getResource("mappingconfig/end-state-mapping.json");
			String endStateConfigJSON = Resources.toString(endStateConfigUrl, Charsets.UTF_8);
			mappingConf.setEndStateConfigMap((List<Map<String, Object>>) new ObjectMapper()
					.readValue(endStateConfigJSON, new TypeReference<List<Object>>() {
					}));

			URL allProcessConfigUrl = Resources.getResource("mappingconfig/all-processes-mapping-config.json");
			String allProcessConfigJSON = Resources.toString(allProcessConfigUrl, Charsets.UTF_8);
			mappingConf.setAllProcessConfigMap((Map<String, Object>) new ObjectMapper().readValue(allProcessConfigJSON,
					new TypeReference<Map<String, Object>>() {
					}));

			URL allTasksConfigUrl = Resources.getResource("mappingconfig/all-tasks-mapping-config.json");
			String allTasksConfigJSON = Resources.toString(allTasksConfigUrl, Charsets.UTF_8);
			mappingConf.setAllTasksConfigMap((Map<String, Object>) new ObjectMapper().readValue(allTasksConfigJSON,
					new TypeReference<Map<String, Object>>() {
					}));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mappingConf;
	}
	
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }
}
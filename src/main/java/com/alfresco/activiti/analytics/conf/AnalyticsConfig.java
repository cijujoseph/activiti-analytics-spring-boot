package com.alfresco.activiti.analytics.conf;

import com.alfresco.activiti.analytics.conf.json.process.AllProcessesMapping;
import com.alfresco.activiti.analytics.conf.json.process.EndState;
import com.alfresco.activiti.analytics.conf.json.task.AllTasksMapping;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Creating Activiti Datasoure and Mapping Configuration
 */
@Configuration("analyticsConfig")
public class AnalyticsConfig {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsConfig.class);

    @Autowired
    ObjectMapper objectMapper;

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
            List<EndState> endStateList = objectMapper.readValue(endStateConfigJSON, new TypeReference<List<EndState>>() {
            });
            mappingConf.setEndStateConfig(endStateList);

            URL allProcessConfigUrl = Resources.getResource("mappingconfig/all-processes-mapping-config.json");
            String allProcessConfigJSON = Resources.toString(allProcessConfigUrl, Charsets.UTF_8);
            mappingConf.setAllProcessConfig(objectMapper.readValue(allProcessConfigJSON, AllProcessesMapping.class));

            URL allTasksConfigUrl = Resources.getResource("mappingconfig/all-tasks-mapping-config.json");
            String allTasksConfigJSON = Resources.toString(allTasksConfigUrl, Charsets.UTF_8);
            mappingConf.setAllTasksConfig(objectMapper.readValue(allTasksConfigJSON, AllTasksMapping.class));
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
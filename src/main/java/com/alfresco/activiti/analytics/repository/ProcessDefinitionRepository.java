package com.alfresco.activiti.analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfresco.activiti.analytics.entity.ProcessDefinition;;

public interface ProcessDefinitionRepository extends JpaRepository<ProcessDefinition, Long> {
	 
	public List<ProcessDefinition> findByProcessDefinitionKeyIn(List<String> processDefinitionKeys);

}

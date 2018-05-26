package com.alfresco.activiti.analytics.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ProcessedActivitiEvents.
 */
@Entity
@Table(name = "PROCESSED_ACTIVITI_EVENTS")
public class ProcessedActivitiEvents extends ActivitiEventAbstract {

    private static final long serialVersionUID = 1L;

}
package com.alfresco.activiti.analytics.aps.addon;

import org.activiti.engine.impl.variable.ValueFields;
import org.activiti.engine.impl.variable.VariableType;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * This is a dummy implementation of data-model-type variable type in APS. Since
 * this solution is running with just the core engine Java APIs, not resolving
 * the complete data model JSON Node for analytics
 *
 */
@Component("variableDataEntityType")
public class VariableDataEntityType implements VariableType {

	@Override
	public String getTypeName() {
		return "data-model-type";
	}

	@Override
	public Object getValue(ValueFields valueFields) {
		return valueFields.getTextValue2();
	}

	@Override
	public boolean isAbleToStore(Object value) {
		return true;
	}

	@Override
	public boolean isCachable() {
		return true;
	}

	@Override
	public void setValue(Object value, ValueFields valueFields) {
		valueFields.setTextValue(value.toString());
		valueFields.setTextValue2(value.toString());
	}

}

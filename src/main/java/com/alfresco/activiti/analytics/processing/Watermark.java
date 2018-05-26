package com.alfresco.activiti.analytics.processing;

import com.alfresco.activiti.analytics.CustomAnalyticsEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("watermark")
public class Watermark {

    protected static final Logger logger = LoggerFactory.getLogger(Watermark.class);

    @Autowired
    CustomAnalyticsEndpoint customAnalyticsEndpoint;

    @Value("${analytics.default.fromTimestamp}")
    private String fromTimestamp;

    public String fetchWatermark() {

        String waterMark = customAnalyticsEndpoint.fetchWaterMark();
        if (waterMark != null) {
            return waterMark;
        } else {
            return fromTimestamp;
        }
    }

    public void updateWatermark(String toTimestamp) {
        customAnalyticsEndpoint.updateWaterMark(toTimestamp);
    }

}

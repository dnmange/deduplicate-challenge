package com.adobe.coding.challenge.deduplicate;

import com.google.gson.JsonObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ConsoleLeadLoggerImpl implements LeadLogger {
    public static Logger logger = LoggerFactory.getLogger(ConsoleLeadLoggerImpl.class);

    @Override
    public void log(Lead source, Lead target) {
        JsonObject sourceJson = source.getRecord();
        JsonObject targetJson = target.getRecord();

        sourceJson.entrySet()
                .forEach(sourceEntry -> {
                    if (!sourceEntry.getValue().equals(targetJson.get(sourceEntry.getKey()))) {
                        logger.info("For source id : {} and email : {} , property {} changed from : {} to : {} ",
                                source.getIdentifier(), source.getEmail(), sourceEntry.getKey(), sourceEntry.getValue(), targetJson.get(sourceEntry.getKey()));
                    }
                });
    }
}

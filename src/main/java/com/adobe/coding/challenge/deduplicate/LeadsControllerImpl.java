package com.adobe.coding.challenge.deduplicate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.PriorityQueue;

public class LeadsControllerImpl implements LeadsController {

    private final LeadLogger leadLogger;

    public LeadsControllerImpl(LeadLogger leadLogger) {
        this.leadLogger = leadLogger;
    }

    @Override
    public List<Lead> deduplicate(List<Lead> leads, String property) {
        if (property == null || property.length() == 0) {
            return leads;
        }

        LinkedHashMap<String, PriorityQueue<Lead>> groupByLeads = new LinkedHashMap<>();

        leads.forEach(lead -> {
            String propertyValue = lead.getRecord().get(property).getAsString();
            if (!groupByLeads.containsKey(propertyValue)) {
                groupByLeads.put(propertyValue, new PriorityQueue<>());
                groupByLeads.get(propertyValue).add(lead);
                return;
            }

            PriorityQueue<Lead> queue = groupByLeads.get(propertyValue);
            Lead latest = queue.peek();
            if (lead.compareTo(latest) > 1) {
                leadLogger.log(lead, latest);
            } else {
                leadLogger.log(latest, lead);
            }
            queue.add(lead);
        });

        List<Lead> result = new ArrayList<>();
        groupByLeads.forEach((k, v) -> {
            result.add(v.peek());
        });

        return result;
    }
}

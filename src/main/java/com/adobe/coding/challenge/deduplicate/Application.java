package com.adobe.coding.challenge.deduplicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("-i") || !args[2].equals("-o")) {
            System.out.println("Usage : jvm Application -i <filename> -o <filename>");
            return;
        }

        File input = new File(args[1]);
        File output = new File(args[3]);
        LeadLogger logger = new ConsoleLeadLoggerImpl();
        LeadsController leadsController = new LeadsControllerImpl(logger);

        JsonArray leadsJson = null;
        try {
            JsonObject records = JsonParser.parseReader(new FileReader(input)).getAsJsonObject();
            leadsJson = records.get("leads").getAsJsonArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Lead> leads = new ArrayList<>();
        leadsJson
                .forEach(lead -> leads.add(new Lead(lead.getAsJsonObject())));

        List<Lead> deduplicateEmails = leadsController.deduplicate(leads, "email");
        List<Lead> deduplicateIds = leadsController.deduplicate(deduplicateEmails, "_id");
        try {
            FileWriter writer = new FileWriter(output);
            writer.write("{\n");
            writer.write("\t\"leads\":" + "\n");
            writer.write("\t\t[\n");
            int count = 0;
            for (Lead lead: deduplicateIds) {
                String comma = (count++ < deduplicateIds.size() - 1) ? "," : "";
                writer.write("\t\t\t" + lead.toString() + comma);
                writer.write("\n");
            }
            writer.write("\t\t]\n");
            writer.write("}");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

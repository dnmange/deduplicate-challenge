package com.adobe.coding.challenge.deduplicate;

import com.google.gson.JsonObject;

import java.util.Objects;

public class Lead implements Comparable<Lead> {
    private String identifier;
    private String email;
    private String entryDate;

    private JsonObject record;

    public Lead(JsonObject record) {
        this.identifier = record.get("_id").getAsString();
        this.email = record.get("email").getAsString();
        this.entryDate = record.get("entryDate").getAsString();
        this.record = record.deepCopy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return getIdentifier().equals(lead.getIdentifier()) || getEmail().equals(lead.getEmail());
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public int compareTo(Lead o) {
        int cmp = o.getEntryDate().compareTo(getEntryDate());
        if (cmp == 0) {
            return -1;
        }
        return cmp;
    }

    @Override
    public String toString() {
        return record.toString();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public JsonObject getRecord() {
        return record;
    }
}

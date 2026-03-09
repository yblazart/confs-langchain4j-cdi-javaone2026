package com.example.demo2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a conference session with its registrations.
 * Stored in memory for the demo.
 */
public class ConferenceSession {

    private final String id;
    private final String title;
    private final LocalDate date;
    private final int capacity;
    private final String speaker;
    private final String description;
    private final String prerequisites;
    private final List<String> registrations = new ArrayList<>();

    public ConferenceSession(String id, String title, LocalDate date, int capacity) {
        this(id, title, date, capacity, null, null, null);
    }

    public ConferenceSession(String id, String title, LocalDate date, int capacity,
                             String speaker, String description, String prerequisites) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.capacity = capacity;
        this.speaker = speaker;
        this.description = description;
        this.prerequisites = prerequisites;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public int getCapacity() { return capacity; }
    public String getSpeaker() { return speaker; }
    public String getDescription() { return description; }
    public String getPrerequisites() { return prerequisites; }

    public int getRemainingPlaces() {
        return capacity - registrations.size();
    }

    public List<String> getRegistrations() {
        return Collections.unmodifiableList(registrations);
    }

    public boolean register(String fullName) {
        if (registrations.size() >= capacity) return false;
        if (registrations.contains(fullName)) return false;
        registrations.add(fullName);
        return true;
    }

    public boolean cancel(String fullName) {
        return registrations.remove(fullName);
    }

    public boolean isRegistered(String fullName) {
        return registrations.contains(fullName);
    }

    public String toRagDocument() {
        StringBuilder sb = new StringBuilder();
        sb.append("Session: ").append(title).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Capacity: ").append(capacity).append(" seats\n");
        if (speaker != null) sb.append("Speaker: ").append(speaker).append("\n");
        if (description != null) sb.append("Description: ").append(description).append("\n");
        if (prerequisites != null) sb.append("Prerequisites: ").append(prerequisites).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return date + " -- " + title + " (" + getRemainingPlaces() + "/" + capacity + " seats)";
    }
}

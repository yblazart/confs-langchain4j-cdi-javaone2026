package com.example.demo2;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CDI Bean providing booking tools for the AI assistant.
 * Connected to the ConferenceRepository (in-memory) for realistic results.
 */
@ApplicationScoped
public class BookingTools {

    @Inject
    ConferenceRepository repository;

    @Tool("Lists all available conference sessions with their remaining seats")
    public String listSessions() {
        List<ConferenceSession> all = repository.listAll();
        return all.stream()
                .map(s -> s.getId() + " | " + s.toString())
                .collect(Collectors.joining("\n"));
    }

    @Tool("Registers a person for a conference session. Use the identifier returned by listSessions.")
    public String register(
            @P("Identifier (e.g.: jug-feb) or partial title of the session") String sessionId,
            @P("First name of the person") String firstName,
            @P("Last name of the person") String lastName) {
        ConferenceSession session = repository.findById(sessionId);
        if (session == null) {
            session = repository.findByTitle(sessionId);
        }
        if (session == null) {
            return "Session '" + sessionId + "' not found. Use listSessions to see available sessions.";
        }
        String fullName = firstName + " " + lastName;
        if (session.isRegistered(fullName)) {
            return fullName + " is already registered for " + session.getTitle();
        }
        if (session.getRemainingPlaces() <= 0) {
            return "Sorry, the session " + session.getTitle() + " is full!";
        }
        session.register(fullName);
        return "Registration confirmed: " + fullName + " for '" + session.getTitle()
                + "'. Remaining seats: " + session.getRemainingPlaces() + "/" + session.getCapacity();
    }

    @Tool("Cancels a person's registration for a session. Use the identifier returned by listSessions.")
    public String cancelRegistration(
            @P("Identifier (e.g.: jug-feb) or partial title of the session") String sessionId,
            @P("First name of the person") String firstName,
            @P("Last name of the person") String lastName) {
        ConferenceSession session = repository.findById(sessionId);
        if (session == null) {
            session = repository.findByTitle(sessionId);
        }
        if (session == null) {
            return "Session '" + sessionId + "' not found.";
        }
        String fullName = firstName + " " + lastName;
        if (session.cancel(fullName)) {
            return "Registration cancelled for " + fullName + " from '" + session.getTitle()
                    + "'. Remaining seats: " + session.getRemainingPlaces() + "/" + session.getCapacity();
        }
        return fullName + " is not registered for " + session.getTitle();
    }

    @Tool("Returns the number of remaining seats for a session. Use the identifier returned by listSessions.")
    public String remainingPlaces(@P("Identifier (e.g.: jug-feb) or partial title of the session") String sessionId) {
        ConferenceSession session = repository.findById(sessionId);
        if (session == null) {
            session = repository.findByTitle(sessionId);
        }
        if (session == null) {
            return "Session '" + sessionId + "' not found.";
        }
        return session.getTitle() + ": " + session.getRemainingPlaces() + " remaining seats out of " + session.getCapacity();
    }

    @Tool("Lists all registrations for a person")
    public String myRegistrations(
            @P("First name of the person") String firstName,
            @P("Last name of the person") String lastName) {
        String fullName = firstName + " " + lastName;
        List<ConferenceSession> regs = repository.findRegistrations(fullName);
        if (regs.isEmpty()) {
            return fullName + " is not registered for any session.";
        }
        return fullName + " is registered for:\n" + regs.stream()
                .map(s -> "- " + s.toString())
                .collect(Collectors.joining("\n"));
    }
}

package com.example.demo2;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory repository for conference sessions.
 * Pre-populated with realistic sessions for the demo.
 */
@ApplicationScoped
public class ConferenceRepository {

    private final Map<String, ConferenceSession> sessions = new LinkedHashMap<>();

    @PostConstruct
    void init() {
        add(new ConferenceSession("jug-feb",
                "JavaOne Day 1 -- LangChain4j-CDI: AI in Jakarta EE",
                LocalDate.of(2026, 3, 17), 5,
                "Yann Blazart & Emmanuel Hugonnet",
                "Discover how to integrate LangChain4j into Jakarta EE via CDI. "
                + "On the agenda: injectable AI agents, RAG, Tools, MCP and Fault Tolerance. "
                + "Live coding on WildFly with Ollama running locally.",
                "Basic knowledge of CDI and Jakarta EE"));
        add(new ConferenceSession("jug-mar",
                "JavaOne Day 2 -- Quarkus 4.0 and Virtual Threads",
                LocalDate.of(2026, 3, 18), 30,
                "Jean Dupont",
                "Quarkus 4.0 leverages Java 21 Virtual Threads to simplify "
                + "reactive programming. Discover the new features and best practices "
                + "for migrating your applications.",
                "Java 21, basic reactive programming concepts"));
        add(new ConferenceSession("devoxx-apr",
                "JavaOne Workshop -- Generative AI and Java",
                LocalDate.of(2026, 3, 19), 200,
                "Multiple speakers",
                "3-day conference on generative AI in the Java ecosystem. "
                + "Talks, hands-on labs and keynotes on LangChain4j, Spring AI, "
                + "Quarkus LangChain4j and open source models.",
                "No specific prerequisites"));
    }

    private void add(ConferenceSession s) {
        sessions.put(s.getId(), s);
    }

    public List<ConferenceSession> listAll() {
        return new ArrayList<>(sessions.values());
    }

    public ConferenceSession findById(String id) {
        return sessions.get(id);
    }

    public ConferenceSession findByTitle(String titleFragment) {
        return sessions.values().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(titleFragment.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns all sessions a person is registered for.
     */
    public List<ConferenceSession> findRegistrations(String fullName) {
        return sessions.values().stream()
                .filter(s -> s.isRegistered(fullName))
                .collect(Collectors.toList());
    }
}

package com.example.demo2;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * In-memory repository for Las Vegas shows.
 * Loads show data from vegas-shows.json on the classpath.
 */
@ApplicationScoped
public class ShowRepository {

    private static final Logger LOG = Logger.getLogger(ShowRepository.class.getName());

    private final Map<String, Show> shows = new LinkedHashMap<>();

    @PostConstruct
    void init() {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("vegas-shows.json");
             JsonReader reader = Json.createReader(is)) {
            JsonArray array = reader.readArray();
            for (JsonObject obj : array.getValuesAs(JsonObject.class)) {
                add(new Show(
                        obj.getString("id"),
                        obj.getString("title"),
                        LocalDate.parse(obj.getString("date")),
                        obj.getInt("capacity"),
                        obj.getString("speaker", null),
                        obj.getString("description", null),
                        obj.getString("prerequisites", null)));
            }
            LOG.info("Loaded " + shows.size() + " shows from vegas-shows.json");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Failed to load vegas-shows.json", e);
        }
    }

    private void add(Show s) {
        shows.put(s.getId(), s);
    }

    public List<Show> listAll() {
        return new ArrayList<>(shows.values());
    }

    public Show findById(String id) {
        return shows.get(id);
    }

    public Show findByTitle(String titleFragment) {
        return shows.values().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(titleFragment.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns all shows a person is booked for.
     */
    public List<Show> findBookings(String fullName) {
        return shows.values().stream()
                .filter(s -> s.isRegistered(fullName))
                .collect(Collectors.toList());
    }
}

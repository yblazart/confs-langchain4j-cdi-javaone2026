package com.example.demo4;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * TODO: REST endpoint for playing 421 in the dwarf tavern.
 *
 * To complete:
 * 1. Inject the DwarfGameMaster
 * 2. Implement the play() method that calls the agent
 */
@Path("/game")
@ApplicationScoped
public class GameResource {

    // TODO: Inject the DwarfGameMaster with @Inject
    // DwarfGameMaster gameMaster;

    /**
     * TODO: Play an action in the 421 game.
     */
    @POST
    @Path("/play")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String play(String playerAction) {
        // TODO: Call gameMaster.play(playerAction)
        throw new UnsupportedOperationException("TODO: To implement during live coding");
    }

    /**
     * Enter the tavern and start a game.
     */
    @GET
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String start() {
        // TODO: Return gameMaster.play("Hello! I'm here to play 421.")
        throw new UnsupportedOperationException("TODO: To implement during live coding");
    }

    /**
     * Health check endpoint.
     */
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "The Golden Pickaxe Tavern OK - Gunther is ready for a game of 421!";
    }
}

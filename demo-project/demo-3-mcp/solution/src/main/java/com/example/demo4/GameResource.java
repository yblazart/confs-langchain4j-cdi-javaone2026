package com.example.demo4;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * REST endpoint for playing 421 in the dwarf tavern.
 *
 * This endpoint exposes the AI agent that hosts the dice game
 * and uses MCP tools to manage dice rolls.
 */
@Path("/game")
@ApplicationScoped
public class GameResource {

    @Inject
    DwarfGameMaster gameMaster;

    /**
     * Play an action in the 421 game.
     *
     * Usage example:
     * POST /api/game/play
     * Content-Type: text/plain
     *
     * Roll the dice
     *
     * The tavern keeper will:
     * 1. Roll 3d6 for the player via MCP
     * 2. Announce the combination
     * 3. Roll 3d6 for himself
     * 4. Compare and announce the winner
     */
    @POST
    @Path("/play")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String play(String playerAction) {
        return gameMaster.play(playerAction);
    }

    /**
     * Enter the tavern and start a game.
     */
    @GET
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String start() {
        return gameMaster.play("Hello! I'm here to play 421.");
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

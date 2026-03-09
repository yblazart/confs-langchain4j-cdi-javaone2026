package com.example.demo1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
// TODO STREAMING: Import for SSE
// import jakarta.ws.rs.core.Context;
// import jakarta.ws.rs.sse.Sse;
// import jakarta.ws.rs.sse.SseEventSink;
// import dev.langchain4j.service.TokenStream;

// TODO STEP 5 : Add @Inject ChatAssistant assistant;

@Path("/chat")
@ApplicationScoped
public class ChatResource {

    // TODO : Inject the AI assistant here
    // private ChatAssistant assistant;

    // TODO STREAMING: Inject ChatAssistantStreaming for streaming
    // private ChatAssistantStreaming streamingAssistant;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(String message) {
        // TODO STEP 6 : Call assistant.chat(message)
        return "TODO: wire the AI assistant here";
    }

    // TODO STREAMING: Add an SSE endpoint for streaming
    // Note: EventSource only supports GET, so we use @QueryParam
    // Important: No need for CompletableFuture.runAsync() - it causes CDI context issues
    // @GET
    // @Path("/stream")
    // @Produces(MediaType.SERVER_SENT_EVENTS)
    // public void chatStream(@QueryParam("message") String message, @Context SseEventSink sseEventSink, @Context Sse sse) {
    //     if (message == null || message.trim().isEmpty()) {
    //         try (sseEventSink) {
    //             sseEventSink.send(sse.newEvent("error", "Message parameter is required"));
    //         }
    //         return;
    //     }
    //
    //     try {
    //         TokenStream tokenStream = streamingAssistant.chatStream(message);
    //
    //         tokenStream
    //             .onNext(token -> {
    //                 sseEventSink.send(sse.newEvent("token", token));
    //             })
    //             .onComplete(response -> {
    //                 sseEventSink.send(sse.newEvent("done", ""));
    //                 sseEventSink.close();
    //             })
    //             .onError(error -> {
    //                 sseEventSink.send(sse.newEvent("error", error.getMessage()));
    //                 sseEventSink.close();
    //             })
    //             .start();
    //
    //     } catch (Exception e) {
    //         try (sseEventSink) {
    //             sseEventSink.send(sse.newEvent("error", "Error: " + e.getMessage()));
    //         }
    //     }
    // }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "OK";
    }
}

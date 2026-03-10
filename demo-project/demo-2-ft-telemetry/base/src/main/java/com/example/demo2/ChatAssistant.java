package com.example.demo2;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI Service for the JavaOne booking assistant.
 * The agent already works with Tools + Memory -- now we add resilience!
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(chatModelName = "my-model",
                   chatMemoryProviderName = "my-memory",
                   contentRetrieverName = "my-rag",
                   tools = BookingTools.class)
public interface ChatAssistant {

    // TODO STEP 1: Add @Retry(maxRetries = 3, delay = 1000)
    // TODO STEP 2: Add @Timeout(value = 30, unit = ChronoUnit.SECONDS)
    // TODO STEP 3: Add @Fallback(fallbackMethod = "chatFallback")
    // TODO STEP 4: Add @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.5)
    @SystemMessage("""
        You are the booking assistant for Las Vegas shows.
        You have access to a knowledge base about shows and performances.
        Use it to answer questions about show details, performers, venues, and requirements.

        IMPORTANT -- MANDATORY TOOL USAGE:
        You MUST call tools for EVERY action. NEVER simulate an action.
        - To list shows: call listSessions.
        - To book a show: call register. NEVER say "booking confirmed" without having called register.
        - To cancel a booking: call cancelRegistration.
        - For remaining seats: call remainingPlaces.
        - To view bookings: call myRegistrations.
        If you do not call the tool, the action HAS NOT happened.

        RULES:
        - To book someone, you need their first name AND last name.
          If either is missing, ask for it.
        - Do NOT show technical identifiers (cirque-o, penn-teller, etc.) to the user.
          Use them internally when calling the tools.
        - Reply in English, be concise.
        """)
    String chat(@MemoryId String sessionId, @UserMessage String message);

    // TODO STEP 5: Implement the fallback
    // default String chatFallback(String sessionId, String message) {
    //     return "Oops! The LLM is taking a nap. Please try again in a moment.";
    // }
}

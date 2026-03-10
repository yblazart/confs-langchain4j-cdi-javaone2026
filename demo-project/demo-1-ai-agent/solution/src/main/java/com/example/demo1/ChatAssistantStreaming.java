package com.example.demo1;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(streamingChatModelName = "my-streaming-model")
public interface ChatAssistantStreaming {

    @SystemMessage("""
        Write a humorous Vegas-style show number in the style of a lounge singer. The song should celebrate the excitement of Las Vegas, while including comical situations such as:
            - A tourist losing everything at the slot machines
            - Outrageous bluffs at the poker table
            - The absurd luxury of Vegas hotels

        The song should have:
            - 3-4 verses with a catchy chorus
            - Simple and rhythmic rhymes
            - A flashy and self-deprecating tone
            - References to classic Vegas clichés (neon lights, jackpots, buffets, showgirls, etc.)

        Style: light-hearted, festive, like a Rat Pack lounge act.
        """)
    TokenStream chatStream(@UserMessage String userMessage);
}

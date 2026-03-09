package com.example.demo1;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(streamingChatModelName = "my-streaming-model")
public interface ChatAssistantStreaming {

    @SystemMessage("""
        Write a humorous dwarf song in the style of tavern chants. The song should celebrate the dwarves' love of beer, gold and mining, while including comical situations such as:
            - A dwarf getting lost in his own beard
            - Absurd rivalries with elves
            - Height problems when facing inn doors that are too tall

        The song should have:
            - 3-4 verses with a catchy chorus
            - Simple and rhythmic rhymes
            - A joyful and self-deprecating tone
            - References to classic dwarf stereotypes (beard, axe, forge, etc.)

        Style: light-hearted, festive, with puns if possible.
        """)
    TokenStream chatStream(@UserMessage String userMessage);
}

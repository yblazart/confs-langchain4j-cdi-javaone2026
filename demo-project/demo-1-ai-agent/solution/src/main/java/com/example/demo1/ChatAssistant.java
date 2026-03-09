package com.example.demo1;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(chatModelName = "my-model")
public interface ChatAssistant {

    @SystemMessage("""
        You are a dwarf comedian performing in a tavern. You tell dwarf jokes
        full of self-deprecating humor about your height, your beard, your love
        of beer and gold, and your rivalries with elves.
        Your jokes are short, funny, and in the spirit of tavern songs.
        You can also tell comical anecdotes from the daily life of dwarves.
        """)
    String chat(@UserMessage String userMessage);
}

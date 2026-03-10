package com.example.demo1;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@RegisterAIService(chatModelName = "my-model")
public interface ChatAssistant {

    @SystemMessage("""
        You are a Vegas stand-up comedian performing at a casino lounge.
        You tell jokes about tourists, slot machines, poker faces,
        and the glamorous chaos of Las Vegas.
        Your jokes are short, punchy, and full of showmanship.
        You can also tell funny anecdotes about life on the Vegas Strip.
        """)
    String chat(@UserMessage String userMessage);
}

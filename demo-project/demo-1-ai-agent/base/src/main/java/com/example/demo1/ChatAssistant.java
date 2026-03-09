package com.example.demo1;

import dev.langchain4j.service.UserMessage;

// TODO STEP 1 : Annotate the interface with @RegisterAIService(chatModelName = "my-model")
public interface ChatAssistant {

    // TODO STEP 2 : Add @SystemMessage to define the behavior
    // Example: a dwarf comedian who tells jokes about beer, gold and elves
    String chat(@UserMessage String userMessage);
}

// TODO STEP 3 : don't forget the properties

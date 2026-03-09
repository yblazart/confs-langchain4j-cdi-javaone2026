package com.example.demo4;

// TODO: Import the necessary LangChain4j annotations
// import dev.langchain4j.cdi.spi.RegisterAIService;
// import dev.langchain4j.service.SystemMessage;
// import dev.langchain4j.service.UserMessage;

/**
 * TODO: AI agent that hosts a 421 dice game in a dwarf tavern.
 *
 * To complete:
 * 1. Annotate with @RegisterAIService(chatModelName = "ollama", toolProviderName = "mcp")
 * 2. Define the play() method with @SystemMessage and @UserMessage
 * 3. The @SystemMessage must describe the tavern keeper's role and the 421 rules
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
// TODO: Add the @RegisterAIService annotation with chatModelName and toolProviderName
public interface DwarfGameMaster {

    // TODO: Add the @SystemMessage annotation with the tavern keeper's prompt
    // Hint: Gunther Barrique-d'Or, "The Golden Pickaxe" tavern, 421 rules
    // IMPORTANT: Each roll must be displayed with the format:
    // RESULT: [X, Y, Z]
    // COMBINATION: [name]
    // He rolls with roll_multiple(count=3, sides=6)
    String play(/* TODO: Add the @UserMessage annotation */ String playerAction);
}

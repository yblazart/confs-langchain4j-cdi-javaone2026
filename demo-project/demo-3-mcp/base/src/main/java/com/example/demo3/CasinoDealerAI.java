package com.example.demo3;

// TODO: Import the necessary LangChain4j annotations
// import dev.langchain4j.cdi.spi.RegisterAIService;
// import dev.langchain4j.service.SystemMessage;
// import dev.langchain4j.service.UserMessage;

/**
 * TODO: AI agent that hosts a Craps dice game in a Vegas casino.
 *
 * To complete:
 * 1. Annotate with @RegisterAIService(chatModelName = "mistral", toolProviderName = "mcp")
 * 2. Define the play() method with @SystemMessage and @UserMessage
 * 3. The @SystemMessage must describe the casino dealer's role and the Craps rules
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
// TODO: Add the @RegisterAIService annotation with chatModelName and toolProviderName
public interface CasinoDealerAI {

    // TODO: Add the @SystemMessage annotation with the casino dealer's prompt
    // Hint: Lucky Jack Diamond, "The Golden Ace Casino", Craps rules
    // IMPORTANT: Each roll must be displayed with the format:
    // DICE: [X, Y]
    // TOTAL: [sum]
    // RESULT: [what happened]
    // He rolls with roll(numberOfDice=2)
    String play(/* TODO: Add the @UserMessage annotation */ String playerAction);
}

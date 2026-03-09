# Demo 3 - 421 Dice Game at the Tavern with MCP

Fourth demo for JavaOne: play **421** (traditional French dice game) against an AI that uses the **MCP** protocol to manage dice rolls on WildFly.

## Overview

1. A **standalone MCP server** exposes a dice rolling tool (roll_multiple for 3d6)
2. An **AI tavern keeper agent** (Gunther Barrique-d'Or) connects to this server via `McpToolProvider`
3. The agent runs a 421 game: rolls dice via MCP, evaluates combinations, and announces the winner
4. A **tavern-themed web interface** lets you play in real time

**MCP for gaming**: Your Jakarta EE agents can drive external game mechanics through standardized tools!

## Prerequisites

- **Java 21+**, **Maven 3.8+**
- **Ollama** with `ministral-3:3b`:
  ```bash
  ollama pull ministral-3:3b
  ollama serve
  ```

## What is 421?

**421** is a very popular traditional French dice game:
- Each player rolls **3 six-sided dice**
- The goal is to get the **best combination**
- **Ranking** (best to worst):
  1. **421** (4-2-1): The ultimate combination!
  2. **Three of a Kind**: 3 identical dice (6-6-6 > 5-5-5 > ... > 1-1-1)
  3. **Straight**: 3 consecutive dice (1-2-3, 2-3-4, etc.)
  4. **Pair**: 2 identical dice (6-6-X > 5-5-X > ...)
  5. **Nothing**: No combination

## Project Structure

```
demo-3-dwarf-dice/
├── pom.xml                          # Aggregator POM
├── mcp-server/                      # MCP dice rolling server (JAR)
│   └── src/main/java/com/example/mcp/
│       └── DiceServer.java          # JSON-RPC 2.0 server over stdio
├── base/                            # Base for live coding
│   ├── src/main/java/com/example/demo4/
│   │   ├── JaxRsActivator.java
│   │   ├── McpConfig.java           # TODO: CDI producer
│   │   ├── DwarfGameMaster.java     # TODO: @RegisterAIService (tavern keeper)
│   │   └── GameResource.java        # TODO: @Inject + call
│   └── src/main/webapp/
│       ├── WEB-INF/beans.xml
│       └── index.html               # Tavern UI (ready!)
└── solution/                        # Complete solution
    ├── src/main/java/com/example/demo4/
    │   ├── McpConfig.java           # Complete
    │   ├── DwarfGameMaster.java     # Complete (Gunther the tavern keeper)
    │   └── GameResource.java        # Complete
    └── src/main/webapp/
        ├── WEB-INF/beans.xml
        └── index.html               # Tavern UI
```

## Getting Started

### Option 1: Quick start with wildfly:dev (recommended for live coding)

```bash
# 1. Build the MCP dice server
cd demo-3-dwarf-dice/mcp-server
mvn clean package

# 2. Launch the app with hot reload (base or solution)
cd ../base    # or ../solution
mvn clean wildfly:dev
```

The app is available at **http://localhost:8080/demo-3/** with an immersive game UI.

### Option 2: Full build and startup with provisioned server

```bash
# 1. Build the entire project (MCP server + WAR with provisioned WildFly)
cd demo-3-dwarf-dice/solution  # or base
mvn clean install

# 2. Start the provisioned WildFly server
./target/server/bin/standalone.sh -Djboss.socket.binding.port-offset=10
```

The app is then available at **http://localhost:8090/** (port 8080 + offset 10).

**Note**: The port offset avoids conflicts if another WildFly instance is already running on port 8080.

### Verification

The UI automatically starts the adventure and displays the available MCP tools.

To test manually:
```bash
# Health check
curl http://localhost:8080/demo-3/api/game/health
# or with offset
curl http://localhost:8090/api/game/health
```

## Live Coding Walkthrough

### Step 1: Understand the MCP dice server

Explore `DiceServer.java`: JSON-RPC 2.0 over stdio, 5 exposed tools.

For 421, we mainly use:
- `roll_multiple`: Rolls multiple dice of the same type (e.g., 3d6 for 421)

### Step 2: Create the CDI producer for MCP

In `McpConfig.java`:
```java
@Produces
@ApplicationScoped
public McpToolProvider mcpToolProvider() {
    McpTransport transport = new StdioMcpTransport.Builder()
        .command(List.of("java", "-jar",
            "mcp-server/target/demo-3-mcp-dice-server.jar"))
        .build();

    return McpToolProvider.builder()
        .transport(transport)
        .build();
}
```

### Step 3: Annotate DwarfGameMaster (the tavern keeper)

```java
@RegisterAIService(chatModelName = "ollama", toolProviderName = "mcp")
public interface DwarfGameMaster {
    @SystemMessage("""
        You are Gunther Barrique-d'Or, the keeper of "The Golden Pickaxe" tavern.
        You host a game of 421, the dwarves' favorite dice game!

        RULES OF 421:
        - Each player rolls 3 six-sided dice
        - Ranking: 421 > Three of a Kind > Straight > Pair > Nothing

        YOUR TOOLS:
        - roll_multiple: Roll 3d6 with {"count": 3, "sides": 6}

        FLOW:
        1. The player says "Roll the dice"
        2. You roll 3d6 for the player with roll_multiple
        3. You announce the combination
        4. You roll 3d6 for yourself
        5. You compare and announce the winner

        STYLE: Old grumpy but friendly dwarf, uses "By my beard!"
        IMPORTANT: ALWAYS roll the dice with roll_multiple, NEVER make them up!
        """)
    String play(@UserMessage String playerAction);
}
```

### Step 4: Wire up the REST endpoint

```java
@Inject DwarfGameMaster gameMaster;

@POST @Path("/play")
public String play(String playerAction) {
    return gameMaster.play(playerAction);
}
```

### Step 5: Configure and test

Uncomment in `microprofile-config.properties`:
```properties
dev.langchain4j.cdi.plugin.my-model.class=dev.langchain4j.model.ollama.OllamaChatModel
dev.langchain4j.cdi.plugin.my-model.config.base-url=http://localhost:11434
dev.langchain4j.cdi.plugin.my-model.config.model-name=ministral-3:3b
```

Open **http://localhost:8080/demo-3/** and play:

**To roll your dice:**
- "Roll the dice"
- "My turn"
- "I roll"

**To have Gunther roll:**
- "Roll for yourself"
- "Your turn"

## Execution Flow (simplified)

```
REST endpoint -> DwarfGameMaster.play()
    -> LLM receives: "Roll the dice"
    -> LLM decides to use roll_multiple(count=3, sides=6)
    -> McpToolProvider -> JSON-RPC -> MCP Dice Server
    -> Server rolls 3d6 and returns {"rolls": [4, 2, 1], "total": 7}
    -> LLM receives the raw result
    -> LLM analyzes and identifies: "421"
    -> LLM formats the response with the REQUIRED FORMAT:
       RESULT: [4, 2, 1]
       COMBINATION: 421 - The best one!
    -> LLM adds a comment: "BY MY BEARD! A 421!"
    -> Complete response sent back to the player
```

**Key points:**
- Each roll is **visible** with the emoji format
- Results are **clear** and **separated**
- The player **controls** when to roll (no automatic rolling)
- Easy to **compare** results visually

## Interaction Examples (simplified mode)

### Example 1: Player's turn

**Player:** "Roll the dice"

**Gunther:**
```
By my beard! Here we go, let's roll!

RESULT: [4, 2, 1]
COMBINATION: 421 - The best one!

A THOUSAND CAULDRONS! A perfect 421! You have the luck of the gods!
```

### Example 2: Gunther's turn

**Player:** "Your turn"

**Gunther:**
```
My turn now!

RESULT: [6, 6, 6]
COMBINATION: Three of a Kind - sixes

Great thunder! A magnificent three of a kind! But your 421 is unbeatable.
You win this round!
```

### Example 3: Full game

**Player:** "My turn"

**Gunther:**
```
RESULT: [3, 4, 5]
COMBINATION: Straight

A nice straight! Not bad at all!
```

**Player:** "Your turn"

**Gunther:**
```
RESULT: [5, 5, 2]
COMBINATION: Pair of fives

Just a pair... Your straight is better! You win the round!
The beer's on me!
```

### Example 4: Average result

**Player:** "Roll the dice"

**Gunther:**
```
RESULT: [6, 3, 1]
COMBINATION: Nothing

Well... nothing good this time. No luck!
```

## Key Points

1. **MCP for gaming**: Game mechanics (dice, scoring, etc.) are externalized in an MCP server
2. **AI as referee**: The LLM rolls dice via MCP, evaluates combinations and announces the winner
3. **Separation of concerns**: The MCP server handles random rolls, the AI handles game logic
4. **Extensibility**: Easy to add other dice games (Yahtzee, Poker Dice, etc.)

## Troubleshooting

- **MCP server won't start**: Check that the JAR is compiled (`cd mcp-server && mvn package`)
- **Agent not responding**: Check that DwarfGameMaster is annotated and McpConfig exposes the producer with `@Named("mcp")`
- **No dice rolled**: Check WildFly logs for MCP tool calls
- **Combinations miscalculated**: The LLM may make mistakes with smaller models - use `qwen2.5:3b` or larger
- **Deploy the solution**: `cd solution && mvn clean wildfly:dev`

## Resources

- **MCP Protocol**: https://modelcontextprotocol.io
- **LangChain4j-CDI**: https://github.com/langchain4j/langchain4j-cdi
- **LangChain4j**: https://docs.langchain4j.dev
- **WildFly**: https://www.wildfly.org

## Extension Ideas

- **421 variants**: Add the re-roll rule (player can re-roll 1 or 2 dice)
- **Tournament mode**: Play multiple rounds, keep score
- **Other games**: Yahtzee, Poker Dice, 10000
- **Multiplayer**: Multiple players against Gunther
- **Betting**: Wager gold coins on each game
- **Statistics**: Track wins/losses, best combinations
- **Multiple tavern keepers**: Different characters with different play styles

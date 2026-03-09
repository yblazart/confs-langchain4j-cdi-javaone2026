# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

JavaOne 2026 talk on **LangChain4j-CDI**: integrating LangChain4j into Jakarta EE / MicroProfile via CDI.
Speakers: Yann Blazart & Emmanuel Hugonnet. License: Apache 2.0.

The repo contains **Reveal.js slides** (`slides/`) and a **multi-module Maven project** (`demo-project/`) with 3 progressive demos, each having a `base/` module (skeleton with TODOs for live coding) and `solution/` (complete reference).

**See `AGENT.md` for LangChain4j-CDI architecture details** (patterns @RegisterAIService, ChatMemoryProvider, Tools, MicroProfile config, known pitfalls).

## Stack

Java 21, Maven 3.8+, Jakarta EE 10, MicroProfile 6.1, WildFly 39 (Galleon), LangChain4j 1.11.0, LangChain4j-CDI 1.0.0, local Ollama.

## Essential Commands

```bash
# Prerequisites: Ollama
ollama pull ministral-3:3b    # demo-1, demo-3
ollama pull qwen2.5:7b        # demo-2 (tool calling + embeddings)
ollama serve

# Launch a demo (replace N and module name)
cd demo-project/demo-1-ai-agent/solution && mvn clean wildfly:dev
cd demo-project/demo-2-ft-telemetry/solution && mvn clean wildfly:dev

# Demo 3: build the MCP server first
cd demo-project/demo-3-mcp/mcp-server && mvn clean package
cd demo-project/demo-3-mcp/solution && mvn clean wildfly:dev

# Test
curl -X POST -H "Content-Type: text/plain" -d "Hello" http://localhost:8080/demo-1/api/chat
curl -X POST -H "Content-Type: text/plain" -H "X-Session-Id: test-123" -d "What sessions are available?" http://localhost:8080/demo-2/api/chat

# Slides
cd slides && python3 -m http.server 8000
```

There are no unit tests in this project. Validation is done manually via curl or the web UIs.

## Project Structure

```
slides/index.html              <- Reveal.js Presentation (all-in-one)
demo-project/
├── pom.xml                    <- Parent POM (centralized versions)
├── demo-1-ai-agent/           <- Injectable AI Agent (@RegisterAIService)
│   ├── base/                  <- Skeleton with TODOs
│   └── solution/              <- Complete reference
├── demo-2-ft-telemetry/       <- Memory + RAG + Tools + Fault Tolerance + Telemetry
│   ├── base/
│   └── solution/
└── demo-3-mcp/                <- MCP Integration (Model Context Protocol)
    ├── mcp-server/            <- Standalone MCP server (fat JAR)
    ├── base/
    └── solution/
```

## Critical Rules

- **base/solution synchronization**: shared business classes (models, repositories, tools) must be identical in `base/` and `solution/`. Only the annotations/config for live coding differ.
- **The 6 `index.html` files** (3 demos x 2 modules) must remain consistent for shared elements (style, animation, scroll).
- **Package**: `com.example.demoN` (N = 1, 2 or 3).
- **Language**: English for code, `@SystemMessage`, and UIs.
- **MicroProfile Config**: the `.config.` prefix is mandatory for builder properties (`dev.langchain4j.cdi.plugin.<name>.config.<prop>=val`). Without it, the property is silently ignored.
- Each WAR module requires a `beans.xml` in `WEB-INF/` and a `JaxRsActivator.java` with `@ApplicationPath("api")`.

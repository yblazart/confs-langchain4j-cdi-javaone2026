# LangChain4j-CDI — JavaOne 2026

## Launch the slides

```bash
cd slides
chmod +x start.sh
./start.sh
```

Or simply:
```bash
cd slides && python3 -m http.server 8000
```

Open http://localhost:8000 in the browser.

**Speaker View (iPad Sidecar)**: press `S` to open the speaker view in a new window, then drag this window to the iPad in Sidecar.

## Reveal.js Shortcuts

| Key | Action |
|-----|--------|
| `S` | Speaker view (notes) |
| `F` | Fullscreen |
| `O` / `Esc` | Overview |
| `B` / `.` | Black screen (pause) |
| `←` `→` | Navigation |

## Structure

```
slides/          → Reveal.js Presentation
  index.html     → Slides + speaker notes
  start.sh       → Launch script
demo-project/    → Maven project for IntelliJ demos
```

## Demos

Each demo has a `base/` module (skeleton with TODOs for live coding) and `solution/` (complete reference).

| Demo | Topic | Module |
|------|-------|--------|
| **Demo 1** | Injectable AI agent (`@RegisterAIService`) | `demo-project/demo-1-ai-agent/` |
| **Demo 2** | Memory + RAG + Tools + Fault Tolerance + Telemetry | `demo-project/demo-2-ft-telemetry/` |
| **Demo 3** | MCP (Model Context Protocol) — external tools | `demo-project/demo-3-mcp/` |

```bash
# Launch a demo (example: demo-2 solution)
cd demo-project/demo-2-ft-telemetry/solution && mvn clean wildfly:dev
```

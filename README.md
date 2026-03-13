# LangChain4j-CDI — JavaOne 2026

## Prerequisites

| Tool | Version | Purpose |
|------|---------|---------|
| **Java** | 21+ | Build & run demos |
| **Maven** | 3.8+ | Build system |
| **Python 3** | 3.x | Serve slides & workshop locally |
| **Ollama** | latest | Local LLM inference |

### Install Python 3

**macOS** (Homebrew):
```bash
brew install python3
```

**Linux** (Debian/Ubuntu):
```bash
sudo apt update && sudo apt install python3
```

**Windows**:

Download from https://www.python.org/downloads/ and run the installer. Make sure to check **"Add Python to PATH"** during installation.

### Install Ollama

Download from https://ollama.com and pull the required models:
```bash
ollama pull mistral-small3.1
```

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

## Workshop

A self-paced hands-on guide covering all 3 demos step by step.

```bash
cd workshop
chmod +x start.sh
./start.sh
```

Windows:
```cmd
cd workshop
start.bat
```

Open http://localhost:8001 in the browser.

## Structure

```
slides/          → Reveal.js Presentation
  index.html     → Slides + speaker notes
  start.sh       → Launch script
workshop/        → Hands-on workshop guide
  index.html     → Self-paced tutorial
  start.sh       → Launch script (macOS/Linux)
  start.bat      → Launch script (Windows)
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

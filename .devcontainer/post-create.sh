#!/usr/bin/env bash
set -e

echo "=== Pulling Ollama models (this may take a few minutes) ==="
# Wait for the Ollama service to be ready
for i in {1..30}; do
  if curl -sf http://localhost:11434/api/tags > /dev/null 2>&1; then
    break
  fi
  echo "Waiting for Ollama to start... ($i/30)"
  sleep 2
done

ollama pull ministral:3b   || echo "WARNING: Failed to pull ministral:3b"
ollama pull qwen2.5:7b     || echo "WARNING: Failed to pull qwen2.5:7b"

echo "=== Building demo project ==="
cd demo-project && mvn clean install -DskipTests -q || echo "WARNING: Maven build failed"

echo "=== Codespace ready! ==="
echo "Run demos with:  cd demo-project/demo-1-ai-agent/solution && mvn wildfly:dev"
echo "Serve slides:    cd slides && python3 -m http.server 8000"

$model = "qwen2.5:0.5b"

docker compose `
-f docker/compose.base.yml `
-f docker/compose.ai-ollama.yml up -d

docker exec ollama ollama pull $model
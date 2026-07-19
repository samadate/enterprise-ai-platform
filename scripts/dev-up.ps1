$llmmodel = "qwen2.5:0.5b"
$embeddingmodel = "all-minilm"

docker compose `
-f docker/compose.base.yml `
-f docker/compose.ai-ollama.yml up -d

docker exec ollama ollama pull $llmmodel
docker exec ollama ollama pull $embeddingmodel
Write-Host ""
Write-Host "==============================================="
Write-Host " Enterprise AI Platform - Development Startup"
Write-Host "==============================================="
Write-Host ""

$llmModel = "qwen2.5:0.5b"
$embeddingModel = "all-minilm"

Write-Host "[1/3] Starting infrastructure..."
Write-Host ""

docker compose `
-f docker/compose.base.yml `
-f docker/compose.postgres.yml `
-f docker/compose.ai-ollama.yml `
-f docker/compose.cloudbeaver.yml `
up -d

Write-Host ""
Write-Host "[2/3] Pulling Ollama models..."
Write-Host ""

docker exec ollama ollama pull $llmModel
docker exec ollama ollama pull $embeddingModel

Write-Host ""
Write-Host "

========================================================
Enterprise AI Platform Development Environment Started
========================================================

AI Services
-----------
Ollama:
http://localhost:11434

PostgreSQL Database
--------
Host      : localhost
Port      : 5432
Database  : enterprise_ai
Username  : enterprise_ai
Password  : enterprise_ai

CloudBeaver
------------
http://localhost:8978

PostgreSQL Connection Details
------------------
Host      : postgres
Port      : 5432
Database  : enterprise_ai
Username  : enterprise_ai
Password  : enterprise_ai

========================================================"
Write-Host "[3/3] Development environment is ready."
Write-Host ""
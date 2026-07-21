Write-Host ""
Write-Host "==============================================="
Write-Host " Enterprise AI Platform - Shutdown"
Write-Host "==============================================="
Write-Host ""

docker compose `
-f docker/compose.base.yml `
-f docker/compose.postgres.yml `
-f docker/compose.ai-ollama.yml `
-f docker/compose.cloudbeaver.yml `
down

Write-Host ""
Write-Host "Environment stopped successfully."
Write-Host ""
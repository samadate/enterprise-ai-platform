### To Maintain the over time evolved project rest endpoints to maintain for postman.

#### Endpoint 1 - To generate text response from LLM. 
* POST http://localhost:8080/api/v1/ai/generate
* Json Body - {
  "prompt": "Explain Kafka in one sentence."
  }

#### Endpoint 2 (Temporary for testing) - To hit embedding model and get vector response.
* POST http://localhost:11434/api/embed
* Json Body - {
  "model": "all-minilm",
  "input": "Kafka is a distributed event streaming platform."
  }


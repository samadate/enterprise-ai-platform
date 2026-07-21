package com.sam.enterpriseai.provider.vectorstore.inmemory;

import com.sam.enterpriseai.constants.VectorStoreProviders;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.provider.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ConditionalOnProperty(
        prefix = "ai.vectorstore",
        name = "provider",
        havingValue = VectorStoreProviders.IN_MEMORY
)
public class InMemoryVectorStore implements VectorStore {

    private final List<EmbeddedChunk> store = new CopyOnWriteArrayList<>();

    @Override
    public void saveAll(List<EmbeddedChunk> embeddedChunks) {
        store.addAll(embeddedChunks);
    }

    @Override
    public List<EmbeddedChunk> search(
            List<Double> queryEmbedding,
            int topK) {

        return store.stream()

                .sorted((first, second) -> Double.compare(

                        CosineSimilarity.calculate(
                                second.getEmbedding(),
                                queryEmbedding),

                        CosineSimilarity.calculate(
                                first.getEmbedding(),
                                queryEmbedding)
                ))

                .limit(topK)

                .toList();
    }

    public List<EmbeddedChunk> getAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
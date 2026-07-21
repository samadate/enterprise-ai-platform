package com.sam.enterpriseai.provider.vectorstore.inmemory;

import com.sam.enterpriseai.constants.VectorStoreProviders;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.provider.vectorstore.VectorStore;
import com.sam.enterpriseai.provider.vectorstore.inmemory.mapper.InMemoryMapper;
import com.sam.enterpriseai.provider.vectorstore.inmemory.model.InMemoryVector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ConditionalOnProperty(
        prefix = "ai.vectorstore",
        name = "provider",
        havingValue = VectorStoreProviders.IN_MEMORY
)
public class InMemoryVectorStore implements VectorStore {

    private final List<InMemoryVector> store =
            new CopyOnWriteArrayList<>();

    private final InMemoryMapper mapper;

    public InMemoryVectorStore(InMemoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void saveAll(List<EmbeddedChunk> embeddedChunks) {

        List<InMemoryVector> vectors =
                embeddedChunks.stream()
                        .map(mapper::toVector)
                        .toList();

        store.addAll(vectors);
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

                .map(mapper::toEmbeddedChunk)

                .toList();
    }
}
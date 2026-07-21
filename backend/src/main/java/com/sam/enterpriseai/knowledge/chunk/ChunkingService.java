package com.sam.enterpriseai.knowledge.chunk;

import java.util.List;

public interface ChunkingService {

    List<Chunk> chunk(String text);

}

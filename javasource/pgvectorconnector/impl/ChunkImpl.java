package pgvectorconnector.impl;

import pgvectorconnector.proxies.Chunk;

public class ChunkImpl {
	public static void validate(java.util.List<Chunk> chunkList, String humanReadableId, String vector, pgvectorconnector.proxies.ENUM_ChunkType chunkType, String key, String value ) {
		
		if (chunkList == null){
			throw new IllegalArgumentException("ChunkList is required.");
		}
		
		if (humanReadableId == null) {
			throw new IllegalArgumentException("HumanReadableID is required");
			
		}
		
		if (vector == null) {
			throw new IllegalArgumentException("Vector is required");
		}
		
		if (chunkType == null) {
			throw new IllegalArgumentException("ChunkType is required");
		}
		
		if (key == null) {
			throw new IllegalArgumentException("Key is required");
		}
		
		if (value == null && chunkType.equals(pgvectorconnector.proxies.ENUM_ChunkType.KeyValue)) {
			throw new IllegalArgumentException("Value is required for KeyValue chunk types");
		}
	}
}
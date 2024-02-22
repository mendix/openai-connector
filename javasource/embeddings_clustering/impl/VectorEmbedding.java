package embeddings_clustering.impl;

public class VectorEmbedding {
	private double[] vector;
	private Long  identifier;
	
	public double[] getVector() {
		return vector;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public VectorEmbedding(double[] vector, Long identifier) {
		super();
		this.vector = vector;
		this.identifier = identifier;
	}
}

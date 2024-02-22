package embeddings_clustering.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import com.mendix.systemwideinterfaces.core.IContext;

public class VectorEmbeddingUtils {
	
	public static VectorEmbedding calculateMean(List<VectorEmbedding> input) {
		int dimensionality = input.get(0).getVector().length;
		double[] result = new double[dimensionality];
		for (VectorEmbedding vector : input) {
			for (int i = 0; i < dimensionality; i++) {
				result[i] += vector.getVector()[i];
			}
		}
		for (int i = 0; i < dimensionality; i++) {
			result[i] = result[i] / input.size();
		}
		return new VectorEmbedding(result, null);
	}
	
	
	
	public static double calculateCosineDistance(VectorEmbedding vectorEmbeddingLeft, VectorEmbedding vectorEmbeddingRight) {
		double[] vectorLeft = vectorEmbeddingLeft.getVector();
		double[] vectorRight = vectorEmbeddingRight.getVector();
		double innerProduct = 0.0;
		for (int i = 0; i < vectorLeft.length; i++) {
		    innerProduct += vectorLeft[i] * vectorRight[i];
		}   
		return 1.0 - innerProduct ;
	}



	public static List<VectorEmbedding> getVectorEmbeddingList(List<embeddings_clustering.proxies.Embedding> EmbeddingList, IContext context) {
		int rows = EmbeddingList.size();
				
		List<VectorEmbedding> vectorList = new ArrayList<>();
		
		for (int i = 0; i< rows; i++) {
			Long GUID = EmbeddingList.get(i).getMendixObject().getId().toLong();
			String vectorString = EmbeddingList.get(i)
				.getVector(context)
				.replace("[","")
				.replace("]","");
			double[] vector = Stream.of(vectorString.split(","))
	                .mapToDouble (Double::parseDouble)
	                .toArray();
			vectorList.add(new VectorEmbedding(vector, GUID));
		}
		return vectorList;
	}
}

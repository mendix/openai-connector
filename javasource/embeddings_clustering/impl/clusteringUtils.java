package embeddings_clustering.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.mendix.systemwideinterfaces.core.IContext;



public class clusteringUtils {
	public static double[][] getEmbeddingsAsDoubles(List<embeddings_clustering.proxies.Embedding> EmbeddingList, IContext context) {
		int rows = EmbeddingList.size();
		int cols = EmbeddingList.get(0)
				.getVector(context)
				.replace("[","")
				.replace("]","")
				.split(",")
				.length;
		
		double[][] points = new double[rows][cols];
		
		for (int i = 0; i< rows; i++) {
			String vectorString = EmbeddingList.get(i)
				.getVector(context)
				.replace("[","")
				.replace("]","");
			String[] vectorElements = vectorString.split(",");
			for (int j = 0; j < cols; j++) {
				points[i][j] = Double.parseDouble(vectorElements[j]);
			}
		}
		return points;
	}
	
	public static List<VectorEmbedding> getEmbeddingsAsDoublesList(List<embeddings_clustering.proxies.Embedding> EmbeddingList, IContext context) {
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

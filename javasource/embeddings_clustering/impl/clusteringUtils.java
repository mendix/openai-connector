package embeddings_clustering.impl;


import java.util.List;

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
}

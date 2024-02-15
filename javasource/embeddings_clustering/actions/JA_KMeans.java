// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package embeddings_clustering.actions;

import java.util.LinkedList;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import embeddings_clustering.implementation.KMeans;

/**
 * k-means algorithm to identify and assign a predetermined number of clusters to a set of vectors.
 * Clustering can help discover valuable, hidden groupings within the data.
 */
public class JA_KMeans extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private java.util.List<IMendixObject> __EmbeddingList;
	private java.util.List<embeddings_clustering.proxies.Embedding> EmbeddingList;
	private java.lang.Long NumberOfClusters;

	public JA_KMeans(IContext context, java.util.List<IMendixObject> EmbeddingList, java.lang.Long NumberOfClusters)
	{
		super(context);
		this.__EmbeddingList = EmbeddingList;
		this.NumberOfClusters = NumberOfClusters;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.EmbeddingList = java.util.Optional.ofNullable(this.__EmbeddingList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__EmbeddingListElement -> embeddings_clustering.proxies.Embedding.initialize(getContext(), __EmbeddingListElement))
			.collect(java.util.stream.Collectors.toList());

		// BEGIN USER CODE
		int k = this.NumberOfClusters != null ? this.NumberOfClusters.intValue() : 3;
		int rows = EmbeddingList.size();
		int cols = this.EmbeddingList.get(0)
				.getVector(getContext())
				.replace("[","")
				.replace("]","")
				.split(",")
				.length;
		
		double[][] points = new double[rows][cols];
		
		for (int i = 0; i< rows; i++) {
			String vectorString = this.EmbeddingList.get(i)
				.getVector(getContext())
				.replace("[","")
				.replace("]","");
			String[] vectorElements = vectorString.split(",");
			for (int j = 0; j < cols; j++) {
				points[i][j] = Double.parseDouble(vectorElements[j]);
			}
		}
		
		KMeans clustering = new KMeans.Builder(k, points)
				.iterations(50)
				.pp(true)
				.epsilon(.001)
				.useEpsilon(true)
				.useL1norm(true)
				.build();   
		
        LinkedList<Integer> assignmentList = new LinkedList<Integer>();
        int[] assignments = clustering.getAssignment();
        for (int assignment : assignments) {
        	assignmentList.add(assignment);
        }
        
		java.util.List<IMendixObject> EmbeddingListToReturn = new LinkedList<IMendixObject>();
		EmbeddingList.forEach((e) -> {
			e.setCluster(getContext(), assignmentList.pop());
			EmbeddingListToReturn.add(e.getMendixObject());
			}
		);
		return EmbeddingListToReturn;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_KMeans";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}

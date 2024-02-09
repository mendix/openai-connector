// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package clusterinplayground.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import java.math.BigDecimal;
import java.util.LinkedList;
import com.jujutsu.tsne.TSne;
import com.jujutsu.tsne.FastTSne;
import com.jujutsu.tsne.TSneConfig;
import com.jujutsu.tsne.TSneConfiguration;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class JA_TSNE extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private java.util.List<IMendixObject> __EmbeddingList;
	private java.util.List<clusterinplayground.proxies.Embedding> EmbeddingList;

	public JA_TSNE(IContext context, java.util.List<IMendixObject> EmbeddingList)
	{
		super(context);
		this.__EmbeddingList = EmbeddingList;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.EmbeddingList = java.util.Optional.ofNullable(this.__EmbeddingList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__EmbeddingListElement -> clusterinplayground.proxies.Embedding.initialize(getContext(), __EmbeddingListElement))
			.collect(java.util.stream.Collectors.toList());

		// BEGIN USER CODE
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
		
		TSneConfiguration config = new TSneConfig(points, 2, 3, 1, 2000, true, 0.5, true, false);
		TSne TSne = new FastTSne();
		double[][] TSneOutput = TSne.tsne(config);
		
		java.util.List<IMendixObject> coordinatesList = new LinkedList<IMendixObject>();
		for (int i = 0; i < TSneOutput.length; i++) {
			clusterinplayground.proxies.Coordinates coordinates = new clusterinplayground.proxies.Coordinates(getContext());
			coordinates.setX(getContext(), BigDecimal.valueOf(TSneOutput[i][0]));
			coordinates.setY(getContext(), BigDecimal.valueOf(TSneOutput[i][1]));
			coordinates.setCluster(getContext(), EmbeddingList.get(i).getCluster());
			coordinatesList.add(coordinates.getMendixObject());
					
		}
		
		
		
		IMendixObject coordinates = new clusterinplayground.proxies.Coordinates(getContext()).getMendixObject();
		return coordinatesList;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_TSNE";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}

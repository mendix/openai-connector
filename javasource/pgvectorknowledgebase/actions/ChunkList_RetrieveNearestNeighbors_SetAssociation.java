// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package pgvectorknowledgebase.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import pgvectorknowledgebase.impl.ChunkUtils;
import pgvectorknowledgebase.impl.MxLogger;
import pgvectorknowledgebase.proxies.Chunk;

/**
 * Use this operation to retrieve chunks from the knowledge base and set associations to the related mendix objects (if applicable). The retrieval is based on similarity with respect to the input vector provided.  This operation returns a list of the same type of the TargetChunk input variable. The returned list is sorted on similarity.
 * Additional filtering can be done by specifying the optional input parameters:
 * -MinimumSimilarity (in the range 0-1.0): acts as a cut-off: chunks are not retrieved if they have a similarity below this value.
 * -MaxNumberOfResults: determines the max number of similar chunks that are returned.
 * -LabelList: when provided, this operation only returns chunks that are conform with all of the labels in the list.
 * 
 * The DatabaseConfiguration that is passed must contain the connection details to a PostgreSQL database server with the PgVector extension installed. This entity is typically configured at runtime or in after-startup logic.
 * By providing the KnowledgeBaseName parameter, you determine the knowledge base that was used for population earlier. 
 * The TargetChunk entity (type parameter) must be a specialization of the Chunk entity from this module. If it contains associations to (specializations of) the related mendix object for which the chunk was created, this will be set by this operation for easy processing afterwards.
 */
public class ChunkList_RetrieveNearestNeighbors_SetAssociation extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __DatabaseConfiguration;
	private pgvectorknowledgebase.proxies.DatabaseConfiguration DatabaseConfiguration;
	private java.lang.String KnowledgeBaseName;
	private IMendixObject TargetChunk;
	private java.lang.String Vector;
	private java.util.List<IMendixObject> __LabelList;
	private java.util.List<pgvectorknowledgebase.proxies.Label> LabelList;
	private java.lang.Long MaxNumberOfResults;
	private java.math.BigDecimal MinimumSimilarity;

	public ChunkList_RetrieveNearestNeighbors_SetAssociation(IContext context, IMendixObject DatabaseConfiguration, java.lang.String KnowledgeBaseName, IMendixObject TargetChunk, java.lang.String Vector, java.util.List<IMendixObject> LabelList, java.lang.Long MaxNumberOfResults, java.math.BigDecimal MinimumSimilarity)
	{
		super(context);
		this.__DatabaseConfiguration = DatabaseConfiguration;
		this.KnowledgeBaseName = KnowledgeBaseName;
		this.TargetChunk = TargetChunk;
		this.Vector = Vector;
		this.__LabelList = LabelList;
		this.MaxNumberOfResults = MaxNumberOfResults;
		this.MinimumSimilarity = MinimumSimilarity;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.DatabaseConfiguration = this.__DatabaseConfiguration == null ? null : pgvectorknowledgebase.proxies.DatabaseConfiguration.initialize(getContext(), __DatabaseConfiguration);

		this.LabelList = java.util.Optional.ofNullable(this.__LabelList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__LabelListElement -> pgvectorknowledgebase.proxies.Label.initialize(getContext(), __LabelListElement))
			.collect(java.util.stream.Collectors.toList());

		// BEGIN USER CODE
		
		try { 
			ChunkUtils.validateTargetChunk(this.TargetChunk);
			
			// call a microflow to retrieve chunks
			java.util.List<Chunk> chunkList = pgvectorknowledgebase.proxies.microflows.Microflows.chunkList_RetrieveNearestNeighbors(
					getContext(), DatabaseConfiguration, KnowledgeBaseName, Vector, MinimumSimilarity, MaxNumberOfResults, LabelList);
			
			//map to target chunks to return
			return ChunkUtils.getTargetChunkList(getContext(), chunkList, TargetChunk, LOGGER);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ChunkList_RetrieveNearestNeighbors_SetAssociation";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(ChunkList_RetrieveNearestNeighbors_SetAssociation.class);
	// END EXTRA CODE
}

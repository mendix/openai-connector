// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package pgvectorknowledgebase.actions;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import genaicommons.proxies.KnowledgeBaseChunk;
import pgvectorknowledgebase.impl.ChunkUtils;
import pgvectorknowledgebase.impl.MxLogger;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * Use this operation to retrieve chunks from the knowledge base. This operation returns a list of KnowledgeBaseChunks
 * 
 * Additional selection and filtering can be done by specifying the optional input parameters:
 * -Offset: number of records to skip (for batching purposes)
 * -MaxNumberOfResults: limit of the amount of records returned
 * -MetadataCollection: when provided, this operation only returns chunks that are conform with all of the metadata key/value pairs in the list.
 * -MxObject: This is the (original) Mendix object that the chunks in the knowledge base represent. Only chunks related to this Mendix object are retrieved.
 * 
 * Output:
 * -KnowledgeBaseChunkList: This list is the result of the retrieval.
 * 
 * The Connection entity passed must be of type PgVectorKnowledgebaseConnection. It must contain the KnowledgeBaseName string attribute filled and a DatabaseConfiguration associated with the connection details to a PostgreSQL database server with the PgVector extension installed. This DatabaseConfiguration entity is typically configured at runtime or in after-startup logic. By providing the KnowledgeBaseName on the Connection, you determine the knowledge base. 
 */
public class KnowledgeBaseChunkList_Retrieve extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __Connection;
	private genaicommons.proxies.Connection Connection;
	private IMendixObject MxObject;
	private IMendixObject __MetadataCollection;
	private genaicommons.proxies.MetadataCollection MetadataCollection;
	private java.lang.Long MaxNumberOfResults;
	private java.lang.Long Offset;

	public KnowledgeBaseChunkList_Retrieve(IContext context, IMendixObject Connection, IMendixObject MxObject, IMendixObject MetadataCollection, java.lang.Long MaxNumberOfResults, java.lang.Long Offset)
	{
		super(context);
		this.__Connection = Connection;
		this.MxObject = MxObject;
		this.__MetadataCollection = MetadataCollection;
		this.MaxNumberOfResults = MaxNumberOfResults;
		this.Offset = Offset;
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.Connection = this.__Connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), __Connection);

		this.MetadataCollection = this.__MetadataCollection == null ? null : genaicommons.proxies.MetadataCollection.initialize(getContext(), __MetadataCollection);

		// BEGIN USER CODE
		try {
			java.util.List<KnowledgeBaseChunk> chunkList = new ArrayList<>();
			if (MxObject == null) {
				LOGGER.info("No MxObject was passed, retrieve will be executed without MendixIDs specified.");
			}
			else {
				ChunkUtils.addChunkWithMxObjectID(getContext(), MxObject, chunkList);
			}
			java.util.List<KnowledgeBaseChunk> knowledgeBaseChunkList = pgvectorknowledgebase.proxies.microflows.Microflows.knowledgeBaseChunkList_Retrieve_ByMxObjectIDs( getContext(), MaxNumberOfResults, Offset, MetadataCollection, Connection, chunkList);
			java.util.List<IMendixObject> returnList = knowledgeBaseChunkList.stream().map(o -> o.getMendixObject()).collect(Collectors.toList());
			return returnList;
		} catch (Error e) {
			LOGGER.error(e, "Something went wrong while retrieving chunks from the knowledge base.");
			return null;
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
		return "KnowledgeBaseChunkList_Retrieve";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(KnowledgeBaseChunkList_Retrieve.class);
	// END EXTRA CODE
}

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
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import genaicommons.proxies.KnowledgeBaseChunk;
import pgvectorknowledgebase.impl.ChunkUtils;
import pgvectorknowledgebase.impl.MxLogger;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * Use this operation to delete existing chunks and corresponding metadata in a knowledge base based on the MxObjectID. 
 * 
 * Input:
 * - MxObjectList: This is the list of (original) Mendix objects that the chunks in the knowledge base represent. Only chunks related to these Mendix objects are to be deleted.
 * - Connection:  This is a connection object that holds the knowledge base name and database connection details. This must be of type PgVectorKnowledgeBaseConnection.
 * 
 * Output:
 * - IsSuccess: This Boolean indicates if the deletion of data in the knowledge base was successful. This can be used for custom error-handling.
 * 
 * The Connection entity passed must be of type PgVectorKnowledgebaseConnection and must contain the KnowledgeBaseName string attribute filled and a DatabaseConfiguration associated with the connection details to a PostgreSQL database server with the PgVector extension installed. This DatabaseConfiguration entity is typically configured at runtime or in after-startup logic. By providing the KnowledgeBaseName on the Connection, you determine the knowledge base from which the chunks are to be deleted.
 */
public class KnowledgeBaseChunkList_Delete_ByMxObjectList extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __Connection;
	private genaicommons.proxies.Connection Connection;
	private java.util.List<IMendixObject> MxObjectList;

	public KnowledgeBaseChunkList_Delete_ByMxObjectList(IContext context, IMendixObject Connection, java.util.List<IMendixObject> MxObjectList)
	{
		super(context);
		this.__Connection = Connection;
		this.MxObjectList = MxObjectList;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.Connection = this.__Connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), __Connection);

		// BEGIN USER CODE
		try {
			if (MxObjectList.isEmpty()) {
				LOGGER.warn("Empty list was passed, nothing was deleted.");
			}
			java.util.List<KnowledgeBaseChunk> chunkList = new ArrayList<>();
			MxObjectList.forEach(o -> ChunkUtils.addChunkWithMxObjectID(getContext(), o, chunkList));
			return pgvectorknowledgebase.proxies.microflows.Microflows.knowledgeBaseChunkList_Delete_FromKnowledgeBase(
					getContext(), chunkList, Connection);
		} catch (Error e) {
			LOGGER.error(e, "Something went wrong while deleting chunks from the knowledge base.");
			return false;
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
		return "KnowledgeBaseChunkList_Delete_ByMxObjectList";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(KnowledgeBaseChunkList_Delete_ByMxObjectList.class);
	// END EXTRA CODE
}

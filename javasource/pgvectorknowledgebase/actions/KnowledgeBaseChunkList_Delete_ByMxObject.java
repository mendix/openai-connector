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
 * Use this operation to delete existing chunks and corresponding labels in a knowledge base based on the MxObjectID. 
 * MxObject is the (original) Mendix object that the chunks in the knowledge base represent. Only chunks related to this Mendix object are to be deleted.
 * By providing the KnowledgeBaseName parameter, you determine the knowledge base.
 * The DatabaseConfiguration that is passed must contain the connection details to a PostgreSQL database server with the PgVector extension installed. This entity is typically configured at runtime or in after-startup logic.
 */
public class KnowledgeBaseChunkList_Delete_ByMxObject extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __Connection;
	private genaicommons.proxies.Connection Connection;
	private IMendixObject MxObject;

	public KnowledgeBaseChunkList_Delete_ByMxObject(IContext context, IMendixObject Connection, IMendixObject MxObject)
	{
		super(context);
		this.__Connection = Connection;
		this.MxObject = MxObject;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.Connection = this.__Connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), __Connection);

		// BEGIN USER CODE
		try {
			java.util.List<KnowledgeBaseChunk> chunkList = new ArrayList<>();
			if (MxObject == null) {
				LOGGER.warn("No MxObject was passed, nothing was deleted");
			}
			else {
				ChunkUtils.addChunkWithMxObjectID(getContext(), MxObject, chunkList);
			}
			return pgvectorknowledgebase.proxies.microflows.Microflows.knowledgeBaseChunkList_Delete_FromKnowledgeBase(getContext(), chunkList, Connection);
		} catch (Error e) {
			LOGGER.error(e.getMessage());
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
		return "KnowledgeBaseChunkList_Delete_ByMxObject";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(KnowledgeBaseChunkList_Delete_ByMxObject.class);
	// END EXTRA CODE
}

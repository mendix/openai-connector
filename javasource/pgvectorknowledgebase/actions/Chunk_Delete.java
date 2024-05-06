// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package pgvectorknowledgebase.actions;

import java.lang.System.Logger;
import java.util.ArrayList;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import communitycommons.StringUtils;
import pgvectorknowledgebase.impl.ChunkUtils;
import pgvectorknowledgebase.impl.MxLogger;
import pgvectorknowledgebase.proxies.Chunk;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Chunk_Delete extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __DatabaseConfiguration;
	private pgvectorknowledgebase.proxies.DatabaseConfiguration DatabaseConfiguration;
	private java.lang.String KnowledgeBaseName;
	private IMendixObject MxObject;

	public Chunk_Delete(IContext context, IMendixObject DatabaseConfiguration, java.lang.String KnowledgeBaseName, IMendixObject MxObject)
	{
		super(context);
		this.__DatabaseConfiguration = DatabaseConfiguration;
		this.KnowledgeBaseName = KnowledgeBaseName;
		this.MxObject = MxObject;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.DatabaseConfiguration = this.__DatabaseConfiguration == null ? null : pgvectorknowledgebase.proxies.DatabaseConfiguration.initialize(getContext(), __DatabaseConfiguration);

		// BEGIN USER CODE
		try {
			java.util.List<Chunk> chunkList = new ArrayList<>();
			if (MxObject == null) {
				LOGGER.warn("No MxObject was passed, nothing was deleted");
			}
			else {
				ChunkUtils.addChunkWithMxObjectID(getContext(), MxObject, chunkList);
			}
			return pgvectorknowledgebase.proxies.microflows.Microflows.chunkList_Delete_FromKnowledgeBase(getContext(), DatabaseConfiguration, KnowledgeBaseName, chunkList);
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
		return "Chunk_Delete";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Chunk_Create.class);
	// END EXTRA CODE
}

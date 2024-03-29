// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package pgvectorknowledgebase.actions;

import static java.util.Objects.requireNonNull;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import communitycommons.StringUtils;
import pgvectorknowledgebase.proxies.Chunk;
import pgvectorknowledgebase.impl.MxLogger;

/**
 * This action can be used for instantiating Chunk objects to create the input for the knowledge base based on your own data structure. A ChunkList must be passed to which the new Chunk object will be added.
 * Optionally, use Label_Create to construct a list of Labels for custom filtering during the retrieval.
 */
public class Chunk_Create extends CustomJavaAction<java.lang.Void>
{
	private java.util.List<IMendixObject> __ChunkList;
	private java.util.List<pgvectorknowledgebase.proxies.Chunk> ChunkList;
	private java.lang.String HumanReadableID;
	private java.lang.String Vector;
	private pgvectorknowledgebase.proxies.ENUM_ChunkType ChunkType;
	private java.lang.String Key;
	private java.lang.String Value;
	private IMendixObject MxObject;
	private java.util.List<IMendixObject> __LabelList;
	private java.util.List<pgvectorknowledgebase.proxies.Label> LabelList;

	public Chunk_Create(IContext context, java.util.List<IMendixObject> ChunkList, java.lang.String HumanReadableID, java.lang.String Vector, java.lang.String ChunkType, java.lang.String Key, java.lang.String Value, IMendixObject MxObject, java.util.List<IMendixObject> LabelList)
	{
		super(context);
		this.__ChunkList = ChunkList;
		this.HumanReadableID = HumanReadableID;
		this.Vector = Vector;
		this.ChunkType = ChunkType == null ? null : pgvectorknowledgebase.proxies.ENUM_ChunkType.valueOf(ChunkType);
		this.Key = Key;
		this.Value = Value;
		this.MxObject = MxObject;
		this.__LabelList = LabelList;
	}

	@java.lang.Override
	public java.lang.Void executeAction() throws Exception
	{
		this.ChunkList = java.util.Optional.ofNullable(this.__ChunkList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__ChunkListElement -> pgvectorknowledgebase.proxies.Chunk.initialize(getContext(), __ChunkListElement))
			.collect(java.util.stream.Collectors.toList());

		this.LabelList = java.util.Optional.ofNullable(this.__LabelList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__LabelListElement -> pgvectorknowledgebase.proxies.Label.initialize(getContext(), __LabelListElement))
			.collect(java.util.stream.Collectors.toList());

		// BEGIN USER CODE
		try {
			requireNonNull(__ChunkList, "ChunkList is required.");
			
			Chunk chunk = new Chunk(getContext());
			chunk.setChunkID(getContext(), StringUtils.randomHash());
			chunk.setHumanReadableID(getContext(), HumanReadableID);
			chunk.setVector(getContext(), Vector);
			chunk.setChunkType(getContext(), ChunkType);
			chunk.setKey(getContext(), Key.replaceAll("'", "''"));
			chunk.setValue(getContext(), ChunkType != null 
					&& ChunkType.equals(pgvectorknowledgebase.proxies.ENUM_ChunkType.KeyValue) 
					? Value.replaceAll("'", "''") : null);
			chunk.setMxObjectID(getContext(), MxObject == null ? null : String.valueOf(MxObject.getId().toLong()));
			chunk.setMxEntity(getContext(), MxObject == null ? null : MxObject.getType());
			
			LabelList.forEach(label -> label.setLabel_Chunk(getContext(), chunk));
			
			__ChunkList.add(chunk.getMendixObject());
			
			return null;
			
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
		return "Chunk_Create";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Chunk_Create.class);
	// END EXTRA CODE
}

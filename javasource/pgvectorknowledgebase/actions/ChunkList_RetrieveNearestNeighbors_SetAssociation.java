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
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import communitycommons.ORM;
import pgvectorknowledgebase.impl.MxLogger;
import pgvectorknowledgebase.proxies.Chunk;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;
import com.mendix.systemwideinterfaces.core.meta.IMetaAssociation;

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
		// verify target chunk on non-null, then non-duplicate outgoing associations
		try { 
			requireNonNull(TargetChunk, "Target Chunk must be specified");
			java.util.Set<IMetaAssociation> associations = new HashSet<IMetaAssociation>();
			java.util.List<IMetaAssociation> duplicateAssociations = 
					TargetChunk.getMetaObject().getMetaAssociationsParent().stream()
						.filter(n -> !associations.add(n))
						.collect(Collectors.toList());
			if (!duplicateAssociations.isEmpty()){
				throw new Exception("Multiple outgoing associations found");
			}
			java.util.List<IMendixObject> __ChunkList = Core.microflowCall("PgVectorKnowledgeBase.ChunkList_RetrieveNearestNeighbors")
														.withParam("Vector", this.Vector)
														.withParam("KnowledgeBaseName", this.KnowledgeBaseName)
														.withParam("DatabaseConfiguration", this.__DatabaseConfiguration)
														.withParam("LabelList", this.__LabelList)
														.withParam("MaxNumberOfResults", this.MaxNumberOfResults)
														.withParam("MinimumSimilarity", this.MinimumSimilarity)
														.execute(this.getContext());
		
			java.util.List<IMendixObject> TargetChunkList = new ArrayList<IMendixObject>();
			__ChunkList.forEach(c -> {
				IMendixObject targetChunk = Core.instantiate(this.getContext(), TargetChunk.getMetaObject().getName());
				try {
					String MxObjectID = pgvectorknowledgebase.proxies.Chunk.initialize(getContext(), c)
							.getMxObjectID(getContext());
					IMendixObject targetObject = MxObjectID == null ? null : Core.retrieveId(
							getContext(), Core.createMendixIdentifier(
									pgvectorknowledgebase.proxies.Chunk.initialize(getContext(), c)
									.getMxObjectID(getContext())
									)
							
							); 
					
					ORM.cloneObject(this.getContext(), c, targetChunk, true);
					
					java.util.List<IMetaAssociation> assocationsFiltered = associations
							.stream()
							.filter(a -> targetObject == null ? false : a.getChild().equals(targetObject.getMetaObject()))
							.collect(Collectors.toList());
					if (!assocationsFiltered.isEmpty()){
						targetChunk.setValue(getContext(), assocationsFiltered.get(0).getName(), targetObject);
					}
					
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}							
			TargetChunkList.add(targetChunk);
			
			});
		
		// per element:
		// - initialize Chunk w/ proxies
		// - use Chunk.guid to retrieve mendix object 
		// - get meta object name
		// - find matching association based on meta object name
		// - set association if found, otherwise throw warning
		
		
			return TargetChunkList;
			
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

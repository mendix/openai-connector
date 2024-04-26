package pgvectorknowledgebase.impl;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import communitycommons.ORM;
import pgvectorknowledgebase.proxies.Chunk;
import static java.util.Objects.requireNonNull;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChunkUtils {
	
	
	public static void validateTargetChunk(IMendixObject TargetChunk) throws Exception {
		// verify target chunk on non-null, subclass of chunk, 
		requireNonNull(TargetChunk, "Target Chunk must be specified");
		if (! TargetChunk.getMetaObject().isSubClassOf("PgVectorKnowledgeBase.Chunk")){
			throw new Exception("Target Chunk must be a specialization of PgVectorKnowledgeBase.Chunk");
		}		
	};




	public static java.util.List<IMendixObject> getTargetChunkList(
			java.util.List<Chunk> ChunkList, IContext context, IMendixObject TargetChunk, MxLogger LOGGER) {
		// create list to return
		java.util.List<IMendixObject> TargetChunkList = new ArrayList<IMendixObject>();
		
		// per chunk create a TargetChunk (custom specialization) 
		ChunkList.forEach(c -> {
			// - instantiate Target Chunk (custom specialization)
			IMendixObject targetChunk = Core.instantiate(context, TargetChunk.getMetaObject().getName());
			// copy values from Chunk to Target Chunk (custom specialization)
			ORM.cloneObject(context, c.getMendixObject(), targetChunk, true);
			try {
				// - retrieve mendix target object    
				String MxObjectID = c.getMxObjectID(context);
				IMendixObject targetObject = MxObjectID == null ? null : Core.retrieveId(
						context, Core.createMendixIdentifier(
								MxObjectID
								)
						); 
				
				// find matching association based on meta object name 
				Long assocationsSetCount= TargetChunk
						.getMetaObject()
						.getMetaAssociationsParent()
						.stream()
						.filter(a -> targetObject == null ? false : targetObject.getMetaObject().isSubClassOf(a.getChild())) 
						.peek(a -> targetChunk.setValue(context, a.getName(), targetObject.getId()))
						.collect(Collectors.counting());
				
				// set association if found, otherwise log a warning
				if (assocationsSetCount == 0){
					LOGGER.warn("No eligible association found for target object " + targetObject.getMetaObject().getName()
						+ " on entity " + TargetChunk.getMetaObject().getName());	
				}
				
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
		TargetChunkList.add(targetChunk);
					
		});
		return TargetChunkList;
	}
}
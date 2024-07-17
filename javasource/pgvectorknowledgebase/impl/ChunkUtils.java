package pgvectorknowledgebase.impl;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaAssociation;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;

import communitycommons.ORM;
import genaicommons.proxies.KnowledgeBaseChunk;

public class ChunkUtils {
	
	private static final MxLogger LOGGER = new MxLogger(ChunkUtils.class);

	public static void validateTargetChunk(IMetaObject TargetChunk) throws Exception {
		// verify target chunk on non-null, subclass of chunk,
		requireNonNull(TargetChunk, "Target Chunk must be specified");
		if (!TargetChunk.isSubClassOf(KnowledgeBaseChunk.entityName)) {
			throw new IllegalArgumentException(
					"Target Chunk must be a specialization of " + KnowledgeBaseChunk.entityName);
		}
	};

	public static java.util.List<IMendixObject> getTargetChunkList(IContext context,
			java.util.List<KnowledgeBaseChunk> chunkList, IMetaObject targetChunk) {
		// create list to return
		java.util.List<IMendixObject> targetChunkList = new ArrayList<IMendixObject>();

		// per chunk create a TargetChunk (custom specialization)
		chunkList.forEach(c -> {
			try {
				// - retrieve Mendix target object
				String MxObjectID = c.getMxObjectID(context);
				IMendixObject targetObject = MxObjectID == null ? null
						: Core.retrieveId(context, Core.createMendixIdentifier(MxObjectID));
				if (targetObject == null) {
					LOGGER.warn("No mxObject " + c.getMxEntity(context) + " with mxID " + c.getMxObjectID(context)
							+ " was found in the app database.");
					return;
				}
				// - instantiate Target Chunk (custom specialization)
				IMendixObject targetChunkSpecialization = Core.instantiate(context, targetChunk.getName());
				// copy values from Chunk to Target Chunk (custom specialization)
				ORM.cloneObject(context, c.getMendixObject(), targetChunkSpecialization, true);

				// find matching association based on meta object name
				Long assocationsSetCount = targetChunk.getMetaAssociationsParent().stream()
						.filter(a -> assocationMatchesTarget(a, targetObject))
						.peek(a -> setAssociationToTarget(context, targetChunkSpecialization, targetObject, a))
						.collect(Collectors.counting());
				// set association if found, otherwise log a warning
				if (assocationsSetCount == 0) {
					LOGGER.warn("No eligible association found for mxObject " + c.getMxEntity(context) + " with mxID "
							+ c.getMxObjectID(context) + " on entity " + targetChunk.getName() + ".");
				}
				targetChunkList.add(targetChunkSpecialization);

			} catch (Exception e) {
				LOGGER.error(e, "Something went wrong creating a targetChunk for a chunk with mxObjectID "
						+ c.getMxObjectID(context) + ".");
			}

		});
		return targetChunkList;
	}

	public static void addChunkWithMxObjectID(IContext context, IMendixObject MxObject,
			java.util.List<KnowledgeBaseChunk> chunkList) {
		KnowledgeBaseChunk chunk = new KnowledgeBaseChunk(context);
		chunk.setMxObjectID(context, String.valueOf(MxObject.getId().toLong()));
		chunkList.add(chunk);
	}

	private static void setAssociationToTarget(IContext context, IMendixObject chunk, IMendixObject targetObject,
			IMetaAssociation association) {
		if (targetObject != null) {
			chunk.setValue(context, association.getName(), targetObject.getId());
		}
	}

	private static boolean assocationMatchesTarget(IMetaAssociation asssociation, IMendixObject targetObject) {
		return targetObject == null ? false : targetObject.getMetaObject().isSubClassOf(asssociation.getChild());
	}

}
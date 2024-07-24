package genaicommons.impl;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;

import genaicommons.proxies.FileContent;
import genaicommons.proxies.microflows.Microflows;
import system.proxies.Image;

public class ImageGenImpl {
	
	public static IMendixObject getSingleGeneratedImage(FileContent fileContent, String type, IContext ctx) {
		IMendixObject generatedImage = createGeneratedImage(type, ctx);
		ImageGenImpl.convertToFile(generatedImage, fileContent, ctx);
		
		return generatedImage;
	}
	
	public static void validateTargetEntity(String type) {
		IMetaObject target = Core.getMetaObject(type);
		if (!target.isSubClassOf(Image.entityName)) {
			throw new IllegalArgumentException("The provided ResponseImageEntity must be a specialization of System.Image");
		}
	}
	
	private static void convertToFile(IMendixObject imageToUse, FileContent fileContent, IContext ctx) {
		Microflows.image_ConvertToFile_Single(ctx, Image.initialize(ctx, imageToUse), fileContent);
	}
	
	private static IMendixObject createGeneratedImage(String type, IContext ctx) {
		IMendixObject generatedImage = Core.instantiate(ctx, type);
		
		return generatedImage;
	}
}

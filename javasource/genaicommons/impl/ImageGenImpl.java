package genaicommons.impl;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import genaicommons.proxies.FileContent;
import genaicommons.proxies.microflows.Microflows;
import system.proxies.Image;

public class ImageGenImpl {
	
	public static IMendixObject getSingleGeneratedImage(FileContent fileContent, String type, IContext ctx) {
		IMendixObject generatedImage = createGeneratedImage(type, ctx);
		ImageGenImpl.decodeToFile(generatedImage, fileContent, ctx);
		
		return generatedImage;
	}
	
	private static void decodeToFile(IMendixObject imageToUse, FileContent fileContent, IContext ctx) {
		Microflows.image_DecodeToFile_Single(ctx, (Image) imageToUse, fileContent);
	}
	
	private static IMendixObject createGeneratedImage(String type, IContext ctx) {
		IMendixObject generatedImage = Core.instantiate(ctx, type);
		
		if (!(generatedImage instanceof system.proxies.Image)) {
			throw new IllegalArgumentException("The entity for response image creation must be a specialization of System.Image");
		}
		
		return generatedImage;
	}
}

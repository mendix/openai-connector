package genaicommons.impl;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import genaicommons.proxies.FileContent;
import genaicommons.proxies.microflows.Microflows;
import system.proxies.Image;

public class ImageGenImpl {
	
	public static void decodeToFile(IMendixObject imageToUse, FileContent fileContent, IContext ctx) {
		Microflows.image_DecodeToFile_Single(ctx, (Image) imageToUse, fileContent);
	}
	
	public static IMendixObject createGeneratedImage(String type, IContext ctx) {
		IMendixObject generatedImage = Core.instantiate(ctx, type);
		
		if (!(generatedImage instanceof system.proxies.Image)) {
			throw new IllegalArgumentException("The entity for response image creation must be a specialization of System.Image");
		}
		
		return generatedImage;
	}
}

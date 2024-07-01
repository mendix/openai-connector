package genaicommons.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import genaicommons.proxies.FileCollection;
import genaicommons.proxies.FileContent;
import genaicommons.proxies.Message;
import genaicommons.proxies.Response;

public class FileContentImpl {
	
	public static final String DECODE_TO_FILE_MF = "genaicommons.image_decodetofile_single";
	public static final String IMAGE_PARAM_NAME = "ImageToUse";
	public static final String FILECONTENT_PARAM_NAME = "FileContent";
	
	public static List<FileContent> getFileContentList(Response response) throws CoreException {
		
		Message responseMessage = response.getResponse_Message();
		
		FileCollection fileCollection = responseMessage.getMessage_FileCollection();
		
		List<FileContent> fileContentList = fileCollection.getFileCollection_FileContent();
		
		return fileContentList;
	}
	
	public static void decodeToFile(IMendixObject imageToUse, FileContent fileContent, IContext ctx) {
		
		Map<String, Object> paramsMap = getParamsMap(imageToUse, fileContent);
		
		Core.microflowCall(DECODE_TO_FILE_MF).withParams(paramsMap).execute(ctx);
	}
	
	private static Map<String, Object> getParamsMap(IMendixObject imageToUse, FileContent fileContent) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put(IMAGE_PARAM_NAME, imageToUse);
		paramsMap.put(FILECONTENT_PARAM_NAME, fileContent);
		
		return paramsMap;
	}

}

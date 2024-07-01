package genaicommons.impl;

import java.util.List;

import com.mendix.core.CoreException;

import genaicommons.proxies.FileContent;
import genaicommons.proxies.Response;

public class FileContentImpl {
	
	public static List<FileContent> getFileContentList(Response response) throws CoreException {
		
		return response.getResponse_Message().getMessage_FileCollection().getFileCollection_FileContent();
	}
}

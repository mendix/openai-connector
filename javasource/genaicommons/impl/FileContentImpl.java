package genaicommons.impl;

import java.util.List;

import com.mendix.core.CoreException;

import genaicommons.proxies.FileCollection;
import genaicommons.proxies.FileContent;
import genaicommons.proxies.Message;
import genaicommons.proxies.Response;

public class FileContentImpl {
	
	public static List<FileContent> getFileContentList(Response response) throws CoreException {
		
		Message message = response.getResponse_Message();
		if (message == null) {
			throw new IllegalArgumentException("The Response is not associated to a Message object.");
		}
		
		FileCollection fileCollection = message.getMessage_FileCollection();
		if (fileCollection == null) {
			throw new IllegalArgumentException("The returned Message object is not associated to a FileCollection object.");
		}
		
		List<FileContent> fileContentList = fileCollection.getFileCollection_FileContent();
		if (fileContentList.size() == 0) {
			throw new IllegalArgumentException("The Response does not contain any FileContent objects.");
		}
		
		return fileContentList;

	}
}

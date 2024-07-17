// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package genaicommons.actions;

import java.util.List;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import genaicommons.impl.FileContentImpl;
import genaicommons.impl.ImageGenImpl;
import genaicommons.impl.MxLogger;
import genaicommons.proxies.FileContent;
import static java.util.Objects.requireNonNull;

/**
 * This operations processes a response that was created by an image generation operation. A return entity can be specified using ResponseImageEntity (needs to be of type image or its specialization). An image of that type will be created and returned.
 */
public class Response_GetSingleResponseImage extends CustomJavaAction<IMendixObject>
{
	private java.lang.String ResponseImageEntity;
	private IMendixObject __Response;
	private genaicommons.proxies.Response Response;

	public Response_GetSingleResponseImage(IContext context, java.lang.String ResponseImageEntity, IMendixObject Response)
	{
		super(context);
		this.ResponseImageEntity = ResponseImageEntity;
		this.__Response = Response;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		this.Response = this.__Response == null ? null : genaicommons.proxies.Response.initialize(getContext(), __Response);

		// BEGIN USER CODE
		try {
			requireNonNull(Response, "Response is required.");
			requireNonNull(ResponseImageEntity, "ResponseImageEntity is required");
			ImageGenImpl.validateTargetEntity(ResponseImageEntity);
			
			List<FileContent> fileContentList = FileContentImpl.getFileContentList(Response);
			FileContent fileContent = fileContentList.get(0);
			IMendixObject generatedImage = ImageGenImpl.getSingleGeneratedImage(fileContent, ResponseImageEntity, getContext());
			
			return generatedImage;
			
		} catch (Exception e) {
			LOGGER.error("An error ocurred while creating the single response image: " + e.getMessage());
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
		return "Response_GetSingleResponseImage";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Response_GetSingleResponseImage.class);
	
	
	// END EXTRA CODE
}

package amazonbedrockconnector.impl;

import java.util.Date;
import java.util.List;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.proxies.ENUM_IngestionJobStatus;
import amazonbedrockconnector.proxies.FailureReason;
import amazonbedrockconnector.proxies.GetIngestionJob;
import amazonbedrockconnector.proxies.GetIngestionJobResponse;
import amazonbedrockconnector.proxies.IngestionJob;
import amazonbedrockconnector.proxies.IngestionJobStats;
import amazonbedrockconnector.proxies.StartIngestionJob;
import amazonbedrockconnector.proxies.StartIngestionJobResponse;
import software.amazon.awssdk.services.bedrockagent.model.IngestionJobStatistics;
import software.amazon.awssdk.services.bedrockagent.model.IngestionJobStatus;

public class MxIngestionJob {
	private static final MxLogger LOGGER = new MxLogger(MxIngestionJob.class);
	
    // GetIngestionJobResponse entry point
	public static GetIngestionJobResponse getMxResponse(software.amazon.awssdk.services.bedrockagent.model.GetIngestionJobResponse awsResponse, IContext context) {
		var mxResponse = new GetIngestionJobResponse(context);
		GetIngestionJob getIngestionJob = new GetIngestionJob(context);
        convertAwsIngestionJobToMx(context, awsResponse.ingestionJob(), IngestionJob.initialize(context, getIngestionJob.getMendixObject()));
	
		mxResponse.setGetIngestionJob_GetIngestionJobResponse(getIngestionJob);
		return mxResponse;
	}

    // StartIngestionJobResponse entry point
	public static StartIngestionJobResponse getMxResponse(software.amazon.awssdk.services.bedrockagent.model.StartIngestionJobResponse awsResponse, IContext context) {
		var mxResponse = new StartIngestionJobResponse(context);
		StartIngestionJob startIngestionJob = new StartIngestionJob(context);
        convertAwsIngestionJobToMx(context, awsResponse.ingestionJob(), IngestionJob.initialize(context, startIngestionJob.getMendixObject()));

		mxResponse.setStartIngestionJob_StartIngestionJobResponse(startIngestionJob);
		return mxResponse;
	} 
    
	private static ENUM_IngestionJobStatus getENUMStatus(IngestionJobStatus status){
		switch (status) {
			case COMPLETE:
				return ENUM_IngestionJobStatus.COMPLETE;
			case FAILED:
				return ENUM_IngestionJobStatus.FAILED;
			case IN_PROGRESS:
				return ENUM_IngestionJobStatus.IN_PROGRESS;
			case STARTING:
				return ENUM_IngestionJobStatus.STARTING;
			default:
				LOGGER.warn("The ENUM value \"" + status.toString() + "\" could not be found for ENUM_IngestionJobStatus");
				return null;
		}
	}

    private static void convertAwsIngestionJobToMx(IContext context, software.amazon.awssdk.services.bedrockagent.model.IngestionJob awsIngestionJob, IngestionJob mxIngestionJob){
		// IngestionJob object
		mxIngestionJob.setDescription(awsIngestionJob.description());
		mxIngestionJob.setKnowledgeBaseId(awsIngestionJob.knowledgeBaseId());
		mxIngestionJob.setDataSourceId(awsIngestionJob.dataSourceId());
		mxIngestionJob.setIngestionJobId(awsIngestionJob.ingestionJobId());
		mxIngestionJob.setStatus(getENUMStatus(awsIngestionJob.status()));
		mxIngestionJob.setStartedAt(Date.from(awsIngestionJob.startedAt()));
		mxIngestionJob.setUpdatedAt(Date.from(awsIngestionJob.updatedAt()));

		// List of Failure Reasons explaining why the call failed
		List<String> awsFailureReasons = awsIngestionJob.failureReasons();
		for (String awsFailureReason : awsFailureReasons) {
			FailureReason mxFailureReason = new FailureReason(context);
			mxFailureReason.setText(awsFailureReason);
			mxFailureReason.setFailureReason_IngestionJob(mxIngestionJob);
		}

		// Statistics object
		IngestionJobStatistics statistics = awsIngestionJob.statistics();
		IngestionJobStats mxIngestionJobStatistics = new IngestionJobStats(context);
		mxIngestionJobStatistics.setnumberOfDocumentsDeleted(statistics.numberOfDocumentsDeleted());
		mxIngestionJobStatistics.setnumberOfDocumentsFailed(statistics.numberOfDocumentsFailed());
		mxIngestionJobStatistics.setnumberOfDocumentsScanned(statistics.numberOfDocumentsScanned());
		mxIngestionJobStatistics.setnumberOfModifiedDocumentsIndexed(statistics.numberOfModifiedDocumentsIndexed());
		mxIngestionJobStatistics.setnumberOfNewDocumentsIndexed(statistics.numberOfNewDocumentsIndexed());
		mxIngestionJobStatistics.setIngestionJobStats_IngestionJob(mxIngestionJob);
    }
}

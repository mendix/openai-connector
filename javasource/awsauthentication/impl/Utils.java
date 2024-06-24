package awsauthentication.impl;

import static java.util.Objects.nonNull;

import com.mendix.thirdparty.org.json.JSONArray;
import com.mendix.thirdparty.org.json.JSONObject;

import awsauthentication.proxies.ENUM_Region;
import software.amazon.awssdk.regions.Region;

public class Utils {
	
	public static final String ALGORITHM_RSA = "RSA";
	public static final String ALGORITHM_EC = "EC";
	
	public static Region convertAWSRegion(ENUM_Region region) {

		Region awsRegion = Region.of(region.toString().replace("_", "-"));
		return awsRegion;
	}
	
	public static JSONObject getClientCertificateDetails(String certId) {
		String jsonString = System.getenv("CLIENT_CERTIFICATES");
		if(nonNull(jsonString) && !jsonString.isBlank()) {
			JSONArray certArray = new JSONArray(jsonString);
			for(int i=0; i<certArray.length(); i++) {
				JSONObject certObj = certArray.getJSONObject(i);
				JSONArray pinArray = certObj.getJSONArray("pin_to");
				for(int j=0; j<pinArray.length(); j++) {
					String key = pinArray.getString(j);
					if(key.equalsIgnoreCase(certId)) {
						return certObj;
					}
				}
			}
		}
		
		return null;
	}

	public static String getAlgorithmName(String keyAlgorithmType) {
		String algoName = null;
		if(ALGORITHM_RSA.equals(keyAlgorithmType)) {
			algoName = "SHA256withRSA";
		}else if(ALGORITHM_EC.equals(keyAlgorithmType)) {
			algoName = "SHA256withECDSA";
		}else {
			throw new RuntimeException("Encryption Algorithm not supported :: "+keyAlgorithmType);
		}
		return algoName;
	}

	public static String getAlgorithmHeaderString(String keyAlgorithmType) {
		String algoHeader = null;
		if(ALGORITHM_RSA.equals(keyAlgorithmType)) {
			algoHeader = "AWS4-X509-RSA-SHA256";
		}else if(ALGORITHM_EC.equals(keyAlgorithmType)) {
			algoHeader = "AWS4-X509-ECDSA-SHA256";
		}else {
			throw new RuntimeException("Encryption Algorithm not supported :: "+keyAlgorithmType);
		}
		return algoHeader;
	}

}

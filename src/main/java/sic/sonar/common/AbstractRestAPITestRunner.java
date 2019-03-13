package sic.sonar.common;


import org.json.simple.JSONObject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractRestAPITestRunner {
//	나중에 쓰게 될지? 티몬 Bearer 정보 - Authorization: Bearer e9e4b31c-a0c1-4aeb-84aa-5203dc29faf9
//	curl -X POST \
//	http://interworkapi-qa.tmon.co.kr/api/qnas/59121357875/replies \
//	-H 'Authorization: Bearer e9e4b31c-a0c1-4aeb-84aa-5203dc29faf9' \
//	-H 'Cache-Control: no-cache' \
//	-H 'Content-Type: application/json' \
//	-H 'Postman-Token: e6e21e96-1238-4c01-82c1-497647124ccd' \
//	-H 'X-Partner-Id: interworkOmPartner' \

	public static final int TESTMODE_LOCAL = 0;
	public static final int TESTMODE_DEVSERVER = 1;
	public static final int TESTMODE_TESTSERVER = 2;
	public static final int TESTMODE_PRODSERVER = 3;

	private static int testMode = TESTMODE_DEVSERVER;
	private static boolean isSetTestMode = false;
	
	public void readTestMode(){
			System.out.println("Checking System variable(TEST_MODE) to define the restapi test's request base url. ");
			String testEnvVar = System.getProperty("TEST_MODE");
			if (testEnvVar == null || "".equals(testEnvVar)) {
				System.out.println(
						"Please add the system variable TEST_MODE among these values [ LOCAL / DEV / TEST / PROD ]");
				throw new RuntimeException(
						"Please add the system variable TEST_MODE among these values [ LOCAL / DEV / TEST / PROD ]");
			}
	
			if ("local".equalsIgnoreCase(testEnvVar)) {
				testMode = TESTMODE_LOCAL;
			} else if ("dev".equalsIgnoreCase(testEnvVar)) {
				testMode = TESTMODE_DEVSERVER;
			} else if ("test".equalsIgnoreCase(testEnvVar)) {
				testMode = TESTMODE_TESTSERVER;
			} else if ("qa".equalsIgnoreCase(testEnvVar)) {
				testMode = TESTMODE_TESTSERVER;
			} else if ("prod".equalsIgnoreCase(testEnvVar)) {
				testMode = TESTMODE_PRODSERVER;
			} else {
				System.out.println(
						"Not Defined target server value. It should be the one of [ LOCAL / DEV / TEST / PROD ] but was "
								+ testEnvVar);
				throw new RuntimeException(
						"Not Defined target server value. It should be the one of [ LOCAL / DEV / TEST / PROD ] but was "
								+ testEnvVar);
			}
//		}
		isSetTestMode = true;
	}

	public int getTestMode() {
		if(isSetTestMode == false){
			readTestMode();
		}
		return testMode;
	}

	public void setTestMode(int testMode) {
		AbstractRestAPITestRunner.testMode = testMode;
	}
	
	public RequestSpecification getDefaultRequestSpec() {
		return APITestUtils.getDefaultRequestSpec();
	}
	
}

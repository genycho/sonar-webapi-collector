package sic.sonar.service;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sic.sonar.common.APITestUtils;
import sic.sonar.common.MySonarUtil;
import sic.sonar.common.SICServiceException;
import sic.sonar.model.TargetInfoVO;

public class SonarWebAPIService {
	//TODO
	String id = "si_choung";
	String pw = "Test12#$";
	
//	public static final String SONAR_BASE_URL_SONAR2= "http://sonarqube2.tmonc.net";
//	public static final String SONAR_BASE_URL_SONAR3= "http://sonarqube3.tmonc.net";
	
	private final String basePath = "/sonar";
	private final String validateAPIPath = "/api/authentication/validate";
//	private final String measureAPIPath = "/api/measures/component_tree";
	private final String measureAPIPath = "/api/measures/component";
	private final String analysisDateAPIPath = "/api/project_analyses/search";
	
	
	private RequestSpecification getDefaultRequestSpec() throws SICServiceException {
		return getDefaultRequestSpec(id, pw);
	}
	
	private RequestSpecification getDefaultRequestSpec(String id, String pw) throws SICServiceException {
		RequestSpecBuilder builder = new RequestSpecBuilder();
//		builder.addHeader("Accept", "application/json");
//		builder.addHeader("Content-Type", "application/json;charset=UTF-8");
//		builder.addHeader("Referer", "http://dealinfoapi.qa.tmon.co.kr/api-helper/node/apis");
		builder.addHeader("Authorization", "Basic "+ getHashValueForIDPW(id, pw));

		RequestSpecification requestSpec = builder.build();
		
		return requestSpec;
	}
	
	protected String getHashValueForIDPW(String id, String pw) throws SICServiceException {
		try {
			return APITestUtils.encodeWithBase64(id+":"+pw);
		} catch (UnsupportedEncodingException e) {
			throw new SICServiceException("Errors while encoding id and pw", e);
		}
	}
	
	/**
	 * http://sonarqube2.tmonc.net/sonar/web_api/api/authentication
	 * @return
	 * @throws SICServiceException
	 */
	@Deprecated //안 쓰임
	public boolean validateCheck(String baseURL) throws SICServiceException {
		RestAssured.baseURI = baseURL;
		RestAssured.basePath = basePath;
		
		RequestSpecification requestSpec = getDefaultRequestSpec();
		requestSpec.log().ifValidationFails();
		
		Response response = 
				RestAssured
					.given().spec(requestSpec)
					.expect()
						.statusCode(200)
						.log().ifValidationFails()
					.when()
				.get(this.validateAPIPath).andReturn();

		JsonPath jsonResponse = new JsonPath(response.asString());
		return jsonResponse.getBoolean(("valid"));
	}
	
	/**
	 * http://sonarqube2.tmonc.net/sonar/web_api/api/project_analyses
	 * @param componentKey
	 * @return
	 * @throws SICServiceException
	 */
	public String getLastAnalysisDate(String baseURL, String componentKey) throws SICServiceException {
		RestAssured.baseURI = baseURL;
		RestAssured.basePath = basePath;
		
		RequestSpecification requestSpec = getDefaultRequestSpec();
		requestSpec.queryParam("project", componentKey);
		requestSpec.queryParam("category", "VERSION");
		requestSpec.log().ifValidationFails();
		
		Response response = 
				RestAssured
					.given().spec(requestSpec)
					.expect()
						.statusCode(200)
						.log().ifValidationFails()
					.when()
				.get(this.analysisDateAPIPath).andReturn();

		JsonPath jsonResponse = new JsonPath(response.asString());
		return jsonResponse.getString("analyses[0].date");
	}
	
	/**
	 * http://sonarqube2.tmonc.net/sonar/web_api/api/measures
	 * @param componentKey
	 * @return
	 * @throws SICServiceException
	 */
	public TargetInfoVO getSingleComponentInfo(String baseURL, String componentKey) throws SICServiceException {
		RestAssured.baseURI = baseURL;
		RestAssured.basePath = basePath;
		
		RequestSpecification requestSpec = getDefaultRequestSpec();
		requestSpec.queryParam("additionalFields", "periods");
		requestSpec.queryParam("componentKey", componentKey);
		requestSpec.queryParam("metricKeys", "coverage,branch_coverage,line_coverage,tests,test_errors,test_failures");
		requestSpec.log().ifValidationFails();
		
		Response response = 
				RestAssured
					.given().spec(requestSpec)
					.expect()
						.statusCode(200)
						.log().ifValidationFails()
					.when()
				.get(this.measureAPIPath).andReturn();

		JsonPath jsonResponse = new JsonPath(response.asString());
		TargetInfoVO targetInfoVO = null;
		if(jsonResponse !=null) {
			targetInfoVO = new TargetInfoVO();
			//line_coverage, tests, test_errors, test_failures, coverage, branch_coverage
			targetInfoVO.setComponentKey(componentKey);
			for(int i=0; i<jsonResponse.getInt("component.measures.size()"); i++) {
				String measureKey = jsonResponse.getString("component.measures["+i+"].metric");
				if("line_coverage".equals(measureKey)){
					targetInfoVO.setStingLineCoverage(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else if("tests".equals(measureKey)){
					targetInfoVO.setStingTests(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else if("test_errors".equals(measureKey)){
					targetInfoVO.setStingTestErrors(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else if("test_failures".equals(measureKey)){
					targetInfoVO.setStingTestFails(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else if("coverage".equals(measureKey)){
					targetInfoVO.setStingCoverage(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else if("branch_coverage".equals(measureKey)){
					targetInfoVO.setStingBranchCoverage(String.valueOf(jsonResponse.getString("component.measures["+i+"].value")));
				}else {
						//do nothing
				}
			}
			targetInfoVO.setPeriodInfo(jsonResponse.getString("periods[0].date"));
		}
		return targetInfoVO;
	}
	
	public List<TargetInfoVO> getMultipleComponentInfo(List<String> componentKeyList) throws SICServiceException {
		List<TargetInfoVO> targetInfoList = new ArrayList<TargetInfoVO>();
		String baseURL = null;
		for(String componentKey : componentKeyList) {
			try {
				baseURL = MySonarUtil.getSonarServerURL(componentKey);
				TargetInfoVO targetInfoVO = getSingleComponentInfo(baseURL, componentKey);
				if(targetInfoVO !=null) {
					targetInfoVO.setLastAnalysisDate(getLastAnalysisDate(baseURL, componentKey));
					targetInfoList.add(targetInfoVO);
				}
			}catch(Exception skipThink) {
				System.out.println("임시. 에러"+componentKey+skipThink.getMessage());
			}
		}
		return targetInfoList;
	}

}

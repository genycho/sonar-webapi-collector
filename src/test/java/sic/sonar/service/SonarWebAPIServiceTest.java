package sic.sonar.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sic.sonar.common.MySonarUtil;
import sic.sonar.model.TargetInfoVO;

public class SonarWebAPIServiceTest {
	SonarWebAPIService sonarWebAPIService;
	
	@Before
	public void setUp() throws Exception {
		sonarWebAPIService = new SonarWebAPIService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetHashValueForIDPW() throws Exception {
		assertEquals("c2lfY2hvdW5nOlRlc3QxMiMk", sonarWebAPIService.getHashValueForIDPW("si_choung", "Test12#$"));
	}

//	@Test
//	public void testValidateCheck() throws Exception {
//		boolean result = sonarWebAPIService.validateCheck();
//		assertTrue(result);
//	}
	
	@Test
	public void testGetSingleComponentInfo() throws Exception {
		String baseURL = MySonarUtil.getSonarServerURL("service_tmon_deal_detail_api");
		TargetInfoVO targetInfoVO = sonarWebAPIService.getSingleComponentInfo(baseURL, "service_tmon_deal_detail_api");
		assertNotNull(targetInfoVO);
	}
	
	@Test
	public void testGetLastAnalysisDate() throws Exception{
		String baseURL = MySonarUtil.getSonarServerURL("service_tmon_deal_detail_api");
		String lastAnalysisDate = sonarWebAPIService.getLastAnalysisDate(baseURL, "service_tmon_deal_detail_api");
		assertNotNull(lastAnalysisDate);
	}
	
	@Test
	public void testGetMultipleComponentInfo() throws Exception{
		List<String> targetList = new ArrayList<String>();
		targetList.add("service_tmon_tab_api");
		targetList.add("service_tmon_deal_detail_api");
		targetList.add("service_tmon_board_api");
		
		List<TargetInfoVO> resultList = sonarWebAPIService.getMultipleComponentInfo(targetList);
		assertNotNull(resultList);
		assertEquals(3, resultList.size());
	}
	

}

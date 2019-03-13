package sic.sonar.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sic.sonar.common.TestUtils;
import sic.sonar.model.TargetInfoVO;
import sic.sonar.service.ExcelPOIService;

public class ExcelPOIServiceTest {
	String filePath = TestUtils.getInstance().getCurrentPath()+"/test_ext_resource/QE_커버리지현황_자동화검증용.xlsx";
	String outputFilePath = TestUtils.getInstance().getCurrentPath()+"/test_ext_resource/QE_커버리지현황_자동화검증용_tmp.xlsx";
	
	ExcelPOIService excelPOIService;
			
	@Before
	public void setUp() throws Exception {
		excelPOIService = new ExcelPOIService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRead() throws Exception {
		List<String> result = excelPOIService.readXLSX(filePath);
		assertNotNull(result);
		assertEquals(46, result.size());
		assertEquals("service_tmon_media_api",result.get(0));
	}
	
	@Test
	public void testWrite() throws Exception {
		List<TargetInfoVO> targetInfoList = new ArrayList<TargetInfoVO>();
		targetInfoList.add(getTestVO("test001", "10.%", "15.0%"));
		targetInfoList.add(getTestVO("test002", "5.0%", "3.0%"));
		targetInfoList.add(getTestVO("test003", "2.0%", "1.0%"));
		
		int writtenCount = excelPOIService.modifyXLSX(filePath, outputFilePath, targetInfoList);
		assertEquals(3, writtenCount);
	}
	
	private TargetInfoVO getTestVO(String componentKey, String coverage, String conditionCoverage) {
		TargetInfoVO targetInfoVO = new TargetInfoVO();
		targetInfoVO.setComponentKey(componentKey);
		targetInfoVO.setStingBranchCoverage(conditionCoverage);
		targetInfoVO.setStingCoverage(coverage);
		targetInfoVO.setStingLineCoverage("33.1%");
		targetInfoVO.setStingTestErrors("2");
		targetInfoVO.setStingTestFails("5");
		targetInfoVO.setStingTests("10");
		return targetInfoVO;
	}
	

}

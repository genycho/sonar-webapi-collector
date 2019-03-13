package sic.sonar.controller;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sic.sonar.common.TestUtils;

public class SonarAutoJobControllerTest {
	String filePath = TestUtils.getInstance().getCurrentPath()+"/test_ext_resource/QE_커버리지현황_자동화검증용.xlsx";
	String outputFilePath = TestUtils.getInstance().getCurrentPath()+"/test_ext_resource";
	String outputFilePathAndName = TestUtils.getInstance().getCurrentPath()+"/test_ext_resource/QE_커버리지현황_자동화검증용_tmp.xlsx";
	
	SonarAutoJobController sonarAutoJobController;

	@Before
	public void setUp() throws Exception {
//		if( (new File(outputFilePath)).exists()){
//			(new File(outputFilePath)).delete();
//		}
		sonarAutoJobController = new SonarAutoJobController();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSonarProcessNewOutFile() throws Exception {
		sonarAutoJobController.sonarProcessNewOutFile(filePath, outputFilePath);
		
	}
	
	@Test
	public void testSonarProcess() throws Exception {
		sonarAutoJobController.sonarProcessNewOutFile(filePath, outputFilePathAndName);
		
		assertTrue((new File(outputFilePath)).exists());
	}
	
	@Test
	public void testTempAFILE() throws Exception{
		File aFile = new File(filePath);
		System.out.println(aFile.getAbsolutePath());
		
	}
}

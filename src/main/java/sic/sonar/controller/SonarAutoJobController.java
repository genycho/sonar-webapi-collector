package sic.sonar.controller;

import java.io.File;
import java.util.List;

import sic.sonar.common.SICRuntimeException;
import sic.sonar.common.SICServiceException;
import sic.sonar.common.TestUtils;
import sic.sonar.model.TargetInfoVO;
import sic.sonar.service.ExcelPOIService;
import sic.sonar.service.SonarWebAPIService;

public class SonarAutoJobController {
	
	ExcelPOIService excelPOIService = new ExcelPOIService();
	SonarWebAPIService sonarWebAPIService = new SonarWebAPIService();
	
	public void sonarProcessNewOutFile(String targetExcelFilePath, String outputFilePath) {
		File aFile = new File(targetExcelFilePath);
		StringBuffer outputFilePathAndName = new StringBuffer();
		outputFilePathAndName.append(outputFilePath);
		
		if(!aFile.exists()) {
			throw new SICRuntimeException("The input file does not exist! - " + aFile.getAbsolutePath());
		}
		outputFilePathAndName.append("/");
		outputFilePathAndName.append(aFile.getName());
		outputFilePathAndName.append(TestUtils.getUniqueString());
		outputFilePathAndName.append(".xlsx");
		sonarProcess(targetExcelFilePath,outputFilePathAndName.toString());
	}

	
	public void sonarProcess(String targetExcelFilePath, String outputFilePathAndName) {
		List<String> targetComponentKeyList = null;
		List<TargetInfoVO> sonarInfoList = null;
		try {
			targetComponentKeyList = excelPOIService.readXLSX(targetExcelFilePath);
		} catch (SICServiceException e) {
			new SICRuntimeException("입력 엑셀 파일을 읽는 과정에서 오류가 발생했습니다 - "+e.getMessage(), e);
		}
		
		if(targetComponentKeyList == null || targetComponentKeyList.size()<=0) {
			throw new SICRuntimeException("입력 요청파일에서 가져온 컴포넌트키가 존재하지 않습니다 - 엑셀파일(절대경로) 및 파일 내 컬럼 위치(col index::7)을 확인해 주세요 - file:: "+ targetExcelFilePath);
		}
		
		try {
			sonarInfoList = sonarWebAPIService.getMultipleComponentInfo(targetComponentKeyList);
		} catch (SICServiceException e) {
			new SICRuntimeException("SonarQube로부터 데이터를 가져오는 과정에서 오류가 발생했습니다 - "+e.getMessage(), e);
		}
		
		if(sonarInfoList == null || sonarInfoList.size() <=0) {
			throw new SICRuntimeException("SonarQube 분석 데이터가 존재하지 않습니다 - 컴포넌트키 요청개수 "+targetComponentKeyList.size());
		}
		
		try {
			excelPOIService.modifyXLSX(targetExcelFilePath, outputFilePathAndName, sonarInfoList);
		} catch (Exception e) {
			throw new SICRuntimeException("SonarQube 데이터를 엑셀파일에 쓰는 과정에서 오류가 발생했습니다 "+targetComponentKeyList.size() +" 건의 데이터를 출력 파일 "+outputFilePathAndName,e);
		}
	}

}

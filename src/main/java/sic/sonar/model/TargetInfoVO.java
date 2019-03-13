package sic.sonar.model;

public class TargetInfoVO {
	private String baseUrl;
	private String componentKey;
	private String stingCoverage;
	private String stingBranchCoverage;
	private String stingLineCoverage;
	private String stingTests;
	private String stingTestFails;
	private String stingTestErrors;
	private String periodInfo;
	private String lastAnalysisDate;;
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getComponentKey() {
		return componentKey;
	}

	public void setComponentKey(String componentKey) {
		this.componentKey = componentKey;
	}

	public String getStingCoverage() {
		return stingCoverage;
	}

	public void setStingCoverage(String stingCoverage) {
		this.stingCoverage = stingCoverage;
	}

	public String getStingBranchCoverage() {
		return stingBranchCoverage;
	}

	public void setStingBranchCoverage(String stingBranchCoverage) {
		this.stingBranchCoverage = stingBranchCoverage;
	}

	public String getStingLineCoverage() {
		return stingLineCoverage;
	}

	public void setStingLineCoverage(String stingLineCoverage) {
		this.stingLineCoverage = stingLineCoverage;
	}

	public String getStingTests() {
		return stingTests;
	}

	public void setStingTests(String stingTests) {
		this.stingTests = stingTests;
	}

	public String getStingTestFails() {
		return stingTestFails;
	}

	public void setStingTestFails(String stingTestFails) {
		this.stingTestFails = stingTestFails;
	}

	public String getStingTestErrors() {
		return stingTestErrors;
	}

	public void setStingTestErrors(String stingTestErrors) {
		this.stingTestErrors = stingTestErrors;
	}

	public String getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(String periodInfo) {
		this.periodInfo = periodInfo;
	}

	public String getLastAnalysisDate() {
		return lastAnalysisDate;
	}

	public void setLastAnalysisDate(String lastAnalysisDate) {
		this.lastAnalysisDate = lastAnalysisDate;
	}

	
}

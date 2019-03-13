package sic.sonar.common;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MySonarUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSonarServerURL() throws Exception {
		String target1= "service_tmon_log_api";
		String target2= "service_tmon_plan_api";
		String target3= "service_tmon_marketing_persona_api";
		
		assertEquals("http://sonarqube1.tmonc.net", MySonarUtil.getSonarServerURL(target1));
		assertEquals("http://sonarqube2.tmonc.net", MySonarUtil.getSonarServerURL(target2));
		assertEquals("http://sonarqube3.tmonc.net", MySonarUtil.getSonarServerURL(target3));
	}

}

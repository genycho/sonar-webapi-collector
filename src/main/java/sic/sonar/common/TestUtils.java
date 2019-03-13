package sic.sonar.common;


import java.io.File;
import java.util.Date;
import java.util.Random;

public class TestUtils {
	private static TestUtils instance;
	
	private TestUtils() {
	}
	
	public static TestUtils getInstance() {
		if(instance == null) {
			instance = new TestUtils();
		}
		return instance;
	}
	/**
	 * This unique value is generated from the currentTimeMilis long type value.
	 * 
	 * @return
	 */
	public static String getUniqueString() {
		long millis = System.currentTimeMillis();
		return String.valueOf(millis);
	}
	
	public static void waitFor(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getCurrentPath() {
		return new File(".").getAbsolutePath();
	}
}

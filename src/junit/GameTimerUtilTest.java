package junit;

import org.junit.Test;

import util.GameTimerUtil;

public class GameTimerUtilTest {

	@Test(timeout = 10 * 1000)
	public void test() {
		new GameTimerUtil(null, null, 10).startRun();
	}

}

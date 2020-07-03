package junit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dao.impl.DataBaseDaoImpl;
import pojo.Player;

public class DataBaseDaoImplTest {

	DataBaseDaoImpl dbdi;

	@Before
	public void init() {
		this.dbdi = new DataBaseDaoImpl();
	}

	@Test
	public void testQuery() {
		assertEquals(dbdi.query("123456", "123456"), false);
	}

	@Test
	public void testUpdate() {
		assertEquals(dbdi.update("123456", 15), true);
		assertEquals(dbdi.update("123456", -10), true);
	}

	@Test
	public void testInsert() {
		assertEquals(dbdi.insert(new Player("123456", "111111")), false); // 插入一个已经存在的账号
	}

	@Test
	public void testRankQuery() {
		Object[][] data = new Object[][] { { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null } };
		assertEquals(dbdi.rankQuery(data), true);
		assertEquals(dbdi.rankQuery(null), false);
	}

	@Test
	public void testInitScore() {
		assertEquals(dbdi.initScore(null), false);
	}

	@Test
	public void testInitLevel() {
		assertEquals(dbdi.initLevel("123456"), true);
	}

	@Test
	public void testOpenNextLevel() {
		assertEquals(dbdi.openNextLevel("123456", 1), false);
		assertEquals(dbdi.openNextLevel("123456", 0), false);
		assertEquals(dbdi.openNextLevel("123456", 100), false);
	}

	@Test
	public void testAccountExist() {
		assertEquals(dbdi.accountExist("123456"), true);
	}

}

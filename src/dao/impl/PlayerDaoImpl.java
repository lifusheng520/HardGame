package dao.impl;

import dao.PlayerDao;
import pojo.Player;

/**
 * 这个类实现了玩家的登录注册等操作
 * 
 * @author 李福生
 *
 */
public class PlayerDaoImpl implements PlayerDao {

	@Override
	public boolean login(String account, String password) {
		return new DataBaseDaoImpl().query(account, password);
	}

	@Override
	public boolean register(Player p) {
		return new DataBaseDaoImpl().insert(p);
	}

}

package dao.impl;

import dao.PlayerDao;
import pojo.Player;

public class PlayerDaoImpl implements PlayerDao{

	@Override
	public boolean login(String account, String password) {
		return new DataBaseDaoImpl().query(account, password);
	}

	@Override
	public boolean register(Player p) {
		return new DataBaseDaoImpl().insert(p);
	}
	

}

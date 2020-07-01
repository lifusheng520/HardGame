package dao;

import pojo.Player;

public interface PlayerDao {
	/**
	 * 用户登录功能
	 * 
	 * @param account
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录是否成功
	 */

	public abstract boolean login(String account, String password);

	/**
	 * 用户注册功能
	 * 
	 * @param p
	 *            被注册的用户信息
	 * 
	 * @return 注册成功返回true
	 */
	public abstract boolean register(Player p);

}

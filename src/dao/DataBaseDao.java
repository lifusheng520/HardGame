package dao;

import pojo.Player;

/**
 * 这是一个简单的数据库操作接口
 * 
 * @author 李福生
 * @version V1.0
 */
public interface DataBaseDao {

	/**
	 * 数据库驱动
	 */
	public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * 连接地址
	 */
	public static final String URL = "jdbc:mysql://localhost:3306/hardestgame" + "?serverTimezone=GMT%2B8";

	/**
	 * 数据库用户
	 */
	public static final String DBUSER = "root";

	/**
	 * 数据库用户密码
	 */
	public static final String DBPASSWORd = "123456";

	/**
	 * 初始化playerscore表中的用户分数
	 * 
	 * @param account
	 *            根据账号初始化账号里的总得分
	 * @return 初始化成功返回true 否则false
	 */
	public abstract boolean initScore(String account);

	/**
	 * 查询account的记录，并比对账号密码是否正确 正确返回true
	 * 
	 * @param account
	 *            查询的账号
	 * @param password
	 *            密码
	 * @return 账号密码正确返回true 否则false
	 */
	public abstract boolean query(String account, String password);

	/**
	 * 更新数据库中用户账号中的分数
	 * 
	 * @param account
	 *            要更新的账号
	 * @param score
	 *            更新的分数
	 * @return 成功true否则false
	 */
	public abstract boolean update(String account, int score);

	/**
	 * 向数据库表中添加一个玩家信息
	 * 
	 * @param p
	 *            添加的玩家信息
	 * @return 添加成功返回true 否则false
	 */
	public abstract boolean insert(Player p);

	/**
	 * 从数据库得分表中选出得分前五的玩家信息及得分
	 * 
	 * @param data
	 *            将查询到的排行榜数据以data传递回去
	 * @return 查询成功返回true 否则false
	 */
	public abstract boolean rankQuery(Object[][] data);

	/**
	 * 获取account的积分
	 * 
	 * @param account
	 *            查询的账号
	 * @return 账号account中的总得分
	 */
	public abstract int getScore(String account);

	/**
	 * 初始化数据库中玩家通关记录
	 * 
	 * @param account
	 *            要初始化的账号
	 * @return 成功返回true 否则false
	 */
	public abstract boolean initLevel(String account);

	/**
	 * 查询account账号的玩家玩的闯关记录
	 * 
	 * @param account
	 *            要查询账号
	 * @return 返回account账号的通关关卡记录
	 */
	public abstract int getAccountLevel(String account);

	/**
	 * account玩家通过记录+1，开启下一关
	 * 
	 * @param account
	 *            玩家账号
	 * @param pass
	 *            当前通过的关卡
	 * @return 成功返回true 否则false
	 */
	public abstract boolean openNextLevel(String account, int pass);

	/**
	 * 关闭连接和操作
	 */
	public abstract void close();

	/**
	 * 查询acount账号是否存在
	 * 
	 * @param account
	 *            被查询的账号
	 * @return 存在返回true 不存在返回false
	 */
	public abstract boolean accountExist(String account);
}

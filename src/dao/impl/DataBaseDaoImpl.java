package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DataBaseDao;
import pojo.Player;

/**
 * 这是一个简单的数据库操作接口的实现类
 * 
 * @author 李福生
 * @version V1.0
 *
 */

public class DataBaseDaoImpl implements DataBaseDao {

	/**
	 * 数据库连接对象
	 */
	private Connection conn = null;

	/**
	 * 数据库操作对象
	 */
	private PreparedStatement pstate = null;

	/**
	 * 加载jdbc驱动 并 建立数据库连接
	 */
	public DataBaseDaoImpl() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动加载失败！");
		}

		try {
			conn = DriverManager.getConnection(URL, DBUSER, DBPASSWORd);
		} catch (SQLException e) {
			System.out.println("获取数据库连接失败！");
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	// 查询account的记录，并比对账号密码是否正确 正确返回true
	@Override
	public boolean query(String account, String password) {
		if (conn == null)
			return false;
		if (account == null)
			return false;
		if (password == null)
			return false;
		// 定义SQL语句
		String sql = "select password from player where account=?;";

		ResultSet rs = null;

		boolean flag = false;

		try {
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			// 执行SQL语句
			rs = pstate.executeQuery();
			while (rs.next()) {
				// 比对账号密码是否正确
				if (rs.getString(1).equals(password)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("数据库查询异常！");
		}

		// 释放资源
		this.close();

		return flag;
	}

	// 更新数据库中用户账号中的分数
	@Override
	public boolean update(String account, int score) {
		if (conn == null)
			return false;

		// 定义SQL语句
		String sql = "update playerscore set score=? where account=?;";

		try {
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			// 设置数值
			pstate.setInt(1, score);
			pstate.setString(2, account);
			// 执行SQL语句
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据库更新操作发生一点小问题！");
			return false;
		}
		return true;
	}

	// 向数据库表中添加一个玩家信息
	@Override
	public boolean insert(Player p) {
		if (conn == null)
			return false;
		// 定义SQL语句
		String sql = "insert into player(account,password) values(?,?);";

		try {
			// 对player表插入账号密码
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			// 设置插入值
			pstate.setString(1, p.getAccount());
			pstate.setString(2, p.getPassword());
			// 执行SQL更新操作
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据库插入失败！");
			return false;
		}

		this.close();// 释放资源

		return true;
	}

	// 从数据库得分表中选出得分前五的玩家信息及得分
	@Override
	public boolean rankQuery(Object[][] data) {
		if (conn == null)
			return false;
		if (data == null)
			return false;
		// 定义SQL语句
		String sql = "select account, score from playerscore order by score desc limit 5;";
		ResultSet rs = null;

		try {
			pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();

			for (int i = 0; rs.next(); i++) {
				data[i][0] = i + 1;
				data[i][1] = rs.getString(1);
				data[i][2] = rs.getInt(2);
			}

		} catch (SQLException e) {
			System.out.println("查询排名时出现了一点小问题！");
			return false;
		}
		return true;
	}

	// 初始化总分
	@Override
	public boolean initScore(String account) {
		if (account == null)
			return false;

		// 定义SQL语句
		String sql = "insert into playerscore(account,score) values(?,0);";
		try {
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			// 执行SQL语句
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据更新错误！");
			return false;
		}
		return true;
	}

	// 获取account的积分
	@Override
	public int getScore(String account) {
		if (conn == null)
			return 0;

		int score = 0;
		// 定义SQL语句
		String sql = "select score from playerscore where account=?;";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			ResultSet rs = pstate.executeQuery();
			while (rs.next()) {
				score = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("数据库查询异常!");
			return 0;
		}
		return score;
	}

	// 关闭连接和操作
	@Override
	public void close() {
		// 释放数据库资源
		if (pstate != null)
			try {
				pstate.close();
			} catch (SQLException e) {
				System.out.println("释放数据库操作资源异常!");
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("释放数据库连接异常!");
			}
	}

	// 初始化数据库中玩家通关记录
	@Override
	public boolean initLevel(String account) {
		boolean flag = true;
		if (conn == null)
			return false;

		// 定义SQL语句
		String sql = "insert into playerLevel(account, level) values(?, 1);";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据库查询异常!");
		}
		return flag;

	}

	// 查询account账号的玩家玩的闯关记录
	@Override
	public int getAccountLevel(String account) {
		if (conn == null)
			return 0;

		int level = 1;
		// 定义SQL语句
		String sql = "select level from playerlevel where account=?;";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			ResultSet rs = pstate.executeQuery();
			while (rs.next()) {
				level = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("数据库查询异常!");
			return 0;
		}
		return level;
	}

	// account玩家通过记录+1
	@Override
	public boolean openNextLevel(String account, int pass) {
		if (this.conn == null)
			return false;

		boolean flag = true;

		// 获取当前账号的通关信息
		int level = this.getAccountLevel(account);

		// 如果不处于同一关不能开启下一关，前面的关卡过来不能相隔开启后面的
		if (level != pass)
			return false;

		// 定义SQL语句
		String sql = "update playerlevel set level=? where account=?;";

		try {
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			// 设置数值
			pstate.setInt(1, level + 1);
			pstate.setString(2, account);
			// 执行SQL语句
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("数据库更新操作发生一点小问题！");
			return false;
		}

		return flag;
	}

	// 查询acount账号是否存在 如果存在返回true
	@Override
	public boolean accountExist(String account) {
		if (conn == null)
			return false;

		boolean flag = false;

		String sql = "select account from player where account=?;";

		try {
			// 预编译SQL语句
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account); // 设置数值
			ResultSet rs = pstate.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				if (rs.getString(1).equals(account)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("数据库异常！");
		}

		return flag;
	}

	// 删除给定表中所有数据
	@Override
	public boolean deleteAllRecord(String tableName) {
		if (conn == null)
			return false;
		if (tableName == null)
			return false;

		boolean flag = true;

		// 拼接表名
		String sql = "truncate table " + tableName;

		try {
			pstate = conn.prepareStatement(sql);
			pstate.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("数据库异常！");
			// flag = false;
		}

		return flag;
	}
}

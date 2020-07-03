package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DataBaseDao;
import pojo.Player;

/**
 * ����һ���򵥵����ݿ�����ӿڵ�ʵ����
 * 
 * @author ���
 * @version V1.0
 *
 */

public class DataBaseDaoImpl implements DataBaseDao {

	/**
	 * ���ݿ����Ӷ���
	 */
	private Connection conn = null;

	/**
	 * ���ݿ��������
	 */
	private PreparedStatement pstate = null;

	/**
	 * ����jdbc���� �� �������ݿ�����
	 */
	public DataBaseDaoImpl() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("���ݿ���������ʧ�ܣ�");
		}

		try {
			conn = DriverManager.getConnection(URL, DBUSER, DBPASSWORd);
		} catch (SQLException e) {
			System.out.println("��ȡ���ݿ�����ʧ�ܣ�");
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

	// ��ѯaccount�ļ�¼�����ȶ��˺������Ƿ���ȷ ��ȷ����true
	@Override
	public boolean query(String account, String password) {
		if (conn == null)
			return false;
		if (account == null)
			return false;
		if (password == null)
			return false;
		// ����SQL���
		String sql = "select password from player where account=?;";

		ResultSet rs = null;

		boolean flag = false;

		try {
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			// ִ��SQL���
			rs = pstate.executeQuery();
			while (rs.next()) {
				// �ȶ��˺������Ƿ���ȷ
				if (rs.getString(1).equals(password)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("���ݿ��ѯ�쳣��");
		}

		// �ͷ���Դ
		this.close();

		return flag;
	}

	// �������ݿ����û��˺��еķ���
	@Override
	public boolean update(String account, int score) {
		if (conn == null)
			return false;

		// ����SQL���
		String sql = "update playerscore set score=? where account=?;";

		try {
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			// ������ֵ
			pstate.setInt(1, score);
			pstate.setString(2, account);
			// ִ��SQL���
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݿ���²�������һ��С���⣡");
			return false;
		}
		return true;
	}

	// �����ݿ�������һ�������Ϣ
	@Override
	public boolean insert(Player p) {
		if (conn == null)
			return false;
		// ����SQL���
		String sql = "insert into player(account,password) values(?,?);";

		try {
			// ��player������˺�����
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			// ���ò���ֵ
			pstate.setString(1, p.getAccount());
			pstate.setString(2, p.getPassword());
			// ִ��SQL���²���
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݿ����ʧ�ܣ�");
			return false;
		}

		this.close();// �ͷ���Դ

		return true;
	}

	// �����ݿ�÷ֱ���ѡ���÷�ǰ��������Ϣ���÷�
	@Override
	public boolean rankQuery(Object[][] data) {
		if (conn == null)
			return false;
		if (data == null)
			return false;
		// ����SQL���
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
			System.out.println("��ѯ����ʱ������һ��С���⣡");
			return false;
		}
		return true;
	}

	// ��ʼ���ܷ�
	@Override
	public boolean initScore(String account) {
		if (account == null)
			return false;

		// ����SQL���
		String sql = "insert into playerscore(account,score) values(?,0);";
		try {
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			// ִ��SQL���
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݸ��´���");
			return false;
		}
		return true;
	}

	// ��ȡaccount�Ļ���
	@Override
	public int getScore(String account) {
		if (conn == null)
			return 0;

		int score = 0;
		// ����SQL���
		String sql = "select score from playerscore where account=?;";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			ResultSet rs = pstate.executeQuery();
			while (rs.next()) {
				score = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("���ݿ��ѯ�쳣!");
			return 0;
		}
		return score;
	}

	// �ر����ӺͲ���
	@Override
	public void close() {
		// �ͷ����ݿ���Դ
		if (pstate != null)
			try {
				pstate.close();
			} catch (SQLException e) {
				System.out.println("�ͷ����ݿ������Դ�쳣!");
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("�ͷ����ݿ������쳣!");
			}
	}

	// ��ʼ�����ݿ������ͨ�ؼ�¼
	@Override
	public boolean initLevel(String account) {
		boolean flag = true;
		if (conn == null)
			return false;

		// ����SQL���
		String sql = "insert into playerLevel(account, level) values(?, 1);";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݿ��ѯ�쳣!");
		}
		return flag;

	}

	// ��ѯaccount�˺ŵ������Ĵ��ؼ�¼
	@Override
	public int getAccountLevel(String account) {
		if (conn == null)
			return 0;

		int level = 1;
		// ����SQL���
		String sql = "select level from playerlevel where account=?;";

		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account);
			ResultSet rs = pstate.executeQuery();
			while (rs.next()) {
				level = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("���ݿ��ѯ�쳣!");
			return 0;
		}
		return level;
	}

	// account���ͨ����¼+1
	@Override
	public boolean openNextLevel(String account, int pass) {
		if (this.conn == null)
			return false;

		boolean flag = true;

		// ��ȡ��ǰ�˺ŵ�ͨ����Ϣ
		int level = this.getAccountLevel(account);

		// ���������ͬһ�ز��ܿ�����һ�أ�ǰ��Ĺؿ���������������������
		if (level != pass)
			return false;

		// ����SQL���
		String sql = "update playerlevel set level=? where account=?;";

		try {
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			// ������ֵ
			pstate.setInt(1, level + 1);
			pstate.setString(2, account);
			// ִ��SQL���
			pstate.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���ݿ���²�������һ��С���⣡");
			return false;
		}

		return flag;
	}

	// ��ѯacount�˺��Ƿ���� ������ڷ���true
	@Override
	public boolean accountExist(String account) {
		if (conn == null)
			return false;

		boolean flag = false;

		String sql = "select account from player where account=?;";

		try {
			// Ԥ����SQL���
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, account); // ������ֵ
			ResultSet rs = pstate.executeQuery(); // ִ��SQL���
			while (rs.next()) {
				if (rs.getString(1).equals(account)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("���ݿ��쳣��");
		}

		return flag;
	}

	// ɾ������������������
	@Override
	public boolean deleteAllRecord(String tableName) {
		if (conn == null)
			return false;
		if (tableName == null)
			return false;

		boolean flag = true;

		// ƴ�ӱ���
		String sql = "truncate table " + tableName;

		try {
			pstate = conn.prepareStatement(sql);
			pstate.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("���ݿ��쳣��");
			// flag = false;
		}

		return flag;
	}
}

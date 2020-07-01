package dao;

import pojo.Player;

/**
 * ����һ���򵥵����ݿ�����ӿ�
 * 
 * @author ���
 * @version V1.0
 */
public interface DataBaseDao {

	// ���ݿ�����
	public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";

	// ���ӵ�ַ
	public static final String URL = "jdbc:mysql://localhost:3306/hardestgame" + "?serverTimezone=GMT%2B8";

	// ���ݿ��û�
	public static final String DBUSER = "root";

	// ���ݿ��û�����
	public static final String DBPASSWORd = "123456";

	// ��ʼ��playerscore���е��û�����
	public abstract boolean initScore(String account);

	// ��ѯaccount�ļ�¼�����ȶ��˺������Ƿ���ȷ ��ȷ����true
	public abstract boolean query(String account, String password);

	// �������ݿ����û��˺��еķ���
	public abstract boolean update(String account, int score);

	// �����ݿ�������һ�������Ϣ
	public abstract boolean insert(Player p);

	// �����ݿ�÷ֱ���ѡ���÷�ǰ��������Ϣ���÷�
	public abstract boolean rankQuery(Object[][] data);

	// ��ȡaccount�Ļ���
	public abstract int getScore(String account);

	// ��ʼ�����ݿ������ͨ�ؼ�¼
	public abstract boolean initLevel(String account);

	// ��ѯaccount�˺ŵ������Ĵ��ؼ�¼
	public abstract int getAccountLevel(String account);

	// account���ͨ����¼+1
	public abstract boolean openNextLevel(String account, int pass);

	// �ر����ӺͲ���
	public abstract void close();

	// ��ѯacount�˺��Ƿ����
	public abstract boolean accountExist(String account);
}

package dao;

import pojo.Player;

/**
 * ����һ���򵥵����ݿ�����ӿ�
 * 
 * @author ���
 * @version V1.0
 */
public interface DataBaseDao {

	/**
	 * ���ݿ�����
	 */
	public static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";

	/**
	 * ���ӵ�ַ
	 */
	public static final String URL = "jdbc:mysql://localhost:3306/hardestgame" + "?serverTimezone=GMT%2B8";

	/**
	 * ���ݿ��û�
	 */
	public static final String DBUSER = "root";

	/**
	 * ���ݿ��û�����
	 */
	public static final String DBPASSWORd = "123456";

	/**
	 * ��ʼ��playerscore���е��û�����
	 * 
	 * @param account
	 *            �����˺ų�ʼ���˺�����ܵ÷�
	 * @return ��ʼ���ɹ�����true ����false
	 */
	public abstract boolean initScore(String account);

	/**
	 * ��ѯaccount�ļ�¼�����ȶ��˺������Ƿ���ȷ ��ȷ����true
	 * 
	 * @param account
	 *            ��ѯ���˺�
	 * @param password
	 *            ����
	 * @return �˺�������ȷ����true ����false
	 */
	public abstract boolean query(String account, String password);

	/**
	 * �������ݿ����û��˺��еķ���
	 * 
	 * @param account
	 *            Ҫ���µ��˺�
	 * @param score
	 *            ���µķ���
	 * @return �ɹ�true����false
	 */
	public abstract boolean update(String account, int score);

	/**
	 * �����ݿ�������һ�������Ϣ
	 * 
	 * @param p
	 *            ��ӵ������Ϣ
	 * @return ��ӳɹ�����true ����false
	 */
	public abstract boolean insert(Player p);

	/**
	 * �����ݿ�÷ֱ���ѡ���÷�ǰ��������Ϣ���÷�
	 * 
	 * @param data
	 *            ����ѯ�������а�������data���ݻ�ȥ
	 * @return ��ѯ�ɹ�����true ����false
	 */
	public abstract boolean rankQuery(Object[][] data);

	/**
	 * ��ȡaccount�Ļ���
	 * 
	 * @param account
	 *            ��ѯ���˺�
	 * @return �˺�account�е��ܵ÷�
	 */
	public abstract int getScore(String account);

	/**
	 * ��ʼ�����ݿ������ͨ�ؼ�¼
	 * 
	 * @param account
	 *            Ҫ��ʼ�����˺�
	 * @return �ɹ�����true ����false
	 */
	public abstract boolean initLevel(String account);

	/**
	 * ��ѯaccount�˺ŵ������Ĵ��ؼ�¼
	 * 
	 * @param account
	 *            Ҫ��ѯ�˺�
	 * @return ����account�˺ŵ�ͨ�عؿ���¼
	 */
	public abstract int getAccountLevel(String account);

	/**
	 * account���ͨ����¼+1��������һ��
	 * 
	 * @param account
	 *            ����˺�
	 * @param pass
	 *            ��ǰͨ���Ĺؿ�
	 * @return �ɹ�����true ����false
	 */
	public abstract boolean openNextLevel(String account, int pass);

	/**
	 * �ر����ӺͲ���
	 */
	public abstract void close();

	/**
	 * ��ѯacount�˺��Ƿ����
	 * 
	 * @param account
	 *            ����ѯ���˺�
	 * @return ���ڷ���true �����ڷ���false
	 */
	public abstract boolean accountExist(String account);
}

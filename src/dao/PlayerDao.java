package dao;

import pojo.Player;

public interface PlayerDao {
	/**
	 * �û���¼����
	 * 
	 * @param account
	 *            �û���
	 * @param password
	 *            ����
	 * @return ��¼�Ƿ�ɹ�
	 */

	public abstract boolean login(String account, String password);

	/**
	 * �û�ע�Ṧ��
	 * 
	 * @param p
	 *            ��ע����û���Ϣ
	 * 
	 * @return ע��ɹ�����true
	 */
	public abstract boolean register(Player p);

}

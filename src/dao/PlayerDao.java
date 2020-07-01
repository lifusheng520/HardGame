package dao;

import pojo.Player;

public interface PlayerDao {
	/**
	 * �û���¼����
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return ��¼�Ƿ�ɹ�
	 */

	public abstract boolean login(String account, String password);

	/**
	 * �û�ע�Ṧ��
	 * 
	 * @param user
	 *            ��ע����û���Ϣ
	 */
	public abstract boolean register(Player p);

}

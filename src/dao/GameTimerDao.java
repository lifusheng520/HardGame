package dao;

/**
 * ��ʱ��Ϸ�ӿ�
 * 
 * @author ���
 *
 */

public interface GameTimerDao {
	/**
	 * ������Ϸ
	 */
	// ������Ϸ
	public abstract void gameRun();
	
	/**
	 * ˢ����Ϸ����
	 */
	// ˢ����Ϸ
	public abstract void flushGame();

	/**
	 * ������Ϸ��������Ϸ����
	 */
	// ʱ�䵽�˽�����Ϸ
	public abstract void countGame();
}

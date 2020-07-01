package util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

import dao.GameTimerDao;

/**
 * һ���򵥵���Ϸ��ʱ��������
 * 
 * @author ���
 *
 */

public class GameTimerUtil extends TimerTask {

	// ����ʱ��ǩ
	private JLabel jtime;
	
	// ��ȡ��ǰϵͳʱ��
	private long startTime;
	
	// ʣ��ʱ�� ��
	private long timeLeft;
	
	// ������
	private Lock lock = new ReentrantLock();
	
	// ��Ϸ���ƶ���
	private GameTimerDao gameControl;
	
	// ��Ϸʱ��
	private long gameTime;
	
	// ���ü�ʱ������
	private Timer timer;

	/**
	 * �����ʼ����ʱ���ĳ�Ա����
	 * 
	 * @param gameControl
	 *            һ��ʵ������Ϸ��ʱ����
	 * @param time
	 *            ����ʱ��ʣ�µ�ʱ����������ǩ��ʾ
	 * @param gameTime
	 *            ��ʱ���ļ�ʱʱ��
	 */

	public GameTimerUtil(GameTimerDao gameControl, JLabel time, long gameTime) {
		this.gameControl = gameControl;
		this.jtime = time;
		// ���뻻��Ϊ����ʹ��
		this.gameTime = gameTime * 1000;
		this.startTime = System.currentTimeMillis();
		this.timer = new Timer();
	}
	
	/**
	 * ��������
	 */
	public void startRun() {
		this.timer.schedule(this, 0, 500);
	}
	
	/**
	 * �ر�����
	 */
	public void closeTask() {
		this.timer.cancel();
	}

	@Override
	public void run() {

		this.lock.lock(); // Ϊ��������
		
		// ����ʣ���ʱ��
		this.timeLeft = (this.gameTime - System.currentTimeMillis() + this.startTime) / 1000;
		
		// Ϊ��ǩ����ʱ��
		this.jtime.setText(String.valueOf(timeLeft));

		// ˢ����Ϸ
		this.gameControl.flushGame();

		// ���û��ʱ����
		if (this.timeLeft <= 0) {
			
			// ʱ�������ʼ��Ϸ����
			this.gameControl.countGame();
			
			this.timer.cancel();	// ȡ������
			
			this.lock.unlock();		// �⿪�߳���
			return;
		}

		this.lock.unlock(); // ������Ͻ���
	}
}

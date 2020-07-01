package util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

import dao.GameTimerDao;

/**
 * 一个简单的游戏计时器工具类
 * 
 * @author 李福生
 *
 */

public class GameTimerUtil extends TimerTask {

	// 倒计时标签
	private JLabel jtime;
	
	// 获取当前系统时间
	private long startTime;
	
	// 剩余时间 秒
	private long timeLeft;
	
	// 锁对象
	private Lock lock = new ReentrantLock();
	
	// 游戏控制对象
	private GameTimerDao gameControl;
	
	// 游戏时长
	private long gameTime;
	
	// 内置计时器对象
	private Timer timer;

	/**
	 * 构造初始化计时器的成员变量
	 * 
	 * @param gameControl
	 *            一个实现了游戏计时的类
	 * @param time
	 *            将计时器剩下的时间放入这个标签显示
	 * @param gameTime
	 *            计时器的计时时长
	 */

	public GameTimerUtil(GameTimerDao gameControl, JLabel time, long gameTime) {
		this.gameControl = gameControl;
		this.jtime = time;
		// 将秒换算为毫秒使用
		this.gameTime = gameTime * 1000;
		this.startTime = System.currentTimeMillis();
		this.timer = new Timer();
	}
	
	/**
	 * 启动任务
	 */
	public void startRun() {
		this.timer.schedule(this, 0, 500);
	}
	
	/**
	 * 关闭任务
	 */
	public void closeTask() {
		this.timer.cancel();
	}

	@Override
	public void run() {

		this.lock.lock(); // 为操作上锁
		
		// 计算剩余的时间
		this.timeLeft = (this.gameTime - System.currentTimeMillis() + this.startTime) / 1000;
		
		// 为标签设置时间
		this.jtime.setText(String.valueOf(timeLeft));

		// 刷新游戏
		this.gameControl.flushGame();

		// 如果没有时间了
		if (this.timeLeft <= 0) {
			
			// 时间结束开始游戏结算
			this.gameControl.countGame();
			
			this.timer.cancel();	// 取消任务
			
			this.lock.unlock();		// 解开线程锁
			return;
		}

		this.lock.unlock(); // 处理完毕解锁
	}
}

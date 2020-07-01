package dao;

/**
 * 计时游戏接口
 * 
 * @author 李福生
 *
 */

public interface GameTimerDao {
	/**
	 * 启动游戏
	 */
	// 启动游戏
	public abstract void gameRun();
	
	/**
	 * 刷新游戏属性
	 */
	// 刷新游戏
	public abstract void flushGame();

	/**
	 * 结束游戏，进行游戏结算
	 */
	// 时间到了结算游戏
	public abstract void countGame();
}

package util;

import java.util.concurrent.Callable;

/**
 * 多线程随机数，保证同时生成随机数
 * 
 * @author 李福生
 *
 */
public class RandomNumberUtil implements Callable<Integer> {

	/**
	 * @return 返回产生的一个随机数
	 */
	@Override
	public Integer call() {
		// 为了公平只取0~9之间的9个随机数
		int num = (int) (Math.random() * 9) % 3;
		return new Integer(num);
	}
}

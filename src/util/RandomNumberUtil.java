package util;

import java.util.concurrent.Callable;

/**
 * ���߳����������֤ͬʱ���������
 * 
 * @author ���
 *
 */
public class RandomNumberUtil implements Callable<Integer> {

	/**
	 * @return ���ز�����һ�������
	 */
	@Override
	public Integer call() {
		// Ϊ�˹�ƽֻȡ0~9֮���9�������
		int num = (int) (Math.random() * 9) % 3;
		return new Integer(num);
	}
}

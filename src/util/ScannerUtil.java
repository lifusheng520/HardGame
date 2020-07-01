package util;

/**
 * 一个简单的输入处理工具类
 * 
 * @author 李福生
 * @version V1.0
 */
public class ScannerUtil {

	/**
	 * 检查输入是否正确
	 * 
	 * @param s
	 *            输入的字符串
	 * @return 符合规则返回true 否则false
	 */
	public static boolean inputCheck(String s) {
		if (s == null)
			return false;
		return s.matches("\\w{6,15}");
	}
}

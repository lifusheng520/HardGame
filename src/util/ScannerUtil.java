package util;

public class ScannerUtil {

	/**
	 * ��������Ƿ���ȷ
	 * 
	 * @param s
	 *            ������ַ���
	 * @return ���Ϲ��򷵻�true ����false
	 */
	public static boolean inputCheck(String s) {
		if (s == null)
			return false;
		return s.matches("\\w{6,15}");
	}
}
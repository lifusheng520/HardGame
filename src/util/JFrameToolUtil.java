package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * һ�����幤�߰�
 * 
 * @author ���
 *
 */
public class JFrameToolUtil {

	/**
	 * ��ȡ��Ļ�м�λ�������
	 * 
	 * @param p
	 *            �����С����������
	 * @return ����������ŵ������
	 */
	public static Point centerLocation(Point p) {
		// ��ȡ���߶���
		Toolkit tk = Toolkit.getDefaultToolkit();

		// ��ȡ��Ļ��С
		Dimension dms = tk.getScreenSize();
		int x = (int) (dms.getWidth() - p.x) / 2;
		int y = (int) (dms.getHeight() - p.y) / 2;

		return new Point(x, y);
	}
}

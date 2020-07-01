package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * 一个窗体工具包
 * 
 * @author 李福生
 *
 */
public class JFrameToolUtil {

	/**
	 * 获取屏幕中间位置坐标点
	 * 
	 * @param p
	 *            窗体大小的坐标点对象
	 * @return 窗体居中所放的坐标点
	 */
	public static Point centerLocation(Point p) {
		// 获取工具对象
		Toolkit tk = Toolkit.getDefaultToolkit();

		// 获取屏幕大小
		Dimension dms = tk.getScreenSize();
		int x = (int) (dms.getWidth() - p.x) / 2;
		int y = (int) (dms.getHeight() - p.y) / 2;

		return new Point(x, y);
	}
}

package test;

import util.PlayMusicUtil;
import view.PlayerLogin;

/**
 * 这个是程序入口
 * 
 * @author 李福生
 * @version V1.0
 */
public class MainTest {
	public static void main(String[] args) {

		// 播放音乐
		PlayMusicUtil.playMusic();

		// 创建登录窗体并设置可见
		new PlayerLogin().setVisible(true);

	}
}

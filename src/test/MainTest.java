package test;

import util.PlayMusicUtil;
import view.PlayerLogin;

public class MainTest {
	public static void main(String[] args) {

		// 播放音乐
		PlayMusicUtil.playMusic();

		// 创建登录窗体并设置可见
		new PlayerLogin().setVisible(true);

	}
}

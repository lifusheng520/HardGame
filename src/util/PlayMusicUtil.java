package util;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

/**
 * 这是一个简单的音乐播放工具类
 * 
 * @author 李福生
 *
 */
public class PlayMusicUtil {
	/**
	 * 音乐文件的路径
	 */
	private static String file1 = "src" + File.separator + "resource" + File.separator + "bgm.wav";
	private static String file2 = "src" + File.separator + "resource" + File.separator + "game.wav";

	/**
	 * 音乐的操作对象
	 */
	private static AudioClip christmas;

	// 记录音乐是否已经播放
	public static boolean played = false;

	// 加载音乐文件
	private static AudioClip loadSound(String file) {
		URL url = null;
		try {
			url = new URL("file:" + file);
		} catch (MalformedURLException e) {
			;
		}
		return JApplet.newAudioClip(url);
	}

	/**
	 * 播放音乐
	 */
	public static void playMusic() {

		// 已经在播放就不能播放了
		if (played)
			return;

		// 加载音乐
		christmas = loadSound(file1);
		christmas.play(); // 播放
		played = true; // 标记为已经播放

	}

	/**
	 * 暂停音乐
	 */
	public static void stopMusic() {

		// 音乐没播放不需要暂停
		if (!played)
			return;

		christmas.stop(); // 暂停音乐
		played = false; // 标记为关闭状态
	}

	/**
	 * 重新播放音乐
	 */
	public static void rePlay() {

		// 游戏已经播放先暂停播放
		if (played)
			christmas.stop();

		christmas = loadSound(file1); // 加载音乐
		christmas.loop(); // 循环播放
		played = true; // 标记为已经播放
	}

	/**
	 * 加载游戏的音乐
	 * 
	 * @param k
	 *            游戏关卡
	 */
	public static void loadGameMusic(int k) {

		// 先暂停音乐
		stopMusic();

		// 选择游戏音乐
		switch (k) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		default:
			christmas = loadSound(file2);
		}

		// 循环播放
		christmas.loop();

		played = true; // 标记为已经播放
	}

}

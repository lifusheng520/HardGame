package util;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

/**
 * ����һ���򵥵����ֲ��Ź�����
 * 
 * @author ���
 *
 */
public class PlayMusicUtil {
	/**
	 * �����ļ���·��
	 */
	private static String file1 = "src" + File.separator + "resource" + File.separator + "bgm.wav";
	private static String file2 = "src" + File.separator + "resource" + File.separator + "game.wav";

	/**
	 * ���ֵĲ�������
	 */
	private static AudioClip christmas;

	// ��¼�����Ƿ��Ѿ�����
	public static boolean played = false;

	// ���������ļ�
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
	 * ��������
	 */
	public static void playMusic() {

		// �Ѿ��ڲ��žͲ��ܲ�����
		if (played)
			return;

		// ��������
		christmas = loadSound(file1);
		christmas.play(); // ����
		played = true; // ���Ϊ�Ѿ�����

	}

	/**
	 * ��ͣ����
	 */
	public static void stopMusic() {

		// ����û���Ų���Ҫ��ͣ
		if (!played)
			return;

		christmas.stop(); // ��ͣ����
		played = false; // ���Ϊ�ر�״̬
	}

	/**
	 * ���²�������
	 */
	public static void rePlay() {

		// ��Ϸ�Ѿ���������ͣ����
		if (played)
			christmas.stop();

		christmas = loadSound(file1); // ��������
		christmas.loop(); // ѭ������
		played = true; // ���Ϊ�Ѿ�����
	}

	/**
	 * ������Ϸ������
	 * 
	 * @param k
	 *            ��Ϸ�ؿ�
	 */
	public static void loadGameMusic(int k) {

		// ����ͣ����
		stopMusic();

		// ѡ����Ϸ����
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

		// ѭ������
		christmas.loop();

		played = true; // ���Ϊ�Ѿ�����
	}

}

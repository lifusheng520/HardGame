package test;

import util.PlayMusicUtil;
import view.PlayerLogin;

/**
 * ����ǳ������
 * 
 * @author ���
 * @version V1.0
 */
public class MainTest {
	public static void main(String[] args) {

		// ��������
		PlayMusicUtil.playMusic();

		// ������¼���岢���ÿɼ�
		new PlayerLogin().setVisible(true);

	}
}

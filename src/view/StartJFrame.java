package view;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.impl.DataBaseDaoImpl;
import util.JFrameToolUtil;
import util.PlayMusicUtil;

/**
 * ��ʼ��Ϸ����ģ��
 * 
 * @author ���
 * @version V1.0
 */
public class StartJFrame extends JFrame {

	/**
	 * ��¼��汾
	 */
	public static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String currentAccount;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            ��ǰ����˺�
	 */
	public StartJFrame(String account) {
		this.currentAccount = account;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartJFrame.class.getResource("/resource/title.png")));
		setTitle("\u6781\u96BE\u6E38\u620F");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u9009\u9879");
		menuBar.add(menu);

		JMenuItem startGameMenuItem = new JMenuItem("\u5F00\u59CB\u6E38\u620F");
		startGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʼ��Ϸ
				startGame();
			}
		});
		menu.add(startGameMenuItem);

		JMenuItem playerScoreMenuItem = new JMenuItem("\u67E5\u770B\u6240\u6709\u73A9\u5BB6\u603B\u79EF\u5206");
		playerScoreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����ܻ��ְ�
				lookRank();
			}
		});
		menu.add(playerScoreMenuItem);

		JMenuItem backLoginMenuItem = new JMenuItem("\u9000\u51FA\u767B\u5F55");
		backLoginMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �˳���¼
				quitLogin();
			}
		});
		menu.add(backLoginMenuItem);

		JMenuItem closeGameMenuItem = new JMenuItem("\u9000\u51FA\u6E38\u620F");
		closeGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(closeGameMenuItem);

		JMenu gameSettingMenu = new JMenu("\u6E38\u620F\u8BBE\u7F6E");
		menuBar.add(gameSettingMenu);

		JMenuItem playMusicMenuItem = new JMenuItem("\u64AD\u653E\u97F3\u4E50");
		playMusicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��������
				PlayMusicUtil.playMusic();
			}
		});
		gameSettingMenu.add(playMusicMenuItem);

		JMenuItem loopMenuItem = new JMenuItem("\u91CD\u65B0\u64AD\u653E(\u5FAA\u73AF\u6A21\u5F0F)");
		loopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ѭ����������
				PlayMusicUtil.rePlay();
			}
		});
		gameSettingMenu.add(loopMenuItem);

		JMenuItem stopMusicMenuItem = new JMenuItem("\u6682\u505C\u97F3\u4E50");
		stopMusicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ͣ����
				PlayMusicUtil.stopMusic();
			}
		});
		gameSettingMenu.add(stopMusicMenuItem);

		JMenuItem closeMusicMenuItem = new JMenuItem("\u5173\u95ED\u97F3\u4E50");
		closeMusicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// ��������Ϊ���ɲ���
				PlayMusicUtil.setting(false);

				// ֹͣ���ֲ���
				PlayMusicUtil.stopMusic();
			}
		});
		gameSettingMenu.add(closeMusicMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("\u5F00\u59CB\u6E38\u620F");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		btnNewButton.setBounds(103, 32, 229, 53);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u73A9\u5BB6\u603B\u5206\u6392\u884C\u699C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lookRank();
			}
		});
		btnNewButton_1.setBounds(103, 98, 229, 53);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("\u9000\u51FA\u6E38\u620F");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1_1.setBounds(103, 164, 229, 53);
		contentPane.add(btnNewButton_1_1);

		JButton button_3 = new JButton("\u9000\u51FA\u767B\u5F55");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitLogin();
			}
		});
		button_3.setBounds(346, 205, 99, 34);
		contentPane.add(button_3);

		JButton btnNewButton_2 = new JButton("\u6CE8\u9500\u8D26\u53F7");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

				// ע����ǰ�˺�
				if (dbdi.closeAccount(currentAccount)) {
					JOptionPane.showMessageDialog(contentPane, "�˺�ע���ɹ�,�´��ټ���");

					// ���ص�¼
					new PlayerLogin().setVisible(true);
					(StartJFrame.this).dispose();// �ͷŴ�����Դ

				} else {
					JOptionPane.showMessageDialog(contentPane, "�˺�ע��ʧ�ܣ�");
				}

				dbdi.close();// �ͷ���Դ
			}
		});
		btnNewButton_2.setBounds(345, 157, 99, 34);
		contentPane.add(btnNewButton_2);

		// ���ò��ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// ��ʼ��Ϸ
	private void startGame() {
		// ��ת���ؿ�ѡ����
		new ChoiceJFrame(currentAccount).setVisible(true);
		(StartJFrame.this).dispose();
	}

	// �鿴����
	private void lookRank() {
		// ��ת����Ϸ���а�
		new RankJFrame(currentAccount).setVisible(true);
		(StartJFrame.this).dispose();
	}

	// �˳���¼
	private void quitLogin() {
		// ���ص���¼����
		new PlayerLogin().setVisible(true);
		this.dispose();// �ͷŴ�����Դ
	}
}

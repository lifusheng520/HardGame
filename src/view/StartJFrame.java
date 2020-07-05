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
 * 开始游戏窗体模块
 * 
 * @author 李福生
 * @version V1.0
 */
public class StartJFrame extends JFrame {

	/**
	 * 记录类版本
	 */
	public static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String currentAccount;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            当前玩家账号
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
				// 开始游戏
				startGame();
			}
		});
		menu.add(startGameMenuItem);

		JMenuItem playerScoreMenuItem = new JMenuItem("\u67E5\u770B\u6240\u6709\u73A9\u5BB6\u603B\u79EF\u5206");
		playerScoreMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 玩家总积分榜
				lookRank();
			}
		});
		menu.add(playerScoreMenuItem);

		JMenuItem backLoginMenuItem = new JMenuItem("\u9000\u51FA\u767B\u5F55");
		backLoginMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 退出登录
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
				// 播放音乐
				PlayMusicUtil.playMusic();
			}
		});
		gameSettingMenu.add(playMusicMenuItem);

		JMenuItem loopMenuItem = new JMenuItem("\u91CD\u65B0\u64AD\u653E(\u5FAA\u73AF\u6A21\u5F0F)");
		loopMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 循环播放音乐
				PlayMusicUtil.rePlay();
			}
		});
		gameSettingMenu.add(loopMenuItem);

		JMenuItem stopMusicMenuItem = new JMenuItem("\u6682\u505C\u97F3\u4E50");
		stopMusicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 暂停音乐
				PlayMusicUtil.stopMusic();
			}
		});
		gameSettingMenu.add(stopMusicMenuItem);

		JMenuItem closeMusicMenuItem = new JMenuItem("\u5173\u95ED\u97F3\u4E50");
		closeMusicMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 设置音乐为不可播放
				PlayMusicUtil.setting(false);

				// 停止音乐播放
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

				// 注销当前账号
				if (dbdi.closeAccount(currentAccount)) {
					JOptionPane.showMessageDialog(contentPane, "账号注销成功,下次再见！");

					// 返回登录
					new PlayerLogin().setVisible(true);
					(StartJFrame.this).dispose();// 释放窗体资源

				} else {
					JOptionPane.showMessageDialog(contentPane, "账号注销失败！");
				}

				dbdi.close();// 释放资源
			}
		});
		btnNewButton_2.setBounds(345, 157, 99, 34);
		contentPane.add(btnNewButton_2);

		// 设置不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// 开始游戏
	private void startGame() {
		// 跳转到关卡选择窗体
		new ChoiceJFrame(currentAccount).setVisible(true);
		(StartJFrame.this).dispose();
	}

	// 查看排行
	private void lookRank() {
		// 跳转到游戏排行榜
		new RankJFrame(currentAccount).setVisible(true);
		(StartJFrame.this).dispose();
	}

	// 退出登录
	private void quitLogin() {
		// 返回到登录界面
		new PlayerLogin().setVisible(true);
		this.dispose();// 释放窗体资源
	}
}

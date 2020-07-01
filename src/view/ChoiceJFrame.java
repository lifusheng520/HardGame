package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.impl.DataBaseDaoImpl;
import util.JFrameToolUtil;
import util.PlayMusicUtil;
import javax.swing.JMenuItem;

public class ChoiceJFrame extends JFrame {

	/**
	 * 关卡选择窗体模块
	 * 
	 * @author 李福生
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// 未通过为false 通过true
	private boolean[][] map = new boolean[2][3];
	private String currentAccount;

	/**
	 * Create the frame.
	 */
	public ChoiceJFrame(String account) {
		// 设置当前玩家账户
		this.currentAccount = account;

		// 初始化map标记数组
		this.initMap();
		
		// 播放音乐
		PlayMusicUtil.playMusic();

		setTitle("\u5173\u5361\u9009\u62E9");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChoiceJFrame.class.getResource("/resource/title.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton1 = new JButton("");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(1)) // 如果当前关卡可玩
					return;

				// 创建第一个游戏的窗体对象并设置可见
				new Game0(currentAccount).setVisible(true);
				(ChoiceJFrame.this).dispose();// 释放当前窗体资源
			}
		});
		// 选择当前关卡图片
		btnNewButton1.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(1 - 1))));
		btnNewButton1.setBounds(38, 58, 93, 93);
		contentPane.add(btnNewButton1);

		JButton btnNewButton2 = new JButton("");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(2)) // 如果当前关卡可玩
					return;

				// 创建第二个游戏的窗体，并设置可见
				new Game1(currentAccount).setVisible(true);
				(ChoiceJFrame.this).dispose();// 释放当前窗体资源
			}
		});
		// 选择当前关卡图片
		btnNewButton2.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(2 - 1))));
		btnNewButton2.setBounds(189, 58, 93, 93);
		contentPane.add(btnNewButton2);

		JButton btnNewButton3 = new JButton("");
		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(3)) // 如果当前关卡可玩
					return;

				// 创建第三个游戏的窗体，并设置可见
				new Game2(currentAccount).setVisible(true);
				(ChoiceJFrame.this).dispose();// 释放窗体资源
			}
		});
		// 选择当前关卡图片
		btnNewButton3.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(3 - 1))));
		btnNewButton3.setBounds(347, 58, 93, 93);
		contentPane.add(btnNewButton3);

		JButton btnNewButton4 = new JButton("");
		btnNewButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(4)) // 如果当前关卡可玩
					return;
				JOptionPane.showMessageDialog(contentPane, "到这一关你已经很厉害了，这里暂时还没有开放！");
			}
		});
		// 选择当前关卡图片
		btnNewButton4.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(4 - 1))));
		btnNewButton4.setBounds(38, 192, 93, 93);
		contentPane.add(btnNewButton4);

		JButton btnNewButton5 = new JButton("");
		btnNewButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(5)) // 如果当前关卡可玩
					return;
				JOptionPane.showMessageDialog(contentPane, "到这一关你已经很厉害了，这里暂时还没有开放！");
			}
		});
		// 选择当前关卡图片
		btnNewButton5.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(5 - 1))));
		btnNewButton5.setBounds(189, 192, 93, 93);
		contentPane.add(btnNewButton5);

		JButton btnNewButton6 = new JButton("");
		btnNewButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canPlay(6)) // 如果当前关卡可玩
					return;
				JOptionPane.showMessageDialog(contentPane, "到这一关你已经很厉害了，这里暂时还没有开放！");
			}
		});
		// 选择当前关卡图片
		btnNewButton6.setIcon(new ImageIcon(ChoiceJFrame.class.getResource(this.picPath(6 - 1))));
		btnNewButton6.setBounds(347, 192, 93, 93);
		contentPane.add(btnNewButton6);

		JLabel lblNewLabel = new JLabel("\u7B2C 1 \u5173");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 41, 72, 18);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u7B2C 2 \u5173");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(199, 41, 72, 18);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u7B2C 3 \u5173");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(357, 41, 72, 18);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u7B2C 4 \u5173");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(50, 174, 72, 18);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("\u7B2C 5 \u5173");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(199, 174, 72, 18);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("\u7B2C 6 \u5173");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(357, 174, 72, 18);
		contentPane.add(lblNewLabel_5);

		JButton button = new JButton("\u8FD4\u56DE\u5F00\u59CB\u754C\u9762");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoStartJFrame();
			}
		});
		button.setBounds(333, 293, 158, 27);
		contentPane.add(button);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBounds(0, 0, 490, 32);
		contentPane.add(menuBar);

		JMenu menu = new JMenu("\u9009\u9879");
		menuBar.add(menu);

		JMenuItem highestMenuItem = new JMenuItem("\u5173\u5361\u5386\u53F2\u6700\u9AD8");
		highestMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 查看关卡历史最高得分
				gotoHighestScore();
			}
		});
		menu.add(highestMenuItem);

		JMenuItem backMenuItem = new JMenuItem("\u8FD4\u56DE");
		backMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回开始游戏界面
				gotoStartJFrame();
			}
		});
		menu.add(backMenuItem);

		JMenu helpMenu = new JMenu("\u5E2E\u52A9");
		menuBar.add(helpMenu);

		JMenuItem gameRulesMenuItem = new JMenuItem("\u6E38\u620F\u8BF4\u660E");
		gameRulesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 查看游戏规则
				gameRules();
			}
		});
		helpMenu.add(gameRulesMenuItem);

		JMenu aboutMenu = new JMenu("\u5173\u4E8E");
		menuBar.add(aboutMenu);

		JMenuItem authorMenuItem = new JMenuItem("\u5173\u4E8E\u4F5C\u8005");
		authorMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 查看作者
				aboutAuthor();
			}
		});
		aboutMenu.add(authorMenuItem);

		JButton button_3 = new JButton("\u67E5\u770B\u5173\u5361\u5386\u53F2\u6700\u9AD8\u5206");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoHighestScore();
			}
		});
		button_3.setBounds(0, 298, 191, 27);
		contentPane.add(button_3);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// 选择第 pass 关的图片
	private String picPath(int pass) {
		String s = "/resource/";
		if (map[pass / 3][pass % 3])
			return s.concat("lock1.png");
		else
			return s.concat("lock2.png");
	}

	// 初始化map标记数组
	private void initMap() {
		// 查询当前用户的关卡信息
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();
		int level = dbdi.getAccountLevel(this.currentAccount);

		// 操作完毕 释放资源
		dbdi.close();

		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[i].length; j++) {
				if (level > 0) {
					this.map[i][j] = true;
					// 开启一关后level--
					level--;
				} else { // 后面关卡没通过不开启
					break;
				}
			}
		}
	}

	// 返回开始游戏界面
	private void gotoStartJFrame() {
		new StartJFrame(currentAccount).setVisible(true);
		this.dispose(); // 释放窗体资源
	}

	// 关于作者
	private void aboutAuthor() {
		JOptionPane.showMessageDialog(contentPane, "作者：不详");
	}

	// 游戏说明
	private void gameRules() {
		// 跳转到游戏说明窗体
		new GameRulesJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // 释放资源
	}

	// 查看历史最高得分
	private void gotoHighestScore() {
		new HighestScoreJFrame(this.currentAccount).setVisible(true);
		this.dispose();
	}

	// 判断当前关卡是否可玩
	private boolean canPlay(int k) {
		k--;
		if (this.map[k / 3][k % 3])
			return true;
		else
			return false;
	}
}

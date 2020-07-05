package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.GameTimerDao;
import dao.impl.DataBaseDaoImpl;
import util.GameTimerUtil;
import util.InputOutputStreamUtil;
import util.JFrameToolUtil;
import util.PlayMusicUtil;

/**
 * 一个简单的计时扇巴掌游戏，扇的次数越接近37分数越高
 * 
 * @author 李福生
 * @version V1.0
 */

public class Game2 extends JFrame implements GameTimerDao {

	/**
	 * 记录类版本
	 */
	public static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// 当前玩家账号
	private String currentAccount;

	// 倒计时标签
	private JLabel timeLabel;

	// 记录当前游戏状态
	private boolean gameRun = false;

	// 游戏开始按钮
	private JButton startGame;

	// 击打显示标签
	private JLabel beatLabel;

	// 当前得分
	private int score;

	// 芬芳标签
	private JLabel moodLabel;

	// 显示得分标签
	private JLabel scoreLabel;

	// 点击次数
	private int times;
	// 次数显示标签
	private JLabel timesLabel;

	// 游戏是否结束
	private boolean gameOver;

	// 计时器任务对象
	private GameTimerUtil task;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            当前玩家账号
	 */
	public Game2(String account) {

		// 播放游戏内音乐
		PlayMusicUtil.loadGameMusic(3);

		// 设置当前账号
		this.currentAccount = account;
		this.times = 0;
		this.gameOver = false;

		setIconImage(Toolkit.getDefaultToolkit().getImage(Game2.class.getResource("/resource/bu.png")));
		setTitle("\u5927\u5DF4\u638C");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel playerNameLabel = new JLabel("玩家：" + this.currentAccount);
		playerNameLabel.setBounds(440, 50, 119, 18);
		contentPane.add(playerNameLabel);

		timeLabel = new JLabel("20");
		timeLabel.setFont(new Font("宋体", Font.PLAIN, 40));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(431, 89, 128, 92);
		contentPane.add(timeLabel);

		startGame = new JButton("\u5F00\u59CB");
		startGame.setFont(new Font("宋体", Font.BOLD, 55));
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 如果游戏还没有结束
				if (!gameOver)
					// 如果游戏没有开始
					if (!gameRun) {
						gameRun(); // 启动游戏
						gameRun = true; // 记录当前游戏状态为启动状态
						// 切换按钮状态
						startGame.setText("打他");
					} else {
						showBeat(); // 显示击打
						closeMood(); // 关闭言语
						times++;
					}
			}
		});
		startGame.setBounds(388, 235, 180, 95);
		contentPane.add(startGame);

		scoreLabel = new JLabel("\u5F97\u5206\uFF1A0");
		scoreLabel.setBounds(440, 13, 128, 27);
		contentPane.add(scoreLabel);

		beatLabel = new JLabel("");
		beatLabel.setIcon(new ImageIcon(Game2.class.getResource("/resource/beat.png")));
		beatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		beatLabel.setBounds(36, 81, 100, 100);
		beatLabel.setVisible(false);
		contentPane.add(beatLabel);

		timesLabel = new JLabel("\u6B21\u6570 X 0");
		timesLabel.setBounds(161, 13, 72, 18);
		contentPane.add(timesLabel);

		moodLabel = new JLabel("*%~ ***%$#");
		moodLabel.setFont(new Font("宋体", Font.PLAIN, 25));
		moodLabel.setForeground(Color.RED);
		moodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moodLabel.setBounds(14, 194, 145, 43);
		contentPane.add(moodLabel);

		JLabel bgPicLabel = new JLabel("");
		bgPicLabel.setIcon(new ImageIcon(Game2.class.getResource("/resource/dabz.png")));
		bgPicLabel.setBounds(14, 13, 360, 270);
		contentPane.add(bgPicLabel);

		JButton btnNewButton = new JButton("\u4E0B\u6B21\u518D\u73A9");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 关闭音乐
				PlayMusicUtil.stopMusic();

				// 如果不为空说明计时器任务已经启动
				if (task != null)
					task.closeTask(); // 关闭任务

				(Game2.this).dispose();// 释放当前游戏窗体
				// 退出游戏返回选关
				new ChoiceJFrame(currentAccount).setVisible(true);
			}
		});
		btnNewButton.setBounds(14, 315, 113, 27);
		contentPane.add(btnNewButton);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// 启动游戏
	@Override
	public void gameRun() { // 启动游戏

		// 创建计时器任务对象并设置时间为20秒
		this.task = new GameTimerUtil(this, this.timeLabel, 20);

		// 启动计时器任务
		this.task.startRun();
	}

	// 显示击打
	private void showBeat() {
		beatLabel.setVisible(true);
	}

	// 关闭击打显示
	private void closeBeat() {
		this.beatLabel.setVisible(false);
	}

	// 显示当前得分
	private void showScore() {
		// 计算得分 距离37次越近得分越高
		this.score = 6 - Math.abs(37 - this.times) / 6;
		this.scoreLabel.setText("得分：" + this.score);
	}

	// 显示点击次数
	private void showTimes() {
		this.timesLabel.setText("次数 X " + this.times);
	}

	// 显示语言标签
	private void showMood() {
		this.moodLabel.setVisible(true);
	}

	// 关闭言语标签
	private void closeMood() {
		this.moodLabel.setVisible(false);
	}

	// 刷新游戏
	@Override
	public void flushGame() {
		// 关闭击打
		closeBeat();
		// 显示言语
		showMood();
		// 显示得分
		showScore();
		// 显示次数
		showTimes();
	}

	// 游戏结算
	@Override
	public void countGame() {

		this.gameOver = true; // 标记游戏结束

		// 记录玩家历史得分
		InputOutputStreamUtil.insertDataFile(3, this.currentAccount, this.score);

		// 保存游戏数据
		this.saveGame();

		// 关闭音乐
		PlayMusicUtil.stopMusic();

		this.dispose();// 释放当前游戏窗体
		// 返回关卡选择
		new ChoiceJFrame(this.currentAccount).setVisible(true);
	}

	private void saveGame() {
		// 创建数据库操作实现对象
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		if (JOptionPane.showConfirmDialog(contentPane, "游戏结束，需要保存得分吗？", "Save",
				JOptionPane.YES_NO_CANCEL_OPTION) == 0) {

			// 累加积分
			int sumScore = this.score + dbdi.getScore(this.currentAccount);

			// 保存得分到玩家账户
			if (new DataBaseDaoImpl().update(this.currentAccount, sumScore))
				JOptionPane.showMessageDialog(contentPane, "保存成功！");
			else
				JOptionPane.showMessageDialog(contentPane, "保存失败！");
		}

		// 判断是否到达通关条件
		if (this.score >= 5) {

			JOptionPane.showMessageDialog(contentPane, "恭喜过关，已经开启下一关！");

			// 开启下一关
			if (dbdi.openNextLevel(this.currentAccount, 3)) {

				// 记录玩家历史得分
				InputOutputStreamUtil.insertDataFile(3, this.currentAccount, this.score);
			} else {

				// 如果已经通过则修改得分即可
				InputOutputStreamUtil.updateAccountScore(currentAccount, 3, this.score);
			}

		} else {
			JOptionPane.showMessageDialog(contentPane, "很遗憾，闯关失败！");
		}

		// 完成操作 关闭连接 释放资源
		dbdi.close();
	}
}

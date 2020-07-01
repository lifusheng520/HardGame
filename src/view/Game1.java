package view;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import util.RandomNumberUtil;

public class Game1 extends JFrame implements GameTimerDao {

	/**
	 * 游戏有时间限制，在规定时间内按对一定数量则过关，否则出局，答对越多得分越高
	 * 
	 * @author 李福生
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// 当前玩家的账号
	private String currentAccount;

	// 显示倒计时的标签
	private JLabel clockLabel;

	// 人机和玩家的随机数
	private int robotNumber;
	private int playerNumber;

	// 随机数对应的图片的显示标签
	private JLabel playerLabel;
	private JLabel robotLabel;

	// HashMap集合存储标签与随机数键值对
	HashMap<JLabel, Integer> hm;

	// 游戏是否已经开始了
	private boolean flag = false;

	// 游戏得分
	private int score = 0;

	// 显示得分标签
	private JLabel scoreLabel;

	// 游戏按钮
	private JButton btnNewButton;

	// 游戏是否结算
	private boolean isCount;

	// 游戏是否已经刷新 可得分状态
	private boolean isUpdate;

	// 显示评测结果的标签
	private JLabel judgeLabel;

	// 计时器任务对象
	private GameTimerUtil task;

	/**
	 * Create the frame.
	 */
	public Game1(String account) {

		// 播放游戏内音乐
		PlayMusicUtil.loadGameMusic(2);

		// 设置当前账号
		this.currentAccount = account;
		this.hm = new HashMap<JLabel, Integer>();
		this.isCount = false;
		this.isUpdate = true;

		setIconImage(Toolkit.getDefaultToolkit().getImage(Game1.class.getResource("/resource/game1.png")));
		setTitle("\u77F3\u5934\u526A\u5200\u5E03\u770B\u8C01\u51FA\u5F97\u5FEB");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		playerLabel = new JLabel("");
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerLabel.setBounds(374, 41, 100, 100);
		contentPane.add(playerLabel);

		robotLabel = new JLabel("");
		robotLabel.setHorizontalAlignment(SwingConstants.CENTER);
		robotLabel.setBounds(118, 41, 100, 100);
		contentPane.add(robotLabel);

		JLabel robotName = new JLabel("\u773C\u75BE\u4E14\u624B\u5FEB27");
		robotName.setBounds(14, 13, 123, 18);
		contentPane.add(robotName);

		JLabel playerName = new JLabel();
		playerName.setText("玩家：" + currentAccount); // 设置游戏中的玩家名称
		playerName.setHorizontalAlignment(SwingConstants.RIGHT);
		playerName.setBounds(463, 12, 118, 18);
		contentPane.add(playerName);

		btnNewButton = new JButton("\u91CD\u62F3\u51FA\u51FB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 如果没结算才做操作，防止结算刷分
				if (!isCount)
					if (!flag) {// 如果游戏未开始
						flag = true;// 记录游戏为开始状态
						gameRun();
					} else {
						gameReferee();
						// 刷新得分
						scoreLabel.setText("得分：" + score);
					}

				// 点击一次后设置为不可得分状态，防止一直点击多次计分
				isUpdate = false;
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 23));
		btnNewButton.setBounds(429, 160, 153, 51);
		contentPane.add(btnNewButton);

		JLabel robotPic = new JLabel("");
		robotPic.setIcon(new ImageIcon(Game1.class.getResource("/resource/robot.png")));
		robotPic.setBounds(14, 43, 80, 80);
		contentPane.add(robotPic);

		JLabel playerPic = new JLabel("");
		playerPic.setIcon(new ImageIcon(Game1.class.getResource("/resource/player.png")));
		playerPic.setBounds(498, 45, 80, 80);
		contentPane.add(playerPic);

		JLabel vsLabel = new JLabel("VS");
		vsLabel.setFont(new Font("宋体", Font.BOLD, 30));
		vsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vsLabel.setBounds(269, 61, 54, 35);
		contentPane.add(vsLabel);

		clockLabel = new JLabel("10");
		clockLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setBounds(216, 13, 160, 35);
		contentPane.add(clockLabel);

		JButton btnNewButton_1 = new JButton("\u800D\u8D56\u6211\u4E0D\u73A9\u4E86");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 关闭音乐
				PlayMusicUtil.stopMusic();

				// 如果不为空说明任务已开启了
				if (task != null)
					task.closeTask();	// 关闭任务

				// 返回选择关卡
				new ChoiceJFrame(currentAccount).setVisible(true);
				(Game1.this).dispose();
			}
		});
		btnNewButton_1.setBounds(14, 184, 135, 27);
		contentPane.add(btnNewButton_1);

		scoreLabel = new JLabel("得分：0");
		scoreLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel.setBounds(343, 0, 100, 34);
		contentPane.add(scoreLabel);

		judgeLabel = new JLabel("");
		judgeLabel.setFont(new Font("宋体", Font.PLAIN, 40));
		judgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		judgeLabel.setBounds(213, 131, 166, 80);
		contentPane.add(judgeLabel);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	@Override
	public void gameRun() {	// 启动游戏
		// 更改按钮状态
		this.btnNewButton.setText("出拳");

		// 创建计时任务
		this.task = new GameTimerUtil(this, this.clockLabel, 10);

		// 启动任务
		this.task.startRun();

	}

	// 为双方设置随机数
	private void setNumber() {
		// 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);

		// 加入到线程池中运算并接收结果
		Future<Integer> num1 = pool.submit(new RandomNumberUtil());
		Future<Integer> num2 = pool.submit(new RandomNumberUtil());

		try {
			// 获取两个线程中的随机数
			this.robotNumber = num1.get();
			this.playerNumber = num2.get();

			// 将标签对应的随机数添加到HashMap集合中
			this.hm.put(this.robotLabel, this.robotNumber);
			this.hm.put(this.playerLabel, this.playerNumber);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// 关闭线程池
		pool.shutdown();
	}

	public void flushGame() {

		this.setNumber();
		this.shiftPic();

		// 游戏数据刷新后可以继续计分了
		this.isUpdate = true;
		// 一轮过后提示消失
		this.judgeLabel.setText("");
	}

	// 切换图片过程
	private void shiftPic() {
		// 获取HashMap的键值对对象
		Set<Map.Entry<JLabel, Integer>> result = this.hm.entrySet();
		// 通过Set集合就可得到键值对
		for (Map.Entry<JLabel, Integer> set : result) {
			// 通过每个键的值找到对应的图片 给键对象设置图片
			if (set.getValue() == 0)
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/bu.png")));
			else if (set.getValue() == 1)
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/jd.png")));
			else
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/st.png")));
		}
	}

	// 计算玩家是否可得分
	private void gameReferee() {
		// 当前为不可得分状态,返回不做计算
		if (!this.isUpdate)
			return;

		boolean win = false;
		// 判定玩家是否胜利
		if (this.playerNumber == 1 && this.robotNumber == 0)
			win = true;
		else if (this.playerNumber == 2 && this.robotNumber == 1)
			win = true;
		else if (this.playerNumber == 0 && this.robotNumber == 2)
			win = true;

		// 玩家胜利,且当前为可得分状态
		if (win) {
			this.score++;
			// 给玩家游戏提示
			this.judgeLabel.setText("胜利 +1");
		} else if (this.playerNumber == this.robotNumber) {
			this.judgeLabel.setText("平局 +0");
		} else {
			this.judgeLabel.setText("失败 -1");
			// 失败得分减一
			this.score--;
		}
	}

	// 保存得分
	private void saveScore() {
		// 创建数据库实现对象
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		// 获取玩家之前的得分
		this.score += dbdi.getScore(this.currentAccount);

		// 更新玩家得分
		if (dbdi.update(this.currentAccount, this.score)) {
			JOptionPane.showMessageDialog(contentPane, "保存成功!");

		} else {
			JOptionPane.showMessageDialog(contentPane, "出现了一点小问题，数据保存失败!");
		}

		dbdi.openNextLevel(this.currentAccount, 2);

		// 如果达到了进入下一关的条件
		if (this.score >= 3) {

			// 开启下一关

			JOptionPane.showMessageDialog(contentPane, "得分保存成功！恭喜进入下一关！");
		} else {
			JOptionPane.showMessageDialog(contentPane, "很遗憾，闯关失败！");
		}

		// 完成操作 关闭连接 释放资源
		dbdi.close();
	}

	// 游戏结算
	public void countGame() {
		// 进入结算
		this.isCount = true;

		// 记录玩家历史得分
		InputOutputStreamUtil.insertDataFile(2, this.currentAccount, this.score);

		// 保存得分
		this.saveScore();

		// 关闭音乐
		PlayMusicUtil.stopMusic();

		// 返回关卡选择
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // 释放当前窗体资源
	}
}

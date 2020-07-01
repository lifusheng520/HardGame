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

import dao.impl.DataBaseDaoImpl;
import util.InputOutputStreamUtil;
import util.JFrameToolUtil;
import util.PlayMusicUtil;
import util.RandomNumberUtil;

public class Game0 extends JFrame {

	/**
	 * 这是一个简单的石头剪刀游戏
	 * 
	 * @author 李福生
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// 红方和蓝方的随机数
	private int redNumber;
	private int blueNumber;

	// 当前的用户账号
	private String currentAccount;
	// 获胜方
	private String winner;
	// 玩家给定的答案
	private String answer;
	// 正确的个数
	private int rightCount = 0;
	// 红方和蓝方的随机数对应的图片的显示标签
	private JLabel redLabel;
	private JLabel blueLabel;
	// HashMap键值对存储每个玩家对应的图片
	private HashMap<JLabel, Integer> hm = new HashMap<JLabel, Integer>();
	// 显示分数标签
	private JLabel showScore;

	/**
	 * Create the frame.
	 */
	public Game0(String account) {
		
		// 播放游戏内音乐
		PlayMusicUtil.loadGameMusic(1);
		
		setTitle("\u6700\u5F3A\u6BD4\u62FC");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Game0.class.getResource("/resource/title.png")));
		// 设置当前玩家账号
		this.currentAccount = account;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u7EA2\u65B9");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(32, 13, 101, 42);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u84DD\u65B9");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(238, 13, 94, 42);
		contentPane.add(label_1);

		redLabel = new JLabel("");
		redLabel.setBounds(32, 63, 103, 103);
		contentPane.add(redLabel);

		blueLabel = new JLabel("");
		blueLabel.setBounds(238, 63, 103, 103);
		contentPane.add(blueLabel);

		JLabel lblVs = new JLabel("VS");
		lblVs.setFont(new Font("宋体", Font.PLAIN, 24));
		lblVs.setHorizontalAlignment(SwingConstants.CENTER);
		lblVs.setBounds(149, 91, 72, 42);
		contentPane.add(lblVs);

		JButton redWin = new JButton("\u7EA2\u65B9\u80DC\u5229");
		redWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 玩家给定答案 并 进行游戏判定
				answer = "red";
				// 启动游戏
				gameRuning();
			}
		});
		redWin.setBounds(14, 179, 101, 27);
		contentPane.add(redWin);

		JButton draw = new JButton("\u5E73\u5C40");
		draw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 玩家给定答案 并 进行游戏判定
				answer = "draw";
				// 启动游戏
				gameRuning();
			}
		});
		draw.setBounds(136, 179, 94, 27);
		contentPane.add(draw);

		JButton blueWin = new JButton("\u84DD\u65B9\u80DC\u5229");
		blueWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 玩家给定答案 并 进行游戏判定
				answer = "blue";
				// 启动游戏
				gameRuning();
			}
		});
		blueWin.setBounds(250, 179, 101, 27);
		contentPane.add(blueWin);

		JButton btnNewButton_1_1 = new JButton("\u8FD4\u56DE");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 如果用户不玩了就退出游戏
				if (JOptionPane.showConfirmDialog(contentPane, "要在玩一会吗？") == 1) {
					
					// 关闭音乐
					PlayMusicUtil.stopMusic();
					
					new ChoiceJFrame(currentAccount).setVisible(true);
					(Game0.this).dispose();
				}
			}
		});
		btnNewButton_1_1.setBounds(0, 226, 94, 27);
		contentPane.add(btnNewButton_1_1);

		showScore = new JLabel("");
		showScore.setHorizontalAlignment(SwingConstants.LEFT);
		showScore.setBounds(148, 27, 72, 18);
		contentPane.add(showScore);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));

		// 初始化游戏数据
		initGame();
	}

	// 初始化游戏数据
	private void initGame() {
		// 设置随机数
		this.setNumber();
		// 获取对应的图片
		this.getPic();
		// 显示当前的得分
		showScore.setText("答对：" + this.rightCount);

	}

	// 刷新游戏
	private void flushGame() {
		// 评定当前游戏
		this.gameReferee();
		// 清空HashMap中的所有键值对
		this.hm.clear();
		// 初始化游戏数据
		initGame();
	}

	// 启动游戏
	private void gameRuning() {

		// 刷新游戏
		flushGame();

		// 检测游戏是否达到通过条件
		if (this.rightCount == 3) {

			// 记录玩家历史得分
			InputOutputStreamUtil.insertDataFile(1, this.currentAccount, this.rightCount);

			// 创建数据库操作实现类对象
			DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

			// 开启下一关
			dbdi.openNextLevel(this.currentAccount, 1);

			// 用户选择是否保存得分
			if (JOptionPane.showConfirmDialog(contentPane, "恭喜你过关了，下一关已开启！要保存这局得分吗？", "Save score option",
					JOptionPane.YES_NO_OPTION) == 0) {

				// 将玩家之前的积分与现在的积分相加后，添加到账户里
				this.rightCount += dbdi.getScore(this.currentAccount);

				// 更新玩家的积分保存到account账户里
				if (dbdi.update(this.currentAccount, this.rightCount)) {
					JOptionPane.showMessageDialog(contentPane, "保存成功!");
				} else {
					JOptionPane.showMessageDialog(contentPane, "出现了一点小问题，数据保存失败!");
				}

				// 完成操作 关闭连接 释放资源
				dbdi.close();
			}
			
			// 关闭游戏音乐
			PlayMusicUtil.stopMusic();
			
			if (JOptionPane.showConfirmDialog(contentPane, "要再玩一把吗？", "Win", JOptionPane.YES_NO_OPTION) == 0) {
				// 继续开启游戏窗体
				new Game0(this.currentAccount).setVisible(true);
				// 当前窗体释放
				this.dispose();
			} else {
				// 返回关卡选择
				new ChoiceJFrame(this.currentAccount).setVisible(true);
				this.dispose(); // 释放当前窗体
			}
		}
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
			this.redNumber = num1.get();
			this.blueNumber = num2.get();

			// 将标签对应的随机数添加到HashMap集合中
			this.hm.put(redLabel, this.redNumber);
			this.hm.put(blueLabel, this.blueNumber);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// 关闭线程池
		pool.shutdown();
	}

	// 获取对应的图片
	private void getPic() {
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

	// 游戏评定
	private void gameReferee() {
		// 判定真正的胜利方
		if (redNumber == 0 && blueNumber == 0)
			this.winner = "draw";
		else if (redNumber == 0 && blueNumber == 1)
			this.winner = "blue";
		else if (redNumber == 0 && blueNumber == 2)
			this.winner = "red";
		else if (redNumber == 1 && blueNumber == 0)
			this.winner = "red";
		else if (redNumber == 1 && blueNumber == 1)
			this.winner = "draw";
		else if (redNumber == 1 && blueNumber == 2)
			this.winner = "blue";
		else if (redNumber == 2 && blueNumber == 0)
			this.winner = "blue";
		else if (redNumber == 2 && blueNumber == 1)
			this.winner = "red";
		else if (redNumber == 2 && blueNumber == 2)
			this.winner = "draw";
		else
			this.winner = "nobody";

		// 胜方和答案是否一致
		if (this.winner.equals(this.answer)) {
			// 答对的数量加一
			this.rightCount++;
		}
	}
}

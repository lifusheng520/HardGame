package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import util.InputOutputStreamUtil;
import util.JFrameToolUtil;

/**
 * 查看所有关卡的历史最高得分
 * 
 * @author 李福生
 * @version V1.0
 */
public class HighestScoreJFrame extends JFrame {

	/**
	 * 记录类版本
	 */
	public static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// 当前玩家账号
	private String currentAccount;
	// 表格标题
	private Object title[];
	// 表格数据
	private Object data[][];

	private JTable jtable;

	private boolean lookMe;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            当前玩家账号
	 * @param lookMe
	 *            是否查看个人历史最高得分
	 */
	public HighestScoreJFrame(String account, boolean lookMe) {
		// 设置当前玩家账号
		this.currentAccount = account;
		this.lookMe = lookMe;

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(HighestScoreJFrame.class.getResource("/resource/title.png")));
		setTitle("\u5386\u53F2\u6700\u9AD8\u5206");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("\u5386\u53F2\u6700\u9AD8\u5F97\u5206\u6392\u884C\u699C");
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.PLAIN, 25));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);

		// 刷新表格
		flushTable();

		jtable.setFont(new Font("宋体", Font.BOLD, 20));
		jtable.setRowHeight(30);

		// 将表格添加到容器中
		contentPane.add(new JScrollPane(jtable), BorderLayout.CENTER);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u8FD4  \u56DE");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoChoice();
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 20));

		JButton btnNewButton = new JButton("\u5220\u9664\u6211\u7684\u8D26\u53F7\u6392\u884C\u699C\u4FE1\u606F");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 删除玩家的所有通关得分记录
				InputOutputStreamUtil.deleteAccountRank(currentAccount);

				new HighestScoreJFrame(currentAccount, false).setVisible(true);
				(HighestScoreJFrame.this).dispose();

			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 20));
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53EA\u770B\u6211\u81EA\u5DF1\u7684\u5F97\u5206");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 查看玩家自己的历史得分
				InputOutputStreamUtil.findAccountRank(currentAccount, data);

				new HighestScoreJFrame(currentAccount, true).setVisible(true);
				(HighestScoreJFrame.this).dispose();

			}
		});
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 20));
		panel.add(btnNewButton_1);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// 刷新表格内容
	private void flushTable() {
		// 准备表格的标题和数据
		title = new Object[] { "游戏关卡", "玩家", "最高得分" };
		data = new Object[][] { { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" },
				{ "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" } };

		// 获取表格的data数据
		if (!lookMe) {
			InputOutputStreamUtil.getHighestScore(data);
		} else {
			InputOutputStreamUtil.findAccountRank(currentAccount, data);
		}

		jtable = new JTable(data, title);
	}

	// 返回关卡选择
	private void gotoChoice() {
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // 释放资源
	}
}

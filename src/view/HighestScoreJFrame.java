package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.InputOutputStreamUtil;
import util.JFrameToolUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            当前玩家账号
	 */
	public HighestScoreJFrame(String account) {
		// 设置当前玩家账号
		this.currentAccount = account;

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(HighestScoreJFrame.class.getResource("/resource/title.png")));
		setTitle("\u5386\u53F2\u6700\u9AD8\u5206");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("\u5386\u53F2\u6700\u9AD8\u5F97\u5206\u6392\u884C\u699C");
		label.setForeground(Color.RED);
		label.setFont(new Font("宋体", Font.PLAIN, 25));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);

		JButton button = new JButton("\u8FD4  \u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoChoice();
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(button, BorderLayout.SOUTH);

		// 准备表格的标题和数据
		title = new Object[] { "游戏关卡", "玩家", "最高得分" };
		data = new Object[][] { { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" },
				{ "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" }, { "暂无数据", "暂无数据", "暂无数据" } };

		// 获取表格的data数据
		InputOutputStreamUtil.getHighestScore(data);

		JTable jtable = new JTable(data, title);
		jtable.setFont(new Font("宋体", Font.BOLD, 20));
		jtable.setRowHeight(30);

		// 将表格添加到容器中
		contentPane.add(new JScrollPane(jtable), BorderLayout.CENTER);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// 返回关卡选择
	private void gotoChoice() {
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // 释放资源
	}
}

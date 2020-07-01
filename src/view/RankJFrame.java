package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.impl.DataBaseDaoImpl;
import util.JFrameToolUtil;

/**
 * 用户积分排行窗体模块
 * 
 * @author 李福生
 * @version V1.0
 */
public class RankJFrame extends JFrame {

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
	public RankJFrame(String account) {
		this.currentAccount = account;
		setTitle("\u73A9\u5BB6\u6392\u884C\u699C");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RankJFrame.class.getResource("/resource/title.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// 添加标题标签
		JLabel lblNewLabel = new JLabel("\u73A9\u5BB6\u79EF\u5206Top5\u6392\u884C");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		// 准备表格的标题和数据
		Object title[] = { "名次", "玩家", "累计积分" };
		Object data[][] = { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null } };

		// 创建数据库操作对象
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		// 更新表格数据
		if (!dbdi.rankQuery(data)) {
			JOptionPane.showMessageDialog(contentPane, "出了一点小问题，没查询到数据！");
		}

		// 完成操作 关闭连接 释放资源
		dbdi.close();

		JTable jtable = new JTable(data, title);
		jtable.setFont(new Font("宋体", Font.BOLD, 20));
		jtable.setRowHeight(30);
		// 将表格添加到容器中
		contentPane.add(new JScrollPane(jtable), BorderLayout.CENTER);

		JButton jbutton = new JButton("返回");
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回游戏开始界面
				new StartJFrame(currentAccount).setVisible(true);
				(RankJFrame.this).dispose();// 释放窗体资源
			}
		});
		jbutton.setFont(new Font("宋体", Font.BOLD, 18));
		contentPane.add(jbutton, BorderLayout.SOUTH);

		// 设置不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}
}

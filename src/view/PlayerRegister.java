package view;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.impl.DataBaseDaoImpl;
import dao.impl.PlayerDaoImpl;
import pojo.Player;
import util.JFrameToolUtil;
import util.ScannerUtil;

/**
 * 用户注册窗体模块
 * 
 * @author 李福生
 * @version V1.0
 */
public class PlayerRegister extends JFrame {

	/**
	 * 记录类版本
	 */
	public static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Create the frame.
	 */
	public PlayerRegister() {
		setTitle("\u6E38\u620F\u6CE8\u518C");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlayerRegister.class.getResource("/resource/title.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 414, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(55, 31, 72, 18);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(55, 79, 72, 18);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(55, 128, 72, 18);
		contentPane.add(label_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(141, 76, 168, 24);
		contentPane.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(141, 125, 168, 24);
		contentPane.add(passwordField_1);

		textField = new JTextField();
		textField.setBounds(141, 28, 168, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u786E\u8BA4\u6CE8\u518C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 判断输入是否合法
				if (ScannerUtil.inputCheck(textField.getText())
						&& ScannerUtil.inputCheck(new String(passwordField.getPassword()))) {

					// 判断密码是否一致
					if (String.valueOf(passwordField.getPassword())
							.equals(String.valueOf(passwordField_1.getPassword()))) {

						// 创建数据库操作实现对象
						DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

						// 如果账户以及存在，就不注册了，直接退出方法
						if (dbdi.accountExist(textField.getText())) {
							JOptionPane.showMessageDialog(contentPane, "该账号已经存在了！");

							// 完成操作 关闭连接 释放资源
							dbdi.close();

							return;
						}

						// 创建玩家对象
						Player p = new Player(textField.getText(), String.valueOf(passwordField.getPassword()));

						// 注册玩家账号
						if (new PlayerDaoImpl().register(p)) {

							// 如果账号注册成功，初始化用户的积分
							dbdi.initScore(textField.getText());

							// 初始化新注册玩家的通关
							dbdi.initLevel(textField.getText());

							JOptionPane.showMessageDialog(contentPane, "注册成功！");
						} else {
							JOptionPane.showMessageDialog(contentPane, "发生不可预料的原因，导致注册失败！");
						}

						// 完成操作 关闭连接 释放资源
						dbdi.close();

					} else {
						JOptionPane.showMessageDialog(contentPane, "两次输入的密码不一致！");
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "账号和密码应该为6~15位的英文或数字！");
				}
			}
		});
		btnNewButton.setBounds(250, 174, 113, 27);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u91CD\u7F6E");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 重置所有文本框的内容
				textField.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
			}
		});
		btnNewButton_1.setBounds(154, 174, 82, 27);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u8FD4\u56DE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 返回登录窗体， 注册窗体释放
				new PlayerLogin().setVisible(true);
				(PlayerRegister.this).dispose();
			}
		});
		btnNewButton_2.setBounds(32, 174, 113, 27);
		contentPane.add(btnNewButton_2);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}
}

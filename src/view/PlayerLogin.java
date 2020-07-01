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
import util.JFrameToolUtil;

public class PlayerLogin extends JFrame {

	/**
	 * 用户注册窗体模块
	 * 
	 * @author 李福生
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public PlayerLogin() {
		setTitle("\u6E38\u620F\u767B\u5F55");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlayerLogin.class.getResource("/resource/title.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u8D26\u53F7");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(24, 42, 72, 18);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(24, 90, 72, 18);
		contentPane.add(label_1);

		textField = new JTextField();
		textField.setBounds(125, 39, 138, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(125, 87, 138, 24);
		contentPane.add(passwordField);

		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获取输入的账号密码
				String account = textField.getText();
				String password = String.valueOf(passwordField.getPassword());

				DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

				if (dbdi.query(account, password)) {
					// 完成操作 关闭连接 释放资源
					dbdi.close();

					JOptionPane.showMessageDialog(contentPane, "账号密码正确，登录成功！");

					// 进入开始游戏窗体
					new StartJFrame(account).setVisible(true);
					(PlayerLogin.this).dispose();

				} else {
					JOptionPane.showMessageDialog(contentPane, "账号或密码错误，请重新输入！");
				}
			}
		});
		btnNewButton.setBounds(24, 136, 113, 27);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 跳转注册界面动作
				new PlayerRegister().setVisible(true);
				(PlayerLogin.this).dispose();
			}
		});
		btnNewButton_1.setBounds(189, 136, 113, 27);
		contentPane.add(btnNewButton_1);

		// 不可更改窗体大小
		this.setResizable(false);

		// 设置窗体居中
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}
}

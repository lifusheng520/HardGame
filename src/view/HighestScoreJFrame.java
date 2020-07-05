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
 * �鿴���йؿ�����ʷ��ߵ÷�
 * 
 * @author ���
 * @version V1.0
 */
public class HighestScoreJFrame extends JFrame {

	/**
	 * ��¼��汾
	 */
	public static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// ��ǰ����˺�
	private String currentAccount;
	// ������
	private Object title[];
	// �������
	private Object data[][];

	private JTable jtable;

	private boolean lookMe;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            ��ǰ����˺�
	 * @param lookMe
	 *            �Ƿ�鿴������ʷ��ߵ÷�
	 */
	public HighestScoreJFrame(String account, boolean lookMe) {
		// ���õ�ǰ����˺�
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
		label.setFont(new Font("����", Font.PLAIN, 25));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);

		// ˢ�±��
		flushTable();

		jtable.setFont(new Font("����", Font.BOLD, 20));
		jtable.setRowHeight(30);

		// �������ӵ�������
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
		button.setFont(new Font("����", Font.PLAIN, 20));

		JButton btnNewButton = new JButton("\u5220\u9664\u6211\u7684\u8D26\u53F7\u6392\u884C\u699C\u4FE1\u606F");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// ɾ����ҵ�����ͨ�ص÷ּ�¼
				InputOutputStreamUtil.deleteAccountRank(currentAccount);

				new HighestScoreJFrame(currentAccount, false).setVisible(true);
				(HighestScoreJFrame.this).dispose();

			}
		});
		btnNewButton.setFont(new Font("����", Font.PLAIN, 20));
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53EA\u770B\u6211\u81EA\u5DF1\u7684\u5F97\u5206");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// �鿴����Լ�����ʷ�÷�
				InputOutputStreamUtil.findAccountRank(currentAccount, data);

				new HighestScoreJFrame(currentAccount, true).setVisible(true);
				(HighestScoreJFrame.this).dispose();

			}
		});
		btnNewButton_1.setFont(new Font("����", Font.PLAIN, 20));
		panel.add(btnNewButton_1);

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// ˢ�±������
	private void flushTable() {
		// ׼�����ı��������
		title = new Object[] { "��Ϸ�ؿ�", "���", "��ߵ÷�" };
		data = new Object[][] { { "��������", "��������", "��������" }, { "��������", "��������", "��������" }, { "��������", "��������", "��������" },
				{ "��������", "��������", "��������" }, { "��������", "��������", "��������" }, { "��������", "��������", "��������" } };

		// ��ȡ����data����
		if (!lookMe) {
			InputOutputStreamUtil.getHighestScore(data);
		} else {
			InputOutputStreamUtil.findAccountRank(currentAccount, data);
		}

		jtable = new JTable(data, title);
	}

	// ���عؿ�ѡ��
	private void gotoChoice() {
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // �ͷ���Դ
	}
}

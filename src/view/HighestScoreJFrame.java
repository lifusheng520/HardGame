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

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            ��ǰ����˺�
	 */
	public HighestScoreJFrame(String account) {
		// ���õ�ǰ����˺�
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
		label.setFont(new Font("����", Font.PLAIN, 25));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label, BorderLayout.NORTH);

		JButton button = new JButton("\u8FD4  \u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoChoice();
			}
		});
		button.setFont(new Font("����", Font.PLAIN, 20));
		contentPane.add(button, BorderLayout.SOUTH);

		// ׼�����ı��������
		title = new Object[] { "��Ϸ�ؿ�", "���", "��ߵ÷�" };
		data = new Object[][] { { "��������", "��������", "��������" }, { "��������", "��������", "��������" }, { "��������", "��������", "��������" },
				{ "��������", "��������", "��������" }, { "��������", "��������", "��������" }, { "��������", "��������", "��������" } };

		// ��ȡ����data����
		InputOutputStreamUtil.getHighestScore(data);

		JTable jtable = new JTable(data, title);
		jtable.setFont(new Font("����", Font.BOLD, 20));
		jtable.setRowHeight(30);

		// �������ӵ�������
		contentPane.add(new JScrollPane(jtable), BorderLayout.CENTER);

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// ���عؿ�ѡ��
	private void gotoChoice() {
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // �ͷ���Դ
	}
}

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
 * �û��������д���ģ��
 * 
 * @author ���
 * @version V1.0
 */
public class RankJFrame extends JFrame {

	/**
	 * ��¼��汾
	 */
	public static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String currentAccount;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            ��ǰ����˺�
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

		// ��ӱ����ǩ
		JLabel lblNewLabel = new JLabel("\u73A9\u5BB6\u79EF\u5206Top5\u6392\u884C");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		// ׼�����ı��������
		Object title[] = { "����", "���", "�ۼƻ���" };
		Object data[][] = { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null },
				{ null, null, null } };

		// �������ݿ��������
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		// ���±������
		if (!dbdi.rankQuery(data)) {
			JOptionPane.showMessageDialog(contentPane, "����һ��С���⣬û��ѯ�����ݣ�");
		}

		// ��ɲ��� �ر����� �ͷ���Դ
		dbdi.close();

		JTable jtable = new JTable(data, title);
		jtable.setFont(new Font("����", Font.BOLD, 20));
		jtable.setRowHeight(30);
		// �������ӵ�������
		contentPane.add(new JScrollPane(jtable), BorderLayout.CENTER);

		JButton jbutton = new JButton("����");
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������Ϸ��ʼ����
				new StartJFrame(currentAccount).setVisible(true);
				(RankJFrame.this).dispose();// �ͷŴ�����Դ
			}
		});
		jbutton.setFont(new Font("����", Font.BOLD, 18));
		contentPane.add(jbutton, BorderLayout.SOUTH);

		// ���ò��ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}
}

package view;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.InputOutputStreamUtil;
import util.JFrameToolUtil;

/**
 * ��Ϸ��������
 * 
 * @author ���
 *
 */
public class GameRulesJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// ��Ϸ���˺�
	private String currentAccount;
	// ��ʾ����
	private JEditorPane editorPane;

	/**
	 * Create the frame.
	 */
	public GameRulesJFrame(String account) {
		// �����˺�
		this.currentAccount = account;

		setIconImage(Toolkit.getDefaultToolkit().getImage(GameRulesJFrame.class.getResource("/resource/title.png")));
		setTitle("Rules");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("\u6211\u77E5\u9053\u4E86");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backChoice();
			}
		});
		btnNewButton.setBounds(155, 225, 113, 27);
		contentPane.add(btnNewButton);

		editorPane = new JEditorPane();
		editorPane.setBounds(14, 13, 416, 203);
		contentPane.add(editorPane);

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));

		// ��ʾ����
		InputOutputStreamUtil.getRules(editorPane);
	}

	// ���عؿ�ѡ��
	private void backChoice() {
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose();// �ͷŴ���
	}
}

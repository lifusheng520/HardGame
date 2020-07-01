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
	 * ����һ���򵥵�ʯͷ������Ϸ
	 * 
	 * @author ���
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// �췽�������������
	private int redNumber;
	private int blueNumber;

	// ��ǰ���û��˺�
	private String currentAccount;
	// ��ʤ��
	private String winner;
	// ��Ҹ����Ĵ�
	private String answer;
	// ��ȷ�ĸ���
	private int rightCount = 0;
	// �췽���������������Ӧ��ͼƬ����ʾ��ǩ
	private JLabel redLabel;
	private JLabel blueLabel;
	// HashMap��ֵ�Դ洢ÿ����Ҷ�Ӧ��ͼƬ
	private HashMap<JLabel, Integer> hm = new HashMap<JLabel, Integer>();
	// ��ʾ������ǩ
	private JLabel showScore;

	/**
	 * Create the frame.
	 */
	public Game0(String account) {
		
		// ������Ϸ������
		PlayMusicUtil.loadGameMusic(1);
		
		setTitle("\u6700\u5F3A\u6BD4\u62FC");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Game0.class.getResource("/resource/title.png")));
		// ���õ�ǰ����˺�
		this.currentAccount = account;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u7EA2\u65B9");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("����", Font.PLAIN, 20));
		label.setBounds(32, 13, 101, 42);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u84DD\u65B9");
		label_1.setFont(new Font("����", Font.PLAIN, 20));
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
		lblVs.setFont(new Font("����", Font.PLAIN, 24));
		lblVs.setHorizontalAlignment(SwingConstants.CENTER);
		lblVs.setBounds(149, 91, 72, 42);
		contentPane.add(lblVs);

		JButton redWin = new JButton("\u7EA2\u65B9\u80DC\u5229");
		redWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��Ҹ����� �� ������Ϸ�ж�
				answer = "red";
				// ������Ϸ
				gameRuning();
			}
		});
		redWin.setBounds(14, 179, 101, 27);
		contentPane.add(redWin);

		JButton draw = new JButton("\u5E73\u5C40");
		draw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��Ҹ����� �� ������Ϸ�ж�
				answer = "draw";
				// ������Ϸ
				gameRuning();
			}
		});
		draw.setBounds(136, 179, 94, 27);
		contentPane.add(draw);

		JButton blueWin = new JButton("\u84DD\u65B9\u80DC\u5229");
		blueWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��Ҹ����� �� ������Ϸ�ж�
				answer = "blue";
				// ������Ϸ
				gameRuning();
			}
		});
		blueWin.setBounds(250, 179, 101, 27);
		contentPane.add(blueWin);

		JButton btnNewButton_1_1 = new JButton("\u8FD4\u56DE");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����û������˾��˳���Ϸ
				if (JOptionPane.showConfirmDialog(contentPane, "Ҫ����һ����") == 1) {
					
					// �ر�����
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

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));

		// ��ʼ����Ϸ����
		initGame();
	}

	// ��ʼ����Ϸ����
	private void initGame() {
		// ���������
		this.setNumber();
		// ��ȡ��Ӧ��ͼƬ
		this.getPic();
		// ��ʾ��ǰ�ĵ÷�
		showScore.setText("��ԣ�" + this.rightCount);

	}

	// ˢ����Ϸ
	private void flushGame() {
		// ������ǰ��Ϸ
		this.gameReferee();
		// ���HashMap�е����м�ֵ��
		this.hm.clear();
		// ��ʼ����Ϸ����
		initGame();
	}

	// ������Ϸ
	private void gameRuning() {

		// ˢ����Ϸ
		flushGame();

		// �����Ϸ�Ƿ�ﵽͨ������
		if (this.rightCount == 3) {

			// ��¼�����ʷ�÷�
			InputOutputStreamUtil.insertDataFile(1, this.currentAccount, this.rightCount);

			// �������ݿ����ʵ�������
			DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

			// ������һ��
			dbdi.openNextLevel(this.currentAccount, 1);

			// �û�ѡ���Ƿ񱣴�÷�
			if (JOptionPane.showConfirmDialog(contentPane, "��ϲ������ˣ���һ���ѿ�����Ҫ������ֵ÷���", "Save score option",
					JOptionPane.YES_NO_OPTION) == 0) {

				// �����֮ǰ�Ļ��������ڵĻ�����Ӻ���ӵ��˻���
				this.rightCount += dbdi.getScore(this.currentAccount);

				// ������ҵĻ��ֱ��浽account�˻���
				if (dbdi.update(this.currentAccount, this.rightCount)) {
					JOptionPane.showMessageDialog(contentPane, "����ɹ�!");
				} else {
					JOptionPane.showMessageDialog(contentPane, "������һ��С���⣬���ݱ���ʧ��!");
				}

				// ��ɲ��� �ر����� �ͷ���Դ
				dbdi.close();
			}
			
			// �ر���Ϸ����
			PlayMusicUtil.stopMusic();
			
			if (JOptionPane.showConfirmDialog(contentPane, "Ҫ����һ����", "Win", JOptionPane.YES_NO_OPTION) == 0) {
				// ����������Ϸ����
				new Game0(this.currentAccount).setVisible(true);
				// ��ǰ�����ͷ�
				this.dispose();
			} else {
				// ���عؿ�ѡ��
				new ChoiceJFrame(this.currentAccount).setVisible(true);
				this.dispose(); // �ͷŵ�ǰ����
			}
		}
	}

	// Ϊ˫�����������
	private void setNumber() {
		// �����̳߳�
		ExecutorService pool = Executors.newFixedThreadPool(2);

		// ���뵽�̳߳������㲢���ս��
		Future<Integer> num1 = pool.submit(new RandomNumberUtil());
		Future<Integer> num2 = pool.submit(new RandomNumberUtil());

		try {
			// ��ȡ�����߳��е������
			this.redNumber = num1.get();
			this.blueNumber = num2.get();

			// ����ǩ��Ӧ���������ӵ�HashMap������
			this.hm.put(redLabel, this.redNumber);
			this.hm.put(blueLabel, this.blueNumber);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// �ر��̳߳�
		pool.shutdown();
	}

	// ��ȡ��Ӧ��ͼƬ
	private void getPic() {
		// ��ȡHashMap�ļ�ֵ�Զ���
		Set<Map.Entry<JLabel, Integer>> result = this.hm.entrySet();
		// ͨ��Set���ϾͿɵõ���ֵ��
		for (Map.Entry<JLabel, Integer> set : result) {
			// ͨ��ÿ������ֵ�ҵ���Ӧ��ͼƬ ������������ͼƬ
			if (set.getValue() == 0)
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/bu.png")));
			else if (set.getValue() == 1)
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/jd.png")));
			else
				set.getKey().setIcon(new ImageIcon(Game0.class.getResource("/resource/st.png")));
		}
	}

	// ��Ϸ����
	private void gameReferee() {
		// �ж�������ʤ����
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

		// ʤ���ʹ��Ƿ�һ��
		if (this.winner.equals(this.answer)) {
			// ��Ե�������һ
			this.rightCount++;
		}
	}
}

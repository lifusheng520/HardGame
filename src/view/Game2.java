package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.GameTimerDao;
import dao.impl.DataBaseDaoImpl;
import util.GameTimerUtil;
import util.InputOutputStreamUtil;
import util.JFrameToolUtil;
import util.PlayMusicUtil;

/**
 * һ���򵥵ļ�ʱ�Ȱ�����Ϸ���ȵĴ���Խ�ӽ�37����Խ��
 * 
 * @author ���
 * @version V1.0
 */

public class Game2 extends JFrame implements GameTimerDao {

	/**
	 * ��¼��汾
	 */
	public static final long serialVersionUID = 1L;

	private JPanel contentPane;

	// ��ǰ����˺�
	private String currentAccount;

	// ����ʱ��ǩ
	private JLabel timeLabel;

	// ��¼��ǰ��Ϸ״̬
	private boolean gameRun = false;

	// ��Ϸ��ʼ��ť
	private JButton startGame;

	// ������ʾ��ǩ
	private JLabel beatLabel;

	// ��ǰ�÷�
	private int score;

	// �ҷ���ǩ
	private JLabel moodLabel;

	// ��ʾ�÷ֱ�ǩ
	private JLabel scoreLabel;

	// �������
	private int times;
	// ������ʾ��ǩ
	private JLabel timesLabel;

	// ��Ϸ�Ƿ����
	private boolean gameOver;

	// ��ʱ���������
	private GameTimerUtil task;

	/**
	 * Create the frame.
	 * 
	 * @param account
	 *            ��ǰ����˺�
	 */
	public Game2(String account) {

		// ������Ϸ������
		PlayMusicUtil.loadGameMusic(3);

		// ���õ�ǰ�˺�
		this.currentAccount = account;
		this.times = 0;
		this.gameOver = false;

		setIconImage(Toolkit.getDefaultToolkit().getImage(Game2.class.getResource("/resource/bu.png")));
		setTitle("\u5927\u5DF4\u638C");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel playerNameLabel = new JLabel("��ң�" + this.currentAccount);
		playerNameLabel.setBounds(440, 50, 119, 18);
		contentPane.add(playerNameLabel);

		timeLabel = new JLabel("20");
		timeLabel.setFont(new Font("����", Font.PLAIN, 40));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(431, 89, 128, 92);
		contentPane.add(timeLabel);

		startGame = new JButton("\u5F00\u59CB");
		startGame.setFont(new Font("����", Font.BOLD, 55));
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �����Ϸ��û�н���
				if (!gameOver)
					// �����Ϸû�п�ʼ
					if (!gameRun) {
						gameRun(); // ������Ϸ
						gameRun = true; // ��¼��ǰ��Ϸ״̬Ϊ����״̬
						// �л���ť״̬
						startGame.setText("����");
					} else {
						showBeat(); // ��ʾ����
						closeMood(); // �ر�����
						times++;
					}
			}
		});
		startGame.setBounds(388, 235, 180, 95);
		contentPane.add(startGame);

		scoreLabel = new JLabel("\u5F97\u5206\uFF1A0");
		scoreLabel.setBounds(440, 13, 128, 27);
		contentPane.add(scoreLabel);

		beatLabel = new JLabel("");
		beatLabel.setIcon(new ImageIcon(Game2.class.getResource("/resource/beat.png")));
		beatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		beatLabel.setBounds(36, 81, 100, 100);
		beatLabel.setVisible(false);
		contentPane.add(beatLabel);

		timesLabel = new JLabel("\u6B21\u6570 X 0");
		timesLabel.setBounds(161, 13, 72, 18);
		contentPane.add(timesLabel);

		moodLabel = new JLabel("*%~ ***%$#");
		moodLabel.setFont(new Font("����", Font.PLAIN, 25));
		moodLabel.setForeground(Color.RED);
		moodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moodLabel.setBounds(14, 194, 145, 43);
		contentPane.add(moodLabel);

		JLabel bgPicLabel = new JLabel("");
		bgPicLabel.setIcon(new ImageIcon(Game2.class.getResource("/resource/dabz.png")));
		bgPicLabel.setBounds(14, 13, 360, 270);
		contentPane.add(bgPicLabel);

		JButton btnNewButton = new JButton("\u4E0B\u6B21\u518D\u73A9");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// �ر�����
				PlayMusicUtil.stopMusic();

				// �����Ϊ��˵����ʱ�������Ѿ�����
				if (task != null)
					task.closeTask(); // �ر�����

				(Game2.this).dispose();// �ͷŵ�ǰ��Ϸ����
				// �˳���Ϸ����ѡ��
				new ChoiceJFrame(currentAccount).setVisible(true);
			}
		});
		btnNewButton.setBounds(14, 315, 113, 27);
		contentPane.add(btnNewButton);

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	// ������Ϸ
	@Override
	public void gameRun() { // ������Ϸ

		// ������ʱ�������������ʱ��Ϊ20��
		this.task = new GameTimerUtil(this, this.timeLabel, 20);

		// ������ʱ������
		this.task.startRun();
	}

	// ��ʾ����
	private void showBeat() {
		beatLabel.setVisible(true);
	}

	// �رջ�����ʾ
	private void closeBeat() {
		this.beatLabel.setVisible(false);
	}

	// ��ʾ��ǰ�÷�
	private void showScore() {
		// ����÷� ����37��Խ���÷�Խ��
		this.score = 6 - Math.abs(37 - this.times) / 6;
		this.scoreLabel.setText("�÷֣�" + this.score);
	}

	// ��ʾ�������
	private void showTimes() {
		this.timesLabel.setText("���� X " + this.times);
	}

	// ��ʾ���Ա�ǩ
	private void showMood() {
		this.moodLabel.setVisible(true);
	}

	// �ر������ǩ
	private void closeMood() {
		this.moodLabel.setVisible(false);
	}

	// ˢ����Ϸ
	@Override
	public void flushGame() {
		// �رջ���
		closeBeat();
		// ��ʾ����
		showMood();
		// ��ʾ�÷�
		showScore();
		// ��ʾ����
		showTimes();
	}

	// ��Ϸ����
	@Override
	public void countGame() {

		this.gameOver = true; // �����Ϸ����

		// ��¼�����ʷ�÷�
		InputOutputStreamUtil.insertDataFile(3, this.currentAccount, this.score);

		// ������Ϸ����
		this.saveGame();

		// �ر�����
		PlayMusicUtil.stopMusic();

		this.dispose();// �ͷŵ�ǰ��Ϸ����
		// ���عؿ�ѡ��
		new ChoiceJFrame(this.currentAccount).setVisible(true);
	}

	private void saveGame() {
		// �������ݿ����ʵ�ֶ���
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		if (JOptionPane.showConfirmDialog(contentPane, "��Ϸ��������Ҫ����÷���", "Save",
				JOptionPane.YES_NO_CANCEL_OPTION) == 0) {

			// �ۼӻ���
			int sumScore = this.score + dbdi.getScore(this.currentAccount);

			// ����÷ֵ�����˻�
			if (new DataBaseDaoImpl().update(this.currentAccount, sumScore))
				JOptionPane.showMessageDialog(contentPane, "����ɹ���");
			else
				JOptionPane.showMessageDialog(contentPane, "����ʧ�ܣ�");
		}

		// �ж��Ƿ񵽴�ͨ������
		if (this.score >= 5) {

			JOptionPane.showMessageDialog(contentPane, "��ϲ���أ��Ѿ�������һ�أ�");

			// ������һ��
			if (dbdi.openNextLevel(this.currentAccount, 3)) {

				// ��¼�����ʷ�÷�
				InputOutputStreamUtil.insertDataFile(3, this.currentAccount, this.score);
			} else {

				// ����Ѿ�ͨ�����޸ĵ÷ּ���
				InputOutputStreamUtil.updateAccountScore(currentAccount, 3, this.score);
			}

		} else {
			JOptionPane.showMessageDialog(contentPane, "���ź�������ʧ�ܣ�");
		}

		// ��ɲ��� �ر����� �ͷ���Դ
		dbdi.close();
	}
}

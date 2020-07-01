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

import dao.GameTimerDao;
import dao.impl.DataBaseDaoImpl;
import util.GameTimerUtil;
import util.InputOutputStreamUtil;
import util.JFrameToolUtil;
import util.PlayMusicUtil;
import util.RandomNumberUtil;

public class Game1 extends JFrame implements GameTimerDao {

	/**
	 * ��Ϸ��ʱ�����ƣ��ڹ涨ʱ���ڰ���һ����������أ�������֣����Խ��÷�Խ��
	 * 
	 * @author ���
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// ��ǰ��ҵ��˺�
	private String currentAccount;

	// ��ʾ����ʱ�ı�ǩ
	private JLabel clockLabel;

	// �˻�����ҵ������
	private int robotNumber;
	private int playerNumber;

	// �������Ӧ��ͼƬ����ʾ��ǩ
	private JLabel playerLabel;
	private JLabel robotLabel;

	// HashMap���ϴ洢��ǩ���������ֵ��
	HashMap<JLabel, Integer> hm;

	// ��Ϸ�Ƿ��Ѿ���ʼ��
	private boolean flag = false;

	// ��Ϸ�÷�
	private int score = 0;

	// ��ʾ�÷ֱ�ǩ
	private JLabel scoreLabel;

	// ��Ϸ��ť
	private JButton btnNewButton;

	// ��Ϸ�Ƿ����
	private boolean isCount;

	// ��Ϸ�Ƿ��Ѿ�ˢ�� �ɵ÷�״̬
	private boolean isUpdate;

	// ��ʾ�������ı�ǩ
	private JLabel judgeLabel;

	// ��ʱ���������
	private GameTimerUtil task;

	/**
	 * Create the frame.
	 */
	public Game1(String account) {

		// ������Ϸ������
		PlayMusicUtil.loadGameMusic(2);

		// ���õ�ǰ�˺�
		this.currentAccount = account;
		this.hm = new HashMap<JLabel, Integer>();
		this.isCount = false;
		this.isUpdate = true;

		setIconImage(Toolkit.getDefaultToolkit().getImage(Game1.class.getResource("/resource/game1.png")));
		setTitle("\u77F3\u5934\u526A\u5200\u5E03\u770B\u8C01\u51FA\u5F97\u5FEB");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		playerLabel = new JLabel("");
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerLabel.setBounds(374, 41, 100, 100);
		contentPane.add(playerLabel);

		robotLabel = new JLabel("");
		robotLabel.setHorizontalAlignment(SwingConstants.CENTER);
		robotLabel.setBounds(118, 41, 100, 100);
		contentPane.add(robotLabel);

		JLabel robotName = new JLabel("\u773C\u75BE\u4E14\u624B\u5FEB27");
		robotName.setBounds(14, 13, 123, 18);
		contentPane.add(robotName);

		JLabel playerName = new JLabel();
		playerName.setText("��ң�" + currentAccount); // ������Ϸ�е��������
		playerName.setHorizontalAlignment(SwingConstants.RIGHT);
		playerName.setBounds(463, 12, 118, 18);
		contentPane.add(playerName);

		btnNewButton = new JButton("\u91CD\u62F3\u51FA\u51FB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���û���������������ֹ����ˢ��
				if (!isCount)
					if (!flag) {// �����Ϸδ��ʼ
						flag = true;// ��¼��ϷΪ��ʼ״̬
						gameRun();
					} else {
						gameReferee();
						// ˢ�µ÷�
						scoreLabel.setText("�÷֣�" + score);
					}

				// ���һ�κ�����Ϊ���ɵ÷�״̬����ֹһֱ�����μƷ�
				isUpdate = false;
			}
		});
		btnNewButton.setFont(new Font("����", Font.PLAIN, 23));
		btnNewButton.setBounds(429, 160, 153, 51);
		contentPane.add(btnNewButton);

		JLabel robotPic = new JLabel("");
		robotPic.setIcon(new ImageIcon(Game1.class.getResource("/resource/robot.png")));
		robotPic.setBounds(14, 43, 80, 80);
		contentPane.add(robotPic);

		JLabel playerPic = new JLabel("");
		playerPic.setIcon(new ImageIcon(Game1.class.getResource("/resource/player.png")));
		playerPic.setBounds(498, 45, 80, 80);
		contentPane.add(playerPic);

		JLabel vsLabel = new JLabel("VS");
		vsLabel.setFont(new Font("����", Font.BOLD, 30));
		vsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vsLabel.setBounds(269, 61, 54, 35);
		contentPane.add(vsLabel);

		clockLabel = new JLabel("10");
		clockLabel.setFont(new Font("����", Font.PLAIN, 20));
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setBounds(216, 13, 160, 35);
		contentPane.add(clockLabel);

		JButton btnNewButton_1 = new JButton("\u800D\u8D56\u6211\u4E0D\u73A9\u4E86");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// �ر�����
				PlayMusicUtil.stopMusic();

				// �����Ϊ��˵�������ѿ�����
				if (task != null)
					task.closeTask();	// �ر�����

				// ����ѡ��ؿ�
				new ChoiceJFrame(currentAccount).setVisible(true);
				(Game1.this).dispose();
			}
		});
		btnNewButton_1.setBounds(14, 184, 135, 27);
		contentPane.add(btnNewButton_1);

		scoreLabel = new JLabel("�÷֣�0");
		scoreLabel.setFont(new Font("����", Font.PLAIN, 18));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel.setBounds(343, 0, 100, 34);
		contentPane.add(scoreLabel);

		judgeLabel = new JLabel("");
		judgeLabel.setFont(new Font("����", Font.PLAIN, 40));
		judgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		judgeLabel.setBounds(213, 131, 166, 80);
		contentPane.add(judgeLabel);

		// ���ɸ��Ĵ����С
		this.setResizable(false);

		// ���ô������
		this.setLocation(JFrameToolUtil.centerLocation(new Point(this.getWidth(), this.getHeight())));
	}

	@Override
	public void gameRun() {	// ������Ϸ
		// ���İ�ť״̬
		this.btnNewButton.setText("��ȭ");

		// ������ʱ����
		this.task = new GameTimerUtil(this, this.clockLabel, 10);

		// ��������
		this.task.startRun();

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
			this.robotNumber = num1.get();
			this.playerNumber = num2.get();

			// ����ǩ��Ӧ���������ӵ�HashMap������
			this.hm.put(this.robotLabel, this.robotNumber);
			this.hm.put(this.playerLabel, this.playerNumber);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// �ر��̳߳�
		pool.shutdown();
	}

	public void flushGame() {

		this.setNumber();
		this.shiftPic();

		// ��Ϸ����ˢ�º���Լ����Ʒ���
		this.isUpdate = true;
		// һ�ֹ�����ʾ��ʧ
		this.judgeLabel.setText("");
	}

	// �л�ͼƬ����
	private void shiftPic() {
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

	// ��������Ƿ�ɵ÷�
	private void gameReferee() {
		// ��ǰΪ���ɵ÷�״̬,���ز�������
		if (!this.isUpdate)
			return;

		boolean win = false;
		// �ж�����Ƿ�ʤ��
		if (this.playerNumber == 1 && this.robotNumber == 0)
			win = true;
		else if (this.playerNumber == 2 && this.robotNumber == 1)
			win = true;
		else if (this.playerNumber == 0 && this.robotNumber == 2)
			win = true;

		// ���ʤ��,�ҵ�ǰΪ�ɵ÷�״̬
		if (win) {
			this.score++;
			// �������Ϸ��ʾ
			this.judgeLabel.setText("ʤ�� +1");
		} else if (this.playerNumber == this.robotNumber) {
			this.judgeLabel.setText("ƽ�� +0");
		} else {
			this.judgeLabel.setText("ʧ�� -1");
			// ʧ�ܵ÷ּ�һ
			this.score--;
		}
	}

	// ����÷�
	private void saveScore() {
		// �������ݿ�ʵ�ֶ���
		DataBaseDaoImpl dbdi = new DataBaseDaoImpl();

		// ��ȡ���֮ǰ�ĵ÷�
		this.score += dbdi.getScore(this.currentAccount);

		// ������ҵ÷�
		if (dbdi.update(this.currentAccount, this.score)) {
			JOptionPane.showMessageDialog(contentPane, "����ɹ�!");

		} else {
			JOptionPane.showMessageDialog(contentPane, "������һ��С���⣬���ݱ���ʧ��!");
		}

		dbdi.openNextLevel(this.currentAccount, 2);

		// ����ﵽ�˽�����һ�ص�����
		if (this.score >= 3) {

			// ������һ��

			JOptionPane.showMessageDialog(contentPane, "�÷ֱ���ɹ�����ϲ������һ�أ�");
		} else {
			JOptionPane.showMessageDialog(contentPane, "���ź�������ʧ�ܣ�");
		}

		// ��ɲ��� �ر����� �ͷ���Դ
		dbdi.close();
	}

	// ��Ϸ����
	public void countGame() {
		// �������
		this.isCount = true;

		// ��¼�����ʷ�÷�
		InputOutputStreamUtil.insertDataFile(2, this.currentAccount, this.score);

		// ����÷�
		this.saveScore();

		// �ر�����
		PlayMusicUtil.stopMusic();

		// ���عؿ�ѡ��
		new ChoiceJFrame(this.currentAccount).setVisible(true);
		this.dispose(); // �ͷŵ�ǰ������Դ
	}
}

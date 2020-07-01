package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JEditorPane;

import pojo.Player;

/**
 * ��IO���߰�
 * 
 * @author ���
 *
 */

public class InputOutputStreamUtil {

	// ��װ��߷��ļ�·��
	private static final String highestfile = "src" + File.separator + "dataFile" + File.separator + "highest.txt";

	// ��װ��Ϸ����˵���ļ�·��
	private static final String rulesfile = "src" + File.separator + "dataFile" + File.separator + "rules.txt";

	/**
	 * IO��ȡ�ļ������ؿ�Ϊ�������ţ���ҵ�����ͨ����Ϣ������TreeSet������
	 * Player���Ѿ�ʵ����Comparable�ӿڣ����÷���Ȼ�������TreeSet��
	 * ����ҵ�ÿ������Ӧ��TreeSet�����еĵ�һ��Ԫ�ؼ�Ϊ��ǰ�ؿ���߷����
	 */
	// ���ùؿ����Ʒ���洢ÿ���ؿ����û��ɼ�
	private static HashMap<String, TreeSet<Player>> dataMap;

	/**
	 * ��ȡ��Ϸ˵���ĵ�
	 * 
	 * @param editorPane
	 *            ��������
	 */
	public static void getRules(JEditorPane editorPane) {
		BufferedReader br = null;
		// �ַ�ƴ�Ӷ���
		StringBuffer sbuff = new StringBuffer();

		try {
			// �����ļ�����
			br = new BufferedReader(new FileReader(rulesfile));

			String s;
			// ��ȡ�ļ�
			while ((s = br.readLine()) != null) {
				sbuff.append(s).append(System.getProperty("line.separator"));// ƴ���ļ����� ����зָ���
			}
		} catch (FileNotFoundException e) {
			System.out.println("��Դ�ļ�ȱʧ��");
		} catch (IOException e) {
			System.out.println("��Դ�ļ���ȡʧ�ܣ�");
		} finally {
			if (br != null)
				try {
					// �ͷ���Դ
					br.close();
				} catch (IOException e) {
					System.out.println("�ļ���Դ�ͷ�ʧ�ܣ�");
				}
		}

		// ���������õ���ǩ
		editorPane.setText(sbuff.toString());
	}

	// ��ʼ��HashMap
	private static void initHashMap() {
		// ����HashMap�ռ�
		dataMap = new HashMap<String, TreeSet<Player>>();
		// Ϊÿһ���ؿ�����һ�� ArrayList���ϴ洢�ùؿ�����Ϣ
		for (int i = 1; i <= 6; i++)
			dataMap.put("Game" + i, new TreeSet<Player>());
	}

	// ����������ݼ�ֵ��
	private static void loadKeyValue() {

		initHashMap();

		BufferedReader br = null;
		try {
			// ���������ȡ�ļ�
			br = new BufferedReader(new FileReader(highestfile));

			// ��ȡHashMap�ļ���
			Set<String> set = dataMap.keySet();
			String line;
			while ((line = br.readLine()) != null) {
				// ���ؿ����ƺ������Ϣ����
				String[] lines = line.split(":");

				// �������Ԫ��
				for (String s : set)
					if (s.equals(lines[0])) { // �ҵ���ǰ����������Ӧ�ĵļ�
						String[] info = lines[1].split("=");
						dataMap.get(s).add(new Player(info[0], Integer.valueOf(info[1]))); // ��ȡ����ArrayList���󲢽������Ϣ��ӵ�ArrayList
						break;
					}
			}
		} catch (FileNotFoundException e) {
			System.out.println("�����ļ������ڣ�");
		} catch (IOException e) {
			System.out.println("��ȡ�ļ������쳣!");
		} finally {
			if (br != null)
				try {
					// �ͷ���Դ
					br.close();
				} catch (IOException e) {
					System.out.println("�ر��ļ�ʧ�ܣ�");
				}
		}
	}

	/**
	 * ��ȡ��ʷ������а�����
	 * 
	 * @param data
	 *            �������а�����
	 */
	public static void getHighestScore(Object data[][]) {
		// �������ݵ�HashMap��
		loadKeyValue();

		// �Լ����� ������浽ArrayList
		TreeSet<String> ts = new TreeSet<String>();
		for (String s : dataMap.keySet()) {
			ts.add(s);
		}

		int len = 0;
		// ͨ�����������HashMap����Ϊdata��������ֵ
		for (String s : ts) {
			data[len][0] = s; // ��ȡ�ؿ���Ϣ
			for (Player p : dataMap.get(s)) {// ͨ���ؿ���Ϣ�õ��ùؿ��з�����ߵ������Ϣ
				data[len][1] = p.getAccount();
				data[len][2] = p.getScore();
				break; // ������ģ�����ֻȡ����һ����Ϣ����
			}
			len++;
		}
	}

	/**
	 * ����һ��������(׷��)����ʷ��������ļ�
	 * 
	 * @param gamePass
	 *            ��Ϸ�Ĺؿ�
	 * @param account
	 *            ����˺�
	 * @param score
	 *            ��ҵ÷�
	 */
	public static void insertDataFile(int gamePass, String account, int score) {
		// �����ļ�д���������
		BufferedWriter bw = null;

		try {
			// ���������ȡ�ļ�
			bw = new BufferedWriter(new FileWriter(highestfile, true));
			// ƴ�����ݸ�ʽ
			StringBuffer sb = new StringBuffer("Game");
			sb.append(gamePass).append(":").append(account).append("=").append(score);
			bw.write(sb.toString()); // д������
			bw.newLine(); // ����
			bw.flush(); // ˢ��
		} catch (IOException e) {
			System.out.println("IO��д�쳣��");
		} finally {
			if (bw != null)
				try {
					// �ͷ���Դ
					bw.close();
				} catch (IOException e) {
					System.out.println("�ͷ�IO��Դ�쳣��");
				}
		}
	}
}

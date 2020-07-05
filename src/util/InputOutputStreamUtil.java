package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JEditorPane;

import pojo.Player;

/**
 * 简单IO工具包
 * 
 * @author 李福生
 *
 */

public class InputOutputStreamUtil {

	/**
	 * 封装最高分文件路径
	 */
	private static final String highestfile = "src" + File.separator + "dataFile" + File.separator + "highest.txt";

	/**
	 * 封装游戏规则说明文件路径
	 */
	private static final String rulesfile = "src" + File.separator + "dataFile" + File.separator + "rules.txt";

	/**
	 * IO读取文件，按关卡为键分类存放，玩家的所有通关信息保存在TreeSet集合中
	 * Player类已经实现了Comparable接口，按得分自然排序放入TreeSet中
	 * 最后找到每个键对应的TreeSet集合中的第一个元素即为当前关卡最高分玩家
	 */
	// 利用关卡名称分类存储每个关卡的用户成绩
	private static HashMap<String, TreeSet<Player>> dataMap;

	/**
	 * 缓存
	 */
	private static ArrayList<String> dataArray;

	/**
	 * 读取游戏说明文档
	 * 
	 * @param editorPane
	 *            接收内容
	 */
	public static void getRules(JEditorPane editorPane) {
		BufferedReader br = null;
		// 字符拼接对象
		StringBuffer sbuff = new StringBuffer();

		try {
			// 创建文件对象
			br = new BufferedReader(new FileReader(rulesfile));

			String s;
			// 读取文件
			while ((s = br.readLine()) != null) {
				sbuff.append(s).append(System.getProperty("line.separator"));// 拼接文件内容 添加行分隔符
			}
		} catch (FileNotFoundException e) {
			System.out.println("资源文件缺失！");
		} catch (IOException e) {
			System.out.println("资源文件读取失败！");
		} finally {
			if (br != null)
				try {
					// 释放资源
					br.close();
				} catch (IOException e) {
					System.out.println("文件资源释放失败！");
				}
		}

		// 将内容设置到标签
		editorPane.setText(sbuff.toString());
	}

	// 初始化HashMap
	private static void initHashMap() {
		// 开辟HashMap空间
		dataMap = new HashMap<String, TreeSet<Player>>();
		// 为每一个关卡分配一个 ArrayList集合存储该关卡的信息
		for (int i = 1; i <= 6; i++)
			dataMap.put("Game" + i, new TreeSet<Player>());
	}

	// 加载玩家数据键值对
	private static void loadKeyValue() {

		initHashMap();

		BufferedReader br = null;
		try {
			// 创建对象读取文件
			br = new BufferedReader(new FileReader(highestfile));

			// 获取HashMap的键集
			Set<String> set = dataMap.keySet();
			String line;
			while ((line = br.readLine()) != null) {
				// 将关卡名称和玩家信息分离
				String[] lines = line.split(":");

				// 分类添加元素
				for (String s : set)
					if (s.equals(lines[0])) { // 找到当前数据行所对应的的键
						String[] info = lines[1].split("=");
						dataMap.get(s).add(new Player(info[0], Integer.valueOf(info[1]))); // 获取键的ArrayList对象并将玩家信息添加到ArrayList
						break;
					}
			}
		} catch (FileNotFoundException e) {
			System.out.println("数据文件不存在！");
		} catch (IOException e) {
			System.out.println("读取文件发生异常!");
		} finally {
			if (br != null)
				try {
					// 释放资源
					br.close();
				} catch (IOException e) {
					System.out.println("关闭文件失败！");
				}
		}
	}

	/**
	 * 获取历史最高排行榜数据
	 * 
	 * @param data
	 *            接收排行榜数据
	 */
	public static void getHighestScore(Object data[][]) {
		// 导入数据到HashMap中
		loadKeyValue();

		// 对键排序 结果保存到TreeSet中
		TreeSet<String> ts = new TreeSet<String>();
		for (String s : dataMap.keySet()) {
			ts.add(s);
		}

		int len = 0;
		// 通过有序键遍历HashMap集合为data数组设置值
		for (String s : ts) {
			data[len][0] = s; // 获取关卡信息
			for (Player p : dataMap.get(s)) {// 通过关卡信息得到该关卡中分数最高的玩家信息
				data[len][1] = p.getAccount();
				data[len][2] = p.getScore();
				break; // 是有序的，所有只取出第一个信息即可
			}
			len++;
		}
	}

	/**
	 * 插入一条数据行(追加)到历史最高数据文件
	 * 
	 * @param gamePass
	 *            游戏的关卡
	 * @param account
	 *            玩家账号
	 * @param score
	 *            玩家得分
	 */
	public static void insertDataFile(int gamePass, String account, int score) {
		// 创建文件写入操作对象
		BufferedWriter bw = null;

		try {
			// 创建对象读取文件
			bw = new BufferedWriter(new FileWriter(highestfile, true));

			// 拼接数据格式
			StringBuffer sb = new StringBuffer("Game");
			sb.append(gamePass).append(":").append(account).append("=").append(score);

			bw.write(sb.toString()); // 写入数据
			bw.newLine(); // 换行
			bw.flush(); // 刷新
		} catch (IOException e) {
			System.out.println("IO读写异常！");
		} finally {
			if (bw != null)
				try {
					// 释放资源
					bw.close();
				} catch (IOException e) {
					System.out.println("释放IO资源异常！");
				}
		}
	}

	/**
	 * 删除account账号的文件中的记录
	 * 
	 * @param account
	 *            要删除的account
	 */
	public static void deleteAccountRank(String account) {
		if (account == null)
			return;

		dataArray = new ArrayList<String>();// 初始化缓存list

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			// 读取文件
			br = new BufferedReader(new FileReader(highestfile));
			String line = null;
			while ((line = br.readLine()) != null) {
				// 比对文件中的账号信息
				if (!(line.split(":")[1].split("=")[0].equals(account))) {
					dataArray.add(line);
				}
			}

			// 写入文件
			bw = new BufferedWriter(new FileWriter(highestfile));

			int k = 1; // 刷新标记

			for (String s : dataArray) {// 遍历ArrayList集合写入
				bw.write(s);
				bw.newLine();
				if (k % 200 == 0) // 写入200行刷新一次
					bw.flush();
				k++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				// 释放资源
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新文件中account账号的分数
	 * 
	 * @param account
	 *            要修改的账号
	 * @param pass
	 *            修改第pass关卡的得分
	 * @param score
	 *            要修改的分数
	 */
	public static void updateAccountScore(String account, int pass, int score) {
		if (account == null)
			return;
		if (pass <= 0 || pass > 6)
			return;

		dataArray = new ArrayList<String>(); // 初始化缓存list

		String gp = "Game" + pass;

		BufferedReader br = null;
		BufferedWriter bw = null;

		int k = 1; // 刷新标记
		boolean flag = true; // 未找到账号信息true

		try {
			// 读入数据行
			br = new BufferedReader(new FileReader(highestfile));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lines = line.split(":");
				String[] datastr = lines[1].split("=");

				// 是第pass关 并且 是account账号的数据行就修改 而且分数要比原来高
				if (lines[0].equals(gp) && datastr[0].equals(account)) {

					flag = false; // 找到了

					// 这次的得分比历史记录搞就添加修改
					if (Integer.parseInt(datastr[1]) < score)
						dataArray.add(line.split("=")[0] + "=" + score);

				} else { // 不是直接添加
					dataArray.add(line);
				}
			}

			// 写入数据
			bw = new BufferedWriter(new FileWriter(highestfile));
			for (String s : dataArray) {
				bw.write(s);
				bw.newLine();
				if (k % 1000 == 0) // 1000行刷新一次
					bw.flush();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 如果有没有找到账号数据行的情况 则 添加记录
		if (flag) {
			insertDataFile(pass, account, score);
		}
	}

	/**
	 * 查找用户的所有关卡历史得分
	 * 
	 * @param account
	 *            要查找的账号
	 * @param data
	 *            将查找的数据通过形参的引用传递回去
	 */
	public static void findAccountRank(String account, Object[][] data) {
		if (account == null)
			return;

		initHashMap();

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(highestfile));
			String line = null;
			while ((line = br.readLine()) != null) {

				// 查找account的数据行
				if (line.split(":")[1].split("=")[0].equals(account)) {

					String[] str = line.split(":")[1].split("=");

					dataMap.get(line.split(":")[0]).add(new Player(str[0], Integer.valueOf(str[1])));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 对键排序 结果保存到TreeSet中
		TreeSet<String> ts = new TreeSet<String>();
		for (String s : dataMap.keySet()) {
			ts.add(s);
		}

		// 添加列标题
		for (String s : ts) {
			int k = s.split(":")[0].codePointAt(4) - '0' - 1;
			data[k][0] = s;
		}

		for (String s : ts) {
			for (Player p : dataMap.get(s)) {
				int k = s.split(":")[0].codePointAt(4) - '0' - 1;
				data[k][0] = s;
				data[k][1] = p.getAccount();
				data[k][2] = p.getScore();
				break; // 选出一个
			}
		}
	}

}

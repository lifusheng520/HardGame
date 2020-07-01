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
 * 简单IO工具包
 * 
 * @author 李福生
 *
 */

public class InputOutputStreamUtil {

	// 封装最高分文件路径
	private static final String highestfile = "src" + File.separator + "dataFile" + File.separator + "highest.txt";

	// 封装游戏规则说明文件路径
	private static final String rulesfile = "src" + File.separator + "dataFile" + File.separator + "rules.txt";

	/**
	 * IO读取文件，按关卡为键分类存放，玩家的所有通关信息保存在TreeSet集合中
	 * Player类已经实现了Comparable接口，按得分自然排序放入TreeSet中
	 * 最后找到每个键对应的TreeSet集合中的第一个元素即为当前关卡最高分玩家
	 */
	// 利用关卡名称分类存储每个关卡的用户成绩
	private static HashMap<String, TreeSet<Player>> dataMap;

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

		// 对键排序 结果保存到ArrayList
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
}

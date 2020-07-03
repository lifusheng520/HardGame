package addData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddFileData {

	/**
	 * 封装最高分文件路径
	 */
	private static final String highestfile = "src" + File.separator + "dataFile" + File.separator + "highest.txt";

	public void addData() {

		BufferedWriter bw = null;

		int k; // 关卡标记

		try {
			bw = new BufferedWriter(new FileWriter(highestfile));

			for (int i = 1; i <= 10000; i++) {

				// 随机关卡
				k = (int) (Math.random() * 100 - 1) % 3;

				StringBuffer sb = new StringBuffer("Game");

				// 拼接关卡和账号
				sb.append(k + 1).append(":account").append((int) (Math.random() * 10000));

				double random = Math.random();

				// 拼接得分
				sb.append("=").append((int) (random * 10000) - (int) (random * 10000) / ((int) (random * 10000) / 4 + 1));

				// 写入文件
				bw.write(String.valueOf(sb));
				bw.newLine();

				if (i % 100 == 0)
					bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
}

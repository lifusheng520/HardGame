package addData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddFileData {

	/**
	 * ��װ��߷��ļ�·��
	 */
	private static final String highestfile = "src" + File.separator + "dataFile" + File.separator + "highest.txt";

	public void addData() {

		BufferedWriter bw = null;

		int k; // �ؿ����

		try {
			bw = new BufferedWriter(new FileWriter(highestfile));

			for (int i = 1; i <= 10000; i++) {

				// ����ؿ�
				k = (int) (Math.random() * 100 - 1) % 3;

				StringBuffer sb = new StringBuffer("Game");

				// ƴ�ӹؿ����˺�
				sb.append(k + 1).append(":account").append((int) (Math.random() * 10000));

				double random = Math.random();

				// ƴ�ӵ÷�
				sb.append("=").append((int) (random * 10000) - (int) (random * 10000) / ((int) (random * 10000) / 4 + 1));

				// д���ļ�
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

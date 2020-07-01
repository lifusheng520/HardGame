package pojo;

/**
 * �������ʵ����Ȼ���򣬹��򣺸��ݵ÷ֵĸߵͽ�������
 * 
 * @author ���
 *
 */
public class Player implements Comparable<Player> {
	/**
	 * ����
	 */
	private String account;

	/**
	 * ����
	 */
	private String password;

	/**
	 * �÷�
	 */
	private int score;

	/**
	 * �����˺ź͵÷ֹ���һ�������
	 * 
	 * @param account
	 *            ����˺�
	 * @param score
	 *            ��ҵ÷�
	 */
	public Player(String account, int score) {
		this.account = account;
		this.score = score;
	}

	/**
	 * �����˺����빹��һ�������
	 * 
	 * @param account
	 *            �˺�
	 * @param password
	 *            ����
	 */
	public Player(String account, String password) {
		this.account = account;
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String name) {
		this.account = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * ʵ��Comparable�ӿ�ʹ�õ÷���Ϊ�Ƚ�
	 */
	@Override
	public int compareTo(Player o) {
		return o.score - this.score;
	}

}

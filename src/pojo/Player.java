package pojo;

/**
 * 本类可以实现自然排序，规则：根据得分的高低降序排序
 * 
 * @author 李福生
 *
 */
public class Player implements Comparable<Player> {
	/**
	 * 姓名
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 得分
	 */
	private int score;

	/**
	 * 根据账号和得分构造一个玩家类
	 * 
	 * @param account
	 *            玩家账号
	 * @param score
	 *            玩家得分
	 */
	public Player(String account, int score) {
		this.account = account;
		this.score = score;
	}

	/**
	 * 根据账号密码构造一个玩家类
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
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
	 * 实现Comparable接口使用得分作为比较
	 */
	@Override
	public int compareTo(Player o) {
		return o.score - this.score;
	}

}

package model;

public class User {

	private String userNo;
	private String userName;
	private String userId;
	private String pass;
	private String pass_re;
	private String userpic;

	//登録用
	public User(String userName, String userId, String pass, String pass_re) {
		this.userName = userName;
		this.userId = userId;
		this.pass = pass;
		this.pass = pass_re;
	}

	public User(String userName, String userId, String pass, String pass_re, String userpic) {
		this.userName = userName;
		this.userId = userId;
		this.pass = pass;
		this.pass = pass_re;
		this.userpic = userpic;
	}

	//ログイン用
	public User(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
	}

	public String getUserName() { return userName; }
	public String getUserId() { return userId; }
	public String getPass() { return pass; }
	public String getPass_re() { return pass_re; }
	public String getUserPic() { return userpic; }

}

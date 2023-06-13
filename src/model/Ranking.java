package model;

public class Ranking {
	int point; // ポイント
	String userId; // ユーザーID
	String userName; // ユーザー名
	int userNo; // ユーザーNO

	public Ranking(int string, int userNo, String userName, String userId) {
		this.point = string;
		this.userNo = userNo;
		this.userId = userId;
		this.userName = userName;
	}

	// ゲッターセッター開始
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	// ゲッターセッター終了

}

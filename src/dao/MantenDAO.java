package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.MyCalendarLogic;
import model.Ranking;
import model.User;

public class MantenDAO {

	//Calendarクラスのオブジェクトを生成する
    Calendar cl = Calendar.getInstance();
    //SimpleDateFormatクラスでフォーマットパターンを設定する
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	//データベース接続に使用する情報
	private final String JDBC_URL="jdbc:h2:tcp://localhost/~/perfectDiary";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	public User execute(User user) {
		User user_info = null;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT * FROM MANTENUSER WHERE USERID = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  user.getUserId());
			pStmt.setString(2, user.getPass());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合
			//そのユーザーを表すAccountインスタンスを生成
			if(rs.next()) {
				//結果表からデータを取得
				String userNo = rs.getString("USERNO");
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				String userPass = rs.getString("PASSWORD");
				String userpic = rs.getString("PROFILE_IMG");
				user_info = new User(userName, userId, userPass, userPass, userpic);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		//見つかったユーザまたはnullを返す
		return user_info;
	}

	public boolean batch_process1() { // バッチ処理(事前処理)USERIDの数、と、今日の日付で登録されている日記の数が同じか確認する
		int userId_count = 0; // USERIDの数
		int diary_count = 0; // 日記の数

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			// 処理1 USERIDの数を取得
			String sql = "SELECT COUNT(USERID) AS COUNT FROM MANTENUSER";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				userId_count = Integer.parseInt(rs.getString("COUNT"));
			}

			System.out.println(userId_count);

			// 処理2 今日の日付で登録されている日記の数を取得
			String sql2 = "SELECT COUNT(USERID) AS COUNT2 FROM MANTENDIARY WHERE ENTRYDATE = ? ;";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);
			pStmt2.setString(1,  sdf.format(cl.getTime()));
			ResultSet rs2 = pStmt2.executeQuery();
			if(rs2.next()) {
				diary_count = Integer.parseInt(rs2.getString("COUNT2"));
			}
			System.out.println(diary_count);


			// デバッグ
//			System.out.println("バッチ処理デバッグ");
//			System.out.println(sdf.format(cl.getTime()));
//			System.out.println("USERID_COUNT:"+userId_count);
//			System.out.println("DIARY_COUNT:"+diary_count);

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userId_count == diary_count;
	}

	public void batch_process2() { // バッチ処理(追加処理)
		List<String> userId_array = new ArrayList<String>(); // USERIDを格納する用の配列

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			// 処理1 USERIDを全て取得する
			String sql = "SELECT USERID FROM MANTENUSER";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				userId_array.add(rs.getString("USERID"));
			}

			// 処理2 USERIDと日付を用いてバッチ処理
			String sql2 = "INSERT INTO MANTENDIARY (USERID,ENTRYDATE,CHECK1,CHECK2,CHECK3,CHECK4,CHECK5,COMMENT,POINT) VALUES (?,?,0,0,0,0,0,'','0');";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);

			for(int i=0; i < userId_array.size(); i++) {
				pStmt2.setString(1,  userId_array.get(i));
				pStmt2.setString(2,  sdf.format(cl.getTime()));
				pStmt2.executeUpdate();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public boolean register_user(User user) {
		int result;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//デバッグ用
//			java.lang.System.out.print(user.getUserId());
//			java.lang.System.out.print(user.getUserName());
//			java.lang.System.out.print(user.getPass());

			//INSERT文の準備(idは自動連番なので指定しなくてよい)
			String sql = "INSERT INTO MANTENUSER(USERID,USERNAME,PASSWORD,PROFILE_IMG) VALUES(?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1,  user.getUserId());
			pStmt.setString(2,  user.getUserName());
			pStmt.setString(3,  user.getPass());
			pStmt.setString(4,  user.getUserPic());

			result = pStmt.executeUpdate();

			java.lang.System.out.println(result);

			if(result != 1) {
				return false;
			}

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result == 1;
	}

	public Boolean userIdCheck(User user) {
		Boolean user_flag = true;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT * FROM MANTENUSER WHERE USERID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  user.getUserId());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合
			if(rs.next()) {
				user_flag = false;
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		//見つかったらfalse,見つからんかったらtrueを返す
		return user_flag;
	}

	public void register_diary(User user) {

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//INSERT文の準備
			String sql = "INSERT INTO MANTENDIARY (USERID,ENTRYDATE,CHECK1,CHECK2,CHECK3,CHECK4,CHECK5,COMMENT,POINT) VALUES (?,?,0,0,0,0,0,'','0');";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  user.getUserId());
			pStmt.setString(2,  sdf.format(cl.getTime()));
			pStmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean update(User user) {
		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "UPDATE MANTENUSER SET USERNAME = ?, PASSWORD = ? , PROFILE_IMG = ? WHERE USERID = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  user.getUserName());
			pStmt.setString(2,  user.getPass());
			pStmt.setString(3,  user.getUserPic());
			pStmt.setString(4,  user.getUserId());
			int result = pStmt.executeUpdate();

			if(result != 1) {
				return false;
			}

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(String userId) { //ユーザ削除処理
		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備(処理1:MANTENUSERから削除用)
			String sql1 = "DELETE FROM MANTENDIARY WHERE USERID = ? ;";
			PreparedStatement pStmt1 = conn.prepareStatement(sql1);
			pStmt1.setString(1,  userId);

			//SELECT文を準備(処理2:MANTENDIARYから削除用)
			String sql2 = "DELETE FROM MANTENUSER WHERE USERID = ? ;";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);
			pStmt2.setString(1,  userId);

			int result1 = pStmt1.executeUpdate();
			int result2 = pStmt2.executeUpdate();

			if(result2 != 1) {
				return false;
			}

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean input(List<String> list) {
		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

//			デバッグ用
//			for(int i = 1; i < 6; i++) {
//				total += Integer.parseInt(list.get(i));
//				System.out.println(total);
//			}

			//SELECT文を準備
			String sql = "SELECT * FROM MANTENDIARY WHERE USERID = ? AND ENTRYDATE = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  (String) list.get(0));
			pStmt.setString(2,  list.get(7));

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			if(rs.next() == false) { // 該当しない場合は、INSERT INTO

				System.out.println("いんさーーーと！");
				//SELECT文を準備
				String sql2 = "INSERT INTO MANTENDIARY (USERID,ENTRYDATE,CHECK1,CHECK2,CHECK3,CHECK4,CHECK5,COMMENT,POINT) VALUES (?,?,?,?,?,?,?,?,?);";
				PreparedStatement pStmt2 = conn.prepareStatement(sql2);

				pStmt2.setString(1,  (String) list.get(0));
				pStmt2.setString(2,  list.get(7));
				pStmt2.setString(3,  (String) list.get(1));
				pStmt2.setString(4,  (String) list.get(2));
				pStmt2.setString(5,  (String) list.get(3));
				pStmt2.setString(6,  (String) list.get(4));
				pStmt2.setString(7,  (String) list.get(5));
				pStmt2.setString(8,  (String) list.get(6));
				pStmt2.setString(9,  list.get(8));
				pStmt2.executeUpdate();
			}else { // 該当した場合は、UPDATE

				System.out.println("あっぷでーーーーーーと!");
				//SELECT文を準備
				String sql2 = "UPDATE MANTENDIARY SET CHECK1 = ?, CHECK2 = ?, CHECK3 = ?, CHECK4 = ?, CHECK5 = ?, COMMENT = ?,  POINT = ? WHERE USERID = ? AND ENTRYDATE = ?;";
				PreparedStatement pStmt2 = conn.prepareStatement(sql2);

				pStmt2.setString(1,  (String) list.get(1));
				pStmt2.setString(2,  (String) list.get(2));
				pStmt2.setString(3,  (String) list.get(3));
				pStmt2.setString(4,  (String) list.get(4));
				pStmt2.setString(5,  (String) list.get(5));
				pStmt2.setString(6,  (String) list.get(6));
				pStmt2.setString(7,  list.get(8));
				pStmt2.setString(8,  (String) list.get(0));
				pStmt2.setString(9,  list.get(7));
				pStmt2.executeUpdate();
			}

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[][] getDiary(String userId, int year, int month) {

		int count = 1;
		String[][] diary_data = null;

//		デバッグ用
//		System.out.println("--デバッグ用--");
//		System.out.println(month+"月");
//		System.out.println("--デバッグ用--");

		MyCalendarLogic cal = new MyCalendarLogic();
		int daysCount = cal.getDaysCount(year, month);
		String monthStr;

        if(String.valueOf( month ).length() == 1) {
        	monthStr = "0"+month;
        }else {
        	monthStr = Integer.valueOf(month).toString();
        }

        String first_date_str = null;
        String end_date_str = null;

        first_date_str = year+"-"+monthStr+"-01";
        end_date_str = year+"-"+monthStr+"-"+daysCount;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//SELECT文を準備
			String sql = "SELECT * FROM MANTENDIARY WHERE USERID = ? AND ENTRYDATE BETWEEN ? AND ? ORDER BY ENTRYDATE ASC;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  userId);
			pStmt.setString(2, first_date_str);
			pStmt.setString(3,  end_date_str);

			diary_data = new String[daysCount][8];

			for(int i = 0; i<diary_data.length; i++){
		        for(int j = 0; j<7; j++){
		            diary_data[i][j] = "";
		        }
			}

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while(rs.next()) {

				int sub = Integer.parseInt(rs.getDate("ENTRYDATE").toString().substring(rs.getDate("ENTRYDATE").toString().length() - 2))-1;

				// ENTRYDATE~COMMENTをdiary_dataに格納する
				diary_data[sub][0] = rs.getDate("ENTRYDATE").toString();
				for(int i = 1; i < 6; i++) { // CHECK1 ~ CHECK5の中身を確認し、20だった場合はcheckedを格納する
					if(rs.getString("CHECK"+i).equals("20")) {
						diary_data[sub][i] = ("checked");
					}else {
						diary_data[sub][i] = "";
					}
				}
				diary_data[sub][6] = rs.getString("COMMENT");
				diary_data[sub][7] = rs.getString("POINT");

//				デバッグ用
//				System.out.println(rs.getDate("ENTRYDATE").toString());
//				System.out.println(rs.getDate("ENTRYDATE").toString().substring(rs.getDate("ENTRYDATE").toString().length() - 2)); //末尾から2文字を取得
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return diary_data;
	}

	public List<String> check(List<String> list) {

		List<String> result = new ArrayList<String>();

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT * FROM MANTENDIARY WHERE USERID = ? AND ENTRYDATE = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  list.get(0));
			pStmt.setString(2, list.get(1));

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 0のデーターが作成されていない場合はrsがない為、whileループに入らない　結果jspの方でエラーになる為
			// else文で空白のデーターを作成する
			if(rs.next()) {
				//結果表からデータを取得
				for(int i = 1; i < 6; i++) { // CHECK1 ~ CHECK5の中身を確認し、20だった場合はcheckedを格納する
					if(rs.getString("CHECK"+i).equals("20")) {
						result.add("checked");
					}else {
						result.add("");
					}
				}
				result.add(rs.getString("COMMENT"));
			}else { // 該当データがなかった場合は、空白を格納する
				for(int i = 1; i < 7; i++) {
					result.add("");
				}
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public List getRanking(int year, int month) {

		int data_count = 0; // ユーザー数
		int count = 0; // 内側のループのカウント
	    List<Ranking> result = new ArrayList(); // USERID,USERNAME,POINT(月/平均) 初期化

		MyCalendarLogic cal = new MyCalendarLogic();
		int daysCount = cal.getDaysCount(year, month);
		String monthStr;

        if(String.valueOf( month ).length() == 1) {
        	monthStr = "0"+month;
        }else {
        	monthStr = Integer.valueOf(month).toString();
        }

        String first_date_str = null;
        String end_date_str = null;

        first_date_str = year+"-"+monthStr+"-01";
        end_date_str = year+"-"+monthStr+"-"+daysCount;

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT COUNT(USERID) AS COUNT FROM MANTENUSER";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				data_count = Integer.parseInt(rs.getString("COUNT"));
			}

//			デバッグ用
			System.out.println("ユーザーの数："+data_count);

			//SELECT文を準備
			String sql2 = "SELECT U.USERNO, D.USERID, U.USERNAME, SUM(D.POINT) AS M_POINT FROM MANTENDIARY AS D "
					+ "RIGHT JOIN MANTENUSER AS U ON D.USERID = U.USERID "
					+ "WHERE D.ENTRYDATE BETWEEN ? AND ? "
					+ "GROUP BY D.USERID "
					+ "ORDER BY M_POINT DESC";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);
			pStmt2.setString(1, first_date_str);
			pStmt2.setString(2,  end_date_str);

			//SELECT文を実行し、結果表を取得
			ResultSet rs2 = pStmt2.executeQuery();

			while(rs2.next()) {
				int days = 1;
				int point = 0;
				int point_m = 0;

				// 月のポイントは(その月の合計ポイント/レコード数)なので、レコード数をdaysに取得する
				String sql3 = "SELECT COUNT(D.USERID) AS D_COUNT FROM MANTENDIARY AS D RIGHT JOIN MANTENUSER AS U ON D.USERID = U.USERID WHERE D.USERID = ? AND D.ENTRYDATE BETWEEN ? AND ?";
				PreparedStatement pStmt3 = conn.prepareStatement(sql3);
				pStmt3.setString(1, rs2.getString("USERID"));
				pStmt3.setString(2, first_date_str);
				pStmt3.setString(3,  end_date_str);

				ResultSet rs3 = pStmt3.executeQuery();

				if(rs3.next()) {
					days = Integer.parseInt(rs3.getString("D_COUNT"));
					if(days == 0) {
						days = 1;
					}
				}

				point = Integer.parseInt(rs2.getString("M_POINT"));
				point_m = Math.floorDiv(point,days);

				result.add(new Ranking(point_m, Integer.valueOf(rs2.getString("USERNO")), rs2.getString("USERNAME"), rs2.getString("USERID")));

				count++; //ループカウントを増やす
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public List getRanking(int year) {

		int data_count = 0; // ユーザー数
		int count = 0; // 内側のループのカウント
	    List<Ranking> result = new ArrayList(); // USERID,USERNAME,POINT(月/平均) 初期化

		MyCalendarLogic cal = new MyCalendarLogic();

        String first_date_str = null;
        String end_date_str = null;

        first_date_str = year+"-01-01";
        end_date_str = year+"-12-31";

        System.out.print(first_date_str);

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT COUNT(USERID) AS COUNT FROM MANTENUSER";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				data_count = Integer.parseInt(rs.getString("COUNT"));
			}

//			デバッグ用
			System.out.println("ユーザーの数："+data_count);

			//SELECT文を準備
			String sql2 = "SELECT U.USERNO, D.USERID, U.USERNAME, SUM(D.POINT) AS M_POINT FROM MANTENDIARY AS D "
					+ "RIGHT JOIN MANTENUSER AS U ON D.USERID = U.USERID "
					+ "WHERE D.ENTRYDATE BETWEEN ? AND ? "
					+ "GROUP BY D.USERID "
					+ "ORDER BY M_POINT DESC";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);
			pStmt2.setString(1, first_date_str);
			pStmt2.setString(2,  end_date_str);

			//SELECT文を実行し、結果表を取得
			ResultSet rs2 = pStmt2.executeQuery();

			while(rs2.next()) {
				int days = 1;
				int point = 0;
				int point_m = 0;

				// 月のポイントは(その月の合計ポイント/レコード数)なので、レコード数をdaysに取得する
				String sql3 = "SELECT COUNT(D.USERID) AS D_COUNT FROM MANTENDIARY AS D RIGHT JOIN MANTENUSER AS U ON D.USERID = U.USERID WHERE D.USERID = ? AND D.ENTRYDATE BETWEEN ? AND ?";
				PreparedStatement pStmt3 = conn.prepareStatement(sql3);
				pStmt3.setString(1, rs2.getString("USERID"));
				pStmt3.setString(2, first_date_str);
				pStmt3.setString(3,  end_date_str);

				ResultSet rs3 = pStmt3.executeQuery();

				if(rs3.next()) {
					days = Integer.parseInt(rs3.getString("D_COUNT"));
					if(days == 0) {
						days = 1;
					}
				}

				point = Integer.parseInt(rs2.getString("M_POINT"));
				point_m = Math.floorDiv(point,days);

				result.add(new Ranking(point_m, Integer.valueOf(rs2.getString("USERNO")), rs2.getString("USERNAME"), rs2.getString("USERID")));

				count++; //ループカウントを増やす
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public List<String> userPicCheck() {

		List<String> result = new ArrayList<String>();

		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//SELECT文を準備
			String sql = "SELECT * FROM USERPIC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while(rs.next()) {
				//結果表からデータを取得
				result.add(rs.getString("URL"));
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return result;
	}


}

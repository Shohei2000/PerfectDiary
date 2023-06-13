package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DiaryInputCheck;

@WebServlet("/DiaryInput")
public class DiaryInput extends HttpServlet {

	//SimpleDateFormatクラスでフォーマットパターンを設定する
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession(false);
		String flag = request.getParameter("when");

	    //日付跨ぎエラー処理用
	    Date date = new Date();

		//デバッグ用
//		java.lang.System.out.println(flag);

		int x = 0;
		if (flag.equals("yesterday")) {
			x = -1;
		}

		Calendar cal = Calendar.getInstance(); //カレンダーインスタンス作成
		int year = cal.get(Calendar.YEAR); //年取得
		int month = cal.get(Calendar.MONTH) + 1; //月取得
		int day = cal.get(Calendar.DATE) + x; //日取得
		String monthStr = null;
		String dayStr = null;
		String sdf_data = null;

		// 一桁だった場合→二桁にする処理
		if(String.valueOf(month).length() == 1) {
			monthStr = "0"+month;
		}else {
			monthStr = String.valueOf(month);
		}

		if(String.valueOf(day).length() == 1) {
			dayStr = "0"+day;
		}else {
			dayStr = String.valueOf(day);
		}

		sdf_data = year+"-"+monthStr+"-"+dayStr; // (YYYY-MM-DD)形式で日付を保存する(DB検索用)

		// 昨日の日付だった場合情報を取得する処理
		if(flag.equals("yesterday")) {
			DiaryInputCheck check = new DiaryInputCheck();
			List<String> list = new ArrayList<String>();
			List<String> pre_data = new ArrayList<String>();

//			デバッグ用
//			System.out.println(session.getAttribute("userId"));
//			System.out.println(sdf_data);

			list.add((String) session.getAttribute("userId"));
			list.add(sdf_data);
			pre_data = check.execute2(list);

//			System.out.println(pre_data.get(0));
//			System.out.println(pre_data.get(1));
//			System.out.println(pre_data.get(2));
//			System.out.println(pre_data.get(3));
//	        System.out.println(pre_data);

			request.setAttribute("pre_data", pre_data);
		}

		request.setAttribute("flag", flag); //セットしてあげる
		request.setAttribute("year", year); //セットしてあげる
		request.setAttribute("month", month); //セットしてあげる
		request.setAttribute("day", day); //セットしてあげる
		request.setAttribute("sdf_data", sdf_data); //セットしてあげる
		session.setAttribute("dayChange_flag", sdf.format(date));//日付跨ぎエラー用
//		session.setAttribute("dayChange_flag", date);//日付跨ぎエラー用(異常用)

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryInput.jsp");
		dispatcher.forward(request, response);

	}// doGet_END

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

		request.setCharacterEncoding("UTF-8");

		//日付跨ぎエラーデバッグ用
		Date date = new Date();
		System.out.println("日付跨ぎ前："+session.getAttribute("dayChange_flag"));
		System.out.println("日付跨ぎ後："+sdf.format(date));

		if(!(session.getAttribute("dayChange_flag").equals(sdf.format(date)))) {
			request.setAttribute("error_msg", "日付跨ぎのエラーが発生しました"); //セットしてあげる
//			System.out.println("相違あり");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryInputNG.jsp");
			dispatcher.forward(request, response);

		}else {

//			System.out.println("相違なし");
			List<String> list = new ArrayList<String>();
			int total = 0;

	        list.add((String) session.getAttribute("userId")); // 0
	        for(int i = 1; i < 6; i++) { // 1 ~ 5
	        	if(request.getParameter("check"+i) != null) {
	        		list.add("20");
	        		total = total + 20;
	        	}else {
	        		list.add("0");
	        	}
	        } // for_END
			list.add(request.getParameter("comment")); // 6
//			list.add(sdf.format(request.getParameter("year")+"-"+request.getParameter("month")+"-"+request.getParameter("day")));

			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String day = request.getParameter("day");
			String monthStr = null;
			String dayStr = null;

//			デバッグ用
//			System.out.println(year);
//			System.out.println(month);
//			System.out.println(day);

			// 一桁だった場合→二桁にする処理
			if(month.length() == 1) {
	        	monthStr = "0"+month;
	        }else {
	        	monthStr = month;
	        }

			if(day.length() == 1) {
	        	dayStr = "0"+day;
	        }else {
	        	dayStr = day;
	        }

//			デバッグ用
//			System.out.println(year+"-"+monthStr+"-"+dayStr);

			list.add(year+"-"+monthStr+"-"+dayStr); // 7
			list.add(Integer.valueOf(total).toString()); // 8

//			for(String value: list) { // デバッグ用
//				System.out.println(value);
//			}

			//日記入力処理の実行
			DiaryInputCheck check = new DiaryInputCheck();
			Boolean result = check.execute(list);

			//日記入力処理の成否によって処理を分岐
			if(result == true) { // 日記入力成功時
				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryInputOK.jsp");
				dispatcher.forward(request, response);

			}else { //日記入力失敗時
				//リダイレクト
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryInput.jsp");
				dispatcher.forward(request, response);
			}

		}

	}// doPost_END

}
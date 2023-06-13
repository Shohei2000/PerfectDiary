package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MyCalendar;
import model.MyCalendarLogic;
import model.Ranking;
import model.RankingCheck;

@WebServlet("/RankingView")
public class RankingView extends HttpServlet {

	// ページング処理用(ランキングを何件表示するか)
	int begin = 1;
	int end = 1;

	// ランキング重複用変数の初期化
	int ranking_index = 1;

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//リクエストパラメータの取得----------------------------------
		request.setCharacterEncoding("UTF-8");

		if(request.getParameter("index") != null) { // ランキング重複用変数の取得
			ranking_index = Integer.parseInt(request.getParameter("index").toString());
//			System.out.println("ranking_index"+ranking_index); // デバッグ用
		}

		// 月ランキングor年ランキングの初期化
		String flag = "";
		if(request.getParameter("flag") != null) { // 月ランキングor年ランキング判定用
			flag = request.getParameter("flag");
			System.out.println("flag: "+flag); // デバッグ用
		}

		//現在の時刻を取得、下の方でリクエストスコープに格納する(jspで利用)
		Calendar cal = Calendar.getInstance(); //カレンダーインスタンス作成

		int current_year = cal.get(Calendar.YEAR);
		int current_month = cal.get(Calendar.MONTH) + 1; //月取得
		int current_day = cal.get(Calendar.DATE); //日取得

		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");
		MyCalendarLogic logic = new MyCalendarLogic();
		MyCalendar mc = null;

		List<Ranking> ranking_data = null; //ランキングデータ格納用
		List<Ranking> ranking_data_sorted = new ArrayList(); //ランキングデータ格納用

		// ------------------------------------------------------------

		if(s_year != null && s_month != null) { // 年と月のクエリパラメーターが来ている場合にはその年月でカレンダーを生成する(月間ランキング用)

			int year =Integer.parseInt(s_year);
			int month=Integer.parseInt(s_month);

			if(month == 0) { // 1月→12月
				month = 12;
				year--;
			}
			if(month == 13) { // 12月→1月
				month = 1;
				year++;
			}

			mc = logic.createMyCalendar(year,month);
			RankingCheck rankcheck = new RankingCheck();
			ranking_data = rankcheck.executeM(year, month, true);

		}else if( s_year != null && s_month == null ) { // 年のパラメータだけ飛んできた場合(年間ランキング用)

			int year =Integer.parseInt(s_year);

//			System.out.println("年間ランキング：パラメータあり:"+year);

			mc = logic.createMyCalendar(year+1,0);

			RankingCheck rankcheck = new RankingCheck();
			ranking_data = rankcheck.executeY(year, true);

		}else if(s_year == null && s_month == null && flag.equals("year")){ // クエリパラメータが来ていないときは実行日時のカレンダーを生成する。(年間ランキング用)
//			System.out.println("年間ランキング：パラメータなし");

			mc = logic.createMyCalendar();
			RankingCheck rankcheck = new RankingCheck();
			ranking_data = rankcheck.executeY(0, false);

		}else { //(月間ランキング用)

			mc = logic.createMyCalendar();
			RankingCheck rankcheck = new RankingCheck();
			ranking_data = rankcheck.executeM(0, 0, false);

		}

		//---------------------------------------------------------------

//		// デバッグ表示(ソート前)
//		System.out.println("ソート前");
//		ranking_data.stream().forEach(a ->
//	      System.out.println(a.getUserNo() + " " + a.getPoint() + " " + a.getUserId() + " " + a.getUserName()));

//		System.out.println("ソート後");
		// Comparator作成
		Comparator<Ranking> comparator = Comparator.comparing(Ranking::getPoint).reversed().thenComparing(Ranking::getUserNo);
		// ソート処理
		ranking_data.stream().sorted(comparator)
		.forEach(a -> ranking_data_sorted.add(new Ranking(a.getPoint(), a.getUserNo(), a.getUserName(), a.getUserId())));

//		.forEach(a -> ranking_data_sorted.add(new Ranking(a.getPoint(), a.getUserNo(), a.getUserName(), a.getUserId())));
		// ソート終了

		// jspで1~10位までなど表示する範囲を決める処理
		if(request.getParameter("page") == null) {
			begin = 1;
		}else if(request.getParameter("page").equals("next")) {
			begin+=10;
		}else if(request.getParameter("page").equals("previous")) {
			begin-=10;
		}

		end = begin + 9;

//		// デバッグ用
//		System.out.println(request.getParameter("page"));
//		System.out.println(begin+" "+end);

		// スコープに格納
		request.setAttribute("current_year", current_year); // 現在の年セット
		request.setAttribute("current_month", current_month); //現在の月セット
		request.setAttribute("current_day", current_day); // 現在の日セット
		request.setAttribute("mc", mc);
		request.setAttribute("ranking_data", ranking_data_sorted); // ranking_data(ランキング順位データ)を格納
		request.setAttribute("begin", begin);// ページング処理
		request.setAttribute("end", end);// ページング処理
		request.setAttribute("ranking_index", ranking_index);// ランキング重複処理

//		System.out.println("フォワード前："+flag);
//		System.out.println(flag.equals("year"));

		if(flag.equals("year")) {
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rankingListY.jsp");
			dispatcher.forward(request, response);
		}else {
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rankingListM.jsp");
			dispatcher.forward(request, response);
		}

	}// doGet_END


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}// doPost_END


	//----------------------------------------------------------------------------

}
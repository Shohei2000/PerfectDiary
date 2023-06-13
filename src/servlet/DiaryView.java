package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MyCalendar;
import model.MyCalendarLogic;

@WebServlet("/DiaryView")
public class DiaryView extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(true);

		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String flag = request.getParameter("display");
		String flag_diary = request.getParameter("diary");

		if(flag_diary != null) { // 詳細画面表示時

			int day = Integer.parseInt(request.getParameter("day"));

			List<String> set_data = new ArrayList<String>();
			String[][] array = (String[][])session.getAttribute("diary_data");

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); // DATE型に戻す用途
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年M月d日(E)", Locale.JAPANESE); // 曜日付きフォーマット用
			Date date;

			// set_date.addを実行し配列に格納する(開始)
			try {
				date = dateFormat1.parse(array[day-1][0]);
				set_data.add(dateFormat2.format(date));

//				デバッグ用
//		        System.out.println(date.toString()); // フォーマット指定なし
//		        System.out.println(dateFormat2.format(date).toString()); // フォーマット指定あり
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			for(int i=1; i<6; i++) {
				set_data.add(array[day-1][i]);
			}
			set_data.add(array[day-1][6]);
			// set_date.addを実行し配列に格納する(終了)

			// set_dataの配列をリクエストパラメータに格納
			request.setAttribute("set_data", set_data);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryView.jsp");
			dispatcher.forward(request, response);

		}else { // カレンダー又はリスト画面表示時
			//現在の時刻を取得、下の方でリクエストスコープに格納する(jspで利用)
			Calendar cal = Calendar.getInstance(); //カレンダーインスタンス作成

			int current_year = cal.get(Calendar.YEAR);
			int current_month = cal.get(Calendar.MONTH) + 1; //月取得
			int current_day = cal.get(Calendar.DATE); //日取得

			//カレンダー表示
			String s_year = request.getParameter("year");
			String s_month = request.getParameter("month");
			MyCalendarLogic logic = new MyCalendarLogic();
			MyCalendar mc = null;
			MyCalendar mc2 = null;
			String diary_data[][] = null;

			if(s_year != null && s_month != null) { // 年と月のクエリパラメーターが来ている場合にはその年月でカレンダーを生成する
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
				mc2 = logic.createMyCalendarList(year,month);
				diary_data = logic.execute((String) session.getAttribute("userId"), year, month, true);

//				デバッグ用
				for(int i = 0; i<diary_data.length; i++){
					System.out.print("["+i+"]"+" ");
			        for(int j = 0; j<8; j++){
			            System.out.print(diary_data[i][j]+" ");
			        }
			        System.out.print(" "+(i+1)+"日");
			        System.out.println("");
			    }

			}else { // クエリパラメータが来ていないときは実行日時のカレンダーを生成する。
				mc = logic.createMyCalendar();
				mc2 = logic.createMyCalendarList();
				diary_data = logic.execute((String) session.getAttribute("userId"), 0, 0 ,false);

//				デバッグ用
				for(int i = 0; i<diary_data.length; i++){
					System.out.print("["+i+"]"+" ");
			        for(int j = 0; j<8; j++){
			            System.out.print(diary_data[i][j]+" ");
			        }
			        System.out.print(" "+(i+1)+"日");
			        System.out.println("");
			    }

			}

			//リクエストスコープに格納
			request.setAttribute("current_year", current_year); // 現在の年セット
			request.setAttribute("current_month", current_month); //現在の月セット
			request.setAttribute("current_day", current_day); // 現在の日セット
			request.setAttribute("mc", mc);
			request.setAttribute("mc2", mc2);
			request.setAttribute("flag", flag); // カレンダー表示orリスト表示
			session.setAttribute("diary_data", diary_data); // カレンダーの内容をセット

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryList.jsp");
			dispatcher.forward(request, response);
		}

	}// doGet_END

}
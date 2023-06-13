package model;
import java.util.Calendar;

import dao.MantenDAO;
public class MyCalendarLogic {

	//カレンダーインスタンスを生成するメソッド(int...は可変長引数)
	public MyCalendar createMyCalendar(int... args) {
		//マイカレンダーインスタンス生成
		MyCalendar mc = new MyCalendar();

		//現在日時でカレンダーインスタンス生成
		Calendar cal = Calendar.getInstance();

		//2つの引数が来ていたら
		if(args.length==2) {
			//最初の引数で年を設定
			cal.set(Calendar.YEAR, args[0]);
			//次の引数で月を設定
			cal.set(Calendar.MONTH, args[1]-1);
		}

		//マイカレンダーに年を設定
		mc.setYear(cal.get(Calendar.YEAR));

		//マイカレンダーに月の設定
		mc.setMonth(cal.get(Calendar.MONTH)+1);

		//その月の1日が何曜日かを調べる為に日付を1日にする
		cal.set(Calendar.DATE, 1);

		//カレンダーの最初の空白の数
		int before=cal.get(Calendar.DAY_OF_WEEK)-2;
		if(before == -1) {
			before = 6;
		}
		System.out.println("カレンダーの最初の空白の数"+before);

		//カレンダーの日付の数
		int daysCount=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("カレンダーの日付の数"+daysCount);

		//その月の最後の日が何曜日かを調べるために日付を最終日にする
		cal.set(Calendar.DATE, daysCount);

		//最後の日後の空白の数
		int after=7-cal.get(Calendar.DAY_OF_WEEK)+1;
		System.out.println("カレンダーの最後の日後の空白の数"+after);


		//すべての要素数
		int total=before+daysCount+after;

		//その要素数を幅7個の配列に入れていった場合何行になるか
		int rows=total/7;

		//その行数で2次元配列を生成
		String[][] data=new String[rows][7];

		//今見ているカレンダーが今月かどうかを調べるために、この瞬間の日付情報をもつもう一つのインスタンス作成しておく
		Calendar now = Calendar.getInstance();

		for(int i=0;i<rows;i++) {
			for(int j=0;j<7;j++) {
				if(i==0 && j<before || i==rows-1 && j>=(7-after)) {
					//カレンダーの前後に入る空白の部分は空文字
					data[i][j]="";
				}else {
					//カウンター変数と実際の日付の変換
					int date=i*7+j+1 - before;

					//配列に日付を入れる
					data[i][j]=String.valueOf(date);

					//今作業しているマイカレンダーが今月のカレンダーだったら今日の日付の先頭に*を付与する
					if(now.get(Calendar.DATE)== date && now.get(Calendar.MONTH)==mc.getMonth()-1  && now.get(Calendar.YEAR)==mc.getYear()) {
						data[i][j]="*"+data[i][j];
					}
				}
			}
		}

		//作成した2次元配列をマイカレンダーにセットする。
		mc.setData(data);
		return mc;
	}

	public MyCalendar createMyCalendarList(int... args) {
		//マイカレンダーインスタンス生成
		MyCalendar mc2 = new MyCalendar();

		//現在日時でカレンダーインスタンス生成
		Calendar cal2 = Calendar.getInstance();

		//2つの引数が来ていたら
		if(args.length==2) {
			//最初の引数で年を設定
			cal2.set(Calendar.YEAR, args[0]);
			//次の引数で月を設定
			cal2.set(Calendar.MONTH, args[1]-1);
		}

		//マイカレンダーに年を設定
		mc2.setYear(cal2.get(Calendar.YEAR));

		//マイカレンダーに月の設定
		mc2.setMonth(cal2.get(Calendar.MONTH)+1);

		//その月の1日が何曜日かを調べる為に日付を1日にする
		cal2.set(Calendar.DATE, 1);

		//カレンダーの日付の数
		int daysCount=cal2.getActualMaximum(Calendar.DAY_OF_MONTH);

		//その月の最後の日が何曜日かを調べるために日付を最終日にする
		cal2.set(Calendar.DATE, daysCount);

		//すべての要素数
		int total=daysCount;

		//その行数で配列を生成
		String[] data = new String[total];

//		デバッグ用
//		System.out.println(total);

		//今見ているカレンダーが今月かどうかを調べるために、この瞬間の日付情報をもつもう一つのインスタンス作成しておく
		Calendar now = Calendar.getInstance();
		for(int i = 0; i < daysCount; i++) {
			//カウンター変数と実際の日付の変換
			int date=i+1;

			//配列に日付を入れる
			data[i] = String.valueOf(date);

			//今作業しているマイカレンダーが今月のカレンダーだったら今日の日付の先頭に*を付与する
			if(now.get(Calendar.DATE)== date && now.get(Calendar.MONTH)==mc2.getMonth()-1  && now.get(Calendar.YEAR)==mc2.getYear()) {
				data[i]="*"+data[i];
			}
		} // for_END

		//作成した2次元配列をマイカレンダーにセットする。
		mc2.setDataList(data);
//		デバッグ用
//		System.out.println("mc2はセット済み");
//		System.out.println(mc2!=null);
		return mc2;
	}

	public int getDaysCount(int year, int month) {
		//カレンダーインスタンス生成
		Calendar cal_daysCount = Calendar.getInstance();

		cal_daysCount.set(Calendar.YEAR, year);
		cal_daysCount.set(Calendar.MONTH, month-1); // -1が必須(次の月が求められてしまう為)
		cal_daysCount.set(Calendar.DATE, 1);

//		デバッグ用
//		System.out.println("--デバッグ用(getDaysCount内)--");
//		System.out.println(month);
//		System.out.println("--デバッグ用(getDaysCount内)--");

		int daysCount = cal_daysCount.getActualMaximum(Calendar.DAY_OF_MONTH);

		return daysCount;
	}

	public String getDayOfWeek(String date) { // 不要 SimpleDateFormatで代用可能
		//カレンダーインスタンス生成
		Calendar cal_daysCount = Calendar.getInstance();

		String[] week_name = {"日", "月", "火", "水", "木", "金", "土"};

		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));

		System.out.println(year+"/"+month+"/"+day);

		cal_daysCount.set(Calendar.YEAR, year);
		cal_daysCount.set(Calendar.MONTH, month-1); // -1が必須(次の月が求められてしまう為)
		cal_daysCount.set(Calendar.DATE, day);

		int week = cal_daysCount.get(Calendar.DAY_OF_WEEK)-1;
		System.out.println(week_name[week]);

		return week_name[week];
	}

	public String[][] execute(String userId, int year, int month, boolean flag) {

		Calendar cal = Calendar.getInstance();

		MantenDAO dao = new MantenDAO();
		String[][] data = null;

		// 今月以外の場合(URLパラメーターが存在する場合)
		if(flag == true) {
			data = dao.getDiary(userId, year, month);
		}else {
//			デバッグ用
//			System.out.println(userId);
//			System.out.println(cal.get(Calendar.YEAR)+"年");
//			System.out.println(cal.get(Calendar.MONTH)+1+"月");
			data = dao.getDiary(userId, cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH)+1);
		}
		return data;
	}

}
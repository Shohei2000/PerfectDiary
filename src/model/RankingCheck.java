package model;

import java.util.Calendar;
import java.util.List;

import dao.MantenDAO;

public class RankingCheck {

	public List executeM(int year, int month, boolean flag) {

		Calendar cal = Calendar.getInstance();

		MantenDAO dao = new MantenDAO();
		List result = null;

		// 今月以外の場合(URLパラメーターが存在する場合)
		if(flag == true) {
			result = dao.getRanking(year, month);
		}else {
//			デバッグ用
//			System.out.println(userId);
//			System.out.println(cal.get(Calendar.YEAR)+"年");
//			System.out.println(cal.get(Calendar.MONTH)+1+"月");
			result = dao.getRanking(cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH)+1);
		}
		return result;

	}// execute(END)

	public List executeY(int year, boolean flag) {

//		System.out.println("executeY:"+year);

		Calendar cal = Calendar.getInstance();

		MantenDAO dao = new MantenDAO();
		List result = null;

		// 今月以外の場合(URLパラメーターが存在する場合)
		if(flag == true) {
			result = dao.getRanking(year);
		}else {
//			デバッグ用
//			System.out.println(userId);
//			System.out.println(cal.get(Calendar.YEAR)+"年");
//			System.out.println(cal.get(Calendar.MONTH)+1+"月");
			result = dao.getRanking(cal.get(Calendar.YEAR));
		}
		return result;

	}// execute(END)

}

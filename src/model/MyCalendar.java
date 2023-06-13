package model;
import java.io.Serializable;
public class MyCalendar implements Serializable{

	//元号表記
	private String gengou;

	//カレンダーの年
	private int year;

	//カレンダーの月
	private int month;

	//カレンダーの日付を保持する配列
	private String[][] data;
	private String[][] diary_data;

	//カレンダー(リスト表示)の日付を保持する配列
	private String[] data_list;
	private String[] diary_data_list;

	// -----------------------------------------

	/*setter & getter*/
	public String getGengou() {
		return gengou;
	}
	public void setGengou(String gengou) {
		this.gengou = gengou;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

	public String[][] getData() {
		return data;
	}
	public void setData(String[][] data) {
		this.data = data;
	}

	public String[][] getDiaryList() {
		return diary_data;
	}
	public void setDiaryList(String[][] diary_data) {
		this.diary_data = diary_data;
	}

	public String[] getDataList() {
		return data_list;
	}
	public void setDataList(String[] data_list) {
		this.data_list = data_list;
	}

	public String[] getDiaryDataList() {
		return diary_data_list;
	}
	public void setDiaryDataList(String[] diary_data_list) {
		this.diary_data_list = diary_data_list;
	}
}
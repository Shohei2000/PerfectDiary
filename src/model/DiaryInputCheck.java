package model;

import java.util.List;

import dao.MantenDAO;

public class DiaryInputCheck {

	public Boolean execute(List<String> list) {
		MantenDAO dao = new MantenDAO();
		boolean result = dao.input(list);
		return result == true;
	}

	public List<String> execute2(List<String> list) {
		MantenDAO dao = new MantenDAO();
		List<String> result = dao.check(list);
		return result;
	}


}

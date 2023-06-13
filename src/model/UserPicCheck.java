package model;

import java.util.List;

import dao.MantenDAO;

public class UserPicCheck {

	public List<String> userPicCheck() {
		MantenDAO dao = new MantenDAO();
		List<String> result = dao.userPicCheck();
		return result;
	}

}

package model;

import dao.MantenDAO;

public class UserDeleteCheck {

	public boolean deleteCheck(String userId) {
		MantenDAO dao = new MantenDAO();
		Boolean delete_flg = dao.delete(userId);
		return delete_flg;
	}

}

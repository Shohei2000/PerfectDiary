package model;

import dao.MantenDAO;

public class UserUpdateCheck {

	public boolean updateCheck(User user) {
		MantenDAO dao = new MantenDAO();
		boolean result = dao.update(user);
		return result;
	}

}

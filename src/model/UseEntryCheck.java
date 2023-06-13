package model;

import dao.MantenDAO;

public class UseEntryCheck {

	public boolean execute(User user) {
		MantenDAO dao = new MantenDAO();
		Boolean account_flg = dao.register_user(user);
		dao.register_diary(user);
		return account_flg;
	}

	public boolean userIdCheck(User user) {
		MantenDAO dao = new MantenDAO();
		Boolean account_flg = dao.userIdCheck(user);
		return account_flg;

	}

}

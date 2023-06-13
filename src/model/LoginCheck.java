package model;

import dao.MantenDAO;

public class LoginCheck {

	public User execute(User user) {
		MantenDAO dao = new MantenDAO();
		User user_info = dao.execute(user);
		return user_info;
	}

	public void execute2() { // バッチ処理
		MantenDAO dao = new MantenDAO();

		if(dao.batch_process1() == false) {
			System.out.println("バッチ処理実行");
			dao.batch_process2();
		}

	}

}

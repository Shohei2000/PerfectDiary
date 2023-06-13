package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.UserPicCheck;
import model.UserUpdateCheck;

@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserUpdateCheck userCheck = new UserUpdateCheck();

		//ユーザー画像取得処理
		UserPicCheck userpiccheck = new UserPicCheck();
		List<String> userpics = userpiccheck.userPicCheck();
		request.setAttribute("userpics", userpics);

		System.out.println(userpics.get(1));

		//ユーザー登録画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
		dispatcher.forward(request, response);
	}// doGet_END

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");
		String pass_re = request.getParameter("pass_re");
		String url_userpic = request.getParameter("userpic");
		String error_msg = null;

		//ユーザー画像取得処理
		UserPicCheck userpiccheck = new UserPicCheck();
		List<String> userpics = userpiccheck.userPicCheck();
		request.setAttribute("userpics", userpics);

		if(pass.equals(pass_re) && pass != "" && pass_re != "") {

			//ユーザ情報更新
			User user = new User(userName, userId, pass, pass_re, url_userpic);
			UserUpdateCheck check = new UserUpdateCheck();
			boolean result = check.updateCheck(user);

			//ユーザ情報更新の成否によって処理を分岐
			if(result) { // ユーザ情報更新成功時

				// セッションスコープにユーザーIDを保存
				HttpSession session = request.getSession();
				session.setAttribute("userName", user.getUserName());
				session.setAttribute("userPic", user.getUserPic());

				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdateOK.jsp");
				dispatcher.forward(request, response);

			}else { //ユーザ情報更新失敗時
				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
				dispatcher.forward(request, response);
			}

		}else {
			if(pass == "" && pass_re != "" || pass == "" & pass_re == "") {
				error_msg = "Passwordを入力してください";
			}else if(pass != "" && pass_re == "") {
				error_msg ="Password（再入力）を入力してください";
			}else if(!(pass.equals(pass_re))) {
				error_msg ="PasswordとPassword(再入力)には同じものを入力してください";
			}

//			System.out.println(error_msg);
			request.setAttribute("error_msg", error_msg); // エラーメッセージをセット

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
			dispatcher.forward(request, response);
		}

	}// doPost_END

}
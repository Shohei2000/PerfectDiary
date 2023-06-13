package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginCheck;
import model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

	}// doGet_END

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");

		//ログイン処理の実行
		User user = new User(userId, pass);
		LoginCheck check = new LoginCheck();
		User result = check.execute(user);

		//ログイン処理の成否によって処理を分岐
		if(result != null) { // ログイン成功時
			// セッションスコープにユーザーIDを保存
			HttpSession session = request.getSession();
			session.setAttribute("userId", result.getUserId());
			session.setAttribute("userName", result.getUserName());
			session.setAttribute("userPic", result.getUserPic());

			// バッチ処理
			check.execute2();

			//デバッグ用
//			java.lang.System.out.print(userName);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/topMenu.jsp");
			dispatcher.forward(request, response);
//			response.sendRedirect("/WEB-INF/jsp/topMenu.jsp");

		}else { //ログイン失敗時
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp?error=1");
			dispatcher.forward(request, response);
		}

	}// doPost_END

}
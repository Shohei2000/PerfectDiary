package servlet;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UseEntryCheck;
import model.User;
import model.UserPicCheck;

@WebServlet("/UserEntry")
public class UserEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ユーザー画像取得処理
		UserPicCheck userpiccheck = new UserPicCheck();
		List<String> userpics = userpiccheck.userPicCheck();
		request.setAttribute("userpics", userpics);

		//ユーザー登録画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userEntry.jsp");
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
		String error_msg[] = new String[7];
		Boolean entry_check = true;
	    String regex = "^[A-Za-z0-9_]+$" ; // 半角英数字と_のみ

	  //ユーザー画像取得処理
	  	UserPicCheck userpiccheck = new UserPicCheck();
	  	List<String> userpics = userpiccheck.userPicCheck();
	  	request.setAttribute("userpics", userpics);

	    //新規登録の登録
		User user = new User(userName, userId, pass, pass_re, url_userpic);
		UseEntryCheck bo = new UseEntryCheck();

	    if(userName == "") {
			error_msg[0] = "ユーザ名を入力してください";
			entry_check = false;
		}
		if(userId == "") {
			error_msg[1] = "ユーザIDを入力してください";
			entry_check = false;
		}
		if(bo.userIdCheck(user) == false) {
			error_msg[2] = "入力されたUserIDは登録済みです";
			entry_check = false;
		}
		if(checkLogic(regex, userId) == false) {
			error_msg[3] = "UserIDには半角英数字と【_】のみ使用可能です";
			entry_check = false;
		}
		if(pass == "" && pass_re != "" || pass == "" & pass_re == "") {
			error_msg[4] = "Passwordを入力してください";
			entry_check = false;
		}
		if(pass_re == "") {
			error_msg[5] = "Password（再入力）を入力してください";
			entry_check = false;
		}
		if(!(pass.equals(pass_re))) {
			error_msg[6] = "PasswordとPassword(再入力)には同じものを入力してください";
			entry_check = false;
		}

		request.setAttribute("error_msg", error_msg); // エラーメッセージをセット

		// エラーがなかった場合は、新規登録処理へ進む
		if(entry_check == true) {

			boolean result = bo.execute(user);// 新規登録処理

			//新規登録処理の成否によって処理を分岐
			if(result) { // 新規登録成功時
				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userEntryOK.jsp");
				dispatcher.forward(request, response);

			}else { //新規登録失敗時
				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userEntry.jsp");
				dispatcher.forward(request, response);
			}

		}else {
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userEntry.jsp");
			dispatcher.forward(request, response);
		}

	}// doPost_END

	public static boolean checkLogic(String regex, String target) {
	    boolean result = true;
	    if( target == null || target.isEmpty() ) return false ;
	    // 3. 引数に指定した正規表現regexがtargetにマッチするか確認する
	    Pattern p1 = Pattern.compile(regex); // 正規表現パターンの読み込み
	    Matcher m1 = p1.matcher(target); // パターンと検査対象文字列の照合
	    result = m1.matches(); // 照合結果をtrueかfalseで取得
	    return result;
	  }

}
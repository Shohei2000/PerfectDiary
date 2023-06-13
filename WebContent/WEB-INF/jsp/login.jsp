<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header1.jsp" %>

	<table class="table_main">
		<tr>
			<td>
				<font color=red>
					<%
						request.setCharacterEncoding("UTF-8");
						String flag = request.getParameter("error");
						if (flag != null) {
							pageContext.setAttribute("flag", "UserIDまたはPasswordに誤りがあります"); //セットしてあげる
						}else{
							pageContext.setAttribute("flag", "　"); //セットしてあげる
						}
					%>
						<c:out value="${flag}"/>
				</font>
			</td>
		</tr>
	</table>

	<form action="/PerfectDiary/Login" name="login" method="post">
		<table class="table_main">
			<tr>
				<td colspan=2>
					<h3 class="h3_login">ログインしてください</h3>
				</td>
			</tr>

			<tr>
				<td>UserID</td>
				<td><input type=text name="userId"></td>
			</tr>

			<tr>
				<td>Password</td>
				<td><input type=password name="pass"></td>
			</tr>

			<tr>
				<td colspan=2>
					<a class="btn05 bordercircle3" href="javascript:login.submit()" style="margin-top:1rem;">ログイン</a>
				</td>
			</tr>

			<tr>
				<td colspan=2><a class="btnripple3" href="/PerfectDiary/UserEntry" style="margin-top:1rem;">新規登録はこちら</a></td>
			</tr>

		</table>
	</form>

<%@ include file="footer.jsp" %>
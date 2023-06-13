<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header2.jsp" %>

	<table class="table_main">
		<tr>
			<td>
				<font color=red><c:out value="${ error_msg }"/></font>
			</td>
		</tr>
	</table>

	<form action="/PerfectDiary/UserUpdate" name="update" method="post">
		<table class="table_main">
			<tr>
				<td colspan=2>
					<h3>ユーザ情報を変更してください</h3>
				</td>
			</tr>

			<tr>
				<td colspan=2>
					<p style="padding:0;">ユーザーアイコンを選択してください</p>
 				</td>
			</tr>

			<tr style="text-align: -webkit-center;">
				<td colspan=2>
					<section>

						<c:forEach items="${userpics}" varStatus="status">
							<div style="display: inline-block;">
 								<img src="<c:out value="${userpics.get(status.index)}" />" alt="No Image" height="50vw">
								<br>

								<c:if test="${userpics.get(status.index) == userPic}" >
									<input type="radio" checked name="userpic" value="<c:out value="${userpics.get(status.index)}"/>">
								</c:if>
								<c:if test="${userpics.get(status.index) != userPic}" >
									<input type="radio" name="userpic" value="<c:out value="${userpics.get(status.index)}"/>">
								</c:if>

							</div>
						</c:forEach>

					</section>
				</td>
			</tr>

			<tr>
				<td>ユーザ名</td>
				<td><input type=text name="userName"></td>
			</tr>

			<tr>
				<td colspan=2>※ユーザ名は画面上に表示されます</td>
			</tr>

			<tr>
				<td>UserID</td>
				<td><input type=text name="userId" value="<c:out value="${userId}" />" readonly></td>
			</tr>

			<tr>
				<td colspan=2>※UserIDは変更できません</td>
			</tr>

			<tr>
				<td>Password</td>
				<td><input type=password name="pass"></td>
			</tr>

			<tr>
				<td>Password(再入力)</td>
				<td><input type=password name="pass_re"></td>
			</tr>

			<tr>
				<td colspan=2><a class="btn05 bordercircle3" href="javascript:update.submit()" style="margin-top:1rem;">変更</a></td>
			</tr>

			<tr>
				<td colspan=2></td>
			</tr>

			<tr>
				<td colspan=2><a class="btnripple3" href="/PerfectDiary/MantenDiary" style="margin-top:1rem;">メニュー画面に戻る</a>
			</tr>

		</table>
	</form>

<%@ include file="footer.jsp" %>

<style>
section {
	width: 300px;
	height: 75px;
	overflow-x: scroll;
	overflow-y: hidden;
	white-space:nowrap;
	padding: 10px;
	margin-bottom: 1rem;
	border: 2px solid floralwhite;
}
</style>

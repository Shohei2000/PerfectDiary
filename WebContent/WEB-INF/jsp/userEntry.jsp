<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header1.jsp" %>

	<table class="table_main">
		<tr>
			<td style="padding-top:2rem;">
				<c:forEach begin="0" end="6" step="1" varStatus="var">
					<c:if test="${!empty error_msg[var.count]}">
						<font color=red><c:out value="${ error_msg[var.count] }"/></font><br>
					</c:if>
				</c:forEach>
			</td>
		</tr>
	</table>

	<form action="/PerfectDiary/UserEntry" name="register" method="post">
		<table class="table_main">
			<tr>
				<td colspan=2>
					<h3>必要事項を入力してください</h3>
 				</td>
			</tr>

			<tr>
				<td colspan=2>
					<p style="padding:0;">ユーザーアイコンを選択してください</p>
 				</td>
			</tr>

			<tr style="">
				<td colspan=2>
					<section>

						<div style="display: inline-block;">
 								<img src="<c:out value="${userpics.get(0)}" />" alt="No Image" height="50vw">
								<br>
								<input type="radio" checked name="userpic" value="<c:out value="${userpics.get(0)}" />">
						</div>

						<c:forEach begin="1" items="${userpics}" varStatus="status">
							<div style="display: inline-block;">
 								<img src="<c:out value="${userpics.get(status.index)}" />" alt="No Image" height="50vw">
								<br>
								<input type="radio" name="userpic" value="<c:out value="${userpics.get(status.index)}"/>">
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
				<td><input type=text name="userId"></td>
			</tr>

			<tr>
				<td colspan=2>※UserIDは半角英数字記号です<br>(記号は"_"のみ使用可能)
				</td>
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
				<td colspan=2><a class="btn05 bordercircle3" href="javascript:register.submit()" style="margin-top:1rem;">登録</a></td>
			</tr>

			<tr>
				<td colspan=2></td>
			</tr>

			<tr>
				<td colspan=2><a class="btnripple3" href="index.html" style="margin-top:1rem;">インデックス画面に戻る</a>
			</tr>

		</table>
	</form>

<%@ include file="footer.jsp" %>

<style>
section {
	width: 30vw;
	overflow-x: scroll;
	overflow-y: hidden;
	white-space:nowrap;
	padding: 10px;
	margin-bottom: 1rem;
	height: 75px;
	border: 2px solid #ccc;
}
</style>

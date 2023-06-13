<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header2.jsp" %>


	<table class="table_main">
		<tr>
			<td style="padding-top:2rem;">
				<font color=red><c:out value="${ error_msg }"/></font>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<h3>日付を入力してください</h3>
			</td>
		</tr>

		<tr>
			<td colspan=2></td>
		</tr>

		<tr>
			<td colspan=2><a href="/PerfectDiary/MantenDiary">メニューに戻る</a></td>
		</tr>
	</table>
<%@ include file="footer.jsp" %>
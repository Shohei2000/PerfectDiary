<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Calendar"%>
<%@ include file="header2.jsp" %>


	<table class="table_main table_user">
		<tr>
			<td class="usertd">
				<img class="userpic" src="<c:out value="${userPic}" />" alt="No Image" height="50vw">
				<font class="username" color=black>ようこそ <c:out value="${userName}" />さん</font>
			</td>
		</tr>
	</table>

	<table class="table_main">
		<tr>
			<td colspan=2>
				<h3 class="h3_top">
					<c:out value="${set_data.get(0)}" />
				</h3>
			</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="check1" value="1" checked disabled></td>
			<td>毎日日記を付ける</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="check2" disabled
				<c:out value="${set_data.get(2)}" />></td>
			<td>時間通りに起きる</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="check3" disabled
				<c:out value="${set_data.get(3)}" />></td>
			<td>遅刻をしない</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="check4" disabled
				<c:out value="${set_data.get(4)}" />></td>
			<td>栄養バランスを考えた食事をする</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="check5" disabled
				<c:out value="${set_data.get(5)}" />></td>
			<td>ちゃんとした挨拶をする</td>
		</tr>
		<tr>
			<td colspan=2>今日の一言<br>
				<textarea name="comment" disabled rows="3" cols="40" wrap="soft" style="width: 100%;"><c:out value="${set_data.get(6)}" /></textarea>
			</td>
		</tr>
		<tr>
			<td colspan=2></td>
		</tr>
		<tr>
			<td colspan=2 align="center"><a href="javascript:history.back();">一覧に戻る</a></td>
		</tr>
		<tr>
			<td colspan=2 align="center"><a href="MantenDiary">メニューに戻る</a></td>
		</tr>
	</table>
<%@ include file="footer.jsp" %>

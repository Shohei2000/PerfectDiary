<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

	<form action="/PerfectDiary/DiaryInput" name="input" method="post">
		<table class="table_main">
			<tr>
				<td colspan=2>
					<h3 class="h3_top">
						<c:out value="${month}"/>月
						<c:out value="${day}"/>日
						<input type="hidden" name="year" value=<c:out value="${year}"/>>
						<input type="hidden" name="month" value=<c:out value="${month}"/>>
						<input type="hidden" name="day" value=<c:out value="${day}"/>>
						の日記を入力してください
					</h3>
				</td>
			</tr>

			<tr style="height:2rem;">
				<td><input type="checkbox" name="check1" value="1" checked  disabled ></td><td>毎日日記を付ける</td>
				<td><input type="hidden" name="check1" value="1"/></td>
			</tr>
			<tr style="height:2rem;">
				<td><input type="checkbox" name="check2" <c:out value="${pre_data.get(1)}" /> ></td>
				<td>時間通りに起きる</td>
			</tr>
			<tr style="height:2rem;">
				<td><input type="checkbox" name="check3" <c:out value="${pre_data.get(2)}" /> ></td>
				<td>遅刻をしない</td>
			</tr>
			<tr style="height:2rem;">
				<td><input type="checkbox" name="check4" <c:out value="${pre_data.get(3)}" /> ></td>
				<td>栄養バランスを考えた食事をする</td>
			</tr>
			<tr style="height:2rem;">
				<td><input type="checkbox" name="check5" <c:out value="${pre_data.get(4)}" /> ></td>
				<td>ちゃんとした挨拶をする</td>
			</tr>
			<tr style="height:5.5rem;">
				<td colspan=2>今日の一言(空白可)<br>
					<textarea name="comment" rows="3" cols="40" wrap="soft" style="width:100%;"><c:out value="${pre_data.get(5)}"/></textarea>
				</td>
			</tr>

			<tr>
				<td colspan=2 align="center">
					<a class="btn05 bordercircle3" href="javascript:input.submit()">送信</a>
				</td>
			</tr>

			<tr>
				<td colspan=2></td>
			</tr>

			<tr>
				<c:choose>
					<c:when test="${flag.equals('today')}">
						<td colspan=2 align="center"><a class="btnripple3-1" href="DiaryInput?when=yesterday" style="margin:1rem;">←前日の日記を付ける</a></td>
					</c:when>
					<c:when test="${flag.equals('yesterday')}">
						<td colspan=2 align="center"><a class="btnripple3-1" href="DiaryInput?when=today" style="margin:1rem;">翌日の日記を付ける→</a></td>
					</c:when>
				</c:choose>
			</tr>
			<tr>
				<td colspan=2 align="center"><a class="btnripple3" href="MantenDiary">メニューに戻る</a></td>
			</tr>
		</table>
	</form>
<%@ include file="footer.jsp" %>

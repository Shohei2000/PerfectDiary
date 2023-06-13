<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<h3 class="h3_top">メニューより選んでください</h3>
			</td>
		</tr>

		<tr>
			<td align="center">
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/DiaryInput?when=today">日記入力</a></p>
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/DiaryView?display=calender">日記閲覧</a></p>
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/RankingView">ランキング閲覧</a></p>
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/UserUpdate">ユーザ情報変更</a></p>
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/UserDelete">ユーザ削除</a></p>
				<p><a class="btn05 bordercircle3" href="/PerfectDiary/Logout">ログアウト</a></p>

<!-- 				<a class="btn-11" href="/PerfectDiary/">日記を入力する</a>
				<a class="btn-11" href="/PerfectDiary/">過去の日時を見る</a>
				<a class="btn-11" href="">ランキングを見る</a>
				<a class="btn-11" href="">ユーザ情報を変更する</a>
				<a class="btn-11" href="/PerfectDiary/Logout">ログアウトする</a> -->
			</td>
		</tr>
	</table>

<%@ include file="footer.jsp" %>

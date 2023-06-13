<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.MyCalendar" import="model.Ranking"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.Calendar"%>
<%
	MyCalendar mc = (MyCalendar) request.getAttribute("mc");
	MyCalendar mc2 = (MyCalendar) request.getAttribute("mc2");

	boolean break_flg = false;
%>
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
			<td colspan=3>
				<h3><%=mc.getYear()%>年の年間ランキング</h3>
			</td>
		</tr>

		<tr>
			<c:choose>
				<c:when test="${ mc.getYear() >= '2021'}">
					<td><a href="RankingView?year=<%=mc.getYear() - 1%>&flag=year">←前年</a></td>
				</c:when>
				<c:otherwise>
					<td><a>←前年</a></td>
				</c:otherwise>
			</c:choose>

			<td><a href="RankingView?flag=year" style="padding: 0 1rem;">今年</a></td>

			<c:choose>
				<c:when test="${ mc.getYear() < current_year }">
					<td><a href="RankingView?year=<%=mc.getYear() + 1%>&flag=year">翌年→</a></td>
				</c:when>
				<c:otherwise>
					<td><a>翌年→</a></td>
				</c:otherwise>
			</c:choose>
		</tr>

		<tr>
			<td align="center">順位</td>
			<td align="center">ユーザ名</td>
			<td align="center">ポイント</td>
		</tr>

		<c:set var="index" value="${ ranking_index }"/>
		<c:forEach begin="${begin}" end="${end}" varStatus="status">
			<c:if test="${ status.index <= fn:length(ranking_data)}">
				<tr>
					<c:choose>
						<c:when test="${ status.index > 1 && ranking_data.get(status.index-2).getPoint() == ranking_data.get(status.index-1).getPoint() }">
							<td align="center"><c:out value="${index}"/></td>
						</c:when>
						<c:otherwise>
							<td align="center"><c:out value="${status.index}"/></td>
							<c:set var="index" value="${ status.index }" />
						</c:otherwise>
					</c:choose>

					<td align="center"><c:out value="${ranking_data.get(status.index-1).getUserName()}" /></td>
					<td align="center"><c:out value="${ranking_data.get(status.index-1).getPoint()}" /></td>

				</tr>
			</c:if>
		</c:forEach>

		<tr>
			<td colspan=3 align="center">
				<c:choose>
					<c:when test="${ begin != 1 && end < fn:length(ranking_data) }">
						<a href="/PerfectDiary/RankingView?page=previous&year=<%=mc.getYear()%>&flag=year" style="margin-right:1rem;">前の10位</a>
						<a href="/PerfectDiary/RankingView?page=next&year=<%=mc.getYear()%>&flag=year&index=<c:out value="${index}"/>" style="margin-left:1rem;">次の10位</a>
					</c:when>
					<c:when test="${ fn:length(ranking_data) == 0 }">
						<a>該当するデータが存在しません。</a>
					</c:when>
					<c:when test="${ end >= fn:length(ranking_data) }">
						<a href="/PerfectDiary/RankingView?page=previous&year=<%=mc.getYear()%>&flag=year" style="margin-right:1rem;">前の10位</a>
					</c:when>
					<c:otherwise>
						<a href="/PerfectDiary/RankingView?page=next&year=<%=mc.getYear()%>&flag=year&index=<c:out value="${index}"/>" style="margin-left:1rem;">次の10位</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td colspan=3 align="center"><a href="/PerfectDiary/RankingView">月間ランキング</a></td>
		</tr>
		<tr>
			<td colspan=3 align="center"><a href="/PerfectDiary/MantenDiary">メニューに戻る</a></td>
		</tr>
	</table>

<%@ include file="footer.jsp" %>

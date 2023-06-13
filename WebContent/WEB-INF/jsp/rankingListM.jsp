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
				<h3><%=mc.getYear()%>年<%=mc.getMonth()%>月の月間ランキング</h3>
			</td>
		</tr>

		<tr>
			<c:choose>
				<c:when test="${ mc.getYear() >= '2021' && mc.getMonth() > '6'}">
					<td><a href="RankingView?year=<%=mc.getYear()%>&month=<%=mc.getMonth() - 1%>">←前月</a></td>
				</c:when>
				<c:otherwise>
					<td><a>←前月</a></td>
				</c:otherwise>
			</c:choose>

			<td><a href="RankingView?display=calender" style="padding: 0 1rem;">今月</a></td>

			<c:choose>
				<c:when test="${ mc.getYear() <= current_year && mc.getMonth() < current_month || mc.getYear() < current_year}">
					<td><a href="RankingView?year=<%=mc.getYear()%>&month=<%=mc.getMonth() + 1%>">翌月→</a></td>
				</c:when>
				<c:otherwise>
					<td><a>翌月→</a></td>
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

					<%-- <td align="center"><c:out value="${ranking_data.get(status.index-1).getName()}" /></td>
					<td align="center"><c:out value="${ranking_data.get(status.index-1).getPoint()}" /></td> --%>
				</tr>
			</c:if>
		</c:forEach>

		<tr>
			<td colspan=3 align="center">
				<c:choose>
					<c:when test="${ begin != 1 && end < fn:length(ranking_data) }">
						<a class="btnripple3-1" href="/PerfectDiary/RankingView?page=previous&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>" style="margin:0.5rem 1rem 0 0;">前の10位</a>
						<a class="btnripple3-1" href="/PerfectDiary/RankingView?page=next&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>&index=<c:out value="${index}"/>" style="margin:0.5rem 0 0 1rem;">次の10位</a>
					</c:when>
					<c:when test="${ fn:length(ranking_data) == 0}">
						<a>該当するデータがありません。</a>
					</c:when>
					<c:when test="${ end >= fn:length(ranking_data) }">
						<a class="btnripple3-1" href="/PerfectDiary/RankingView?page=previous&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>" style="margin:0.5rem 1rem 0 0;">前の10位</a>
					</c:when>
					<c:otherwise>
						<a class="btnripple3-1" href="/PerfectDiary/RankingView?page=next&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>&index=<c:out value="${index}"/>" style="margin:0.5rem 0 0 1rem;">次の10位</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td colspan=3 align="center"><a class="btnripple3" href="/PerfectDiary/RankingView?flag=year" style="margin-top:0.5rem;">年間ランキング</a></td>
		</tr>
		<tr>
			<td colspan=3 align="center"><a class="btnripple3" href="/PerfectDiary/MantenDiary" style="margin-top:0.5rem;">メニューに戻る</a></td>
		</tr>
	</table>

<%@ include file="footer.jsp" %>

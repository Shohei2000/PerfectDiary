<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.MyCalendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<td colspan=7 align="center">
				<h3>日付を選んでください</h3>
			</td>
		</tr>
	</table>

	<div style="text-align: center;">

			<c:choose>
				<c:when test="${flag.equals('calender')}">

					<a href="DiaryView?display=calender&year=<%=mc.getYear()%>&month=<%=mc.getMonth() - 1%>">←前月</a>
					<a href="DiaryView?display=calender" style="padding: 0 1rem;">今月</a>

					<c:choose>
						<c:when
							test="${ mc.getYear() <= current_year && mc.getMonth() < current_month || mc.getYear() < current_year}">
							<a href="DiaryView?display=calender&year=<%=mc.getYear()%>&month=<%=mc.getMonth() + 1%>">翌月→</a>
						</c:when>
						<c:otherwise>
							<a>翌月→</a>
						</c:otherwise>
					</c:choose>

					<br>
					<p align="center"><%=mc.getYear()%>年<%=mc.getMonth()%>月</p>

					<table class="table_main" style="width:40%;">
						<!-- カレンダー表示 -->
						<tr class="table_row4">
							<th>月</th>
							<th>火</th>
							<th>水</th>
							<th>木</th>
							<th>金</th>
							<th>土</th>
							<th>日</th>
						</tr>

						<%
							for (String[] row : mc.getData()) {
						%>
						<tr class="table_row4">
							<% for (String col : row) { %>
							<% if (col.startsWith("*")) { %>
							<fmt:parseNumber var="numberDay" value="<%=col.substring(1)%>"
								integerOnly="true" />
							<c:choose>
								<c:when test="${ !diary_data[numberDay-1][0].equals('') }">
									<td class="today"><a
										href="/PerfectDiary/DiaryView?diary=view&day=<%=col.substring(1)%>"><%=col.substring(1)%></a></td>
								</c:when>
								<c:otherwise>
									<td class="today"><%=col.substring(1)%></td>
								</c:otherwise>
							</c:choose>
							<% break_flg = true;
								break;
								} else {
							%>
							<fmt:parseNumber var="numberDay" value="<%=col%>" integerOnly="true" />
							<c:choose>
								<c:when test="${ !diary_data[numberDay-1][0].equals('') }">
									<td><a href="/PerfectDiary/DiaryView?diary=view&day=<%=col%>"><%=col%></a></td>
								</c:when>
								<c:otherwise>
									<td><%=col%></td>
								</c:otherwise>
							</c:choose>
							<% } %>
							<% } %>
						</tr>

						<% if (break_flg == true) {
							break;
							}
						%>

						<% } %>

						<tr>
							<td colspan=7></td>
						</tr>

						<tr>
							<td colspan=7 align="center"><a class="btnripple3" href="/PerfectDiary/DiaryView?display=list&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>" style="margin-top:1rem;">リスト表示に切替</a></td>
						</tr>

					</table>
				</c:when>

				<c:when test="${flag.equals('list')}">
					<!-- リスト表示 -->

					<a href="DiaryView?display=list&year=<%=mc2.getYear()%>&month=<%=mc2.getMonth() - 1%>">←前月</a>
					<a href="DiaryView?display=list" style="padding: 0 1rem;">今月</a>

					<c:choose>
						<c:when
							test="${ mc2.getYear() <= current_year && mc2.getMonth() < current_month || mc2.getYear() < current_year}">
							<a href="DiaryView?display=list&year=<%=mc2.getYear()%>&month=<%=mc2.getMonth() + 1%>">翌月→</a>
						</c:when>
						<c:otherwise>
							<td style="width: 40%;"><a>翌月→</a></td>
						</c:otherwise>
					</c:choose>

					<p align="center"><%=mc.getYear()%>年<%=mc.getMonth()%>月</p>

					<table class="table_main">

						<tr>
							<td align="center">日付</td>
							<td align="center">点数</td>
							<td align="center">今日の一言</td>
						</tr>

						<c:set var="count" value="0" />
						<!-- 二次元配列用のポインタを初期化 -->

						<% for (String col : mc2.getDataList()) { %>
						<tr>
							<% if (col.startsWith("*")) { %>
							<td class="today"><%=col.substring(1)%></td>
							<td align="center"><c:out value="${diary_data[count][7]}" /></td>
							<td style="max-width: 15rem;">
								<p style="margin: 0; padding: 0; overflow-wrap: break-word;">
								<c:out value="${diary_data[count][6]}" />
								</p>
							</td>
							<% break_flg = true;
								break;
								} else {
							%>
							<td><%=col%></td>
							<td align="center"><c:out value="${diary_data[count][7]}" /></td>
							<td style="max-width: 15rem;">
								<p style="margin: 0; padding: 0; overflow-wrap: break-word;">
								<c:out value="${diary_data[count][6]}" />
								</p>
							</td>
							<% } %>
						</tr>

						<%-- <c:out value="${ count }"/> --%>
						<!-- デバッグ用 -->
						<c:set var="count" value="${ count+1 }" />
						<!-- 二次元配列用のポインタをインクリメント -->

						<% if (break_flg == true) {
							break;
							}
						%>

						<% } %>

					</table>

					<a class="btnripple3" href="/PerfectDiary/DiaryView?display=calender&year=<%=mc.getYear()%>&month=<%=mc.getMonth()%>" style="margin-top:1rem;">カレンダー表示に切り替える</a>
					<br>

				</c:when>
			</c:choose>

			<a class="btnripple3" href="MantenDiary" style="margin-top:1rem;">メニューに戻る</a>

	</div>

<%@ include file="footer.jsp" %>
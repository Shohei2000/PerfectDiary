<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header2.jsp" %>


	<table class="table_main">
		<tr>
			<td colspan=2>
				<h3>ユーザ情報を削除しますか？</h3>
			</td>
		</tr>
		<tr>
			<td colspan=2></td>
		</tr>
		<tr>
			<td colspan=2>
				<form action="/PerfectDiary/UserDelete" name="delete" method="post">
					<a class="btn05 bordercircle3" href="javascript:delete.submit()" style="margin-top:1rem;">削除</a>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan=2><a class="btnripple3" href="/PerfectDiary/MantenDiary" style="margin-top:1rem;">メニューに戻る</a></td>
		</tr>
	</table>
<%@ include file="footer.jsp" %>
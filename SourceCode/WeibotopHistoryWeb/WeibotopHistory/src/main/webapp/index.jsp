<%@page import="com.mingdos.weibotop.dao.*"%>
<%@page import="java.util.*"%>
<%@page import="com.mingdos.weibotop.model.Topic"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<title>Topic Ranking History</title>
		<link rel="stylesheet" type="text/css" href="style.css">
		<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
	</head>
	
	<body>

		<nav>
			<a class="home" href="/WeibotopHistory/index.jsp">WeiboTopHistory</a>
			<a class="findtopic" href="/WeibotopHistory/findtopic.jsp">Find Topic</a>
	    	<a class="about" href="#">About</a>
		</nav>

		<div class="topics">
		
			<h1>Top 50 Lasting Topics</h1>
	
			<table>
				
				
				
				<thead>
					<tr>
						<th>Rank</th>
						<th>Topic</th>
						<th>Duration(Secs)</th>
					</tr>
				</thead>
					
				<tbody>
					<%
						List<Topic> result = new ArrayList<Topic>();
						WeiboTopDao dao = new WeiboTopDaoImpl();
				
						result = dao.getTopicsBasedDuration(50, true);
						
						for (Integer i=0;i<result.size();i++) {
							Topic t = result.get(i);
							out.println("<tr>");
							out.println("<td class=\"rank\">");
							out.println(Integer.toString(i + 1));
							out.println("</td>");
							out.println("<td>");
							out.println(t.getTopic() + "<br>");
							out.println(t.getContent());
							out.println("</td>");
							out.println("<td class=\"duration\">");
							out.println(t.getDuration());
							out.println("</td>");
							out.println("</tr>");
						}
					%>
				</tbody>
	
	
			</table>

		
		</div>



	
	</body>
	
</html> 
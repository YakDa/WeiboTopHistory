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
        <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
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
						<th>Duration</th>
						<th>Traffic</th>
					</tr>
				</thead>
					
				<tbody>
					<%
						List<Topic> result = new ArrayList<Topic>();
						WeiboTopDao dao = new WeiboTopDaoImpl();
				
						result = dao.getTopicsBasedTraffic(50, true);
						
						for (Integer i=0;i<result.size();i++) {
							Topic t = result.get(i);
							out.println("<tr>");
							out.println("<td class=\"rank\">");
							out.println(Integer.toString(i + 1));
							out.println("</td>");
							out.println("<td>");
							out.println("<div class=\"content\">");
							out.println("<a href=\"#" + t.getTopic() + "\">" + t.getTopic() + "</a>" + "<br>");
							out.println(t.getContent());
							out.println("</div>");
							out.println("</td>");
							out.println("<td class=\"duration\">");
							out.println(t.getDurationFormated());
							out.println("</td>");
							out.println("<td class=\"traffic\">");
							out.println(t.getHotpoints());
							out.println("</td>");
							out.println("</tr>");
						}
					%>
					
				</tbody>
			</table>
		</div>
	
	</body>
	
	<script type="text/javascript" src="js/clicktopic.js"></script>
	
	
</html> 
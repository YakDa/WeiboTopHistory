<%@ page import="com.mingdos.weibotophistory.model.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Topic Information</title>
		<link rel="stylesheet" type="text/css" href="style.css">
		<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
	</head>
	
	<body>
		<nav>
			<a class="home" href="top?toptype=today">WeiboTopHistory</a>
			<a class="findtopic" href="findtopic.jsp">Find Topic</a>
	    	<a class="about" href="about.jsp">About</a>
		</nav>
		
		<div class="topics">
			<%
				Topic t = (Topic) request.getAttribute("topic");
				if(t != null) {
					out.println("<h1>" + t.getTopic() + "</h1>");
					
					out.println("<table class=\"topicinfotable\">");
					out.println("<tr>" + "<th>First time enter top list</th>" + "<td>" + t.getFirstTime() + "</td>" + "</tr>");
					out.println("<tr>" + "<th>Last time enter top list</th>" + "<td>" + t.getLastTime() + "</td>" + "</tr>");
					out.println("<tr>" + "<th>Highest rank reached</th>" + "<td>" + t.getHighestRank() + "</td>" + "</tr>");
					out.println("<tr>" + "<th>Accumulated time in top list</th>" + "<td>" + t.getDurationFormated() + "</td>" + "</tr>");
					out.println("<tr>" + "<th>Traffic</th>" + "<td>" + t.getHotpoints() + "</td>" + "</tr>");
					out.println("</table>");
					
					out.println("<h2>" + "<a href=\"https://s.weibo.com/weibo?q=" + t.getTopic() + "\">" + t.getTopic() + "</a>" + "</h2>");
					
					out.println("<p>" + t.getContent() + "</p>");
				}
				else {
					out.println("<h2>Sorry, no information found!</h2>");
				}
			%>
		</div>	
	</body>
</html>
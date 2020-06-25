<%@page import="com.mingdos.weibotop.dao.*"%>
<%@page import="java.util.*"%>
<%@page import="com.mingdos.weibotop.model.Topic"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Find Topic</title>
	<link rel="stylesheet" type="text/css" href="style.css">
	<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
	<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
</head>
<body>

	<nav>
		<a class="home" href="/WeibotopHistory/index.jsp">WeiboTopHistory</a>
		<a class="findtopic" href="/WeibotopHistory/findtopic.jsp">Find Topic</a>
    	<a class="about" href="/WeibotopHistory/about.jsp">About</a>
	</nav>
	
	<div class="topics">
		<h1>Find Topics</h1>

		<form class="find" action="/WeibotopHistory/selecttopics">
			<label for="from">From: </label>
			<input type="date" id="from" name="from">
	
			<label for="to">To: </label>
			<input type="date" id="to" name="to">
			
			<input type="submit" value="Query">
		</form>



				<%
					List<Topic> result = (List<Topic>)request.getAttribute("topics");
				
					if(result != null) {
						if(result.size() == 0) {
							out.println("<h2>Sorry, no information found!</h2>");
						}
						else {
							out.println("<table class=\"toplisttable\" style=\"width:100%;\">");
							out.println("<thead>");
							out.println("<tr>");
							out.println("<th>Rank</th>");
							out.println("<th>Topic</th>");
							out.println("<th>Traffic</th>");
							out.println("</tr>");
							out.println("</thead>");
							out.println("<tbody>");
							for (Integer i=0;i<result.size();i++) {
								Topic t = result.get(i);
								out.println("<tr>");
								out.println("<td class=\"rank\">");
								out.println(Integer.toString(i + 1));
								out.println("</td>");
								out.println("<td>");
								out.println("<a href=\"showinfo?id=" + t.getId() + "\">" + t.getTopic() + "</a>");
								out.println("</td>");
								out.println("<td class=\"traffic\">");
								out.println(t.getHotpoints());
								out.println("</td>");
								out.println("</tr>");
							}
							out.println("</tbody>");
							out.println("</table>");
						}
					}
				%>

	</div>
	

</body>

</html>
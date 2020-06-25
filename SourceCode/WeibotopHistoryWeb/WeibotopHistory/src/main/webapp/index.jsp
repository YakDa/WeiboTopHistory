<%@page import="com.mingdos.weibotop.dao.*"%>
<%@page import="java.util.*"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
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
		<title>Topic Ranking</title>
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
			<%
				String topType = request.getParameter("toptype");
				if(topType == null || "today".equals(topType)) {
					out.println("<h1>Today Top Topics</h1>");
				}
				else if("traffic".equals(topType)) {
					out.println("<h1>History Top Traffic</h1>");
				}
				else if("duration".equals(topType)) {
					out.println("<h1>History Top Longest</h1>");
				}
			%>
			
			<form class="topform" action="/WeibotopHistory/top">
				<input type="hidden" name="toptype" value="today" />
				<input class="topbutton" type="submit" value="Today Top Traffic">
			</form>
			
			<form class="topform"  action="/WeibotopHistory/top">
				<input type="hidden" name="toptype" value="traffic" />
				<input class="topbutton" type="submit" value="History Top Traffic">
			</form>
			
			<form class="topform"  action="/WeibotopHistory/top">
				<input type="hidden" name="toptype" value="duration" />
				<input class="topbutton" type="submit" value="History Top Duration">
			</form>

			<table class="toplisttable" style="width:100%;">
				<thead>
					<tr>
						<th>Rank</th>
						<th>Topic</th>
						
						<%
							if(topType == null || "today".equals(topType) || "traffic".equals(topType)) {
								out.println("<th>Traffic</th>");
							}
							else if("duration".equals(topType)) {
								out.println("<th>Duration</th>");
							}
						%>
						
					</tr>
				</thead>
				<tbody>
					<%
						List<Topic> result = new ArrayList<Topic>();
						
						if(topType == null || "today".equals(topType)) {
							WeiboTopDao dao = new WeiboTopDaoImpl();
							DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							String now = dtfLog.format(LocalDateTime.now());
							result = dao.getTopicsFromToDate(now + " 00:00:00", now + " 23:59:59");
						}
						else if("traffic".equals(topType) || "duration".equals(topType)) {
							result = (List<Topic>) request.getAttribute("topics");
						}
						
						for (Integer i=0;i<result.size();i++) {
							Topic t = result.get(i);
							out.println("<tr>");
							out.println("<td class=\"rank\">");
							out.println(Integer.toString(i + 1));
							out.println("</td>");
							out.println("<td>");
							out.println("<a href=\"showinfo?id=" + t.getId() + "\">" + t.getTopic() + "</a>");
							out.println("</td>");

							out.println("</td>");
							if(topType == null || "today".equals(topType) || "traffic".equals(topType)) {
								out.println("<td class=\"traffic\">");
								out.println(t.getHotpoints());
							}
							else if("duration".equals(topType)) {
								out.println("<td class=\"duration\">");
								out.println(t.getDurationFormated());
							}
							out.println("</td>");
							out.println("</tr>");
						}
					%>
					
				</tbody>
			</table>
		</div>
	</body>
</html> 
package com.mingdos.weibotophistory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingdos.weibotop.dao.WeiboTopDao;
import com.mingdos.weibotop.dao.WeiboTopDaoImpl;
import com.mingdos.weibotop.model.Topic;

/**
 * Servlet implementation class TopicsTopServlet
 */
public class TopicsTopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopicsTopServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String topType = request.getParameter("toptype");
		List<Topic> result = new ArrayList<Topic>();
		WeiboTopDao dao = new WeiboTopDaoImpl();
		DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String now = dtfLog.format(LocalDateTime.now());
		
		if("traffic".equals(topType)) {
			result = dao.getTopicsBasedTraffic(50, true);
		}
		else if("duration".equals(topType)) {
			result = dao.getTopicsBasedDuration(50, true);
		}
		
		request.setAttribute("topics", result);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
			
	}



}

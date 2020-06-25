package com.mingdos.weibotophistory;

import com.mingdos.weibotop.dao.*;
import com.mingdos.weibotop.model.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TopicInfoServlet
 */
public class TopicInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopicInfoServlet() {

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeiboTopDao dao = new WeiboTopDaoImpl();
		Topic t = dao.getTopicWithId(request.getParameter("id"));
		request.setAttribute("topic", t);
		
		RequestDispatcher rd = request.getRequestDispatcher("topicinfo.jsp");
		rd.forward(request, response);
	}

}

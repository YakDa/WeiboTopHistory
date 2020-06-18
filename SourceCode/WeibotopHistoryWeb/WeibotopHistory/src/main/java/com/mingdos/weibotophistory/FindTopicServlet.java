package com.mingdos.weibotophistory;

import com.mingdos.weibotop.dao.*;
import com.mingdos.weibotop.model.*;
import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FindTopicServlet
 */
public class FindTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindTopicServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String from = request.getParameter("from") + " 00:00:00";
		String to = request.getParameter("to") + " 23:59:59";
	
		List<Topic> result = new ArrayList<Topic>();
		WeiboTopDao dao = new WeiboTopDaoImpl();
	
		result = dao.getTopicsFromToDate(from, to);
		
		request.setAttribute("topics", result);
		RequestDispatcher rd = request.getRequestDispatcher("findtopic.jsp");
		rd.forward(request, response);

		
	}



}

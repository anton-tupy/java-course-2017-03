package com.kladov.jcourse;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet( name = "GuestBook",    urlPatterns = {"/guestbook"} )
public class GuestBook extends HttpServlet {
	@Resource(name = "jdbc/testDS")
	private DataSource ds;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		try {
			ds.getConnection().isClosed();
			req.setAttribute("string", "lalala");
			req.getRequestDispatcher("/WEB-INF/guestbook.jsp").forward(req, resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
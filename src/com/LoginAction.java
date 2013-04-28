package com;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ConnectionUtils;
/**
. * Servlet implementation class LoginAction
 */
@WebServlet(description = "This will allow login", urlPatterns = { "/LoginAction" })
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
	     out.println("<h1> Hello Servlet !</h1>");
		 try {
			Connection conn = ConnectionUtils.getConnection();
			String uname=  request.getParameter("username").trim();
			String pass=request.getParameter("password").trim();
			PreparedStatement psmt = null;
	        ResultSet rs = null;
			String sql="Select id,userName,userPass from blog where userName=? and  userPass=?";
			psmt=conn.prepareStatement(sql);
			out.println(uname);
			out.print(pass);
			psmt.setString(1, uname);
			psmt.setString(2, pass);
			rs=psmt.executeQuery();
			if(rs.next())
			{
				HttpSession session=request.getSession(true);
				session.setAttribute("userId",rs.getInt("id"));
				session.setAttribute("user",rs.getString("userName") );
				out.println(rs.getString("userName"));
				RequestDispatcher view =  request.getRequestDispatcher("home.jsp");
				view.forward(request, response);
		} 
			else{
				out.println("<div style='font-size:30px; color:red'>"
				          +" Either Userid and password does not matches or please fill up all the details "+"</div>");
				 RequestDispatcher view =
				request.getRequestDispatcher("index.html");
				view.include(request, response);
				   
			}
			
		 }	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

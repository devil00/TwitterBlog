package com;

import com.ConnectionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

import sun.security.util.Length;

/**
 * Servlet implementation class First
 */

@WebServlet("/First")
public class SignUpAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpAction() {
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

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	     // out.println("<h1> Hello Servlet !</h1>");
	      
	      try {
	    	String sql= null;   
			Connection conn=ConnectionUtils.getConnection();
			String user=request.getParameter("userName").trim();
			String pass=request.getParameter("userPassword").trim();
		  if (user.length()>0  && pass.length()>0 ){
			sql="insert into  blog (userName,userPass) values(?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, user);
			psmt.setString(2, pass);
			int i=psmt.executeUpdate();
			if (i!=0){
				out.println("<b>Hello </b>" + user);
				out.println("<br/><p><font color='blue'>Greetings you are a member now !</font></p><br/>");
				out.println("<a href='index.html' taget='_self'> Go Back and Login </a>");
				}
			psmt.close();
			}
			else{
				ServletContext context= getServletContext();
				RequestDispatcher rd= context.getRequestDispatcher("/index.html");
				out.println("<font color=red>Invalid user name or password</font>");
				rd.include(request, response);

				
			}
         					
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}

}

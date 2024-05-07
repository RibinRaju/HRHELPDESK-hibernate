package com.luminar;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateuser")
	public class AdminUpdateUser extends HttpServlet {
		private static final long serialVersionUID = 1L;
		final String driver = "com.mysql.cj.jdbc.Driver";
		final String url = "jdbc:mysql://localhost:3306/hr-helpdesk";
		final String user = "root";
		final String password = "mysql";

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		RequestDispatcher dis = null;
		String userName = "";
		String userRole = "";
		String userId = "";

		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				
				HttpSession session = request.getSession(false);
				/*String name = (String) session.getAttribute("user");
				String adminID = (String) session.getAttribute("userId");
				*/
				String userperID = request.getParameter("userperID");
				String uname = request.getParameter("uname");
				String uemailId = request.getParameter("uemailId");
				
				String uusername = request.getParameter("uusername");			
				String upass = request.getParameter("upassword");
				String role = request.getParameter("role");

				Class.forName(driver);
				con=DriverManager.getConnection(url,user,password);
						
				
						
				String sql="UPDATE employee set emp_name=?,emp_username=?,emp_password=?,emp_email=?,emp_role=? where emp_id=?";
						
				
				pst=con.prepareStatement(sql);
				
				pst.setString(1, uname);
				pst.setString(2, uusername);
				pst.setString(3, upass);
				pst.setString(4, uemailId);
				pst.setString(5, role);
				pst.setString(6, userperID);
				
				
		        
				pst.executeUpdate();
				
				response.sendRedirect("viewusers");
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}




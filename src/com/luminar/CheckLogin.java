package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class CheckLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// Retrieve form parameters
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		Boolean isValidUser = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String userName = "";
		String passWord = "";
		String userRole = "";
		int userId=0;		
		


		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root", "mysql");

			
			String sql = "SELECT * FROM employee WHERE emp_username = ? AND emp_password = ? AND emp_role = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, role);

			// Execute the query
			rs = pstmt.executeQuery();

			
			while(rs.next()){
				isValidUser = true;
					userId=rs.getInt(1);
					userName = rs.getString(2);
					userRole = rs.getString(3);
				}
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		HttpSession session=req.getSession();
		session.setAttribute("user", userName);
		session.setAttribute("id", userId);
		

		if (isValidUser) {
			switch (role.toLowerCase()) {
			case "hr":
				res.sendRedirect("hrsolution");
				break;
			case "employee":

				res.sendRedirect("ticketrasing");
				break;
			case "admin":
				res.sendRedirect("admin");
				break;
			default:
				res.sendRedirect("home.html");

			}
		} else {
			PrintWriter out = res.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Incorrect username or password');");
			out.println("window.location='home.html';");
			out.println("</script>");
		}
	
		/*
		 * }
		 * 
		 * 
		 * public boolean validateUser(String username, String password, String
		 * role) {
		 */
		/* boolean isValidUser = false; */

	}
}

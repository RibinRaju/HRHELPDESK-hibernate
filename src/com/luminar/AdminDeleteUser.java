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


@WebServlet("/deleteuser")
public class AdminDeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hr-helpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	RequestDispatcher dis = null;
	
	
	String userId = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
						
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			int id=Integer.parseInt(request.getParameter("did"));
			
			
			String sql = "DELETE FROM employee WHERE emp_id = " + id;

			pst=con.prepareStatement(sql);
            
            int rowsAffected =pst .executeUpdate();

            if (rowsAffected > 0) {
               response.sendRedirect("viewusers");
            } else {
            	response.sendRedirect("error");
            }
			
	
		
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

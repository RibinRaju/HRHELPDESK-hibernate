package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/forgotpass")
public class ForgotPass  extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		
		        // Retrieve form parameters
		        String username = req.getParameter("username");
		        String password = req.getParameter("password");
		        
		        
		        // Perform database validation
		        boolean isValidUser = validateUser(username);
		        
		        if (isValidUser) {
		        	 try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root", "mysql");
						String sql="UPDATE employee set emp_password=? WHERE emp_username=?";
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, password);
			            pstmt.setString(2, username);
			            
			            pstmt.executeUpdate();
			            conn.close();
			            res.sendRedirect("home.html");
					} catch (Exception e) {
						
						e.printStackTrace();
					
					}
			            
		        }else{
		        	res.sendRedirect("forgetpassword.html");
		        }
		            
	}

		    
		    public boolean validateUser(String username) {
		    	 	boolean isValidUser = false;
			        Connection conn = null;
			        PreparedStatement pstmt = null;
			        ResultSet rs = null;
		        
		        try {
		            
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root", "mysql");
		            
		            
		            String sql = "SELECT * FROM employee WHERE emp_username = ? ";
		            pstmt = conn.prepareStatement(sql);
		            pstmt.setString(1, username);
		           
		            
		            
		            rs = pstmt.executeQuery();
		            
		            
		            if (rs.next()) {
		                isValidUser = true;
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            // Close JDBC objects
		            try { if (rs != null) rs.close(); } catch (Exception e) {}
		            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		            try { if (conn != null) conn.close(); } catch (Exception e) {}
		        }
		        
		        return isValidUser;
		    }
        }
	
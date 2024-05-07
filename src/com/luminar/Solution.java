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

@WebServlet("/solution")
public class Solution extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hr-helpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String desc = "";
	int id = 0;
	int tick_id=0;	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		int id = Integer.parseInt(req.getParameter("sid")); // Corrected
															// retrieval of
													// ticket ID
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "SELECT t.ticket_id,e.emp_id,e.emp_name,t.ticket_date,t.ticket_catogery,t.ticket_desc "
					+ "FROM employee e " + "INNER JOIN ticketdetails t ON e.emp_id = t.emp_id " + "WHERE t.ticket_id=?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				desc = rs.getString("ticket_desc");
				tick_id=rs.getInt("ticket_id");
			}

			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html>");
			res.setContentType("text/html");

			 String desc = (String) session.getAttribute("description_" + id);

		
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			out.println("<title>Login Page</title>");
			out.println("<style>");
			out.println("body {");
			out.println("font-family: Arial, sans-serif;");
			out.println("background-color: #5ed4f2;");
			out.println("}");
			out.println(".container {");
			out.println("width: 300px;");
			out.println("margin: 0 auto;");
			out.println("padding: 30px;");
			out.println("background-color: #ffffff;");
			out.println("border-radius: 5px;");
			out.println("box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
			out.println("}");
			out.println("input[type=\"text\"],textarea, input[type=\"password\"], select {");
			out.println("width: 100%;");
			out.println("padding: 12px;");
			out.println("margin: 8px 0;");
			out.println("box-sizing: border-box;");
			out.println("border: 1px solid #ccc;");
			out.println("border-radius: 4px;");
			out.println("}");
			out.println("input[type=\"submit\"] {");
			out.println("width: 100%;");
			out.println("background-color: #4CAF50;");
			out.println("color: white;");
			out.println("padding: 14px 20px;");
			out.println("margin: 8px 0;");
			out.println("border: none;");
			out.println("border-radius: 4px;");
			out.println("cursor: pointer;");
			out.println("}");
			out.println("input[type=\"submit\"]:hover {");
			out.println("background-color: #45a049;");
			out.println("}");
			out.println("</style>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"container\">");
			out.println("<form action=\"solution\" method=\"post\">");
			out.println("<h2>solution</h2>");
			out.println("<input type='hidden' name='tickeID' value='" + id + "' ");
			out.println("<table>");
			out.println("<tr><td>Description::</td><td>" + desc + "</td></tr>");
			out.println(
					"<tr><td>Solution:</td><td><textarea name=\"solution\" rows=\"5\" cols=\"30\"></textarea></td></tr>");
			out.println("</table>");
			out.println("<input type=\"submit\" value=\"Submit\">");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String solution = req.getParameter("solution");
	    /*int id = Integer.parseInt(req.getParameter("ticketID")); */// Corrected retrieval of ticket ID

	    try {
	        Class.forName(driver);
	        conn = DriverManager.getConnection(url, user, password);
	        String sql = "UPDATE ticketdetails SET ticket_solution=? WHERE ticket_id=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, solution);
	        pstmt.setInt(2,tick_id);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            // Successful update
	            res.sendRedirect("hrsolution");
	        } else {
	            // Update failed
	            PrintWriter out = res.getWriter();
	            out.println("Failed to update ticket solution.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    }
	}




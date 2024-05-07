package com.luminar;

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
@WebServlet("/previoustickets")
public class PreviousTicket extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
			

			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = "";
			ResultSet rs = null;
			String driver = "com.mysql.cj.jdbc.Driver";
			
			HttpSession Session = req.getSession(false);
			int id = (Integer) Session.getAttribute("id");
			try{
			Class.forName(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root", "mysql");
			sql = "Select ticket_date,ticket_desc,ticket_solution from ticketdetails where emp_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			res.setContentType("text/html");
			PrintWriter out=res.getWriter();
			
			
			out.println("<html lang=\"en\">"
				    + "<head>"
				    + "<title>HR Solutions</title>"
				    + "<style>"
				    + "body {"
				    + "    font-family: Arial, sans-serif;"
				    + "    background-color: cyan;"
				    + "    margin: 0;"
				    + "    padding: 0;"
				    + "}"
				    + ".container {"
				    + "    width: 50%;"
				    + "    margin: 50px auto;"
				    + "    background-color: #fff;"
				    + "    padding: 20px;"
				    + "    border-radius: 5px;"
				    + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
				    + "}"
				    + "input[type=\"text\"], textarea, select, input[type=\"date\"] {"
				    + "    width: 100%;"
				    + "    padding: 10px;"
				    + "    margin: 8px 0;"
				    + "    box-sizing: border-box;"
				    + "    border: 1px solid #ccc;"
				    + "    border-radius: 4px;"
				    + "    resize: none;"
				    + "}"
				    + "input[type=\"submit\"] {"
				    + "    background-color: #4CAF50;"
				    + "    color: white;"
				    + "    padding: 14px 20px;"
				    + "    margin: 8px 0;"
				    + "    border: none;"
				    + "    border-radius: 4px;"
				    + "    cursor: pointer;"
				    + "}"
				    + "input[type=\"submit\"]:hover {"
				    + "    background-color: #45a049;"
				    + "}"
				    + "</style>"
				    + "</head>"
				    + "<body>"
				    + "<div class=\"container\">"
				    + "<h2><b>Previous Tickets and solutions</h2>"
				    + "<table border=2>"
				    + "<thead>"
				    + "<tr>"
				    + "<th>Date</th>"
				    + "<th>Problem</th>"
				    + "<th>Solution</th>"
				    + "</tr>"
				    + "</thead>"
				    + "<tbody>");

				while (rs.next()) {
				    out.println("<tr>"
				    	+"<td>" + rs.getDate("ticket_date") + "</td>"	
				        + "<td>" + rs.getString("ticket_desc") + "</td>"
				        + "<td>" + rs.getString("ticket_solution") + "</td>"
				        
				        + "</tr>");
				}

				out.println("</tbody>"
				    + "</table>"
				    + "</div>"
				   
				    + "</body>"
				    + "</html>");
				out.close();
			}catch(Exception e){
				e.printStackTrace();		
				}
			
			
	}
}
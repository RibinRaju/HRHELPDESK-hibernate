package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

@WebServlet("/editticket")
public class AdminTicketEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hr-helpdesk";
	final String user = "root";
	final String password = "mysql";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	
	String userName = "";
	String userRole = "";
	String userId = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String adminID = (String) session.getAttribute("userId");

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int ticketId = Integer.parseInt(request.getParameter("eid"));

			String sql = "SELECT t.ticket_id, e.emp_name, t.ticket_date, t.ticket_catogery, t.ticket_desc, t.ticket_solution "
					+ "FROM employee e " + "INNER JOIN ticketdetails t ON e.emp_id = t.emp_id " + "WHERE t.ticket_id = ?";

			pst = con.prepareStatement(sql);
			pst.setInt(1, ticketId);
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			out.println("<title>Edit Ticket</title>");
			out.println("<style>");
			out.println("body {");
			out.println("    font-family: Arial, sans-serif;");
			out.println("    background-color: #5ed4f2;");
			out.println("}");
			out.println(".container {");
			out.println("    width: 50%;");
			out.println("    margin: 0 auto;");
			out.println("    padding: 30px;");
			out.println("    background-color: #ffffff;");
			out.println("    border-radius: 5px;");
			out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
			out.println("}");
			out.println("input[type=\"text\"], textarea {");
			out.println("    width: calc(100% - 24px);");
			out.println("    padding: 12px;");
			out.println("    margin: 8px 0;");
			out.println("    box-sizing: border-box;");
			out.println("    border: 1px solid #ccc;");
			out.println("    border-radius: 4px;");
			out.println("}");
			out.println("input[type=\"submit\"] {");
			out.println("    width: 100%;");
			out.println("    background-color: #4CAF50;");
			out.println("    color: white;");
			out.println("    padding: 14px 20px;");
			out.println("    margin: 8px 0;");
			out.println("    border: none;");
			out.println("    border-radius: 4px;");
			out.println("    cursor: pointer;");
			out.println("}");
			out.println("input[type=\"submit\"]:hover {");
			out.println("    background-color: #45a049;");
			out.println("}");
			out.println("</style>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"container\">");
			out.println("<form name='edituser' action='updateticket' method='get'>");
			out.println("<h1>Edit Ticket</h1>");

			while (rs.next()) {
				out.println("<input type='hidden' name='ticket_id' value='" + rs.getInt("ticket_id") + "'/></br>");

				out.println("<label><b>Date:</b></label>");
				out.println("<input type='text' name='date' value='" + rs.getString("ticket_date") + "' readonly/>");

				out.println("<label><b>Name:</b></label>");
				out.println("<input type='text' name='emp_name' value='" + rs.getString("emp_name") + "' readonly/>");

				out.println("<label><b>Category:</b></label>");
				out.println("<input type='text' name='category' value='" + rs.getString("ticket_catogery")
						+ "' readonly/>");

				out.println("<label><b>Description:</b></label>");
				out.println("<textarea name='description' rows='5' cols='30' readonly>" + rs.getString("ticket_desc")
						+ "</textarea>");

				out.println("<label><b>Solution:</b></label>");
				out.println("<textarea name='solution' rows='5' cols='30'>" + rs.getString("ticket_solution")
						+ "</textarea>");

				out.println("<input type='submit' value='Update'/>");

			}

			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

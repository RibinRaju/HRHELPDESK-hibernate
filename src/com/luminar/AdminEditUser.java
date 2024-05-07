package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/edituser")
public class AdminEditUser extends HttpServlet {
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");
			String adminID = (String) session.getAttribute("userId");

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int id = Integer.parseInt(request.getParameter("eid"));

			String sql = "SELECT emp_id,emp_name,emp_email,emp_username,emp_password,emp_role FROM employee where emp_id="
					+ id;

			// to delete
			//System.out.println(sql);

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

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
			out.println("<script>");
			out.println("function goBack() {");
			out.println("    window.history.back();");
			out.println("}");
			out.println("</script>");
			out.println("</style>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"container\">");
			
			

			out.println("<form name='edit-user' action='updateuser' method='post'>");
			out.println("<h1>---- User Details ----</h1>");
			while (rs.next()) {
				out.println("<input type='hidden' name='userperID' value='" + rs.getInt("emp_id") + "'/></br>");
				out.println("<table border='0'>");

				out.println("<tr>");
				out.println("<td>" + "<label><b>Name</b></label>" + "</td>");
				out.println("<td>" + "<input  type='text'  name='uname' value='" + rs.getString("emp_name") + "'> " + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>" + "<label><b>Email ID</b></label>" + "</td>");
				out.println(
						"<td>" + "<input  type='text'  name='uemailId' value='" + rs.getString("emp_email") + "'> " + "</td>");
				out.println("</tr>");
				
				

				

				out.println("<tr>");
				out.println("<td>" + "<label><b>User Name</b></label>" + "</td>");
				out.println(
						"<td>" + "<input  type='text'  name='uusername' value='" + rs.getString("emp_username") + "'> " + "</td>");
				out.println("</tr>");

				out.println("<tr>");
				out.println("<td>" + "<label><b>Password</b></label>" + "</td>");
				out.println(
						"<td>" + "<input  type='text'  name='upassword' value='" + rs.getString("emp_password") + "'> " + "</td>");
				out.println("</tr>");

				out.println("<tr>");
				out.println("<td>" + "<label><b>Role</b></label>" + "</td>");
				out.println("<td>" + "<select name='role' id='role'>" + "<option value='admin'"
						+ (rs.getString(6).equals("admin") ? " selected" : "") + ">Admin</option>"
						+ "<option value='hr'" + (rs.getString(6).equals("hr") ? " selected" : "") + ">HR User</option>"
						+ "<option value='employee'" + (rs.getString(6).equals("employee") ? " selected" : "")
						+ ">Employee</option>" + "</select>" + "</td>");
				out.println("</tr>");

				out.println("<tr>");
				out.println("<td>" + "</td>");
				out.println("<td style='padding-top:25px'>" + "<input type='submit' value='Update' /> " + "</td>");
				out.println("<td style='padding-top:25px'>" + "<input type='button' value='back' onClick='goBack()' /> " + "</td>");
				out.println("</tr>");

				out.println("</table>");
			}
			out.println("</form ></div>");
			out.println("</body></html>");

			out.println("<html><body>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
}



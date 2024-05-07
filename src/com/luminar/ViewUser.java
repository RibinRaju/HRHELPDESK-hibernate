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

@WebServlet("/viewusers")

public class ViewUser  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final String driver="com.mysql.cj.jdbc.Driver";
	final String url="jdbc:mysql://localhost:3306/hr-helpdesk";
	final String user="root";
	final String password="mysql";
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			/*HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");*/

			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
						
			String sql="SELECT emp_id,emp_name,emp_username,emp_email,emp_role FROM employee";
			/*System.out.println(sql);*/
			pst=con.prepareStatement(sql);
			rs=pst.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>HR Solutions</title>");
            out.println("<style>");
            out.println("* {");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("    box-sizing: border-box;");
            out.println("}");
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    background-color: #f0f0f0;");
            out.println("}");
            out.println(".container {");
            out.println("    width: 80%;");
            out.println("    margin: 20px auto;");
            out.println("    background-color: #fff;");
            out.println("    padding: 20px;");
            out.println("    border-radius: 8px;");
            out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
            out.println("}");
            out.println("h2 {");
            out.println("    text-align: center;");
            out.println("    margin-bottom: 20px;");
            out.println("    color: #333;");
            out.println("}");
            out.println("table {");
            out.println("    width: 100%;");
            out.println("    border-collapse: collapse;");
            out.println("    margin-top: 20px;");
            out.println("}");
            out.println("th, td {");
            out.println("    padding: 10px;");
            out.println("    text-align: left;");
            out.println("}");
            out.println("th {");
            out.println("    background-color: #5ed4f2;");
            out.println("    color: #fff;");
            out.println("}");
            out.println("tr:nth-child(even) {");
            out.println("    background-color: #f2f2f2;");
            out.println("}");
            out.println(".solution-link {");
            out.println("    text-decoration: none;");
            out.println("    color: #333;");
            out.println("}");
            out.println(".solution-link:hover {");
            out.println("    text-decoration: underline;");
            out.println("    color: #000;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
			out.println("<h1><center>----User List----</center></h1>");
				out.println("<table border='3' style='width: 80%;'>");
					out.println("<tr>");
						out.println("<th>Sl No</th>");						
						out.println("<th>Name</th>");
						out.println("<th>User Name</th>");
						out.println("<th>Email ID</th>");
						out.println("<th>Role</th>");
						
						out.println("<th>EDIT</th>");
						out.println("<th>DELETE</th>");
					out.println("</tr>");
					
					int i=1;
					while(rs.next())	{
						out.println("<tr>");
							out.println("<td>"+rs.getInt(1)+"</td>");
							out.println("<td>"+rs.getString(2)+"</td>");
							out.println("<td>"+rs.getString(3)+"</td>");
							out.println("<td>"+rs.getString(4)+"</td>");
							out.println("<td>"+rs.getString(5)+"</td>");
																		
														
							out.println("<td> <a href='edituser?eid="+ rs.getInt(1)+"'>Edit</a></td>");
							out.println("<td> <a href='deleteuser?did="+ rs.getInt(1)+"'>Delete</a></td>");
						out.println("</tr>");
						i++;
					}
				out.println("</table></div>");
			out.println("</body></html>");
				
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}


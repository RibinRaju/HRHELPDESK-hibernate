package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addemployee")
public class AddEmployee extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.print("<html>\n" + "    <head>\n" + "        <title>HR Admin Dashboard</title>\n" + "        <style>\n"
					+ "            body {\n" + "                font-family: Arial, sans-serif;\n"
					+ "                background-color: cyan;\n" + "                margin: 0;\n"
					+ "                padding: 0;\n" + "            }\n" + "            header {\n"
					+ "                background-color: cyan;\n" + "                color: black;\n"
					+ "                padding: 20px;\n" + "                text-align: center;\n" + "            }\n"
					+ "            nav {\n" + "                background-color:cyan;\n"
					+ "                color:black;\n" + "                padding: 10px;\n"
					+ "                text-align: center;\n" + "            }\n" + "            nav a {\n"
					+ "                color: #fff;\n" + "                text-decoration: none;\n"
					+ "                padding: 10px;\n" + "                margin: 0 10px;\n" + "            }\n"
					+ "            .container {\n" + "                width: 300px;\n"
					+ "                margin: 0 auto;\n" + "                padding: 30px;\n"
					+ "                background-color: transparent;\n" + "                border-radius: 5px;\n"
					+ "            }\n" + "            input[type=\"text\"], input[type=\"password\"], select {\n"
					+ "                width: 100%;\n" + "                padding: 14px 20px;\n"
					+ "                margin: 8px 0;\n" + "                box-sizing: border-box;\n"
					+ "                border: none;\n" + "                border-radius: 40px;\n" + "            }\n"
					+ "            input[type=\"submit\"] {\n" + "                width: 100%;\n"
					+ "                background-color: #4CAF50;\n" + "                color: white;\n"
					+ "                padding: 14px 20px;\n" + "                margin: 8px 0;\n"
					+ "                border: none;\n" + "                border-radius: 30px;\n"
					+ "                cursor: pointer;\n" + "            }\n" +

					"            input[type=\"submit\"]:hover {\n" + "                background-color: #45a049;\n"
					+ "            }\n" + "            h2 {\n" + "                text-align: center;\n"
					+ "            }\n" + "            table {\n" + "                width: 100%;\n"
					+ "                border-collapse: collapse;\n" + "            }\n" + "            th, td {\n"
					+ "                padding: 10px;\n" + "                text-align: left;\n"
					+ "                border-bottom: 1px solid #ddd;\n" + "            }\n" + "        </style>\n"
					+ "    </head>\n<script>function goBack() {"
					+	"    window.history.back();"
					 + "}"
					+"</script>"
			
			
			
					+"    <body>\n" + "        <header>\n"
					+ "            <h1>HR Admin Dashboard</h1>\n" + "        </header>\n" + "        <nav>\n"
					+ "            <a href=\"viewtickets\">View Tickets</a>\n"
					+ "            <a href=\"addemployee\">Add Employee</a>\n" + "        </nav>\n"
					+ "<div class=\"container\">\n" + "    <center><h2>User Management Form</h2></center>\n"
					+ "    <form action=\"addemployee\" method=\"post\">\n"
					+ "        <label for=\"name\">Name:</label>\n"
					+ "        <input type=\"text\" id=\"name\" name=\"name\" required>\n"
					+ "        <label for=\"email\">Email:</label>\n"
					+ "        <input type=\"text\" id=\"email\" name=\"email\" required>\n"
					+ "        <label for=\"username\">Username:</label>\n"
					+ "        <input type=\"text\" id=\"username\" name=\"username\" required>\n"
					+ "        <label for=\"password\">Password:</label>\n"
					+ "        <input type=\"password\" id=\"password\" name=\"password\" required>\n"
					+ "        Role&nbsp;<select name=\"role\"><option value=\"HR_Admin\">Admin</option><option value=\"HR\">HR</option><option value\"employee\">Employee</option>\n"
					+ "        <input type=\"submit\" value=\"Add user\">\n <input type=\"button\" value=\"back\"on>" + "    </form>\n" + "</div>\n"
					+ "    </body>\n" + "</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
			String name = req.getParameter("name");
			String email=req.getParameter("email");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String role=req.getParameter("role");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root",
						"mysql");
				String sql = "INSERT INTO employee (emp_name, emp_email, emp_username, emp_password, emp_role) VALUES (?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				pstmt.setString(3, username);
				pstmt.setString(4, password);
				pstmt.setString(5, role);
	
				int executed=pstmt.executeUpdate();
				if(executed>0){
					res.sendRedirect("addemployee");
				}else{
					out.print("pending");
				}
			
				conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
}

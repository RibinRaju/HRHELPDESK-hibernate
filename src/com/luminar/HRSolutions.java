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

@WebServlet("/hrsolution")
public class HRSolutions extends HttpServlet {

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            String sql = "SELECT t.ticket_id,e.emp_id,e.emp_name,t.ticket_date,t.ticket_catogery,t.ticket_desc "
                    + "FROM employee e "
                    + "INNER JOIN ticketdetails t ON e.emp_id = t.emp_id "
                    + "WHERE t.ticket_solution IS NULL";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

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
            out.println(".logout {");
            out.println("    text-align: right;");
            out.println("    margin-bottom: 10px;");
            out.println("}");
            out.println(".logout-link {");
            out.println("    text-decoration: none;");
            out.println("    color: #333;");
            out.println("}");
            out.println(".logout-link:hover {");
            out.println("    text-decoration: underline;");
            out.println("    color: #000;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<div class=\"logout\">");
            out.println("<a href=\"logout\" class=\"logout-link\">Logout</a>");
            out.println("</div>");
            out.println("<h2>HR Solutions</h2>");
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Date</th>");
            out.println("<th>Employee Name</th>");
            out.println("<th>Problem</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            out.print("<div>");

            HttpSession session = request.getSession();
            while (rs.next()) {
                id = rs.getInt("ticket_id");
                desc = rs.getString("ticket_desc");
                out.println("<tr>");
                out.println("<td>" + rs.getDate("ticket_date") + "</td>");
                out.println("<td>" + rs.getString("emp_name") + "</td>");
                out.println("<td>" + rs.getString("ticket_catogery") + "</td>");
                out.println("<td><a href='solution?sid=" + id + "' class=\"solution-link\">Give Solution</a></td>");
                out.println("</tr>");
                session.setAttribute("description_" + id, desc);
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

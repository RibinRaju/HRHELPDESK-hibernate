package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ticketrasing")
public class TicketRaising extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");

        PrintWriter pw = res.getWriter();
        HttpSession session = req.getSession(false);
        String name = (String) session.getAttribute("user");

        pw.println("<html><head> <meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Raise a Ticket</title>" + "<style>" + "body {" + "    font-family: Arial, sans-serif;"
                + "    background-color: cyan;" + "    margin: 0;" + "    padding: 0;" + "}" + ".container {"
                + "    width: 50%;" + "    margin: 50px auto;" + "    background-color: #fff;" + "    padding: 20px;"
                + "    border-radius: 5px;" + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" + "}"
                + "input[type=\"text\"], textarea, select, input[type=\"date\"] {" + "    width: 100%;"
                + "    padding: 10px;" + "    margin: 8px 0;" + "    box-sizing: border-box;"
                + "    border: 1px solid #ccc;" + "    border-radius: 4px;" + "    resize: none;" + "}"
                + "input[type=\"submit\"] {" + "    background-color: #4CAF50;" + "    color: white;"
                + "    padding: 14px 20px;" + "    margin: 8px 0;" + "    border: none;" + "    border-radius: 4px;"
                + "    cursor: pointer;" + "}" + "input[type=\"submit\"]:hover {" + "    background-color: #45a049;"
                + "</b> }" + "</style>" + "</head>" + "<body>" + "    <div class=\"container\">" + "        <h2><b>welcome" + name
                + "</h2>" + "        <form action=\"ticketrasing\" method=\"post\">"
                + "            <label for=\"due_date\">Due Date:</label>"
                + "            <input type=\"date\" id=\"duedate\" name=\"duedate\">"
                + "            <label for=\"category\">Category:</label>"
                + "            <select id=\"category\" name=\"category\">"
                + "                <option value=\"Finance\">Finance</option>"
                + "                <option value=\"information technology\">IT</option>"
                + "                <option value=\"hr\">HR</option>"
                + "                <option value=\"legal\">Legal</option>" + "            </select>"
                + "            <label for=\"description\">Description:</label>"
                + "            <textarea id=\"description\" name=\"description\" rows=\"4\" required></textarea><input type=\"submit\" value=\"Submit\">"
                + "    </div>"
                + "<div class=\"container\"><a href=\"previoustickets\"><b>Previous Tickets</b></a> <a href=\"logout\">Logout</a></div></form></body>"
                + "</html>");
        pw.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ResultSet rs = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String dateStr = req.getParameter("duedate");
        HttpSession session = req.getSession(false);
        int id = (Integer) session.getAttribute("id");
        String category = req.getParameter("category");
        String desc = req.getParameter("description");
        LocalDate date = LocalDate.parse(dateStr);
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr-helpdesk", "root", "mysql");
            sql = "INSERT INTO ticketdetails(emp_id,ticket_date,ticket_catogery,ticket_desc)VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            pstmt.setString(3, category);
            pstmt.setString(4, desc);
            pstmt.executeUpdate();
            conn.close();
            res.sendRedirect("ticketrasing");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

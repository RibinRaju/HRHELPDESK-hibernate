package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateticket")
public class AdminUpdateTicket extends HttpServlet {

    private static final long serialVersionUID = 1L;

    final String driver = "com.mysql.cj.jdbc.Driver";
    final String url = "jdbc:mysql://localhost:3306/hr-helpdesk";
    final String user = "root";
    final String password = "mysql";

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String tick_id = request.getParameter("ticket_id");
            String tick_solution = request.getParameter("solution");
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            sql = "UPDATE ticketdetails SET ticket_solution=? WHERE ticket_id=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, tick_solution);
            pst.setString(2, tick_id);

            pst.executeUpdate();
            response.sendRedirect("viewtickets");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
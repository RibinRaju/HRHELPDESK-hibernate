package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminPage extends HttpServlet {

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
					+ "            .container {\n" + "                width: 80%;\n"
					+ "                margin: 20px auto;\n" + "                padding: 20px;\n"
					+ "                background-color: #fff;\n" + "                border-radius: 5px;\n"
					+ "                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" + "            }\n"
					+ "            h2 {\n" + "                text-align: center;\n" + "            }\n"
					+ "            table {\n" + "                width: 100%;\n"
					+ "                border-collapse: collapse;\n" + "            }\n" + "            th, td {\n"
					+ "                padding: 10px;\n" + "                text-align: left;\n"
					+ "                border-bottom: 1px solid #ddd;\n" + "            }\n" + "        </style>\n"
					+ "    </head>\n" + "    <body>\n" + "        <header>\n"
					+ "            <h1>HR Admin Dashboard</h1>\n" + "        </header>\n" + "        <nav>\n"
					+ "            <a href=\"viewtickets\">View Tickets</a>\n"
					+ "            <a href=\"viewusers\">View users</a>\n"
					+ "            <a href=\"addemployee\">Add Employee</a>\n" 
					
					+ "            <a href=\"logout\">Logout</a>\n" 
					+ "        </nav>\n" +

					"    </body>\n" + "</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
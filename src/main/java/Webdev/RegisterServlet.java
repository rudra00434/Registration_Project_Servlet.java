package Webdev;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        // Get values from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String securitry_question = request.getParameter("securitry_question");
        String answer = request.getParameter("answer");
        String contact = request.getParameter("contact");

        try {
            // 1. Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to MySQL
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student", "root", "rudranil123"
            );

            // 3. Insert into table
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO rudra (email ,password,name, securitry_question,answer, contact) VALUES (?,?,?,?,?,?)"
            );

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, securitry_question);
            ps.setString(5, answer);
            ps.setString(6, contact);

            int i = ps.executeUpdate();

            out.println("<html><body style='font-family:Arial; text-align:center; background:#f4f7fb;'>");
            if (i > 0) {
                out.println("<h2>Registration Successful!</h2>");
                out.println("<p>Welcome, <b>" + name + "</b></p>");
                out.println("<a href='reg.html'>Go Back</a><br><br>");
                out.println("<a href='login.html'>Go to Login Page</a><br><br>");
                out.println("<a href='Loginservlet'>Proceed to LoginServlet</a>");
            } else {
                out.println("<h3>Registration Failed. Please Try Again.</h3>");
                out.println("<a href='reg.html'>Go Back</a><br><br>");
                out.println("<a href='login.html'>Login Page</a>");
            }
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body style='font-family:Arial; text-align:center;'>");
        out.println("<h3>This servlet only handles form submission via POST method.</h3>");
        out.println("<a href='reg.html'>Go to Registration Form</a><br><br>");
        out.println("<a href='login.html'>Go to Login Page</a>");
        out.println("</body></html>");
    }
}

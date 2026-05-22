package Webdev;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/LoginServlet")
public class Loginservlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// JDBC connection details
    private static final String URL = "jdbc:mysql://localhost:3306/student";  // your database name
    private static final String USER = "root"; // MySQL username
    private static final String PASSWORD = "rudranil123"; // <-- replace with your MySQL root password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get parameters from HTML form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish Connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepare SQL query to check login
            String query = "SELECT * FROM rudra WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful
                out.println("<html><body style='background-color:lightgreen; text-align:center;'>");
                out.println("<h2>Login Successful! Welcome, " + rs.getString("name") + "!</h2>");
                // ✅ Added hyperlink to registration page
                out.println("<p><a href='reg.html' style='color:blue; text-decoration:none;'>Go to Registration Page</a></p>");
                out.println("</body></html>");
            } else {
                // Invalid login
                out.println("<html><body style='background-color:pink; text-align:center;'>");
                out.println("<h2>Invalid Email or Password!</h2>");
                out.println("<a href='login.html'>Try Again</a>");
                // ✅ Added hyperlink to registration page
                out.println("<p>Don't have an account? <a href='reg.html' style='color:blue; text-decoration:none;'>Register Here</a></p>");
                out.println("</body></html>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}

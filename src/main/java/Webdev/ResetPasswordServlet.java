package Webdev;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASSWORD = "rudranil123";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String answer = request.getParameter("answer");
        String newPassword = request.getParameter("newPassword");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM rudra WHERE email=? AND answer=?");
            ps.setString(1, email);
            ps.setString(2, answer);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PreparedStatement updatePs = con.prepareStatement("UPDATE rudra SET password=? WHERE email=?");
                updatePs.setString(1, newPassword);
                updatePs.setString(2, email);
                updatePs.executeUpdate();

                out.println("<html><body style='text-align:center;background-color:lightgreen;'>");
                out.println("<h3>Password reset successfully!</h3>");
                out.println("<a href='login.html'>Go to Login</a>");
                out.println("</body></html>");
            } else {
                out.println("<html><body style='text-align:center;background-color:pink;'>");
                out.println("<h3>Incorrect answer! Try again.</h3>");
                out.println("<a href='forgotpassword.html'>Back</a>");
                out.println("</body></html>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}


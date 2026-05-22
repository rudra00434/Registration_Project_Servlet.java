package Webdev;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps = con.prepareStatement("SELECT securitry_question FROM rudra WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String question = rs.getString("securitry_question");
                out.println("<html><body style='text-align:center;background-color:lightblue;'>");
                out.println("<h3>Security Question:</h3>");
                out.println("<form action='ResetPasswordServlet' method='post'>");
                out.println("<p>" + question + "</p>");
                out.println("Answer: <input type='text' name='answer'><br><br>");
                out.println("<input type='hidden' name='email' value='" + email + "'>");
                out.println("New Password: <input type='password' name='newPassword'><br><br>");
                out.println("<input type='submit' value='Reset Password'>");
                out.println("</form>");
                out.println("</body></html>");
            } else {
                out.println("<h3 style='color:red;text-align:center;'>Email not found!</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}

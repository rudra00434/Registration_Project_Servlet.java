package Webdev;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class dynamicfetch extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to MySQL
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student", "root", "rudranil123"
            );
            Statement stmt=con.createStatement();
            String qi="select * from rudra";
            
            
           ResultSet rs=stmt.executeQuery(qi);
            out.println("<html><body><table border=2 width=100%>");
            out.println("<tr><th>EmailId</th><th>Password</th><th>Name</th>"+ 
            "<th>securitry_question</th><th>answer</th></tr>");
            while(rs.next()) {
            	  out.println("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+
            rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");
            }
            out.println("</table></body></html>");
            con.close();
            
        }catch(Exception e) {
        	   out.println(e);
        }
    }
}

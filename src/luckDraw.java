import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class luckDraw extends HttpServlet {
    private userInfo userInfo;
    private String status =  "{\"status\":\"failure\"}";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //抽奖并存入数据库
        getMysql getMysql = new getMysql();
        try {
            getMysql.connect_mysql();
            String sql = "update test_schema.user_data set prize = '"+request.getParameter("prize")+"'where username = '"+request.getParameter("username")+"'";
            getMysql.statement.executeUpdate(sql);
            status = "{\"status\":\""+request.getParameter("prize")+"\"}";
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getMysql.closeMyConnection();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(status);
    }
}

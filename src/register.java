import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class register extends javax.servlet.http.HttpServlet {
    private String username;
    private String password;
    private String status;
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //获取前端发来的用户信息,加入到数据库中
        username = request.getParameter("username");
        password = request.getParameter("password");

        getMysql getMysql = new getMysql();
        try{
            getMysql.connect_mysql();
            String sql = "INSERT INTO test_schema.user_data(username,password,prize)VALUES('"+username+"','"+password+"',null)";
            getMysql.statement.executeUpdate(sql);
            System.out.println("用户名:"+username+"注册啦");
            status = "{\"status\":\"success\"}";
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (MySQLIntegrityConstraintViolationException e){
//            e.printStackTrace();
            System.out.println("用户名重复");
            status = "{\"status\":\"failure\"}";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getMysql.closeMyConnection();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(status);
    }
}

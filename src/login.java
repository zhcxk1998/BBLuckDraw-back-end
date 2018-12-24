
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends HttpServlet {
    private static String username = null;
    private static String password = null;
    private String status;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //查询数据库中有无此用户名
        username = request.getParameter("username");
        password = request.getParameter("password");
        boolean username_existent = false;
        boolean password_error = false;
        getMysql getMysql = new getMysql();
        try {
            getMysql.connect_mysql();
            ResultSet res = null;
            res = getMysql.statement.executeQuery("SELECT * from test_schema.user_data");
            while(res.next()){
                String temp = res.getString("username");
                if(username.equals(temp)){
                    username_existent = true;
                    String temp2 = res.getString("password");
                    if(!password.equals(temp2)){
                        password_error = true;
                    }
                }
            }
            getMysql.closeMyConnection();
            if(username_existent == true && password_error == false){
                status = "{\"status\":\"success\"}";
                System.out.println(username+"登录成功");
            }
            else if(username_existent == false) {
                status = "{\"status\":\"notexist\"}";
                System.out.println("用户名不存在");
            }
            else if(password_error == true){
                status = "{\"status\":\"failure\"}";
                System.out.println("密码错误");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.write(status);
    }
}

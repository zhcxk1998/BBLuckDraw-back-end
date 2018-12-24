import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class prize extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //获取数据库用户信息
        getMysql getMysql = new getMysql();
        ArrayList<userInfo> arrayList = new ArrayList<userInfo>();
        try {
            getMysql.connect_mysql();
            ResultSet res = null;
            res = getMysql.statement.executeQuery("SELECT * from test_schema.user_data");
            while(res.next()){
                arrayList.add(new userInfo(res.getString("username"),res.getString("password"),res.getString("prize")));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getMysql.closeMyConnection();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(arrayList);
        PrintWriter pw = response.getWriter();
        pw.println(json);
    }
}

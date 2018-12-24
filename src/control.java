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

public class control extends HttpServlet {
    private String status;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //控制抽奖开关
        status = request.getParameter("status");
        getMysql getMysql = new getMysql();
        if(status.equals("open")||status.equals("close")){
            try {
                getMysql.connect_mysql();
                String sql = "update test_schema.status_parameter set status = '"+status+"'";
                getMysql.statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            getMysql.closeMyConnection();
        }
        try {
            getMysql.connect_mysql();
            ResultSet res = null;
            String sql = "SELECT * from test_schema.status_parameter";
            res = getMysql.statement.executeQuery(sql);
            while(res.next()) {
                status = res.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        getMysql.closeMyConnection();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(status);
        PrintWriter pw = response.getWriter();
        pw.println(json);
    }
}

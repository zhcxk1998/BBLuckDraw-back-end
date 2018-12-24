import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class setPrize extends HttpServlet {
    private String status = "{\"status\":\"failure\"}";
    private String prize_old;
    private String prize_new;
    private String prize_type;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //设置奖品
        prize_old = request.getParameter("oldName");
        prize_new = request.getParameter("newName");
        prize_type = request.getParameter("type");
        getMysql getMysql = new getMysql();
        String sql;
        try {
            getMysql.connect_mysql();
            //当oldName为空值时，向表内添加奖品
            System.out.println(prize_old);
            System.out.println(prize_new);
            System.out.println(prize_type);
            if(prize_old == ""){
                sql = "insert into test_schema.prize_list(prize, type) VALUE ('"+prize_new+"','"+prize_type+"')";
            }
            //当newName为"null"时，向表内删除 奖品名为"oldName",等级为"type" 的奖品
            else if(prize_new.equals("null")){
                sql = "delete from test_schema.prize_list where (prize = '"+prize_old+"'and type = '"+prize_type+"')";
            }
            //当oldName,newName不为空时，向表内更新奖品与等级
            else {
                sql = "update test_schema.prize_list SET type = '"+prize_type+"',prize = '" + prize_new + "' where( prize = '" + prize_old + "'and type = '"+prize_type+"')";
            }
            getMysql.statement.executeUpdate(sql);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (MySQLIntegrityConstraintViolationException e){
            System.out.println("奖品: "+prize_new+" 重复,添加失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet res = null;
        ArrayList<prizeList> first = new ArrayList<prizeList>();
        ArrayList<prizeList> second = new ArrayList<prizeList>();
        ArrayList<prizeList> third = new ArrayList<prizeList>();
        try {
            res = getMysql.statement.executeQuery("select * from test_schema.prize_list");
            while(res.next()){

                if(res.getString("type").equals("1"))
                    first.add(new prizeList(res.getString("prize"),res.getString("type")));
                if(res.getString("type").equals("2"))
                    second.add(new prizeList(res.getString("prize"),res.getString("type")));
                if(res.getString("type").equals("3"))
                    third.add(new prizeList(res.getString("prize"),res.getString("type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getMysql.closeMyConnection();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(first);
        String json2 = mapper.writeValueAsString(second);
        String json3 = mapper.writeValueAsString(third);
        PrintWriter pw = response.getWriter();
        pw.println(json+'#'+json2+'#'+json3);
    }
}


import javax.naming.NamingException;

import java.sql.*;

public class getMysql {
    public Connection getCon() {
        return con;
    }

    public Statement getStatement() {
        return statement;
    }

    Connection con = null;
    String driver = "com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "czk8379530";
    Statement statement = null;
    public void connect_mysql() throws NamingException {
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            if(!con.isClosed()){
                System.out.println("数据库连接成功");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动没有安装");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }

    public void closeMyConnection(){//关闭数据库连接
        try{
            statement.close();
            con.close();
            System.out.println("数据库连接关闭");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("咋关不掉呢");
        }
    }


}

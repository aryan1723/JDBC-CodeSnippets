package bankingsystem;
import java.sql.DriverManager;
import java.sql.Connection;
public class DBconnection {
    public static Connection getConnection(){
        Connection conn = null;
        try {
            String url = DBconfig.get("db.url");
            String user = DBconfig.get("db.user");
            String pass = DBconfig.get("db.pass");

            conn = DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}

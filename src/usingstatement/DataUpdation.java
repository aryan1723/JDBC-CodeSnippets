package usingstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataUpdation {
    private static final String url="jdbc:mysql://localhost:3306/productcatalog";
    private static final String username = "root";
    private static final String password = "1234";

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE product SET price='%f' WHERE ID='%o'",2100.0,2);
            int rowsaffected = statement.executeUpdate(query);
            if(rowsaffected>0){
                System.out.println("Data updated sucessfully, rows affected "+rowsaffected);
            }else{
                System.out.println("Data not updated");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

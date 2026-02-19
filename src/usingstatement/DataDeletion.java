package usingstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataDeletion {
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
            String query = String.format("DELETE FROM product WHERE id='%o'",2);
            int rowsaffected = statement.executeUpdate(query);
            if(rowsaffected>0){
                System.out.println("Data deleted sucessfully, rows affected "+rowsaffected);
            }else{
                System.out.println("Data not deleted");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

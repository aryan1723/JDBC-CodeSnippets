package usingstatement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DataFetching {

    private static final String url="jdbc:mysql://localhost:3306/productcatalog";
    private static final String username="root";
    private static final String password="1234";

    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "select * from product";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String description=resultSet.getString("description");
                double price=resultSet.getDouble("price");
                int quantity=resultSet.getInt("quantity");
                System.out.println(id+" "+name+" "+description+" "+price+" "+quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

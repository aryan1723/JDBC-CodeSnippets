package usingpreparedstatement;

import java.sql.*;

public class DataFetching {
    private static final String url="jdbc:mysql://localhost:3306/productcatalog";
    private static final String username="root";
    private static final String password="1234";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            String query="SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String description=resultSet.getString("description");
                double price=resultSet.getDouble("price");
                int quantity=resultSet.getInt("quantity");
                System.out.println(id+" "+name+" "+description+" "+price+" "+quantity);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

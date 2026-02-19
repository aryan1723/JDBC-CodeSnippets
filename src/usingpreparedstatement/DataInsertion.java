package usingpreparedstatement;

import java.sql.*;

public class DataInsertion {
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
            String query="INSERT INTO product(name,description,price,quantity) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,"Mouse");
            preparedStatement.setString(2,"Cooler Master MM710");
            preparedStatement.setDouble(3,1500.0);
            preparedStatement.setInt(4,1);
            int rowsaffected = preparedStatement.executeUpdate();
            if(rowsaffected>0){
                System.out.println("Data inserted successfully rows affected"+rowsaffected);
            }
            else{
                System.out.println("data not inserted");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

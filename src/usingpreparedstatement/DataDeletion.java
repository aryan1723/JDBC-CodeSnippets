package usingpreparedstatement;

import java.sql.*;

public class DataDeletion {
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
            String query="DELETE FROM product WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,3);
            int rowsaffected = preparedStatement.executeUpdate();
            if(rowsaffected>0){
                System.out.println("Data deleted successfully rows affected"+rowsaffected);
            }
            else{
                System.out.println("data not deleted");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

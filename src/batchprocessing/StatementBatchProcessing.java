package batchprocessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StatementBatchProcessing {
    private static String url = "jdbc:mysql://localhost:3306/productcatalog";
    private static String usrname = "root";
    private static String password = "1234";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(url, usrname, password);
            Statement statement = connection.createStatement();
            Scanner in = new Scanner(System.in);

            while(true){
                System.out.println("Name:");
                String name = in.nextLine();
                System.out.println("Description:");
                String description = in.nextLine();
                System.out.println("Price:");
                double price = in.nextDouble();
                System.out.println("Quantity:");
                int quantity = in.nextInt();
                in.nextLine();
                String query = String.format("insert into product(name,description,price,quantity) values('%s','%s',%f,%d)",name,description,price,quantity);
                statement.addBatch(query);
                System.out.println("Do you want to add more data Y/N:");
                char choice = in.next().charAt(0);
                in.nextLine();
                if(choice == 'N'){
                    break;
                }
            }

            int rowsaffected[] = statement.executeBatch();

            for(int i=0;i<rowsaffected.length;i++){
                if(rowsaffected[i]==0){
                    System.out.println("Error in query "+rowsaffected[i]);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

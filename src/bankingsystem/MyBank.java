package bankingsystem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MyBank {
    public static void main(String[] args) {
        Connection myconn = DBconnection.getConnection();
        if(myconn == null){
            System.out.println("Connection to banking server failed.");
        }
        else {
            String createAccount = "insert into user(name) values(?)";
            String showBalance = "Select balance from user where accountnumber = ?";
            String depositMoney = "update user set balance = balance + ? where accountnumber = ?";
            String withdrawMoney = "update user set balance = balance - ? where accountnumber = ?";
            String delete = "delete from user where accountnumber=?";

            Scanner in = new Scanner(System.in);
            while (true){
                System.out.println("Welcome to banking System.\n"+
                        "(choose an option)\n" +
                        "    a-Create an Account\n" +
                        "    b-Show Balance\n" +
                        "    c-Deposit Money\n" +
                        "    d-Withdraw Money\n" +
                        "    e-Close Bank Account (DELETE)\n"+
                        "    f-exit"
                );
                String option = in.nextLine();
                try {

                    switch (option) {
                        case "a":
                            System.out.println("Enter Account Holder Name:");
                            String user_name = in.nextLine();
                            try(PreparedStatement preparedStatement_createAccount = myconn.prepareStatement(createAccount);){
                                preparedStatement_createAccount.setString(1,user_name);
                                int effect = preparedStatement_createAccount.executeUpdate();
                                if(effect>0){
                                    System.out.println("Account Created Sucessfully");
                                }else{
                                    System.out.println("Error in  creating an Account");
                                }
                                break;
                            }

                        case "b":
                            System.out.println("Enter Account Number:");
                            long accountNumber = in.nextLong();
                            in.nextLine();
                            try(PreparedStatement preparedStatement_fetchBalance = myconn.prepareStatement(showBalance)){
                                preparedStatement_fetchBalance.setLong(1,accountNumber);
                                ResultSet resultSet = preparedStatement_fetchBalance.executeQuery();
                                if(resultSet.next()){
                                    System.out.printf("Your Balance is: %.2f%n",resultSet.getBigDecimal("balance"));
                                }
                                else{
                                    System.out.println("Error in Fetching the Account");
                                }
                                break;
                            }

                        case "c":
                            System.out.println("Enter Account Number:");
                            long accNum = in.nextLong();
                            System.out.println("Enter amount to Deposit:");
                            BigDecimal amount = in.nextBigDecimal();
                            in.nextLine();
                            try{
                                myconn.setAutoCommit(false);
                                try(PreparedStatement preparedStatement_depositMoney = myconn.prepareStatement(depositMoney);
                                PreparedStatement preparedStatement_log = myconn.prepareStatement("insert into activity(accountnumber,action) values(?,'Credit')")) {
                                    preparedStatement_depositMoney.setBigDecimal(1, amount);
                                    preparedStatement_depositMoney.setLong(2, accNum);
                                    int rowsupdated = preparedStatement_depositMoney.executeUpdate();
                                    preparedStatement_log.setLong(1, accNum);
                                    preparedStatement_log.executeUpdate();
                                    if (rowsupdated > 0) {
                                        myconn.commit();
                                        System.out.println("Amount Deposited Successfully");
                                    } else {
                                        myconn.rollback();
                                        System.out.println("Account number not found.");
                                    }
                                }
                            } catch (Exception e) {
                                myconn.rollback();
                                throw new RuntimeException(e);
                            }
                            finally {
                                myconn.setAutoCommit(true);
                            }
                            break;

                        case "d":
                            myconn.setAutoCommit(false);
                            System.out.println("Enter Account Number");
                            long accNum1 = in.nextLong();
                            System.out.println("Enter Ammount:");
                            BigDecimal widamount = in.nextBigDecimal();
                            in.nextLine();
                            try(PreparedStatement preparedStatement_withdraw = myconn.prepareStatement(withdrawMoney);
                                PreparedStatement preparedStatement_log2 = myconn.prepareStatement("insert into activity(accountnumber,action) values(?,'Debit')")){
                                preparedStatement_withdraw.setBigDecimal(1,widamount);
                                preparedStatement_withdraw.setLong(2,accNum1);
                                preparedStatement_log2.setLong(1,accNum1);
                                int rupdated = preparedStatement_withdraw.executeUpdate();
                                if(rupdated>0){
                                    myconn.commit();
                                    System.out.println("Amount "+widamount+" Withdrawn Sucessfully go and check your balance.");
                                }
                                else {
                                    myconn.rollback();
                                    System.out.println("error in transaction of money.");
                                }
                            } catch (Exception e) {
                                myconn.rollback();
                                throw new RuntimeException(e);
                            }
                            finally {
                                myconn.setAutoCommit(true);
                            }
                            break;

                        case "e":
                            System.out.println("Enter acount number:");
                            long accNum2 = in.nextLong();
                            try(PreparedStatement preparedStatement_close = myconn.prepareStatement(delete)){
                                preparedStatement_close.setLong(1,accNum2);
                                int res =preparedStatement_close.executeUpdate();
                                if(res>0){
                                    System.out.println("Account has been deleted");
                                }
                                else{
                                    System.out.println("Error in deletion");
                                }
                                break;
                            }

                        case "f":
                            System.out.println("Exiting System. Goodbye!");
                            myconn.close();
                            System.exit(0);
                            break;
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

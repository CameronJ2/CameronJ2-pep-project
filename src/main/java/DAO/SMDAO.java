package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class SMDAO {

        public Account insertAccount(Account account){
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "insert into account (username, password) values (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());

                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }

    // Need an account getter for validation in registering new users.
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account checkValidAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ? and password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //need another accout getter that checks if the user_id is real
    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql1 = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, id);
            ResultSet rs = preparedStatement1.executeQuery();

            String sql2 = "delete from message where message_id = ?;";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();

            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
//     create table account (
//     account_id int primary key auto_increment,
//     username varchar(255) unique,
//     password varchar(255)
// );
//     create table message (
//     message_id int primary key auto_increment,
//     posted_by int,
//     message_text varchar(255),
//     time_posted_epoch bigint,
//     foreign key (posted_by) references  account(account_id)
// );

    public void updateMessage(int id, String messageText){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text=? where (message_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessagesFromUser(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

}

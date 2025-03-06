package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;


import Model.Account;
import Util.ConnectionUtil;

public class SMDAO {

        public Account insertAccount(Account account){
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here. When inserting, you only need to define the departure_city and arrival_city
                //values (two columns total!)
                String sql = "insert into account (username, password) values (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                //write preparedStatement's setString and setInt methods here.
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
}

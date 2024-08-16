package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Model.Account;
import Model.Message;

public class SocialMediaDAOImpl implements SocialMediaDAO {

    @Override
    public Account registerUser(Account newAccount) {
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();

        if (username == "" || password.length() <= 3) {
            return null;
        } else {
            try (Connection conn = Util.ConnectionUtil.getConnection()) {

                String checkUsernameExistSql = "SELECT username FROM account WHERE username = ?";

                PreparedStatement checkps = conn.prepareStatement(checkUsernameExistSql);

                checkps.setString(1, username);

                ResultSet userName = checkps.executeQuery();

                if (!userName.next()) {
                    String sql = "INSERT INTO account(username, password) VALUES(?,?)";

                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, username);
                    ps.setString(2, password);

                    ps.executeUpdate();

                    ResultSet pkeyResultSet = ps.getGeneratedKeys();

                    if (pkeyResultSet.next()) {
                        int generated_user_id = (int) pkeyResultSet.getLong(1);
                        return new Account(generated_user_id, username, password);
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return null;
        }
    }

    @Override
    public Account loginUser(Account login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }

    @Override
    public Message createMessage(Message newMessage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMessage'");
    }

    @Override
    public List<Message> getAllMessages() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessages'");
    }

    @Override
    public Message getOneMessage(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOneMessage'");
    }

    @Override
    public Message deleteMessage(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessage'");
    }

    @Override
    public Message updateMessage(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessage'");
    }

    @Override
    public List<Message> getAllMessagesFromUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessagesFromUser'");
    }

}

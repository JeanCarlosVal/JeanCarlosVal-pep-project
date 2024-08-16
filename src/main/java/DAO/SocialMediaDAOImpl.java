package DAO;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.longThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;

import Model.Account;
import Model.Message;
import kotlin.time.MeasureTimeKt;

public class SocialMediaDAOImpl implements SocialMediaDAO {

    @Override
    public Account registerUser(Account newAccount) {
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();

        if (username == "" || password.length() <= 3) {
            return null;
        } else {
            try (Connection conn = Util.ConnectionUtil.getConnection()) {

                // Checking if username already exist in database
                String checkUsernameExistSql = "SELECT username FROM account WHERE username = ?";

                PreparedStatement checkps = conn.prepareStatement(checkUsernameExistSql);

                checkps.setString(1, username);

                ResultSet userName = checkps.executeQuery();

                // If the query returns no data we proceed if not we return null
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

                conn.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return null;
        }
    }

    @Override
    public Account loginUser(Account login) {
        String username = login.getUsername();
        String password = login.getPassword();

        try (Connection conn = Util.ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM account WHERE username = ? AND password = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }

            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Message createMessage(Message newMessage) {
        String message = newMessage.getMessage_text();
        int posted_by = newMessage.getPosted_by();
        long time = newMessage.getTime_posted_epoch();

        if (message == "" || message.length() >= 255) {
            return null;
        } else {

            try (Connection conn = Util.ConnectionUtil.getConnection()) {

                String checkIdExistSql = "SELECT account_id FROM account WHERE account_id = ?";

                PreparedStatement checkps = conn.prepareStatement(checkIdExistSql);

                checkps.setInt(1, posted_by);

                ResultSet userId = checkps.executeQuery();

                if (userId.next()) {
                    String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";

                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, posted_by);
                    ps.setString(2, message);
                    ps.setLong(3, time);

                    ps.executeUpdate();

                    ResultSet pkeyResultSet = ps.getGeneratedKeys();

                    if (pkeyResultSet.next()) {
                        int generated_user_id = (int) pkeyResultSet.getLong(1);
                        return new Message(generated_user_id, posted_by, message, time);
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new LinkedList<>();

        try (Connection conn = Util.ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM message";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

            return messages;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    @Override
    public Message getOneMessage(int message_id) {
        try (Connection conn = Util.ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, message_id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Message(message_id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Message();
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

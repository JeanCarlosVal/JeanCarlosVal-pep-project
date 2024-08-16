package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

public interface SocialMediaDAO {

    // Insert statement to register a new user
    public Account registerUser(Account newAccount);

    // Select query to fetch an account
    public Account loginUser(Account login);

    // Insert statement to create a new messsage
    public Message createMessage(Message newMessage);

    // Select query to fetch all messages in the database
    public List<Message> getAllMessages();

    // Select query to fetch one message given message id
    public Message getOneMessage(int message_id);

    // Delete statement to delete a message given message id
    public Message deleteMessage(int message_id);

    // Update statement to update a message given message id
    public Message updateMessage(int message_id, Message message);

    // Select query to fetch all messages from a user given account id
    public List<Message> getAllMessagesFromUser();
}

package Service;

import java.util.List;

import DAO.SocialMediaDAOImpl;
import Model.Account;
import Model.Message;

public class SocialMediaService {

    SocialMediaDAOImpl socialMediaDAOImpl;

    public SocialMediaService(){
        socialMediaDAOImpl = new SocialMediaDAOImpl();
    }

    public SocialMediaService(SocialMediaDAOImpl socialMediaDAOImpl){
        this.socialMediaDAOImpl = socialMediaDAOImpl;
    }

    public Account registerAccount(Account account){
        Account newAccount = socialMediaDAOImpl.registerUser(account);

        return newAccount;
    }

    public Account logInUser(Account account){
        Account LoggedInUser = socialMediaDAOImpl.loginUser(account);

        return LoggedInUser;
    }

    public Message createMessage(Message message){
        Message newMessage = socialMediaDAOImpl.createMessage(message);

        return newMessage;
    }
    
    public List<Message> getAllMessages(){
        List<Message> messages = socialMediaDAOImpl.getAllMessages();

        return messages;
    }

    public Message getOneMessage(int id){
        Message message = socialMediaDAOImpl.getOneMessage(id);

        return message;
    }
}
